package com.softmed.ccm_facility.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.base.BaseActivity;
import com.softmed.ccm_facility.utils.constants;

/**
 * Created by issy on 10/8/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class IssuedReferralsActivity extends BaseActivity {

    public static final String SERVICE_ID = "service_id";

    private Toolbar toolbar;
    private CardView ctcReferralsCard, tbReferralsCard, labReferralsCard, otherReferralsCard;
    private TextView ctcIssuedCount, tbIssuedCount, labIssuedCount, othersIssuedCount;

    private int serviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_referrals);
        setUpViews();

        if (getIntent().getExtras() != null){
            serviceId = getIntent().getIntExtra("service_id", 0);
        }

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LiveData<Integer> ctcIssuedCountValue = baseDatabase.referalModel().getIssuedReferralCount(serviceId, session.getKeyHfid(), new int[]{constants.HIV_SERVICE_ID});
        ctcIssuedCountValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                ctcIssuedCount.setText(String.valueOf(integer));
            }
        });

        LiveData<Integer> tbIssuedCountValue = baseDatabase.referalModel().getIssuedReferralCount(serviceId, session.getKeyHfid(), new int[]{constants.TB_SERVICE_ID});
        tbIssuedCountValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tbIssuedCount.setText(String.valueOf(integer));
            }
        });

        LiveData<Integer> labIssuedCountValue = baseDatabase.referalModel().getIssuedReferralCount(serviceId, session.getKeyHfid(), new int[]{constants.LAB_SERVICE_ID});
        labIssuedCountValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                labIssuedCount.setText(String.valueOf(integer));
            }
        });

        LiveData<Integer> othersIssuedCountValue = baseDatabase.referalModel().getOtherServicesIssuedReferralCount(serviceId, session.getKeyHfid(),
                new int[]{constants.HIV_SERVICE_ID, constants.LAB_SERVICE_ID, constants.TB_SERVICE_ID, constants.OPD_SERVICE_ID});
        othersIssuedCountValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                othersIssuedCount.setText(String.valueOf(integer));
            }
        });

        ctcReferralsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedReferralsActivity.this, ReferedClientsActivity.class);
                intent.putExtra("service_id", serviceId);
                intent.putExtra("category", constants.HIV_SERVICE_ID);
                startActivity(intent);
            }
        });

        tbReferralsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedReferralsActivity.this, ReferedClientsActivity.class);
                intent.putExtra("service_id", serviceId);
                intent.putExtra("category", constants.TB_SERVICE_ID);
                startActivity(intent);
            }
        });

        labReferralsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedReferralsActivity.this, ReferedClientsActivity.class);
                intent.putExtra("service_id", serviceId);
                intent.putExtra("category", constants.LAB_SERVICE_ID);
                startActivity(intent);
            }
        });

        otherReferralsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedReferralsActivity.this, ReferedClientsActivity.class);
                intent.putExtra("service_id", serviceId);
                intent.putExtra("category", -1); //Remove -> OPD is used as an indicator for all the other remaining services
                startActivity(intent);
            }
        });

    }

    private void setUpViews(){
        toolbar = findViewById(R.id.toolbar);
        ctcReferralsCard = findViewById(R.id.issued_hiv_referrals);
        tbReferralsCard = findViewById(R.id.issued_tb_referrals);
        labReferralsCard = findViewById(R.id.issued_lab_referrals);
        otherReferralsCard = findViewById(R.id.issued_other_referrals);

        //TextView
        ctcIssuedCount = findViewById(R.id.ctc_issued_count);
        tbIssuedCount = findViewById(R.id.tb_issued_count);
        labIssuedCount = findViewById(R.id.lab_issued_count);
        othersIssuedCount = findViewById(R.id.others_issued_count);
    }

}
