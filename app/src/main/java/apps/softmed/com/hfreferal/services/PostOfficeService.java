package apps.softmed.com.hfreferal.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.List;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;

/**
 * Created by issy on 1/6/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class PostOfficeService extends IntentService {

    AppDatabase database;

    public PostOfficeService() {
        super("PostOfficeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        database = AppDatabase.getDatabase(this.getApplicationContext());

        //Check if data is available in the PostOffice
        if (database.postOfficeModelDao().getUnpostedDataCount() > 0){
            List<PostOffice> unpostedData = database.postOfficeModelDao().getUnpostedData();
            for (int i=0; i<unpostedData.size(); i++){

                PostOffice data = unpostedData.get(i);
                Log.d("PostOfficeService", data.getPost_data_type());

            }

        }


        // Release the wake lock provided by the WakefulBroadcastReceiver.
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

}
