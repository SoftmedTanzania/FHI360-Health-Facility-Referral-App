package apps.softmed.com.hfreferal.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.sql.Ref;
import java.util.List;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Referal;

import static apps.softmed.com.hfreferal.utils.constants.HIV_SERVICE_ID;
import static apps.softmed.com.hfreferal.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 11/28/17.
 */

public class ReferalListViewModel extends AndroidViewModel{

    private final LiveData<List<Referal>> referalList;
    private final LiveData<List<Referal>> unattendedReferrals;
    private final LiveData<List<Referal>> tbReferalList;
    private final LiveData<List<Referal>> referredClientsList;
    private final LiveData<List<Referal>> tbReferredClientsList;

    private AppDatabase appDatabase;

    public ReferalListViewModel(Application application){
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        referalList = appDatabase.referalModel().getAllReferals(HIV_SERVICE_ID);
        unattendedReferrals = appDatabase.referalModel().getUnattendedReferals(HIV_SERVICE_ID);
        tbReferalList = appDatabase.referalModel().getTbReferralList(TB_SERVICE_ID);
        referredClientsList = appDatabase.referalModel().getHivReferredClients(HIV_SERVICE_ID, BaseActivity.getThisFacilityId());
        tbReferredClientsList = appDatabase.referalModel().getHivReferredClients(TB_SERVICE_ID, BaseActivity.getThisFacilityId());
    }

    public LiveData<List<Referal>> getReferalList(){
        return referalList;
    }

    public LiveData<List<Referal>> getReferredClientsList(){
        return referredClientsList;
    }

    public LiveData<List<Referal>> getTbReferredClientsList() {
        return tbReferredClientsList;
    }

    public LiveData<List<Referal>> getUnattendedReferrals(){
        return unattendedReferrals;
    }

    public void deleteReferal(Referal referal){
        new deleteAsyncTask(appDatabase).execute(referal);
    }

    public LiveData<List<Referal>> getTbReferralList(){
        return tbReferalList;
    }

    private static class deleteAsyncTask extends AsyncTask<Referal, Void, Void>{

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase){
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Referal... referals) {
            db.referalModel().deleteReferal(referals[0]);
            return null;
        }
    }

}
