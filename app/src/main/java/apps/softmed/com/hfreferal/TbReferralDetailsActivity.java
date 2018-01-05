package apps.softmed.com.hfreferal;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.ENTRY_NOT_SYNCED;
import static apps.softmed.com.hfreferal.utils.constants.REFERRAL_STATUS_COMPLETED;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbReferralDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    public Button saveButton, referButton;
    private MaterialSpinner servicesSpinner, healthFacilitySpinner;
    public TextView clientName, ctcNumber, referalReasons, villageLeaderValue, referrerName;
    private EditText servicesOfferedEt, otherInformationEt;
    public ProgressView saveProgress;
    private CheckBox hivStatus;

    public TextView clientNames, wardText, villageText, hamletText, patientGender;

    public Dialog referalDialogue;

    private Referal currentReferral;

    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_referral_details);
        setupviews();

        if (getIntent().getExtras() != null){
            currentReferral = (Referal) getIntent().getSerializableExtra("referal");
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
                referalReasons.setText(currentReferral.getReferralReason() == null ? "" : currentReferral.getReferralReason());
                villageLeaderValue.setText(currentReferral.getVillageLeader() == null ? "" : currentReferral.getVillageLeader());
                referrerName.setText(currentReferral.getServiceProviderUIID() == null ? "" : currentReferral.getServiceProviderUIID());
                new TbReferralDetailsActivity.patientDetailsTask(baseDatabase, currentReferral.getPatient_id()).execute();
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
                referalDialogueEvents();
            }
        });

    }

    private void saveReferalInformation(){
        if (servicesOfferedEt.getText().toString().isEmpty()){
            Toast.makeText(this, "Tafadhali jaza huduma uliyoitoa", Toast.LENGTH_LONG).show();
        }else {

            String serviceOferedString = servicesOfferedEt.getText().toString();
            String otherInformation = otherInformationEt.getText().toString();

            currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
            currentReferral.setServiceGivenToPatient(serviceOferedString);
            currentReferral.setOtherNotesAndAdvices(otherInformation);

            //Show progress bar
            saveProgress.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);

            TbReferralDetailsActivity.UpdateReferralTask updateReferralTask = new TbReferralDetailsActivity.UpdateReferralTask(currentReferral, baseDatabase);
            updateReferralTask.execute();


        }
    }

    private void referalDialogueEvents(){

        final View mView = LayoutInflater.from(TbReferralDetailsActivity.this).inflate(R.layout.custom_dialogue_layout, null);
        referalDialogue.setContentView(mView);

        servicesSpinner = (MaterialSpinner) mView.findViewById(R.id.spin_service);
        healthFacilitySpinner = (MaterialSpinner) mView.findViewById(R.id.spin_to_facility);

        String[] servicesList = {"TB", "HIV", "MALARIA" };
        String[] hflist = {"Lugalo Hospital", "Kaloleni Dispensary", "Mount Meru Hospital" };

        Button tumaButton = (Button) mView.findViewById(R.id.tuma_button);
        tumaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referalDialogue.dismiss();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servicesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);

        ArrayAdapter<String> hfAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hflist);
        hfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        healthFacilitySpinner.setAdapter(hfAdapter);

        referalDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        referalDialogue.setCancelable(false);
        referalDialogue.show();

    }

    private void setupviews(){

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

    class UpdateReferralTask extends AsyncTask<Void, Void, Void> {

        Referal referal;
        AppDatabase database;

        UpdateReferralTask(Referal ref, AppDatabase db){
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
            postOffice.setPatient_id(referal.getPatient_id());
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

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

        patientDetailsTask(AppDatabase database, String patientID){
            this.db = database;
            this.patientId = patientID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientNames = db.patientModel().getPatientName(patientId);
            patient = db.patientModel().getPatientById(patientId);
            Log.d("", "PATIENT : "+patient.getPatientId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background!"+patientNames);
            clientNames.setText(patientNames);
            if (patient != null){
                wardText.setText(patient.getWard() == null ? "Kata :  " : "Kata : "+patient.getWard());
                villageText.setText(patient.getVillage() == null ? "Kijiji :  " : "Kijiji : "+patient.getVillage());
                hamletText.setText(patient.getHamlet() == null ? "Kitongoji :  " : "Kitongoji : "+patient.getHamlet());
                patientGender.setText(patient.getGender());
            }
            //adapter.notifyDataSetChanged();
        }

    }


}
