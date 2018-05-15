package com.softmed.htmr_facility.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.LabTestedPatients;
import com.softmed.htmr_facility.activities.ReferedClientsActivity;
import com.softmed.htmr_facility.activities.ReferralListActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.AppData;
import com.softmed.htmr_facility.viewmodels.ReferralCountViewModels;

import static com.softmed.htmr_facility.utils.constants.CHW_TO_FACILITY;
import static com.softmed.htmr_facility.utils.constants.INTERFACILITY;
import static com.softmed.htmr_facility.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 20/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class LabFragment extends Fragment {

    CardView referralListCard, testedPatientsCard;
    TextView labReferralCount, labTestedClientsCount;

    AppDatabase database;

    ReferralCountViewModels referralCountViewModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(LabFragment.this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_lab, container, false);
        setupviews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Handle Class Here

        referralListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LabFragment.this.getActivity(), ReferralListActivity.class);
                intent.putExtra("service", LAB_SERVICE_ID);
                startActivity(intent);
            }
        });

        testedPatientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LabFragment.this.getActivity(), LabTestedPatients.class);
                startActivity(intent);
            }
        });

        referralCountViewModels = ViewModelProviders.of(this).get(ReferralCountViewModels.class);
        referralCountViewModels.getLabReferralCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                labReferralCount.setText(integer+" "+getResources().getString(R.string.new_referrals_unattended));
            }
        });

        referralCountViewModels.getLabTestedClients().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                labTestedClientsCount.setText(integer+" "+getResources().getString(R.string.lab_tested_patients));
            }
        });

    }

    private void setupviews(View rootView){

        referralListCard = rootView.findViewById(R.id.lab_referral_list_card);
        testedPatientsCard = rootView.findViewById(R.id.lab_tested_patients_card);

        labReferralCount = rootView.findViewById(R.id.lab_referal_count_text);
        labTestedClientsCount = rootView.findViewById(R.id.lab_tested_clients_count_text);

    }

}
