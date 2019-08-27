package com.softmed.htmr_facility.utils

import android.content.Context
import com.softmed.htmr_facility.dom.objects.Patient
import com.softmed.htmr_facility.dom.objects.Referral
import com.softmed.htmr_facility.dom.objects.ReferralWithNames
import kotlin.collections.ArrayList

class ReferralsSearchFilterUtil {

    var referrals : List<ReferralWithNames> = ArrayList()
    var associatedPatients : List<Patient>  = ArrayList()

    var context : Context

    constructor(mReferrals : List<ReferralWithNames>, mPatients : List<Patient>,  mContext : Context){
        this.referrals = mReferrals
        this.associatedPatients = mPatients
        this.context = mContext

    }

    fun searchbyFromDate(fromDateMilliseconds : Long) : List<ReferralWithNames> {
        val result = referrals.filter {
            it.referral.referralDate >= fromDateMilliseconds
        }
        return result
    }

    fun searchbyToDate(toDateMilliseconds : Long) : List<ReferralWithNames> {
        val result = referrals.filter {
            it.referral.referralDate <= toDateMilliseconds
        }
        return result
    }

    fun searchByPatientNames(searchQuery : String) : List<ReferralWithNames> {

        val result = referrals.filter {
            it.firstName.contains(searchQuery, ignoreCase = true) ||
                    it.middleName.contains(searchQuery, ignoreCase = true) ||
                    it.surname.contains(searchQuery, ignoreCase = true)
        }
        return result
    }


}