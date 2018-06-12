package com.softmed.htmr_facility.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.rey.material.widget.EditText;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.utils.ServiceGenerator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.FEMALE;
import static com.softmed.htmr_facility.utils.constants.MALE;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ClientRegisterActivity extends BaseActivity {

    private final String TAG = "ClientRegisterActivity";

    private Toolbar toolbar;
    private MaterialSpinner genderSpinner;
    private EditText firstName, middleName, surname, phone, ward, village, hamlet, weight, mwenyekiti, careTakerName, careTakerPhone, careTakerRelationship, chbsNumber, ctcNumber;
    private Button btnCancel;
    private TextView dateOfBirth, saveButtonText;
    private CheckBox pregnant;
    private ProgressDialog dialog;
    private RelativeLayout pregnantWrap, saveButtonContainer;
    private CircularProgressView circularProgressView;

    private Date dob;
    private String strFname, strMname, strSurname, strGender, strPhone, strWard, strVillage, strHamlet, strCbhsNumber, strCTCNumber;
    private String strDateOfBirth, strWeight, strMwenyekiti, strCareTakerName, strCareTakerPhone, strCareTakerRelationship;
    private boolean isPregnant;
    private boolean isTbClient = false;
    private Calendar dobCalendar;

    private Endpoints.PatientServices patientServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opd_client_registration_activity);
        setupview();

        if (getIntent().getExtras() != null){
            isTbClient = getIntent().getBooleanExtra("isTbClient", false);
        }

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dialog = new ProgressDialog(ClientRegisterActivity.this, 0);
        dialog.setTitle(getResources().getString(R.string.saving));
        dialog.setMessage(getResources().getString(R.string.loading_please_wait));

        patientServices = ServiceGenerator.createService(Endpoints.PatientServices.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());

        //dialog = ProgressDialog.show(TbRegisterActivity.this, "Saving",
        //        "Loading. Please wait...", true);

        final String[] genders = {MALE, FEMALE};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, genders);
        spinAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        genderSpinner.setAdapter(spinAdapter);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(getFragmentManager(),"dateOfBirth");
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        dateOfBirth.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        dobCalendar = Calendar.getInstance();
                        dobCalendar.set(year, monthOfYear, dayOfMonth);
                        dob = dobCalendar.getTime();
                    }

                });
            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    pregnantWrap.setVisibility(View.INVISIBLE);
                }else {
                    pregnantWrap.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButtonContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonText.setVisibility(View.GONE);
                circularProgressView.setVisibility(View.VISIBLE);
                if (getInputs()){
                    saveData();
                }else {
                    saveButtonText.setVisibility(View.VISIBLE);
                    circularProgressView.setVisibility(View.GONE);
                    Toast.makeText(ClientRegisterActivity.this,
                            "Weka taarifa zote kabla ya kuhifadhi taarifa",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private boolean getInputs(){

        if (firstName.getText().toString().isEmpty()){
            return false;
        }else {
            strFname = firstName.getText().toString();
        }

        strMname = middleName.getText().toString().isEmpty() ? "" : middleName.getText().toString();

        strSurname = surname.getText().toString().isEmpty() ? "" : surname.getText().toString();

        if (genderSpinner.getSelectedItemPosition() == -1){
            return false;
        }else {
            strGender = (String) genderSpinner.getSelectedItem();
        }

        strPhone = phone.getText().toString().isEmpty() ? "" : phone.getText().toString();

        strWard = ward.getText().toString().isEmpty() ? "" : ward.getText().toString();

        strVillage = village.getText().toString().isEmpty() ? "" : village.getText().toString();

        strHamlet = hamlet.getText().toString().isEmpty() ? "" : hamlet.getText().toString();

        strDateOfBirth = dateOfBirth.getText().toString();

        isPregnant = pregnant.isChecked();

        strWeight = weight.getText().toString().isEmpty()? "0.0" : weight.getText().toString();

        strMwenyekiti = mwenyekiti.getText().toString();

        strCareTakerName = careTakerName.getText().toString();

        strCareTakerPhone = careTakerPhone.getText().toString();

        strCareTakerRelationship = careTakerRelationship.getText().toString();

        strCbhsNumber = chbsNumber.getText().toString();

        strCTCNumber = ctcNumber.getText().toString();

        return true;
    }

    private void saveData(){
        Patient patient = new Patient();
        TbPatient tbPatient = new TbPatient();

        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);

        patient.setId(number);
        patient.setPatientId(number+"");
        patient.setGender(strGender);
        patient.setHamlet(strHamlet);
        patient.setVillage(strVillage);
        patient.setWard(strWard);
        patient.setPhone_number(strPhone);
        patient.setPatientFirstName(strFname);
        patient.setPatientMiddleName(strMname);
        patient.setPatientSurname(strSurname);
        patient.setCurrentOnTbTreatment(isTbClient);//This value depends on where the client has been registered from
        patient.setDateOfBirth(dobCalendar.getTimeInMillis());
        patient.setCareTakerName(strCareTakerName);
        patient.setCareTakerPhoneNumber(strCareTakerPhone);
        patient.setCareTakerRelationship(strCareTakerRelationship);
        patient.setCbhs(strCbhsNumber);
        patient.setCtcNumber(strCTCNumber);

        savePatientEndpointCall(patient);

    }

    private void setupview(){

        circularProgressView =  findViewById(R.id.progress_view);
        circularProgressView.stopAnimation();
        circularProgressView.setVisibility(View.GONE);

        pregnantWrap =  findViewById(R.id.pregnant_wrap);

        toolbar =  findViewById(R.id.toolbar);
        genderSpinner =  findViewById(R.id.spin_gender);

        //TextViews
        dateOfBirth =  findViewById(R.id.date_of_birth);
        saveButtonText = findViewById(R.id.save_button_text);

        //EditTexts
        firstName =  findViewById(R.id.fname_et);
        middleName =  findViewById(R.id.mname_et);
        surname =  findViewById(R.id.surname_et);
        phone =  findViewById(R.id.phone_number);
        ward =  findViewById(R.id.ward);
        village =  findViewById(R.id.village);
        hamlet =  findViewById(R.id.hamlet);
        weight =  findViewById(R.id.weight);
        mwenyekiti =  findViewById(R.id.mwenyekiti);
        careTakerName =  findViewById(R.id.care_taker_name);
        careTakerPhone =  findViewById(R.id.care_taker_phone);
        careTakerRelationship =  findViewById(R.id.care_taker_relationship);

        chbsNumber =  findViewById(R.id.cbhs_number);
        ctcNumber =  findViewById(R.id.patient_ctc_number);

        pregnant =  findViewById(R.id.is_pregnant);

        saveButtonContainer = findViewById(R.id.save_button_container);
        btnCancel = findViewById(R.id.cancel_button);
    }

    private void savePatientEndpointCall(Patient _patient){

        Call call = patientServices.postPatient(session.getServiceProviderUUID(), getPatientRequestBody(_patient, session.getKeyHfid()));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                //Store Received Patient Information, TbPatient as well as PatientAppointments
                if (response.body() != null){
                    Patient patient = (Patient) response.body();
                    Log.d(TAG, patient.getPatientFirstName());
                    new AsyncTask<Patient, Void, Void>(){
                        Patient p;
                        @Override
                        protected Void doInBackground(Patient... patients) {
                            p = patients[0];
                            baseDatabase.patientModel().addPatient(p);
                            return null;
                        }
                        /**
                         * On Post Execute call the Patient info summary Activity
                         * @param aVoid
                         */
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            patientSummary(p);
                        }
                    }.execute(patient);

                }else {
                    Log.d(TAG,"Patient Responce is null "+response.body());
                    savePatientToPostOffice _savePatientToPostOffice = new savePatientToPostOffice(baseDatabase);
                    _savePatientToPostOffice.execute(_patient);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("patient_response", t.getMessage());
                //If saving patient to server has failed store patient to postman and proceed
                savePatientToPostOffice _savePatientToPostOffice = new savePatientToPostOffice(baseDatabase);
                _savePatientToPostOffice.execute(_patient);
            }
        });

    }

    /**
     * This will call the patient details summary screen after the user have been saved successfully
     * @param patient
     */
    void patientSummary(Patient patient){

        //
        saveButtonText.setVisibility(View.VISIBLE);
        circularProgressView.setVisibility(View.GONE);

        Intent intent = new Intent(ClientRegisterActivity.this, PatientDetailsActivity.class);
        intent.putExtra("service", OPD_SERVICE_ID);
        intent.putExtra("patient", patient);
        startActivity(intent);
        finish();
    }

    class savePatientToPostOffice extends AsyncTask<Patient, Void, Void>{

        AppDatabase database;
        Patient p;

        savePatientToPostOffice(AppDatabase db){
            this.database = db;
        }

        @Override
        protected Void doInBackground(Patient... args) {

            p = args[0];

            PostOffice po = new PostOffice();
            po.setPost_id(p.getPatientId());
            po.setPost_data_type(POST_DATA_TYPE_PATIENT);
            po.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(po);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            patientSummary(p);
        }

    }

}
