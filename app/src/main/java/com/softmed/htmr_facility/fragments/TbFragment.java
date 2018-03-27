package com.softmed.htmr_facility.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.ReferedClientsActivity;
import com.softmed.htmr_facility.activities.TbClientListActivity;
import com.softmed.htmr_facility.activities.TbReferalListActivity;
import com.softmed.htmr_facility.activities.ClientRegisterActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.utils.constants;
import com.softmed.htmr_facility.viewmodels.ReferralCountViewModels;

import static com.softmed.htmr_facility.utils.constants.CHW_TO_FACILITY;
import static com.softmed.htmr_facility.utils.constants.INTERFACILITY;
import static com.softmed.htmr_facility.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbFragment extends Fragment {

    private CardView referalListCard, newReferalsCard, referredListCard, tbRegister;
    private TextView referalCountText, referalFeedbackCount, toolbarTitle;

    AppDatabase database;
    ReferralCountViewModels referralCountViewModels;

    public TbFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(TbFragment.this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_tb, container, false);
        setUpView(rootView);

        referalListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TbFragment.this.getActivity(), TbReferalListActivity.class));
            }
        });

        newReferalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TbFragment.this.getActivity(), TbClientListActivity.class));
            }
        });

        referredListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TbFragment.this.getActivity(), ReferedClientsActivity.class);
                intent.putExtra("service_id",  constants.TB_SERVICE_ID);
                startActivity(intent); //TODO: Implement Service Id in querying List of referred clients
            }
        });

        tbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TbFragment.this.getActivity(), ClientRegisterActivity.class);
                intent.putExtra("isTbClient", true);
                startActivity(intent);
            }
        });

        referralCountViewModels = ViewModelProviders.of(this).get(ReferralCountViewModels.class);
        referralCountViewModels.getTbReferralCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                referalCountText.setText(integer+" "+getResources().getString(R.string.new_referrals_unattended));
            }
        });

        referralCountViewModels.getTbFeedbackReferralsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                referalFeedbackCount.setText(getResources().getString(R.string.pending_feedback)+" : "+integer);
            }
        });


        return rootView;
    }

    public void setUpView(View v){
        referalListCard = (CardView) v.findViewById(R.id.tb_referal_list_card);
        newReferalsCard = (CardView) v.findViewById(R.id.tb_new_referals_card);
        referredListCard = (CardView) v.findViewById(R.id.tb_refered_clients_card);
        tbRegister = (CardView) v.findViewById(R.id.tb_register);

        referalCountText = (TextView) v.findViewById(R.id.tb_referal_count_text);
        referalFeedbackCount = (TextView) v.findViewById(R.id.tb_referal_feedback_count);

    }

    private class ReferalCountsTask extends AsyncTask<Void, Void, Void> {

        String referralCounts = "";
        String feedbackCount = "";

        @Override
        protected Void doInBackground(Void... voids) {
            referralCounts = database.referalModel().geCounttUnattendedReferalsByService(TB_SERVICE_ID)+" "+getResources().getString(R.string.new_referrals_unattended);
            feedbackCount = getResources().getString(R.string.pending_feedback)+" : "+database.referalModel().geCountPendingReferalFeedback(TB_SERVICE_ID, BaseActivity.getThisFacilityId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            referalCountText.setText(referralCounts);
            referalFeedbackCount.setText(feedbackCount);
            super.onPostExecute(aVoid);
        }

    }

}
