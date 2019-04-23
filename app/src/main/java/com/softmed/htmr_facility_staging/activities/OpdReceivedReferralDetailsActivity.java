package com.softmed.htmr_facility_staging.activities;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.softmed.htmr_facility_staging.R;
import com.softmed.htmr_facility_staging.base.AppDatabase;
import com.softmed.htmr_facility_staging.base.BaseActivity;
import com.softmed.htmr_facility_staging.dom.objects.Patient;
import com.softmed.htmr_facility_staging.dom.objects.PostOffice;
import com.softmed.htmr_facility_staging.dom.objects.Referral;
import com.softmed.htmr_facility_staging.dom.objects.ReferralFeedback;
import com.softmed.htmr_facility_staging.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility_staging.fragments.IssueReferralDialogueFragment;

import static com.softmed.htmr_facility_staging.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility_staging.utils.constants.FEMALE;
import static com.softmed.htmr_facility_staging.utils.constants.FEMALE_SW;
import static com.softmed.htmr_facility_staging.utils.constants.FEMALE_VALUE;
import static com.softmed.htmr_facility_staging.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility_staging.utils.constants.MALE;
import static com.softmed.htmr_facility_staging.utils.constants.MALE_SW;
import static com.softmed.htmr_facility_staging.utils.constants.MALE_VALUE;
import static com.softmed.htmr_facility_staging.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility_staging.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.htmr_facility_staging.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 05/02/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class OpdReceivedReferralDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    private Button saveButton, referButton;
    private TextView ctcNumber, referalReasons, villageLeaderValue, referrerName, referralServiceName, clientPhoneNumberValue, referralDate;
    private EditText servicesOfferedEt, otherInformationEt;
    private RecyclerView indicatorsRecyclerView;
    private TextView clientNames, wardText, clientAge, villageText, hamletText, patientGender, otherClinicalInformationValue;
    private Dialog referalDialogue;
    private MaterialSpinner referralFeedbackMaterialSpinner;
    private List<ReferralFeedback> referralFeedbacks = new ArrayList<>();
    private int service;
    private Referral currentReferral;
    private Patient currentPatient;
    private boolean isNewCase = false;
    private String referralFeedbackValue;
    private ArrayList<String> mReferralFeedbackOptions = new ArrayList<>();
    private RelativeLayout tbHivFeedback;
    private CheckBox tbStatusCheckbox;
    private boolean tbStatus=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opd_received_referral_details);
        setupviews();

        if (getIntent().getExtras() != null) {
            currentReferral = (Referral) getIntent().getSerializableExtra("referal");
            Log.d("coze", "current referral = " + new Gson().toJson(currentReferral));
            service = getIntent().getIntExtra("service", 0);

            if (currentReferral != null) {
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED) {

                    servicesOfferedEt.setEnabled(false);
                    servicesOfferedEt.setText(currentReferral.getServiceGivenToPatient());

                    otherInformationEt.setText(currentReferral.getOtherNotesAndAdvices());
                    otherInformationEt.setEnabled(false);

                    referButton.setEnabled(false);

                }

                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "N/A" : currentReferral.getVillageLeader());
                otherClinicalInformationValue.setText(currentReferral.getOtherClinicalInformation() == null ? "N/A" : currentReferral.getOtherClinicalInformation());
                referalReasons.setText(currentReferral.getReferralReason() == null ? "N/A" : currentReferral.getReferralReason());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "N/A" : currentReferral.getVillageLeader());
                referralDate.setText(simpleDateFormat.format(currentReferral.getReferralDate()));

                new OpdReceivedReferralDetailsActivity.patientDetailsTask(baseDatabase, currentReferral.getPatient_id(), currentReferral.getServiceId()).execute();

                if(currentReferral.getServiceId()==TB_SERVICE_ID || currentReferral.getServiceId()==HIV_SERVICE_ID ){
                    tbHivFeedback.setVisibility(View.VISIBLE);
                }


            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)) {
                    for (ReferralFeedback referralFeedback : referralFeedbacks)
                        mReferralFeedbackOptions.add(referralFeedback.getDesc());
                } else {
                    for (ReferralFeedback referralFeedback : referralFeedbacks)
                        mReferralFeedbackOptions.add(referralFeedback.getDescSw());
                }

                referralFeedbackMaterialSpinner.setItems(mReferralFeedbackOptions);
                referralFeedbackValue = referralFeedbacks.get(0).getId().toString();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                referralFeedbacks = baseDatabase.referralFeedbackModelDao().getReferralFeedbackByRefeerralType(2);
                return null;
            }
        }.execute();



        referralFeedbackMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                referralFeedbackValue = referralFeedbacks.get(position).getId().toString();
            }
        });

        referalDialogue = new Dialog(this);
        referalDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Finish Activity
                finish();
                saveReferalInformation(true);
            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = OpdReceivedReferralDetailsActivity.this;
                AlertView alert = new AlertView(context.getResources().getString(R.string.issue_referral), context.getResources().getString(R.string.issue_referral_prompt), AlertStyle.DIALOG);
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    if (currentPatient != null) {
                        //Needs to save current referral feedback before issuing another referral
                        saveReferalInformation(true);
                    } else {
                        Toast.makeText(OpdReceivedReferralDetailsActivity.this, getResources().getString(R.string.feedback_required), Toast.LENGTH_LONG).show();
                    }
                }));
                alert.show(OpdReceivedReferralDetailsActivity.this);

            }
        });

    }

    private void saveReferalInformation(boolean isForwardingReferral) {

        String serviceOferedString = referralFeedbackValue;
        String otherInformation = otherInformationEt.getText().toString();

        currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
        currentReferral.setServiceGivenToPatient(serviceOferedString);
        currentReferral.setOtherNotesAndAdvices(otherInformation);
        currentReferral.setUpdatedAt(Calendar.getInstance().getTimeInMillis());

        if(currentReferral.getServiceId()==TB_SERVICE_ID){
            currentReferral.setTestResults(tbStatus?1:0);
        }

        //Show progress bar
        saveButton.setVisibility(View.INVISIBLE);

        //Do not allow further forward of this referral at this point
        referButton.setVisibility(View.GONE);

        OpdReceivedReferralDetailsActivity.UpdateReferralTask updateReferralTask = new OpdReceivedReferralDetailsActivity.UpdateReferralTask(currentReferral, baseDatabase);
        updateReferralTask.execute(isForwardingReferral, isNewCase);

    }

    private void callReferralFragmentDialogue(Patient patient) {

        FragmentManager fm = getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, service, currentReferral.getReferralUUID());
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    private void setupviews() {

        indicatorsRecyclerView = (RecyclerView) findViewById(R.id.indicators_linear_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        indicatorsRecyclerView.setLayoutManager(layoutManager);
        indicatorsRecyclerView.setHasFixedSize(true);
        otherClinicalInformationValue = (TextView) findViewById(R.id.other_clinical_inforamtion_value);


        clientAge = (TextView) findViewById(R.id.client_age_value);
        servicesOfferedEt = (EditText) findViewById(R.id.service_offered_et);
        otherInformationEt = (EditText) findViewById(R.id.other_information_et);

        referrerName = (TextView) findViewById(R.id.referer_name_value);
        villageLeaderValue = (TextView) findViewById(R.id.mwenyekiti_name_value);
        patientGender = (TextView) findViewById(R.id.patient_gender_value);
        clientNames = (TextView) findViewById(R.id.client_name);
        referalReasons = (TextView) findViewById(R.id.sababu_ya_rufaa_value);


        referralFeedbackMaterialSpinner = findViewById(R.id.referral_feedback_spinner);
        referralDate = findViewById(R.id.tarehe_ya_rufaa_value);
        clientPhoneNumberValue = findViewById(R.id.client_phone_number_value);
        referralServiceName = findViewById(R.id.referral_service_name);
        wardText = (TextView) findViewById(R.id.client_kata_value);
        villageText = (TextView) findViewById(R.id.client_kijiji_value);
        hamletText = (TextView) findViewById(R.id.client_kitongoji_value);

        tbHivFeedback =  findViewById(R.id.tb_feedback);
        tbStatusCheckbox =  findViewById(R.id.tb_status);

        tbStatusCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tbStatus = b;
            }
        });

        saveButton = (Button) findViewById(R.id.save);
        referButton = (Button) findViewById(R.id.referal_button);

    }

    class IndicatorsRecyclerAdapter extends RecyclerView.Adapter<OpdReceivedReferralDetailsActivity.IndicatorsViewHolder> {

        private List<ReferralIndicator> indicators = new ArrayList<>();
        private LayoutInflater mInflater;

        // data is passed into the constructor
        public IndicatorsRecyclerAdapter(Context context, List<ReferralIndicator> items) {
            this.mInflater = LayoutInflater.from(context);
            this.indicators = items;
        }

        // inflates the cell layout from xml when needed
        @Override
        public OpdReceivedReferralDetailsActivity.IndicatorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.referral_details_indicatior_list_item, parent, false);
            OpdReceivedReferralDetailsActivity.IndicatorsViewHolder holder = new OpdReceivedReferralDetailsActivity.IndicatorsViewHolder(view);
            return holder;
        }

        // binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(OpdReceivedReferralDetailsActivity.IndicatorsViewHolder holder, int position) {
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

        private void bindIndicator(ReferralIndicator indicator) {
            this.referralIndicator = indicator;
            if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)) {
                indicatorName.setText(referralIndicator == null ? "" : referralIndicator.getIndicatorName());
            } else {
                indicatorName.setText(referralIndicator == null ? "" : referralIndicator.getIndicatorNameSw());
            }
        }

    }

    class UpdateReferralTask extends AsyncTask<Boolean, Void, Void> {

        Referral referal;
        AppDatabase database;
        Boolean isForwardingThisReferral = false;
        Boolean isNewCase;

        UpdateReferralTask(Referral ref, AppDatabase db) {
            this.referal = ref;
            this.database = db;
            Log.d("Coze", "Saved referral = " + new Gson().toJson(referal));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {

            isForwardingThisReferral = booleans[0];
            isNewCase = booleans[1];

            database.referalModel().updateReferral(referal);

            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(referal.getReferral_id());
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);
            postOffice.setPost_data_type(POST_DATA_REFERRAL_FEEDBACK);
            database.postOfficeModelDao().addPostEntry(postOffice);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            saveButton.setVisibility(View.VISIBLE);

            if (isForwardingThisReferral) {
                servicesOfferedEt.setEnabled(false);
                otherInformationEt.setEnabled(false);
                saveButton.setText(getResources().getString(R.string.done));
                callReferralFragmentDialogue(currentPatient);
            } else {
                finish();
            }


        }
    }

    class patientDetailsTask extends AsyncTask<Void, Void, Void> {

        String patientNames, patientId, serviceName, serviceNameSw;
        long mServiceId;
        Patient patient;
        AppDatabase db;
        List<ReferralIndicator> indicators = new ArrayList<>();

        patientDetailsTask(AppDatabase database, String patientID, long serviceId) {
            this.db = database;
            this.patientId = patientID;
            this.mServiceId = serviceId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientNames = db.patientModel().getPatientName(patientId);
            patient = db.patientModel().getPatientById(patientId);
            serviceName = db.referralServiceIndicatorsDao().getServiceNameById(Integer.valueOf(mServiceId + ""));
            serviceNameSw = db.referralServiceIndicatorsDao().getServiceNameByIdSW(Integer.valueOf(mServiceId + ""));
            currentPatient = patient;

            List<Long> ids = currentReferral.getServiceIndicatorIds();

            //Call Patient Referral Indicators
            for (int i = 0; i < ids.size(); i++) {
                long id = Long.parseLong(ids.get(i) + "");
                ReferralIndicator referralIndicator = db.referralIndicatorDao().getReferralIndicatorById(id);
                indicators.add(referralIndicator);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clientNames.setText(patientNames);
            if (patient != null) {

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

                String wardTitle = getResources().getString(R.string.ward);
                String villageTitle = getResources().getString(R.string.village);
                String mapCueTitle = getResources().getString(R.string.map_cue);

                //Set referral Service name CHECK LOCALE
                if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)) {
                    referralServiceName.setText(serviceName);
                } else {
                    referralServiceName.setText(serviceNameSw);
                }

                //display clients Phone Number
                clientPhoneNumberValue.setText(patient.getPhone_number());

                wardText.setText(patient.getWard() == null ? "N/A " : patient.getWard());
                villageText.setText(patient.getVillage() == null ? "N/A " : patient.getVillage());
                hamletText.setText(patient.getHamlet() == null ? "N/A " : patient.getHamlet());

                if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)) {
                    if (patient.getGender().equals(MALE) || patient.getGender().equals(MALE_VALUE)) {
                        patientGender.setText(MALE);
                    } else if (patient.getGender().equals(FEMALE) || patient.getGender().equals(FEMALE_VALUE)) {
                        patientGender.setText(FEMALE);
                    }
                } else {
                    if (patient.getGender().equals(MALE) || patient.getGender().equals(MALE_VALUE)) {
                        patientGender.setText(MALE_SW);
                    } else if (patient.getGender().equals(FEMALE) || patient.getGender().equals(FEMALE_VALUE)) {
                        patientGender.setText(FEMALE_SW);
                    }
                }
            }

            OpdReceivedReferralDetailsActivity.IndicatorsRecyclerAdapter adapter = new OpdReceivedReferralDetailsActivity.IndicatorsRecyclerAdapter(OpdReceivedReferralDetailsActivity.this, indicators);
            indicatorsRecyclerView.setAdapter(adapter);

        }

    }

}
