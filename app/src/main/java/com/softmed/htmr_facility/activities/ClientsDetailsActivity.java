package com.softmed.htmr_facility.activities;

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
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.rey.material.widget.ProgressView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility.fragments.IssueReferralDialogueFragment;
import com.softmed.htmr_facility.utils.ListStringConverter;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.MALARIA_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_INDETERMINATE;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_INDETERMINATE_SW;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_NEGATIVE;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_NEGATIVE_SW;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_POSITIVE;
import static com.softmed.htmr_facility.utils.constants.TEST_RESULT_POSITIVE_SW;

/**
 * Created by issy on 11/17/17.
 */

public class ClientsDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    public Button saveButton, referButton;
    public TextView ctcNumber, referalReasons, villageLeaderValue, referrerName, testResultsHint, labTestType, referralService;
    private EditText servicesOfferedEt, otherInformationEt, ctcNumberEt;
    public ProgressView saveProgress;
    private RecyclerView indicatorsRecyclerView;
    public TextView clientNames,clientAgeValue, wardText, villageText, hamletText, patientGender, otherClinicalInformationValue;
    public Dialog referalDialogue;
    MaterialSpinner testResultsSpinner;
    RelativeLayout resultsInputContainer, indicatorsContainer;
    LinearLayout servicesGivenContainer;

    int service;
    Referral currentReferral;
    Patient currentPatient;
    boolean isNewCase = false;
    boolean isCTCNumberEmpty = false;
    boolean updatePatientObject = false;
    private String clientCTCNumber;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        setupviews();

        service = getIntent().getIntExtra("service", 0);
        calibrateUI(service);

        if (getIntent().getExtras() != null){
            currentReferral = (Referral) getIntent().getSerializableExtra("referal");

            if (currentReferral != null){
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED){

                    servicesOfferedEt.setEnabled(false);
                    servicesOfferedEt.setText(currentReferral.getServiceGivenToPatient());

                    otherInformationEt.setText(currentReferral.getOtherNotesAndAdvices());
                    otherInformationEt.setEnabled(false);

                    saveButton.setEnabled(false);
                    referButton.setVisibility(View.INVISIBLE);
                    testResultsSpinner.setEnabled(false);

                }

                if (service == LAB_SERVICE_ID){
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
                }

                //hivStatus.setChecked(currentReferral.isTestResults());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "N/A" : currentReferral.getVillageLeader());
                otherClinicalInformationValue.setText(currentReferral.getOtherClinicalInformation() == null ? "N/A" : currentReferral.getOtherClinicalInformation());
                referalReasons.setText(currentReferral.getReferralReason() == null ? "N/A" : currentReferral.getReferralReason());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "N/A" : currentReferral.getVillageLeader());
                referrerName.setText(currentReferral.getServiceProviderUIID() == null ? "N/A" : currentReferral.getServiceProviderUIID());


                new patientDetailsTask(baseDatabase, currentReferral.getPatient_id(), currentReferral.getServiceId()).execute();

            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referalDialogue = new Dialog(this);
        referalDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);

        List<String> results = new ArrayList<>();

        if (getLocaleString().equals(SWAHILI_LOCALE)){
            results.add(TEST_RESULT_POSITIVE_SW);
            results.add(TEST_RESULT_NEGATIVE_SW);
            results.add(TEST_RESULT_INDETERMINATE_SW);
        }else if (getLocaleString().equals(ENGLISH_LOCALE)){
            results.add(TEST_RESULT_POSITIVE);
            results.add(TEST_RESULT_NEGATIVE);
            results.add(TEST_RESULT_INDETERMINATE);
        }

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, results);
        testResultsSpinner.setAdapter(spinAdapter);
        testResultsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    if (isCTCNumberEmpty){
                        isNewCase = true;
                    }else {
                        isNewCase = false;
                    }
                    Log.d("result_selected", "selected results : "+adapterView.getSelectedItem());
                }else if (i == 1){
                    isNewCase = false;
                    Log.d("result_selected", "selected results : "+adapterView.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReferalInformation(false);
            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = ClientsDetailsActivity.this;
                AlertView alert = new AlertView(context.getResources().getString(R.string.issue_referral), context.getResources().getString(R.string.issue_referral_prompt), AlertStyle.DIALOG);
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    if (currentPatient != null){
                        saveReferalInformation(true);
                    }
                }));
                alert.show(ClientsDetailsActivity.this);
            }
        });

    }

    private void setupviews(){

        labTestType = findViewById(R.id.lab_test_type);
        testResultsSpinner = findViewById(R.id.spin_test_results);

        resultsInputContainer = findViewById(R.id.results_input_wrap);
        servicesGivenContainer =  findViewById(R.id.service_given_container);
        indicatorsContainer = findViewById(R.id.indicators_container);

        indicatorsRecyclerView = findViewById(R.id.indicators_linear_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        indicatorsRecyclerView.setLayoutManager(layoutManager);
        indicatorsRecyclerView.setHasFixedSize(true);

        otherClinicalInformationValue = findViewById(R.id.other_clinical_inforamtion_value);
        saveProgress = findViewById(R.id.save_progress);
        servicesOfferedEt = findViewById(R.id.service_offered_et);
        otherInformationEt = findViewById(R.id.other_information_et);
        ctcNumberEt = findViewById(R.id.ctc_number_et);

        referralService = findViewById(R.id.referral_service);
        referrerName = findViewById(R.id.referer_name_value);
        villageLeaderValue = findViewById(R.id.mwenyekiti_name_value);
        patientGender = findViewById(R.id.patient_gender_value);
        testResultsHint = findViewById(R.id.test_results_hint);
        wardText = findViewById(R.id.client_kata_value);
        villageText = findViewById(R.id.client_kijiji_value);
        hamletText = findViewById(R.id.client_kitongoji_value);
        saveButton = findViewById(R.id.save_button);
        referButton = findViewById(R.id.referal_button);
        clientNames = findViewById(R.id.client_name);
        clientAgeValue = findViewById(R.id.client_age_value);
        referalReasons = findViewById(R.id.sababu_ya_rufaa_value);

    }

    private void calibrateUI(int serviceID){
        switch (serviceID){
            case HIV_SERVICE_ID:
                //Do all CTC Calibrations
                testResultsSpinner.setVisibility(View.GONE);
                labTestType.setVisibility(View.GONE);
                resultsInputContainer.setVisibility(View.GONE);
                servicesGivenContainer.setVisibility(View.VISIBLE);
                indicatorsContainer.setVisibility(View.GONE);
                ctcNumberEt.setVisibility(View.VISIBLE);
                break;
            case TB_SERVICE_ID:
                //Do all TB Calibrations
                labTestType.setVisibility(View.GONE);
                testResultsSpinner.setVisibility(View.GONE);
                resultsInputContainer.setVisibility(View.GONE);
                servicesGivenContainer.setVisibility(View.VISIBLE);
                indicatorsContainer.setVisibility(View.GONE);
                ctcNumberEt.setVisibility(View.GONE);
                break;
            case LAB_SERVICE_ID:
                /*
                * Calibrations
                * Test Type = VISIBLE
                * Cannot issue referral from lab : Issue Referral = INVISIBLE
                * Lab does not have services given and advices
                * */
                referButton.setVisibility(View.GONE);
                labTestType.setVisibility(View.VISIBLE);
                testResultsSpinner.setVisibility(View.VISIBLE);
                ctcNumberEt.setVisibility(View.GONE);
                resultsInputContainer.setVisibility(View.VISIBLE);
                servicesGivenContainer.setVisibility(View.GONE);
                indicatorsContainer.setVisibility(View.GONE);
                break;
        }
    }


    //TODO => This method needs code restructuring
    private void saveReferalInformation(boolean isForwardingReferral){
        if (service == LAB_SERVICE_ID){

            if (testResultsSpinner.getSelectedItemPosition() == 0){
                testResultsSpinner.setEnableErrorLabel(true);
                testResultsSpinner.setError(getResources().getString(R.string.input_required));
                testResultsSpinner.setErrorColor(getResources().getColor(R.color.red_500));
                Toast.makeText(this, getResources().getString(R.string.please_add_test_results), Toast.LENGTH_LONG);
            } else {
                boolean result = false;

                if (testResultsSpinner.getSelectedItem().equals(TEST_RESULT_POSITIVE) || testResultsSpinner.getSelectedItem().equals(TEST_RESULT_POSITIVE_SW)){
                    result = true;
                }else if (testResultsSpinner.getSelectedItem().equals(TEST_RESULT_NEGATIVE) || testResultsSpinner.getSelectedItem().equals(TEST_RESULT_NEGATIVE_SW)){
                    result = false;
                }

                currentReferral.setTestResults(result);
            }

        }

        if (service == HIV_SERVICE_ID){
            if (!ctcNumberEt.getText().toString().isEmpty()){
                currentPatient.setHivStatus(true);
                currentPatient.setCtcNumber(ctcNumberEt.getText().toString());
                currentPatient.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
                updatePatientObject = true;
            }
        }

        String serviceOferedString = servicesOfferedEt.getText().toString();
        String otherInformation = otherInformationEt.getText().toString();

        clientCTCNumber = ctcNumberEt.getText().toString();

        currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
        currentReferral.setServiceGivenToPatient(serviceOferedString);
        currentReferral.setOtherNotesAndAdvices(otherInformation);
        currentReferral.setUpdatedAt(Calendar.getInstance().getTimeInMillis());

        //Show progress bar
        saveProgress.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);

        //Referral has already ended it cannot be chained again from here
        referButton.setVisibility(View.GONE);

        UpdateReferralTask updateReferralTask = new UpdateReferralTask(currentReferral, baseDatabase);
        updateReferralTask.execute(isForwardingReferral, isNewCase);

    }

    private void callReferralFragmentDialogue(Patient patient){

        FragmentManager fm = getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, service, currentReferral.getReferralUUID());
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    class IndicatorsRecyclerAdapter  extends RecyclerView.Adapter<IndicatorsViewHolder> {

        private List<ReferralIndicator> indicators = new ArrayList<>();
        private LayoutInflater mInflater;

        // data is passed into the constructor
        public IndicatorsRecyclerAdapter(Context context, List<ReferralIndicator> items) {
            this.mInflater = LayoutInflater.from(context);
            this.indicators = items;
        }

        // inflates the cell layout from xml when needed
        @Override
        public IndicatorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.referral_details_indicatior_list_item, parent, false);
            IndicatorsViewHolder holder = new IndicatorsViewHolder(view);
            return holder;
        }

        // binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(IndicatorsViewHolder holder, int position) {
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
            if (BaseActivity.getLocaleString().equals(ENGLISH_LOCALE)){
                indicatorName.setText(referralIndicator.getIndicatorName());
            }else {

            }
        }

    }

    class UpdateReferralTask extends AsyncTask<Boolean, Void, Void>{

        Referral referal;
        AppDatabase database;
        Boolean isForwardingThisReferral = false;
        Boolean isNewCase;

        UpdateReferralTask(Referral ref, AppDatabase db){
            this.referal = ref;
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {

            if (updatePatientObject){
                database.patientModel().updatePatient(currentPatient);

                PostOffice postOffice = new PostOffice();
                postOffice.setPost_id(currentPatient.getPatientId());
                postOffice.setSyncStatus(ENTRY_NOT_SYNCED);
                postOffice.setPost_data_type(POST_DATA_TYPE_PATIENT);
                database.postOfficeModelDao().addPostEntry(postOffice);

                updatePatientObject = false;

            }

            isForwardingThisReferral = booleans[0];
            isNewCase = booleans[1];

            database.referalModel().updateReferral(referal);

            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(referal.getReferral_id());
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);
            postOffice.setPost_data_type(POST_DATA_REFERRAL_FEEDBACK);
            database.postOfficeModelDao().addPostEntry(postOffice);

            /*if (isNewCase){

                Patient patient = database.patientModel().getPatientById(referal.getPatient_id());
                patient.setCtcNumber(clientCTCNumber);
                patient.setHivStatus(true);

                database.patientModel().updatePatient(patient);

                PostOffice postOffice1 = new PostOffice();
                postOffice1.setPost_id(patient.getPatientId());
                postOffice1.setPost_data_type(POST_DATA_TYPE_PATIENT);
                postOffice1.setSyncStatus(ENTRY_NOT_SYNCED);

                database.postOfficeModelDao().addPostEntry(postOffice1);

            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            saveProgress.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);

            if (isForwardingThisReferral){
                testResultsSpinner.setEnabled(false);
                servicesOfferedEt.setEnabled(false);
                otherInformationEt.setEnabled(false);
                ctcNumberEt.setEnabled(false);
                saveButton.setText(getResources().getString(R.string.done));
                callReferralFragmentDialogue(currentPatient);
            }else {
                finish();
            }


        }
    }

    class patientDetailsTask extends AsyncTask<Void, Void, Void> {

        String patientNames, patientId, serviceName;
        long referralServiceId;
        Patient patient;
        AppDatabase db;
        List<ReferralIndicator> indicators =  new ArrayList<>();

        patientDetailsTask(AppDatabase database, String patientID, long mReferralServiceId){
            this.db = database;
            this.patientId = patientID;
            this.referralServiceId = mReferralServiceId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientNames = db.patientModel().getPatientName(patientId);
            patient = db.patientModel().getPatientById(patientId);
            serviceName = db.referralServiceIndicatorsDao().getServiceNameById(Integer.valueOf(referralServiceId+""));
            currentPatient = patient;

            if (currentReferral.getServiceIndicatorIds() != null){
                List<Long> ids = currentReferral.getServiceIndicatorIds();

                //Call Patient Referral Indicators
                for (int i=0; i<ids.size(); i++){
                    long id = Long.parseLong(ids.get(i)+"");
                    ReferralIndicator referralIndicator = db.referralIndicatorDao().getReferralIndicatorById(id);
                    indicators.add(referralIndicator);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clientNames.setText(patientNames);
            if (patient != null){

                String wardTitle = getResources().getString(R.string.ward);
                String villageTitle = getResources().getString(R.string.village);
                String mapCueTitle = getResources().getString(R.string.map_cue);

                try {
                    Calendar cal = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    cal.setTimeInMillis(patient.getDateOfBirth());

                    int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                    Integer ageInt = new Integer(age);
                    clientAgeValue.setText(ageInt.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //display the referral service
                referralService.setText(serviceName);

                wardText.setText(patient.getWard() == null ? "N/A " : patient.getWard());
                villageText.setText(patient.getVillage() == null ? "N/A " : patient.getVillage());
                hamletText.setText(patient.getHamlet() == null ? "N/A " : patient.getHamlet());
                patientGender.setText(patient.getGender());
                if (patient.getCtcNumber() != null){
                    //testResultsHint.setVisibility(View.VISIBLE);
                    //hivStatus.setVisibility(View.VISIBLE);
                    ctcNumberEt.setVisibility(View.VISIBLE);

                    ctcNumberEt.setText(patient.getCtcNumber());
                    isCTCNumberEmpty =false;

                }else {

                    isCTCNumberEmpty = true;

                    //testResultsHint.setVisibility(View.VISIBLE);
                    //hivStatus.setVisibility(View.VISIBLE);
                }
            }

            IndicatorsRecyclerAdapter adapter = new IndicatorsRecyclerAdapter(ClientsDetailsActivity.this, indicators);
            indicatorsRecyclerView.setAdapter(adapter);

        }

    }

}
