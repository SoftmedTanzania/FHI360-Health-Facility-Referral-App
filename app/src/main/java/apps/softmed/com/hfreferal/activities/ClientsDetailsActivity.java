package apps.softmed.com.hfreferal.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.objects.ReferralIndicator;
import apps.softmed.com.hfreferal.fragments.IssueReferralDialogueFragment;
import apps.softmed.com.hfreferal.utils.ListStringConverter;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.ENTRY_NOT_SYNCED;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_REFERRAL;
import static apps.softmed.com.hfreferal.utils.constants.REFERRAL_STATUS_COMPLETED;

/**
 * Created by issy on 11/17/17.
 */

public class ClientsDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    public Button saveButton, referButton;
    public TextView ctcNumber, referalReasons, villageLeaderValue, referrerName;
    private EditText servicesOfferedEt, otherInformationEt;
    public ProgressView saveProgress;
    private CheckBox hivStatus;
    private RecyclerView indicatorsRecyclerView;

    public TextView clientNames, wardText, villageText, hamletText, patientGender, otherClinicalInformationValue;

    public Dialog referalDialogue;

    private int service;
    private Referral currentReferral;
    private Patient currentPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        setupviews();

        if (getIntent().getExtras() != null){
            currentReferral = (Referral) getIntent().getSerializableExtra("referal");
            service = getIntent().getIntExtra("service", 0);

            if (currentReferral != null){
                if (currentReferral.getReferralStatus() == REFERRAL_STATUS_COMPLETED){

                    servicesOfferedEt.setEnabled(false);
                    servicesOfferedEt.setText(currentReferral.getServiceGivenToPatient());

                    otherInformationEt.setText(currentReferral.getOtherNotesAndAdvices());
                    otherInformationEt.setEnabled(false);

                    saveButton.setEnabled(false);
                    referButton.setEnabled(false);
                    hivStatus.setEnabled(false);

                }

                hivStatus.setChecked(currentReferral.isTestResults());
                villageLeaderValue.setText(currentReferral.getVillageLeader());
                otherClinicalInformationValue.setText(currentReferral.getOtherClinicalInformation());
                referalReasons.setText(currentReferral.getReferralReason() == null ? "" : currentReferral.getReferralReason());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "" : currentReferral.getVillageLeader());
                referrerName.setText(currentReferral.getServiceProviderUIID() == null ? "" : currentReferral.getServiceProviderUIID());
                new patientDetailsTask(baseDatabase, currentReferral.getPatient_id()).execute();
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referalDialogue = new Dialog(this);
        referalDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReferalInformation();
            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                referalDialogueEvents();

                if (currentPatient != null){
                    callReferralFragmentDialogue(currentPatient);
                }

            }
        });

    }

    private void saveReferalInformation(){
        if (servicesOfferedEt.getText().toString().isEmpty()){
            Toast.makeText(this, "Tafadhali jaza huduma uliyoitoa", Toast.LENGTH_LONG).show();
        }else {

            String serviceOferedString = servicesOfferedEt.getText().toString();
            String otherInformation = otherInformationEt.getText().toString();
            boolean result = hivStatus.isChecked();

            currentReferral.setTestResults(result);
            currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
            currentReferral.setServiceGivenToPatient(serviceOferedString);
            currentReferral.setOtherNotesAndAdvices(otherInformation);

            //Show progress bar
            saveProgress.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);

            UpdateReferralTask updateReferralTask = new UpdateReferralTask(currentReferral, baseDatabase);
            updateReferralTask.execute();

        }
    }

    private void callReferralFragmentDialogue(Patient patient){

        FragmentManager fm = getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, service);
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    private void setupviews(){

        indicatorsRecyclerView = (RecyclerView) findViewById(R.id.indicators_linear_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        indicatorsRecyclerView.setLayoutManager(layoutManager);
        indicatorsRecyclerView.setHasFixedSize(true);

        otherClinicalInformationValue = (TextView) findViewById(R.id.other_clinical_inforamtion_value);

        hivStatus = (CheckBox) findViewById(R.id.hiv_status);

        saveProgress = (ProgressView) findViewById(R.id.save_progress);

        servicesOfferedEt = (EditText) findViewById(R.id.service_offered_et);

        otherInformationEt = (EditText) findViewById(R.id.other_information_et);

        referrerName = (TextView) findViewById(R.id.referer_name_value);
        villageLeaderValue = (TextView) findViewById(R.id.mwenyekiti_name_value);

        patientGender = (TextView) findViewById(R.id.patient_gender_value);
        wardText = (TextView) findViewById(R.id.client_kata_value);
        villageText = (TextView) findViewById(R.id.client_kijiji_value);
        hamletText = (TextView) findViewById(R.id.client_kitongoji_value);

        saveButton = (Button) findViewById(R.id.save_button);
        referButton = (Button) findViewById(R.id.referal_button);

        clientNames = (TextView) findViewById(R.id.client_name);

        referalReasons = (TextView) findViewById(R.id.sababu_ya_rufaa_value);

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
            indicatorName.setText(referralIndicator.getIndicatorName());
        }

    }

    class UpdateReferralTask extends AsyncTask<Void, Void, Void>{

        Referral referal;
        AppDatabase database;

        UpdateReferralTask(Referral ref, AppDatabase db){
            this.referal = ref;
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

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
            saveProgress.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            finish();
        }
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

            List<Long> ids = ListStringConverter.stringToSomeObjectList(currentReferral.getServiceIndicatorIds()+"");

            //Call Patient Referral Indicators
            for (int i=0; i<ids.size(); i++){
                ReferralIndicator referralIndicator = db.referralIndicatorDao().getReferralIndicatorById(ids.get(i)+"");
                indicators.add(referralIndicator);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background!"+patientNames);
            clientNames.setText(patientNames);
            if (patient != null){
                wardText.setText(patient.getWard() == null ? "Kata : __ " : "Kata : "+patient.getWard());
                villageText.setText(patient.getVillage() == null ? "Kijiji : __ " : "Kijiji : "+patient.getVillage());
                hamletText.setText(patient.getHamlet() == null ? "Kitongoji : __ " : "Kitongoji : "+patient.getHamlet());
                patientGender.setText(patient.getGender());
            }

            IndicatorsRecyclerAdapter adapter = new IndicatorsRecyclerAdapter(ClientsDetailsActivity.this, indicators);
            indicatorsRecyclerView.setAdapter(adapter);

        }

    }

}