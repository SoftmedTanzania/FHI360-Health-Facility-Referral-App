package com.softmed.htmr_facility.services;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
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
import retrofit2.http.POST;

import static com.softmed.htmr_facility.base.BaseActivity.getPatientRequestBody;
import static com.softmed.htmr_facility.base.BaseActivity.getTbPatientRequestBody;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_APPOINTMENTS;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_REFERRAL;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.RESPONCE_SUCCESS;

public class DataSyncJob extends JobService {

    private final String TAG = DataSyncJob.class.getSimpleName();

    AppDatabase mDatabase;
    SessionManager mSession;
    Endpoints.PatientServices patientServices;
    Endpoints.ReferalService referalService;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(JobParameters job) {

        logthat("Job Started, Sync in progress");

        mDatabase = AppDatabase.getDatabase(this);
        mSession = new SessionManager(this);

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

        //Run the syncing in a different thread
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    syncDataInPostOffice();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();



        return true;
    }

    private void syncDataInPostOffice(){

        //Check if data is available in the PostOffice
        if (mDatabase.postOfficeModelDao().getUnpostedDataCount() > 0){

            /**
             * Get the list of all unposted data that exist at postOffice
             */
            List<PostOffice> unpostedData = new ArrayList<>();
            unpostedData = mDatabase.postOfficeModelDao().getUnpostedData();

            /**
             * Sort the unposted data and start syncing to maintain data integrity
             */
            List<PostOffice> patientsData = new ArrayList<>();
            List<PostOffice> tbPatientData = new ArrayList<>();
            List<PostOffice> referralData = new ArrayList<>();
            List<PostOffice> referralFeedbackData = new ArrayList<>();
            List<PostOffice> encounterData = new ArrayList<>();
            List<PostOffice> appointmentData = new ArrayList<>();

            Log.d("HomeActivity", "Size of data in postman is : "+unpostedData.size());
            logthat("Size of data in postman is : "+unpostedData.size());

            for (PostOffice data : unpostedData){
                switch (data.getPost_data_type()){
                    case POST_DATA_TYPE_PATIENT:
                        patientsData.add(data);
                        //handlePatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_TB_PATIENT:
                        tbPatientData.add(data);
                        //handleTbPatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_REFERRAL:
                        referralData.add(data);
                        //handleReferralDataSync(data);
                        break;
                    case POST_DATA_REFERRAL_FEEDBACK:
                        referralFeedbackData.add(data);
                        //handleReferralFeedbackDataSync(data);
                        break;
                    case POST_DATA_TYPE_ENCOUNTER:
                        encounterData.add(data);
                        //handleEncounterDataSync(data);
                        break;
                    case POST_DATA_TYPE_APPOINTMENTS:
                        appointmentData.add(data);
                        break;
                }
            }

            //Saving patient data
            for (PostOffice data : patientsData){
                handlePatientDataSync(data);
            }

            //Saving TbPatientData
            for (PostOffice data : tbPatientData){
                handleTbPatientDataSync(data);
            }

            //Saving referral data
            for (PostOffice data : referralData){
                handleReferralDataSync(data);
            }

            //Saving referral feefback data
            for (PostOffice data : referralFeedbackData){
                handleReferralFeedbackDataSync(data);
            }

            //Saving encounter data
            for (PostOffice data : encounterData){
                handleEncounterDataSync(data);
            }

            //Saving appointment data
            for (PostOffice data : appointmentData){
                handleAppointmentDataSync(data);
            }

        }
    }

    private void handlePatientDataSync(PostOffice data){

        logthat("Handling Patient data");
        Log.d("HomeActivity", "Patient");

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

                        new AsyncTask<Patient, Void, Void>(){
                            @Override
                            protected Void doInBackground(Patient... patients) {
                                Patient oldPatient = patients[0];
                                Patient receivedPatient = patients[1];

                                // :1: Change referrals patientIds

                                //Get all the referrals associated with the previously locally stored patient
                                List<Referral> oldPatientReferrals = new ArrayList<>();
                                oldPatientReferrals = mDatabase.referalModel().getReferalsByPatientId(oldPatient.getPatientId()).getValue();

                                //Loop through all the referrals and change the patient ID
                                if (oldPatientReferrals != null){
                                    for (int i=0; i<oldPatientReferrals.size(); i++){
                                        Referral ref = oldPatientReferrals.get(i);
                                        if (ref.getPatient_id() != rPatient.getPatientId()){
                                            ref.setPatient_id(rPatient.getPatientId());
                                            mDatabase.referalModel().addReferal(ref);
                                        }
                                    }

                                }

                                // :2: Changing all appointments associated with the old patient to the new patient

                                List<PatientAppointment> oldPatientAppointments = mDatabase.appointmentModelDao().getAppointmentsByTypeAndPatientID(2, oldPatient.getPatientId());
                                if (oldPatientAppointments != null){
                                    for (PatientAppointment appointment : oldPatientAppointments){
                                        appointment.setPatientID(rPatient.getPatientId());
                                        mDatabase.appointmentModelDao().updateAppointment(appointment);
                                    }
                                }

                                //Delete the old object
                                mDatabase.patientModel().deleteAPatient(oldPatient);

                                //Insert server's patient reference
                                mDatabase.patientModel().addPatient(receivedPatient);

                                /**
                                 * Delete the postOffice data from the database after it has been successfully posted to the server
                                 */
                                mDatabase.postOfficeModelDao().deletePostData(data);


                                // :3: Change TbPatient HealthFacilityClientIds

                                TbPatient tbPatient = mDatabase.tbPatientModelDao().getTbPatientCurrentOnTreatment(oldPatient.getPatientId());
                                if (tbPatient != null){
                                    tbPatient.setHealthFacilityPatientId(Long.valueOf(receivedPatient.getPatientId()));
                                    mDatabase.tbPatientModelDao().updateTbPatient(tbPatient);
                                }

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);

                            }
                        }.execute(patient, rPatient);

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

        Log.d("HomeActivity", "Tb Patient");
        logthat("Handling TB patient data");

        final TbPatient tbPatient = mDatabase.tbPatientModelDao().getTbPatientByTbPatientId(data.getPost_id());
        final Patient patient = mDatabase.patientModel().getPatientById(String.valueOf(tbPatient.getHealthFacilityPatientId()));
        final UserData userData = mDatabase.userDataModelDao().getUserDataByUserUIID(mSession.getUserDetails().get("uuid"));

        Call call = patientServices.postTbPatient(mSession.getServiceProviderUUID(), getTbPatientRequestBody(patient, tbPatient, userData));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                //Store Received Patient Information, TbPatient as well as PatientAppointments
                if (response != null){
                    if (response.code() == RESPONCE_SUCCESS){

                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                mDatabase.postOfficeModelDao().deletePostData(data);
                                return null;
                            }
                        }.execute();

                        //If response is success then the tpPatient has been saved successfully

                            /*
                            TbPatient receivedPatient = new TbPatient(); //Change this to received patient from the server
                            List<TbEncounters> encounters = baseDatabase.tbEncounterModelDao().getEncounterByPatientID(tbPatient.getTbPatientId());
                            for (TbEncounters encounter : encounters){
                                encounter.setTbPatientID(receivedPatient.getTbPatientId());
                                baseDatabase.tbEncounterModelDao().addEncounter(encounter);
                            }
                             */

                    }else {
                        logthat("Error updating data CODE : "+response.code());
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

        Log.d("HomeActivity", "Referral");
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
                        new AsyncTask<Referral, Void, Void>(){
                            @Override
                            protected Void doInBackground(Referral... referrals) {

                                Referral oldReferral = referrals[0];
                                Referral receivedReferral = referrals[1];

                                mDatabase.referalModel().deleteReferal(oldReferral); //Delete local referral reference
                                mDatabase.referalModel().addReferal(receivedReferral); //Store the server's referral reference
                                mDatabase.postOfficeModelDao().deletePostData(data);
                                return null;
                            }
                        }.execute(referral, rReferral);


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

        Log.d("HomeActivity", "Referral Feedback");
        logthat("Handling referral feedback data");

        final Referral referral = mDatabase.referalModel().getReferalById(data.getPost_id());
        final UserData userData = mDatabase.userDataModelDao().getUserDataByUserUIID(mSession.getUserDetails().get("uuid"));

        Call call = referalService.sendReferralFeedback(mSession.getServiceProviderUUID(), BaseActivity.getReferralFeedbackRequestBody(referral, userData));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                if (response != null){
                    if (response.body() != null){
                        if (response.code() == 200){
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    mDatabase.postOfficeModelDao().deletePostData(data);
                                    return null;
                                }
                            }.execute();
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

        Log.d("HomeActivity", "Encounter");
        logthat("Handling encounter data");

        /**
         * Get the list of all Encounters with the Id stored on postOffice
         */
        List<TbEncounters> encounter = mDatabase.tbEncounterModelDao().getEncounterByLocalId(data.getPost_id());

        /**
         * Loop through all of them to send to server
         */
        for (TbEncounters e : encounter){

            logthat("handleEncounterDataSync: Service Provider UUID : "+mSession.getServiceProviderUUID());

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
                            EncounterResponse encResponse = (EncounterResponse) response.body();

                            new AsyncTask<EncounterResponse, Void, Void>(){
                                @Override
                                protected Void doInBackground(EncounterResponse... encounterResponses) {
                                    EncounterResponse encounterResponse = encounterResponses[0];

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
                                    List<PatientAppointment> listOfAppointments = encounterResponse.getPatientAppointments();
                                    for (PatientAppointment appointment : listOfAppointments){
                                        mDatabase.appointmentModelDao().addAppointment(appointment);
                                    }

                                    return null;
                                }
                            }.execute(encResponse);

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

    private void handleAppointmentDataSync(PostOffice data){

    }

    private void logthat(String message){
        Log.d(TAG , message);
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        mDatabase = null;
        mSession = null;
        return false;
    }

}
