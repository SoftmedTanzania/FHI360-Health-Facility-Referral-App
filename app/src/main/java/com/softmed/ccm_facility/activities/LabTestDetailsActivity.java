package com.softmed.ccm_facility.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.base.BaseActivity;
import com.softmed.ccm_facility.dom.objects.Patient;
import com.softmed.ccm_facility.dom.objects.Referral;
import com.softmed.ccm_facility.dom.objects.ReferralIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

import static com.softmed.ccm_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.ccm_facility.utils.constants.MALARIA_SERVICE_ID;
import static com.softmed.ccm_facility.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.ccm_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 27/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class LabTestDetailsActivity extends BaseActivity {

    RelativeLayout testResultsToggleContainer;
    TextView clientNames,clientAge, wardText, villageText, hamletText, patientGender, labTestType, waitingForResults, villageLeaderValue, referrerName;
    Toolbar toolbar;
    ToggleSwitch testResultsToggle;

    private Referral currentReferral;
    private Patient currentPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);
        setupviews();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null){
            currentReferral = (Referral) getIntent().getSerializableExtra("referal");
            if (currentReferral != null){
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED){

                    testResultsToggle.setEnabled(false);
                    testResultsToggle.setVisibility(View.VISIBLE);
                    waitingForResults.setVisibility(View.GONE);
                    if (currentReferral.getTestResults() == 1){
                        testResultsToggle.setCheckedTogglePosition(1);
                    }

                }else {
                    //Test has not yet been conducted
                    waitingForResults.setVisibility(View.VISIBLE);
                    testResultsToggle.setVisibility(View.GONE);
                }

                switch (currentReferral.getLabTest()){
                    case MALARIA_SERVICE_ID:
                        labTestType.setText("Malaria");
                        break;
                    case TB_SERVICE_ID:
                        labTestType.setText(getResources().getString(R.string.tb));
                        break;
                    case HIV_SERVICE_ID:
                        labTestType.setText(getResources().getString(R.string.hiv));
                        break;
                    default:
                        labTestType.setText(getResources().getString(R.string.unspecified_test_type));
                        break;
                }

                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "" : currentReferral.getVillageLeader());
                referrerName.setText(currentReferral.getServiceProviderUIID() == null ? "" : currentReferral.getServiceProviderUIID());
                new patientDetailsTask(baseDatabase, currentReferral.getPatient_id()).execute();
            }
        }

        findViewById(R.id.fake_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void setupviews(){

        testResultsToggle = findViewById(R.id.test_results_toggle);

        testResultsToggleContainer = findViewById(R.id.test_results_toggle_container);

        waitingForResults = findViewById(R.id.waiting_for_results);
        labTestType = findViewById(R.id.lab_test_type);

        patientGender =  findViewById(R.id.patient_gender_value);
        wardText = findViewById(R.id.client_kata_value);
        villageText =  findViewById(R.id.client_kijiji_value);
        hamletText = findViewById(R.id.client_kitongoji_value);

        clientNames = findViewById(R.id.client_name);
        clientAge = findViewById(R.id.client_age_value);

        referrerName =  findViewById(R.id.referer_name_value);
        villageLeaderValue = findViewById(R.id.mwenyekiti_name_value);

    }

    class patientDetailsTask extends AsyncTask<Void, Void, Void> {

        String patientNames, patientId;
        Patient patient;
        AppDatabase db;
        List<ReferralIndicator> indicators =  new ArrayList<>();

        patientDetailsTask(AppDatabase database, String patientID){
            this.db = database;
            this.patientId = patientID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientNames = db.patientModel().getPatientName(patientId);
            patient = db.patientModel().getPatientById(patientId);
            currentPatient = patient;

            if (currentReferral.getServiceIndicatorIds() != null){
                //..List<Long> ids = ListStringConverter.stringToSomeObjectList(currentReferral.getServiceIndicatorIds()+"");
                List<Long> ids = currentReferral.getServiceIndicatorIds();
                //Call Patient Referral Indicators
                for (int i=0; i<ids.size(); i++){
                    try {
                        ReferralIndicator referralIndicator = db.referralIndicatorDao().getReferralIndicatorById(ids.get(i));
                        indicators.add(referralIndicator);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background!"+patientNames);
            clientNames.setText(patientNames);
            if (patient != null){

                try {
                    Calendar cal = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    cal.setTimeInMillis(patient.getDateOfBirth());

                    int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                    Integer ageInt = new Integer(age);
                    clientAge.setText(ageInt.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                wardText.setText(patient.getWard() == null ? "N/A " : patient.getWard());
                villageText.setText(patient.getVillage() == null ? "N/A " : patient.getVillage());
                //hamletText.setText(patient.getHamlet() == null ? "N/A " : patient.getHamlet());

                patientGender.setText(patient.getGender());
            }

        }

    }

}
