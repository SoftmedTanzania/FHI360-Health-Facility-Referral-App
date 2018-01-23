package apps.softmed.com.hfreferal.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Referral;

import static apps.softmed.com.hfreferal.utils.constants.HIV_SERVICE_ID;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_CHW;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_HF;
import static apps.softmed.com.hfreferal.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 11/28/17.
 */

public class ReferalListViewModel extends AndroidViewModel{

    private final LiveData<List<Referral>> referalList;
    private final LiveData<List<Referral>> unattendedReferrals;
    private final LiveData<List<Referral>> tbReferalList;
    private final LiveData<List<Referral>> referredClientsList;
    private final LiveData<List<Referral>> tbReferredClientsList;

    private final LiveData<List<Referral>> referalListHfSource;
    private final LiveData<List<Referral>> referalListChwSource;

    private final LiveData<List<Referral>> referalListHfSourceTb;
    private final LiveData<List<Referral>> referalListChwSourceTb;

    private final LiveData<List<Referral>> allReferralListFromChw;
    private final LiveData<List<Referral>> allReferralListFromHealthFacilities;

    private AppDatabase appDatabase;

    public ReferalListViewModel(Application application){
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        referalList = appDatabase.referalModel().getAllReferals(HIV_SERVICE_ID);
        unattendedReferrals = appDatabase.referalModel().getUnattendedReferals(HIV_SERVICE_ID);
        tbReferalList = appDatabase.referalModel().getTbReferralList(TB_SERVICE_ID);
        referredClientsList = appDatabase.referalModel().getReferredClients(HIV_SERVICE_ID, BaseActivity.getThisFacilityId());
        tbReferredClientsList = appDatabase.referalModel().getReferredClients(TB_SERVICE_ID, BaseActivity.getThisFacilityId());

        referalListHfSource = appDatabase.referalModel().getReferralsBySourceId(HIV_SERVICE_ID, SOURCE_HF);
        referalListChwSource = appDatabase.referalModel().getReferralsBySourceId(HIV_SERVICE_ID, SOURCE_CHW);

        referalListHfSourceTb = appDatabase.referalModel().getReferralsBySourceId(TB_SERVICE_ID, SOURCE_HF);
        referalListChwSourceTb = appDatabase.referalModel().getReferralsBySourceId(TB_SERVICE_ID, SOURCE_CHW);

        allReferralListFromChw = appDatabase.referalModel().getAllReferalsBySource(SOURCE_CHW);
        allReferralListFromHealthFacilities = appDatabase.referalModel().getAllReferalsBySource(SOURCE_HF);

    }

    public LiveData<List<Referral>> getTbReferalList() {
        return tbReferalList;
    }

    public LiveData<List<Referral>> getReferalListHfSource() {
        return referalListHfSource;
    }

    public LiveData<List<Referral>> getReferalListChwSource() {
        return referalListChwSource;
    }

    public LiveData<List<Referral>> getReferalList(){
        return referalList;
    }

    public LiveData<List<Referral>> getReferredClientsList(){
        return referredClientsList;
    }

    public LiveData<List<Referral>> getTbReferredClientsList() {
        return tbReferredClientsList;
    }

    public LiveData<List<Referral>> getAllReferralListFromChw() {
        return allReferralListFromChw;
    }

    public LiveData<List<Referral>> getAllReferralListFromHealthFacilities() {
        return allReferralListFromHealthFacilities;
    }

    public LiveData<List<Referral>> getUnattendedReferrals(){
        return unattendedReferrals;
    }

    public void deleteReferal(Referral referral){
        new deleteAsyncTask(appDatabase).execute(referral);
    }

    public LiveData<List<Referral>> getTbReferralList(){
        return tbReferalList;
    }

    public LiveData<List<Referral>> getReferalListHfSourceTb() {
        return referalListHfSourceTb;
    }

    public LiveData<List<Referral>> getReferalListChwSourceTb() {
        return referalListChwSourceTb;
    }

    private static class deleteAsyncTask extends AsyncTask<Referral, Void, Void>{

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase){
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Referral... referrals) {
            db.referalModel().deleteReferal(referrals[0]);
            return null;
        }
    }

}
