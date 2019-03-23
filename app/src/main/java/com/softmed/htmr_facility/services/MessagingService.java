package com.softmed.htmr_facility.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.HomeActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.utils.Config;
import com.softmed.htmr_facility.utils.NotificationUtils;

/**
 * Created by issy on 1/8/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = MessagingService.class.getSimpleName();
    private final int NOTIFICATION_ID = 1010;

    private NotificationUtils notificationUtils;
    private AppDatabase database;
    String dataType = "";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        database = AppDatabase.getDatabase(this);

        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData());

            try {
                Gson gson = new Gson();
                String jsonString = gson.toJson(remoteMessage.getData());

                JSONObject json = new JSONObject(jsonString);
                handleDataMessage(json);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }

    private void triggerNotification(String msg)
    {
        Notification.Builder builder =
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Htmr Facility")
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0))
                        .setContentText(msg);

        Notification notification = builder.build();
        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

    private void handleDataMessage(JSONObject json) {

        Patient patient = new Patient();
        Referral referral = new Referral();
        Gson gson = new Gson();

        try {
            JSONObject data = new JSONObject(json.toString());
            String type = data.getString("type");
            if (type.equals("PatientReferral")){
                triggerNotification(getResources().getString(R.string.patient_referral_notification));
                patient = gson.fromJson(data.getJSONObject("patientsDTO").toString(), Patient.class);
                database.patientModel().addPatient(patient);
                Log.d("handleNotification", "added a patient");
                Log.d("handleNotification", "Name : "+patient.getPatientFirstName());

                JSONArray referralsDTOS = data.getJSONArray("patientReferralsList");
                for (int i=0; i<referralsDTOS.length(); i++){
                    referral = gson.fromJson(referralsDTOS.getJSONObject(i).toString(), Referral.class);
                    database.referalModel().addReferal(referral);
                    Log.d("handleNotification", "added Patient's Referral");
                    Log.d("Added Referral ", new Gson().toJson(referral));
                }
            }else if (type.equals("PatientRegistration")){
                triggerNotification(getResources().getString(R.string.new_client_notification));
                JSONObject patientDTOS = new JSONObject(json.toString());
                patient = gson.fromJson(patientDTOS.toString(), Patient.class);
                database.patientModel().addPatient(patient);
                Log.d("handleNotification", "added Registered Patient");

            }else if (type.equals("ReferralFeedback")){
                Log.d("handleNotification", "Received Feedback");
                triggerNotification(getResources().getString(R.string.referral_feedback_notification));
                JSONObject referralDTOS = new JSONObject(json.toString());
                referral = gson.fromJson(referralDTOS.toString(), Referral.class);
                database.referalModel().addReferal(referral);
                Log.d("handleNotification", "added Referral Feedback");
            }else if (type.equals("PatientUpdate")){
                //Received a patient updated data
                triggerNotification("Patient Data Updated");
                JSONObject patientDTOS = new JSONObject(json.toString());
                patient = gson.fromJson(patientDTOS.toString(), Patient.class);
                database.patientModel().addPatient(patient);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e(TAG, "push json: " + json.toString());

        String msg = "Umepokea Data";

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", msg);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            //play notification sound
            //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            //notificationUtils.playNotificationSound();
        } else {
            Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            resultIntent.putExtra("message", "");

            showNotificationMessage(getApplicationContext(), "HTMR", msg, null, resultIntent);

        }

    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private void parseString(String data){
        String string = data.replace("=",":\"");
    }

}
