package com.softmed.htmr_facility.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.softmed.htmr_facility.base.SyncAdapter;

public class SyncService extends Service {

    private static final String TAG = "SyncService";

    private static SyncAdapter sSyncAdapter = null;

    //Object to use as a threadSafeLock
    private static final Object sSyncAdapterLock = new Object();

    /**
     * Instantiate the syncadapter object on the onCreate method of this service
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: Launched the SyncService");

        /**
         * Create the sync adapter as a singleton
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock){
            if (sSyncAdapter == null)
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
        }
    }


    /**
     * Return an object that allows the system to invoke the sync adapter
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
