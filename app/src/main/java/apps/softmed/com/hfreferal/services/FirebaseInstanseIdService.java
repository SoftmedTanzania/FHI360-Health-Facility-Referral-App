package apps.softmed.com.hfreferal.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.utils.Config;
import apps.softmed.com.hfreferal.utils.ServiceGenerator;
import apps.softmed.com.hfreferal.utils.SessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by issy on 2/1/17.
 */

public class FirebaseInstanseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCMService", "Firebase Device ID : "+refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    private void sendRegistrationToServer(String token){

        SessionManager sess = new SessionManager(getApplicationContext());

        String datastream = "";
        JSONObject object   = new JSONObject();
        RequestBody body;

        try {
            object.put("userUuid", sess.getUserDetails().get("uuid")); //TODO
            object.put("googlePushNotificationToken", token);
            object.put("facilityUuid", sess.getKeyHfid());

            datastream = object.toString();

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }


        if (sess.isLoggedIn()){
            Endpoints.NotificationServices notificationServices = ServiceGenerator.createService(Endpoints.NotificationServices.class, sess.getUserName(), sess.getUserPass(), null);
            retrofit2.Call call = notificationServices.registerDevice(body);
            call.enqueue(new retrofit2.Callback() {
                @Override
                public void onResponse(retrofit2.Call call, Response response) {
                    //Log.d("", response.body().toString());
                }

                @Override
                public void onFailure(retrofit2.Call call, Throwable t) {
                    Log.d("", t.getMessage());
                }
            });
        }
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

}
