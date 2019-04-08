package com.softmed.ccm_facility.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.activities.IssuedReferralsActivity;
import com.softmed.ccm_facility.activities.NewReferalsActivity;
import com.softmed.ccm_facility.activities.ReferralListActivity;
import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.viewmodels.ReferralCountViewModels;

import static com.softmed.ccm_facility.utils.constants.HIV_SERVICE_ID;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class HivFragment extends Fragment {

    private TextView referalCountText, referalFeedbackCount, chwReferralCounts, hfReferralCount;
    private CardView referalListCard, referedClientsCard, newReferalsCard;

    private Context context;

    AppDatabase database;

    ReferralCountViewModels referralCountViewModels;

    public HivFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(HivFragment.this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_hiv, container, false);

        setupviews(rootView);

        referalListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HivFragment.this.getActivity(), ReferralListActivity.class);
                intent.putExtra("service", HIV_SERVICE_ID);
                startActivity(intent);
            }
        });

        referedClientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HivFragment.this.getActivity(), IssuedReferralsActivity.class);
                intent.putExtra(IssuedReferralsActivity.SERVICE_ID, HIV_SERVICE_ID);
                startActivity(intent);
            }
        });

        newReferalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HivFragment.this.getActivity(), NewReferalsActivity.class);
                intent.putExtra("service", HIV_SERVICE_ID);
                startActivity(intent);
            }
        });

        referralCountViewModels = ViewModelProviders.of(this).get(ReferralCountViewModels.class);
        referralCountViewModels.getHivReferralCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                referalCountText.setText(integer+" "+getResources().getString(R.string.new_referrals_unattended));
            }
        });

        referralCountViewModels.getHivFeedbackReferralsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                referalFeedbackCount.setText(getResources().getString(R.string.pending_feedback)+" : "+integer);
            }
        });

        return rootView;
    }

    private void setupviews(View view) {
        referalCountText = (TextView) view.findViewById(R.id.referal_count_text);
        referalFeedbackCount = (TextView) view.findViewById(R.id.referal_feedback_count);
        referedClientsCard = (CardView) view.findViewById(R.id.refered_clients_card);
        referalListCard = (CardView) view.findViewById(R.id.referal_list_card);
        newReferalsCard = (CardView) view.findViewById(R.id.new_referals_card);

    }

}
