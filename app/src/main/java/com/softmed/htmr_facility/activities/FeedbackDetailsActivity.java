package com.softmed.htmr_facility.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility.fragments.IssueReferralDialogueFragment;
import com.softmed.htmr_facility.utils.ListStringConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.MALARIA_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 1/6/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class FeedbackDetailsActivity extends BaseActivity {

    Toolbar toolbar;
    TextView ctcNumber, referalReasons, villageLeaderValue, referrerName;
    EditText servicesOfferedEt, otherInformationEt;
    CheckBox hivStatus;
    TextView clientNames,clientAge, wardText, villageText, hamletText, patientGender, labTestType, waitingForResults;
    Button referralButton, cancelButton;
    RecyclerView indicatorsRecyclerView;
    ToggleSwitch testResultsToggle;
    LinearLayout testTypeContainer, serviceGivenContainer;
    RelativeLayout testInputContainer;

    private Referral currentReferral;
    private Patient currentPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_feedback);
        setupviews();

        if (getIntent().getExtras() != null){
            currentReferral = (Referral) getIntent().getSerializableExtra("referal");
            if (currentReferral != null){
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED){

                    servicesOfferedEt.setEnabled(false);
                    servicesOfferedEt.setText(currentReferral.getServiceGivenToPatient());

                    otherInformationEt.setText(currentReferral.getOtherNotesAndAdvices());
                    otherInformationEt.setEnabled(false);

                    testResultsToggle.setEnabled(false);
                    testResultsToggle.setVisibility(View.VISIBLE);
                    waitingForResults.setVisibility(View.GONE);
                    if (currentReferral.getTestResults() == 1){
                        testResultsToggle.setCheckedTogglePosition(1);
                    }
                    referralButton.setVisibility(View.VISIBLE);

                }else {
                    //Test has not yet been conducted
                    waitingForResults.setVisibility(View.VISIBLE);
                    testResultsToggle.setVisibility(View.GONE);
                    referralButton.setVisibility(View.GONE);
                }

                if (currentReferral.getServiceId() == LAB_SERVICE_ID){

                    testTypeContainer.setVisibility(View.VISIBLE);
                    testInputContainer.setVisibility(View.VISIBLE);
                    serviceGivenContainer.setVisibility(View.GONE);

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
                }else {
                    testTypeContainer.setVisibility(View.GONE);
                    testInputContainer.setVisibility(View.GONE);
                    serviceGivenContainer.setVisibility(View.VISIBLE);
                }

                referalReasons.setText(currentReferral.getReferralReason() == null ? "" : currentReferral.getReferralReason());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "" : currentReferral.getVillageLeader());
                referrerName.setText(currentReferral.getServiceProviderUIID() == null ? "" : currentReferral.getServiceProviderUIID());
                new FeedbackDetailsActivity.patientDetailsTask(baseDatabase, currentReferral.getPatient_id()).execute();
            }
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        referralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = FeedbackDetailsActivity.this;
                AlertView alert = new AlertView(context.getResources().getString(R.string.issue_referral), context.getResources().getString(R.string.issue_referral_prompt), AlertStyle.DIALOG);
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    forwardReferral();
                }));
                alert.show(FeedbackDetailsActivity.this);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void forwardReferral(){
        FragmentManager fm = this.getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(currentPatient, currentReferral.getReferralSource(), currentReferral.getReferralUUID());
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");
    }

    private void setupviews(){

        findViewById(R.id.fake_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do nothing
            }
        });

        serviceGivenContainer = findViewById(R.id.service_given_container);

        testInputContainer = findViewById(R.id.results_input_container);
        testTypeContainer = findViewById(R.id.test_type_container);

        waitingForResults = findViewById(R.id.waiting_for_results);
        labTestType = findViewById(R.id.lab_test_type);

        testResultsToggle = findViewById(R.id.test_results_toggle);

        indicatorsRecyclerView = findViewById(R.id.indicators_linear_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        indicatorsRecyclerView.setLayoutManager(layoutManager);
        indicatorsRecyclerView.setHasFixedSize(true);

        referralButton =  findViewById(R.id.referal_button);
        cancelButton =  findViewById(R.id.cancel_button);

        servicesOfferedEt = findViewById(R.id.service_offered_et);
        servicesOfferedEt.setEnabled(false);
        otherInformationEt =  findViewById(R.id.other_information_et);
        otherInformationEt.setEnabled(false);

        referrerName =  findViewById(R.id.referer_name_value);
        villageLeaderValue = findViewById(R.id.mwenyekiti_name_value);

        patientGender =  findViewById(R.id.patient_gender_value);
        wardText = findViewById(R.id.client_kata_value);
        villageText =  findViewById(R.id.client_kijiji_value);
        hamletText = findViewById(R.id.client_kitongoji_value);

        clientNames = findViewById(R.id.client_name);
        clientAge = findViewById(R.id.client_age_value);

        referalReasons = findViewById(R.id.sababu_ya_rufaa_value);

    }

    class patientDetailsTask extends AsyncTask<Void, Void, Void> {

        String patientNames, patientId;
        Patient patient;
        AppDatabase db;
        List<ReferralIndicator> indicators =  new ArrayList<>();

        boolean referralAlreadyChained = false;
        List<Referral> chainedReferrals = new ArrayList<>();

        patientDetailsTask(AppDatabase database, String patientID){
            this.db = database;
            this.patientId = patientID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientNames = db.patientModel().getPatientName(patientId);
            patient = db.patientModel().getPatientById(patientId);
            currentPatient = patient;

            chainedReferrals = db.referalModel().listOfChainedReferrals(currentReferral.getReferralUUID(), currentReferral.getReferralDate());
            if (chainedReferrals != null){
                if (chainedReferrals.size() > 0){
                    referralAlreadyChained = true;
                }else {
                    referralAlreadyChained = false;
                }
            }

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

//            //Hide the refer button for already chained referrals
//            if (referralAlreadyChained){
//                referralButton.setVisibility(View.GONE);
//            }else{
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED){
                    referralButton.setVisibility(View.VISIBLE);
                }else {
                    referralButton.setVisibility(View.GONE);
                }
//            }


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

            FeedbackDetailsActivity.IndicatorsRecyclerAdapter adapter = new FeedbackDetailsActivity.IndicatorsRecyclerAdapter(FeedbackDetailsActivity.this, indicators);
            indicatorsRecyclerView.setAdapter(adapter);

            //adapter.notifyDataSetChanged();
        }

    }

    class IndicatorsRecyclerAdapter  extends RecyclerView.Adapter<FeedbackDetailsActivity.IndicatorsViewHolder> {

        private List<ReferralIndicator> indicators = new ArrayList<>();
        private LayoutInflater mInflater;

        // data is passed into the constructor
        public IndicatorsRecyclerAdapter(Context context, List<ReferralIndicator> items) {
            this.mInflater = LayoutInflater.from(context);
            this.indicators = items;
        }

        // inflates the cell layout from xml when needed
        @Override
        public FeedbackDetailsActivity.IndicatorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.referral_details_indicatior_list_item, parent, false);
            FeedbackDetailsActivity.IndicatorsViewHolder holder = new FeedbackDetailsActivity.IndicatorsViewHolder(view);
            return holder;
        }

        // binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(FeedbackDetailsActivity.IndicatorsViewHolder holder, int position) {
            ReferralIndicator indicator = indicators.get(position);
            holder.bindIndicator(indicator);
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return indicators.size();
        }

        // convenience method for getting data at click position
        ReferralIndicator getItem(int id) {
            return indicators.get(id);
        }

    }

    class IndicatorsViewHolder extends RecyclerView.ViewHolder {

        private TextView indicatorName;
        private ReferralIndicator referralIndicator;

        private IndicatorsViewHolder(View itemView) {
            super(itemView);
            indicatorName = (TextView) itemView.findViewById(R.id.indicator_name);
        }

        private void bindIndicator(ReferralIndicator indicator){
            this.referralIndicator = indicator;
            indicatorName.setText(referralIndicator.getIndicatorName());
        }

    }

}
