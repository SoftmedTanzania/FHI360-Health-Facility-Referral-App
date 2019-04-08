package com.softmed.ccm_facility.services;

import android.app.Service;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.dom.objects.PostOffice;

import java.util.List;

public class PostOfficeWatcher extends Service {

    private AppDatabase database;
    private int unpostedListSize = 0;

    private FirebaseJobDispatcher dispatcher;

    private Job syncJob;

    @Override
    public void onCreate() {
        super.onCreate();

        database = AppDatabase.getDatabase(this);

        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        syncJob = dispatcher.newJobBuilder()
                .setService(DataSyncJob.class)
                .setTag("10002")
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(0, 0)) //Immediately
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();

        /**
         * Observe the changes in postOffice table and invoke the background sync
         */
        LiveData<List<PostOffice>> unpostedData = database.postOfficeModelDao().getUnpostedLiveData();
        unpostedData.observeForever(new Observer<List<PostOffice>>() {
            @Override
            public void onChanged(@Nullable List<PostOffice> postOffices) {
                if (postOffices != null){
                    int size = postOffices.size();
                    if (size > unpostedListSize){

                        //Set the new value of unposted data count just incase the sync fail
                        unpostedListSize = size;

                        //Data has been added to postOffice
                        dispatcher.mustSchedule(syncJob);

                    }else{
                        //Data has been posted and removed
                        //Set new unposted data count
                        unpostedListSize = size;
                    }
                }
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
