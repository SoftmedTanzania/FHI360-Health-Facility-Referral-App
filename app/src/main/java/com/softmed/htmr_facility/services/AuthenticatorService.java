package com.softmed.htmr_facility.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.softmed.htmr_facility.base.Authenticator;

public class AuthenticatorService extends Service{

    private final String TAG = "AuthenticatorService";

    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
        Log.d(TAG, "onCreate: Launched the authenticator service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
