package com.softmed.htmr_facility.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.utils.constants;

/**
 * Created by issy on 10/8/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class IssuedReferralsActivity extends BaseActivity {

    private Toolbar toolbar;
    private CardView ctcReferralsCard, tbReferralsCard, labReferralsCard, otherReferralsCard;

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
                intent.putExtra("category", constants.OPD_SERVICE_ID); //Remove -> OPD is used as an indicator for all the other remaining services
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
    }

}
