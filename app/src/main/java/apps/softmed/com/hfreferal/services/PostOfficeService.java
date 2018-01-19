package apps.softmed.com.hfreferal.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.AppData;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PatientAppointment;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.objects.TbEncounters;
import apps.softmed.com.hfreferal.dom.objects.TbPatient;
import apps.softmed.com.hfreferal.dom.objects.UserData;
import apps.softmed.com.hfreferal.dom.responces.PatientResponce;
import apps.softmed.com.hfreferal.utils.ServiceGenerator;
import apps.softmed.com.hfreferal.utils.SessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_PATIENT;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_REFERRAL;

/**
 * Created by issy on 1/6/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class PostOfficeService extends IntentService {

    AppDatabase database;
    SessionManager sess;
    Endpoints.PatientServices patientServices;
    Endpoints.ReferalService referalService;

    public PostOfficeService() {
        super("PostOfficeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        database = AppDatabase.getDatabase(this.getApplicationContext());
        sess = new SessionManager(this.getApplicationContext());

        patientServices = ServiceGenerator.createService(Endpoints.PatientServices.class,
                sess.getUserName(),
                sess.getUserPass(),
                sess.getKeyHfid());

        referalService = ServiceGenerator.createService(Endpoints.ReferalService.class,
                sess.getUserName(),
                sess.getUserPass(),
                sess.getKeyHfid());

        //Check if data is available in the PostOffice
        if (database.postOfficeModelDao().getUnpostedDataCount() > 0) {
            List<PostOffice> unpostedData = database.postOfficeModelDao().getUnpostedData();
            for (int i = 0; i < unpostedData.size(); i++) {

                final PostOffice data = unpostedData.get(i);
                Log.d("PostOfficeService", data.getPost_data_type());

                if (data.getPost_data_type().equals(POST_DATA_TYPE_PATIENT)) {

                    final Patient patient = database.patientModel().getPatientById(data.getPost_id());
                    final TbPatient tbPatient = database.tbPatientModelDao().getTbPatientById(patient.getPatientId());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(sess.getUserDetails().get("uuid"));

                    Call call = patientServices.postPatient(BaseActivity.getPatientRequestBody(patient, tbPatient, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            PatientResponce patientResponce = (PatientResponce) response.body();
                            //Store Received Patient Information, TbPatient as well as PatientAppointments
                            if (response.body() != null) {
                                Log.d("patient_response", response.body().toString());

                                Patient patient1 = patientResponce.getPatient();
                                TbPatient tbPatient1 = patientResponce.getTbPatient();
                                List<PatientAppointment> appointments = patientResponce.getPatientAppointments();

                                //Delete local patient reference
                                database.patientModel().deleteAPatient(patient);
                                database.tbPatientModelDao().deleteAPatient(tbPatient);
                                List<PatientAppointment> oldAppointments = database.appointmentModelDao().getThisPatientAppointments(patient.getPatientId());
                                for (int i = 0; i < oldAppointments.size(); i++) {
                                    database.appointmentModelDao().deleteAppointment(oldAppointments.get(i));
                                }

                                //Insert server's patient reference
                                database.patientModel().addPatient(patient1);
                                database.tbPatientModelDao().addPatient(tbPatient1);
                                for (int j = 0; j < appointments.size(); j++) {
                                    database.appointmentModelDao().addAppointment(appointments.get(j));
                                }

                            } else {
                                Log.d("patient_response", "Patient Responce is null " + response.body());
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("patient_response", t.getMessage());
                        }
                    });
                } else if (data.getPost_data_type().equals(POST_DATA_TYPE_REFERRAL)) {

                    final Referral referral = database.referalModel().getReferalById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(sess.getUserDetails().get("uuid"));

                    Call call = referalService.postReferral(BaseActivity.getReferralRequestBody(referral, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Referral receivedReferral = (Referral) response.body();
                            if (response.body() != null) {

                                Log.d("PostReferral", response.body().toString());
                                database.referalModel().deleteReferal(referral); //Delete local referral reference
                                database.referalModel().addReferal(receivedReferral); //Store the server's referral reference

                            } else {
                                Log.d("PostReferral", "Responce is Null : " + response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("PostReferral", t.getMessage());
                        }
                    });

                } else if (data.getPost_data_type().equals(POST_DATA_REFERRAL_FEEDBACK)) {

                    final Referral referral = database.referalModel().getReferalById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(sess.getUserDetails().get("uuid"));

                    Call call = referalService.sendReferralFeedback(BaseActivity.getReferralFeedbackRequestBody(referral, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.d("POST_RESPOMCES", "Saved to seerver : " + response.body());
                            //database.postOfficeModelDao().deletePostData(data);
                            if (response.code() == 200) {
                                new BaseActivity.DeletePostData(database).execute(data);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });

                } else if (data.getPost_data_type().equals(POST_DATA_TYPE_ENCOUNTER)) {
                    //TbEncounters encounter = database.tbEncounterModelDao().getEncounterByPatientID(data.getPost_id());
                }
            }

        }


        // Release the wake lock provided by the WakefulBroadcastReceiver.
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }


}
