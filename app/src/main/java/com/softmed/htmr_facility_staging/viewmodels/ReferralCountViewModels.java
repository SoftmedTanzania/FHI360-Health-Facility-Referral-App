package com.softmed.htmr_facility_staging.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.softmed.htmr_facility_staging.base.AppDatabase;
import com.softmed.htmr_facility_staging.base.BaseActivity;

import static com.softmed.htmr_facility_staging.utils.constants.CHW_TO_FACILITY;
import static com.softmed.htmr_facility_staging.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility_staging.utils.constants.INTERFACILITY;
import static com.softmed.htmr_facility_staging.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility_staging.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility_staging.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility_staging.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 28/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferralCountViewModels extends AndroidViewModel {

    //OPD
    private final LiveData<Integer> referralCount;
    private final LiveData<Integer> opdChwReferralsCount;
    private final LiveData<Integer> opdFacilityReferralsCount;
    private final LiveData<Integer> opdFeedbackReferralsCount;

    //HIV
    private final LiveData<Integer> hivReferralCount;
    private final LiveData<Integer> hivFeedbackReferralsCount;

    //TB
    private final LiveData<Integer> tbReferralCount;
    private final LiveData<Integer> tbFeedbackReferralsCount;

    //LAB
    private final LiveData<Integer> labReferralCount;
    private final LiveData<Integer> labTestedClients;

    AppDatabase database;

    public ReferralCountViewModels(Application application){
        super(application);
        database = AppDatabase.getDatabase(this.getApplication());

        //OPD
        referralCount = database.referalModel().getLiveOPDReferralsCount(BaseActivity.getThisFacilityId());
        opdChwReferralsCount = database.referalModel().getLiveCountReferralsBySource(new int[] {CHW_TO_FACILITY}, BaseActivity.getThisFacilityId());
        opdFacilityReferralsCount = database.referalModel().getLiveCountReferralsBySource(new int[] {INTERFACILITY}, BaseActivity.getThisFacilityId());
        opdFeedbackReferralsCount = database.referalModel().getLiveCountPendingReferalFeedback(OPD_SERVICE_ID, BaseActivity.session.getKeyHfid());

        //HIV
        hivReferralCount = database.referalModel().getLiveCountUnattendedReferalsByService(HIV_SERVICE_ID, new int[] {INTRAFACILITY});
        hivFeedbackReferralsCount = database.referalModel().getLiveCountPendingReferalFeedback(HIV_SERVICE_ID, BaseActivity.session.getKeyHfid());

        //TB
        tbReferralCount = database.referalModel().getLiveCountUnattendedReferalsByService(TB_SERVICE_ID, new int[] {INTRAFACILITY}); //Removed [INTERFACILITY] since all referrals land on OPD and then to the subsequent clinics
        tbFeedbackReferralsCount = database.referalModel().getLiveCountPendingReferalFeedback(TB_SERVICE_ID, BaseActivity.session.getKeyHfid());

        //LAB
        labReferralCount = database.referalModel().getLiveCountUnattendedReferalsByService(LAB_SERVICE_ID, new int[] {INTRAFACILITY});
        labTestedClients = database.referalModel().getLiveCountTestedClients(LAB_SERVICE_ID, BaseActivity.session.getKeyHfid());

    }

    public LiveData<Integer> getOpdChwReferralsCount() {
        return opdChwReferralsCount;
    }

    public LiveData<Integer> getOpdFacilityReferralsCount() {
        return opdFacilityReferralsCount;
    }

    public LiveData<Integer> getOpdFeedbackReferralsCount() {
        return opdFeedbackReferralsCount;
    }

    public LiveData<Integer> getReferralCount() {
        return referralCount;
    }

    public LiveData<Integer> getHivReferralCount() {
        return hivReferralCount;
    }

    public LiveData<Integer> getHivFeedbackReferralsCount() {
        return hivFeedbackReferralsCount;
    }

    public LiveData<Integer> getTbReferralCount() {
        return tbReferralCount;
    }

    public LiveData<Integer> getTbFeedbackReferralsCount() {
        return tbFeedbackReferralsCount;
    }

    public LiveData<Integer> getLabReferralCount() {
        return labReferralCount;
    }

    public LiveData<Integer> getLabTestedClients() {
        return labTestedClients;
    }
}
