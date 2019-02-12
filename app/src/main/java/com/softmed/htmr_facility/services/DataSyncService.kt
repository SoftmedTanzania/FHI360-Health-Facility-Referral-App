package com.softmed.htmr_facility.services

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.softmed.htmr_facility.api.Endpoints
import com.softmed.htmr_facility.base.AppDatabase
import com.softmed.htmr_facility.dom.objects.PostOffice
import com.softmed.htmr_facility.utils.ServiceGenerator
import com.softmed.htmr_facility.utils.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SyncRabbit {

    companion object {

        var database: AppDatabase? = null
        var session: SessionManager? = null
        var patientServices : Endpoints.PatientServices? = null
        var referalService : Endpoints.ReferalService? = null

        fun getInstance(mDatabase: AppDatabase, mSession: SessionManager) : SyncRabbit {
            database = mDatabase
            session = mSession

            patientServices = ServiceGenerator.createService(Endpoints.PatientServices::class.java,
                    session?.userName,
                    session?.userPass,
                    session?.keyHfid)

            referalService = ServiceGenerator.createService(Endpoints.ReferalService::class.java,
                    session?.userName,
                    session?.userPass,
                    session?.keyHfid)

            return SyncRabbit()
        }
    }


    fun getUnsynced() : Int {

        var checked = false
        var count = 0

        GlobalScope.launch {
            checked = true
            count = getUnsyncedDataCount()
        }

        while (!checked){
            //Wait here
        }

        return count

    }

     fun checkUnsyncedData() {

       GlobalScope.launch {

           var count = 0
           count = getUnsyncedDataCount()

           Log.d("coroutines_job", "Unsynced Data count is : "+count)

           /*if (database!!.postOfficeModelDao().unpostedDataCount > 0){
               //Post office has data thats not posted

               //1. Get the list of unposted data
               var unpostedData : List<PostOffice> = ArrayList()
               unpostedData = database!!.postOfficeModelDao().unpostedData

               //Loop through the unposted data to arrange the data so as to maintain data integrity
               var patientData : List<PostOffice> = ArrayList()
               var tbPatientData : List<PostOffice> = ArrayList()
               var referralData : List<PostOffice> = ArrayList()
               var referralFeedbackData : List<PostOffice> = ArrayList()
               var encounterData : List<PostOffice> = ArrayList()
               var appointmentData : List<PostOffice> = ArrayList()

               Log.d("coroutines_job", "Size of unposted data "+unpostedData.size)

           } */
       }
    }

    suspend fun getUnsyncedDataCount() : Int {
        return database!!.postOfficeModelDao().unpostedDataCount
    }

}