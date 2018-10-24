package com.softmed.htmr_facility.base;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.softmed.htmr_facility.activities.HomeActivity;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.softmed.htmr_facility.dom.objects.UserData;
import com.softmed.htmr_facility.dom.responces.EncounterResponse;
import com.softmed.htmr_facility.utils.ServiceGenerator;
import com.softmed.htmr_facility.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.base.BaseActivity.getPatientRequestBody;
import static com.softmed.htmr_facility.base.BaseActivity.getTbPatientRequestBody;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_REFERRAL;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.RESPONCE_SUCCESS;

public class SyncAdapter extends AbstractThreadedSyncAdapter{

    ContentResolver mContentResolver;
    AppDatabase mDatabase;
    SessionManager mSession;
    Endpoints.PatientServices patientServices;
    Endpoints.ReferalService referalService;

    public SyncAdapter(Context context, boolean autoInitialize){
        super(context, autoInitialize);

        logthat("Sync Adapter started!");

        mContentResolver = context.getContentResolver();
        mDatabase = AppDatabase.getDatabase(context);
        mSession = new SessionManager(context);

        //Initialize patient api call services
        patientServices = ServiceGenerator.createService(Endpoints.PatientServices.class,
                mSession.getUserName(),
                mSession.getUserPass(),
                mSession.getKeyHfid());

        //Initialize referral api call services
        referalService = ServiceGenerator.createService(Endpoints.ReferalService.class,
                mSession.getUserName(),
                mSession.getUserPass(),
                mSession.getKeyHfid());

    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSync){
        super(context, autoInitialize, allowParallelSync);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(
            Account account,
            Bundle bundle,
            String s,
            ContentProviderClient contentProviderClient,
            SyncResult syncResult) {
        /**
         * Data transfer code goes here
         */
        syncDataInPostOffice();

    }

    private void syncDataInPostOffice(){

        //Check if data is available in the PostOffice
        if (mDatabase.postOfficeModelDao().getUnpostedDataCount() > 0){

            /**
             * Get the list of all unposted data that exist at postOffice
             */
            List<PostOffice> unpostedData = new ArrayList<>();
            unpostedData = mDatabase.postOfficeModelDao().getUnpostedData();

            logthat("Size of data in postman is : "+unpostedData.size());

            for (PostOffice data : unpostedData){
                switch (data.getPost_data_type()){
                    case POST_DATA_TYPE_PATIENT:
                        handlePatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_TB_PATIENT:
                        handleTbPatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_REFERRAL:
                        handleReferralDataSync(data);
                        break;
                    case POST_DATA_REFERRAL_FEEDBACK:
                        handleReferralFeedbackDataSync(data);
                        break;
                    case POST_DATA_TYPE_ENCOUNTER:
                        handleEncounterDataSync(data);
                        break;

                }
            }
        }
    }

    private void handlePatientDataSync(PostOffice data){

        logthat("Handling Patient data");

        //Get the patient that needs to be synced
        final Patient patient = mDatabase.patientModel().getPatientById(data.getPost_id());

        //Get UserData for the currently logged in user
        final UserData userData = mDatabase.userDataModelDao().getUserDataByUserUIID(mSession.getUserDetails().get("uuid"));

        //Make a server call to send the current patient to the server
        Call call = patientServices.postPatient(mSession.getServiceProviderUUID(), getPatientRequestBody(patient, userData.getUserFacilityId()));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {

                /**
                 * Check to see if response is not null and the server has returned something
                 */
                if (response != null){
                    if (response.body() != null){
                        //Get the patient object from the response
                        Patient rPatient = (Patient) response.body();

                        //Get all the referrals associated with the previously locally stored patient
                        List<Referral> oldPatientReferrals = new ArrayList<>();
                        oldPatientReferrals = mDatabase.referalModel().getReferalsByPatientId(patient.getPatientId()).getValue();

                        //Loop through all the referrals and change the patient ID
                        if (oldPatientReferrals != null){
                            for (int i=0; i<oldPatientReferrals.size(); i++){
                                Referral ref = oldPatientReferrals.get(i);
                                if (ref.getPatient_id() != rPatient.getPatientId()){
                                    ref.setPatient_id(rPatient.getPatientId());
                                }
                            }

                        }

                        //Delete the old object
                        mDatabase.patientModel().deleteAPatient(patient);

                        //Insert server's patient reference
                        mDatabase.patientModel().addPatient(rPatient);

                        /**
                         * Delete the postOffice data from the database after it has been successfully posted to the server
                         */
                        mDatabase.postOfficeModelDao().deletePostData(data);

                    }else {
                        logthat("The body of the response from server is null : Code = "+response.code());
                    }
                }else{
                    //Server returned null
                    logthat("Server has returned Null");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("patient_response", t.getMessage());
            }
        });
    }

    private void handleTbPatientDataSync(PostOffice data){

        logthat("Handling TB patient data");

        final Patient patient = mDatabase.patientModel().getPatientById(data.getPost_id());
        final TbPatient tbPatient = mDatabase.tbPatientModelDao().getTbPatientById(patient.getPatientId());
        final UserData userData = mDatabase.userDataModelDao().getUserDataByUserUIID(mSession.getUserDetails().get("uuid"));

        Call call = patientServices.postTbPatient(mSession.getServiceProviderUUID(), getTbPatientRequestBody(patient, tbPatient, userData));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                //Store Received Patient Information, TbPatient as well as PatientAppointments
                if (response != null){
                    if (response.body() != null){
                        if (response.code() == RESPONCE_SUCCESS){

                            //If response is success then the tpPatient has been saved successfully

                            mDatabase.postOfficeModelDao().deletePostData(data);

                        }else {
                            logthat("Error updating data CODE : "+response.code());
                        }
                    }
                }else {
                    logthat("Server returned Null");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("patient_response", "Error : "+t.getStackTrace());
            }
        });
    }

    private void handleReferralDataSync(PostOffice data){

        logthat("Hangling referral data");

        final Referral referral = mDatabase.referalModel().getReferalById(data.getPost_id());

        Call call = referalService.postReferral(mSession.getServiceProviderUUID(), BaseActivity.getReferralRequestBody(referral));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                if (response != null){
                    if (response.body() != null) {

                        Referral rReferral = (Referral) response.body();
                        mDatabase.referalModel().deleteReferal(referral); //Delete local referral reference
                        mDatabase.referalModel().addReferal(rReferral); //Store the server's referral reference
                        mDatabase.postOfficeModelDao().deletePostData(data);

                    } else {
                        logthat("Server returned an error "+response.code());
                    }
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                logthat("Sync Failed : "+t.getMessage());
            }
        });
    }

    private void handleReferralFeedbackDataSync(PostOffice data){

        logthat("Handling referral feedback data");

        final Referral referral = mDatabase.referalModel().getReferalById(data.getPost_id());
        final UserData userData = mDatabase.userDataModelDao().getUserDataByUserUIID(mSession.getUserDetails().get("uuid"));

        Call call = referalService.sendReferralFeedback(mSession.getServiceProviderUUID(), BaseActivity.getReferralFeedbackRequestBody(referral, userData));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response != null){
                    if (response.body() != null){
                        if (response.code() == 200){
                            mDatabase.postOfficeModelDao().deletePostData(data);
                        }else {
                            logthat("Referral feedback");
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                logthat("Syncing data failed with message : "+t.getMessage());
            }
        });
    }

    private void handleEncounterDataSync(PostOffice data){

        logthat("Handling encounter data");

        /**
         * Get the list of all Encounters with the Id stored on postOffice
         */
        List<TbEncounters> encounter = mDatabase.tbEncounterModelDao().getEncounterByLocalId(data.getPost_id());

        /**
         * Loop through all of them to send to server
         */
        for (TbEncounters e : encounter){

            Call call = patientServices.postEncounter(mSession.getServiceProviderUUID(), BaseActivity.getTbEncounterRequestBody(e));
            call.enqueue(new Callback() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onResponse(Call call, Response response) {
                    /*
                    Receive an Encounter object as a response
                    Replace the local encounter with the response encounter
                    */
                    if (response != null){
                        if (response.body() != null) {

                            logthat("Posting encounter response : "+response.body());

                            EncounterResponse encounterResponse = (EncounterResponse) response.body();
                            List<PatientAppointment> listOfAppointments = encounterResponse.getPatientAppointments();

                            TbEncounters receivedEncounter =  encounterResponse.getEncounter();
                            mDatabase.tbEncounterModelDao().deleteAnEncounter(e);
                            mDatabase.tbEncounterModelDao().addEncounter(receivedEncounter);
                            mDatabase.postOfficeModelDao().deletePostData(data);

                            TbPatient tbPatient = mDatabase.tbPatientModelDao().getTbPatientByTbPatientId(receivedEncounter.getTbPatientID()+"");

                            //Delete all appointments associated with the locally stored patient
                            List<PatientAppointment> appointments = mDatabase.appointmentModelDao().getAppointmentsByTypeAndPatientID(2, tbPatient.getHealthFacilityPatientId()+"");
                            for (PatientAppointment a : appointments){
                                mDatabase.appointmentModelDao().deleteAppointment(a);
                            }

                            //Insert the new appointment of patient received from the server
                            for (PatientAppointment appointment : listOfAppointments){
                                mDatabase.appointmentModelDao().addAppointment(appointment);
                            }

                        }else {
                            logthat("Server returned response null : "+response.code());
                        }
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();

                }
            });

        }
    }

    private void logthat(String message){
        Log.d("SYNCDATA" , message);
    }

}
