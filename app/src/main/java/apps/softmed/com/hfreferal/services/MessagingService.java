package apps.softmed.com.hfreferal.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Map;

import apps.softmed.com.hfreferal.activities.HomeActivity;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.responces.ReferalResponce;
import apps.softmed.com.hfreferal.utils.Config;
import apps.softmed.com.hfreferal.utils.NotificationUtils;

/**
 * Created by issy on 1/8/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = MessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private AppDatabase database;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        database = AppDatabase.getDatabase(this);

        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            /*
            TODO
            Broadcast is sent to all listening activities that a notification has been received
            use this broadcast to listen and update the notifications list in main activity

            // app is in foreground, broadcast the push message
            */
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            //notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        Patient patient = new Patient();
        Referral referral = new Referral();

        try {

            JSONObject data = new JSONObject(json.toString());

            JSONObject patientDTOS = data.getJSONObject("patientsDTO");

            String patientFirstName = patientDTOS.getString("firstName");
            String patientPhoneNumber = patientDTOS.getString("phoneNumber");
            String patientGender = patientDTOS.getString("gender");
            String patientSurname = patientDTOS.getString("surname");
            String patientMiddleName = patientDTOS.getString("middleName");
            int patientId = patientDTOS.getInt("patientId");
            long patientDateOfBirth = patientDTOS.getLong("dateOfBirth");
            long patientDateOfDeath = patientDTOS.getLong("dateOfDeath");
            boolean patientHivStatus = patientDTOS.getBoolean("hivStatus");

            patient.setPatientFirstName(patientFirstName);
            patient.setPatientMiddleName(patientMiddleName);
            patient.setPatientSurname(patientSurname);
            patient.setPhone_number(patientPhoneNumber);
            patient.setGender(patientGender);
            patient.setPatientId(patientId+"");
            patient.setDateOfBirth(patientDateOfBirth);
            patient.setDateOfDeath(patientDateOfDeath);
            patient.setHivStatus(patientHivStatus);

            Log.d("FinalTAg", "Patient : "+patient.getPatientId());
            database.patientModel().addPatient(patient);

            JSONArray referralsDTOS = data.getJSONArray("referralsDTOS");
            for (int i=0; i<referralsDTOS.length(); i++){
                    JSONObject referralObject = referralsDTOS.getJSONObject(i);

                    referral.setReferralReason(referralObject.getString("referralReason"));
                    referral.setFacilityId(referralObject.getString("facilityId"));
                    referral.setServiceProviderUIID(referralObject.getString("serviceProviderUIID"));
                    referral.setServiceProviderGroup(referralObject.getString("serviceProviderGroup"));
                    referral.setCommunityBasedHivService(referralObject.getString("communityBasedHivService"));
                    referral.setVillageLeader(referralObject.getString("villageLeader"));
                    referral.setCtcNumber(referralObject.getString("ctcNumber"));

                    referral.setTestResults(referralObject.getBoolean("testResults"));
                    referral.setHasSevereSweating(referralObject.getBoolean("hasSevereSweating"));
                    referral.setHasFever(referralObject.getBoolean("hasFever"));
                    referral.setHadWeightLoss(referralObject.getBoolean("hadWeightLoss"));
                    referral.setHasBloodCough(referralObject.getBoolean("hasBloodCough"));
                    referral.setHas2WeeksCough(referralObject.getBoolean("has2WeeksCough"));

                    referral.setReferralStatus(referralObject.getInt("referralStatus"));
                    referral.setPatient_id(referralObject.getInt("patientId")+"");
                    referral.setFacilityId(referralObject.getInt("fromFacilityId")+"");
                    referral.setReferral_id(referralObject.getInt("referralId")+"");
                    referral.setReferralSource(referralObject.getInt("referralSource"));
                    referral.setServiceId(referralObject.getInt("serviceId"));
                    referral.setReferralDate(referralObject.getLong("referralDate"));

                    Log.d("FinalTAg", "Referral : "+referral.getReferralReason());

                    database.referalModel().addReferal(referral);

                }

            }catch (Exception e){
                e.printStackTrace();
            }

            String msg = "Data Received";

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", msg);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                //notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                resultIntent.putExtra("message", "");

                showNotificationMessage(getApplicationContext(), "HTMR", msg, null, resultIntent);

                // check for image attachment
                /*if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }*/

                // play notification sound
                //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                //notificationUtils.playNotificationSound();

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

}
