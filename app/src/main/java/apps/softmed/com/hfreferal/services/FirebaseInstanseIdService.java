package apps.softmed.com.hfreferal.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

    private final String TAG = FirebaseInstanseIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCMService", "Firebase Device ID : "+refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }


//    class RegisterDeviceOnceLoggedIn extends AsyncTask<String, Void, Void>{
//
//    }

}
