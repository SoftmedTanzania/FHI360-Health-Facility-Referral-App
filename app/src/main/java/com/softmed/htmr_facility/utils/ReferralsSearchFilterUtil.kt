package com.softmed.htmr_facility.utils

import android.content.Context
import android.util.Log
import com.softmed.htmr_facility.base.AppDatabase
import com.softmed.htmr_facility.dom.objects.Patient
import com.softmed.htmr_facility.dom.objects.Referral
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class ReferralsSearchFilterUtil {

    var referrals : List<Referral> = ArrayList()
    var associatedPatients : List<Patient>  = ArrayList()

    var context : Context

    constructor(mReferrals : List<Referral>, mContext : Context){
        this.referrals = mReferrals
        this.context = mContext
    }

    fun searchbyFromDate(fromDateMilliseconds : Long) : List<Referral> {
        val result = referrals.filter {
            it.referralDate >= fromDateMilliseconds
        }
        return result
    }

    fun searchbyToDate(toDateMilliseconds : Long) : List<Referral> {
        val result = referrals.filter {
            it.referralDate <= toDateMilliseconds
        }
        return result
    }

    fun searchByPatientNames(searchQuery : String) : List<Referral> {

        Log.d("blown", "Searching -> "+searchQuery)

        var result : ArrayList<Referral> = ArrayList()

        Log.d("blown", "associated Patients size-> "+associatedPatients.size)

        val patientNameSearchResult = associatedPatients.filter {
            it.patientFirstName.contains(searchQuery, ignoreCase = true) ||
                    it.patientMiddleName.contains(searchQuery, ignoreCase = true) ||
                    it.patientSurname.contains(searchQuery, ignoreCase = true)
        }

        Log.d("blown", "Name search Result -> "+patientNameSearchResult.size)

        val idList : ArrayList<String> = ArrayList()

        for (patient : Patient in patientNameSearchResult){
            idList.add(patient.patientId)

            val res = referrals.filter { referral ->
                referral.patient_id == patient.patientId
            }

            for (r : Referral in res){
                result.add(r)
            }
        }

        Log.d("blown", "Referrals found size is -> "+result.size)

        return result

    }

    fun getEquivalentPatients() {

        val patientsObservable : Observable<List<Patient>> = Observable.create {

            val patientsList : ArrayList<Patient> = ArrayList()

            for (referral : Referral in referrals){
                val patient = getPatientFromDb(referral.patient_id)
                patientsList.add(patient)
            }

            it.onNext(patientsList)
        }

        patientsObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

        val patientObserver : Observer<List<Patient>> = object : Observer<List<Patient>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: List<Patient>) {
                Log.d("blown", "Got patientes : "+t.size)
                associatedPatients = t
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

        }

        patientsObservable.subscribe(patientObserver)

    }

    fun getPatientFromDb (patientId : String) : Patient {
        val database : AppDatabase = AppDatabase.getDatabase(context)
        val patient = database.patientModel().getPatientById(patientId)
        return patient
    }


}