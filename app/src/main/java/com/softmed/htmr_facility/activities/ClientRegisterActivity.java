package com.softmed.htmr_facility.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.Gson;
import com.rey.material.widget.EditText;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
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
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.FEMALE;
import static com.softmed.htmr_facility.utils.constants.FEMALE_VALUE;
import static com.softmed.htmr_facility.utils.constants.MALE;
import static com.softmed.htmr_facility.utils.constants.MALE_VALUE;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_APPOINTMENTS;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ClientRegisterActivity extends BaseActivity {

    private final String TAG = "ClientRegisterActivity";
    public static final String ORIGIN = "origin";
    public static final String CURRENT_PATIENT = "current_patient";

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
    private int phoneNumberCounter =  0;

    public static final int SOURCE_UPDATE = 1;
    public static final int SOURCE_CREATE = 0;
    private int activityAction;

    private Endpoints.PatientServices patientServices;
    private Patient currentPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opd_client_registration_activity);
        setupview();

        //Initialization of Data
        dobCalendar = Calendar.getInstance();

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null){
            isTbClient = getIntent().getBooleanExtra("isTbClient", false);
            activityAction = getIntent().getIntExtra(ORIGIN, SOURCE_CREATE);
            currentPatient = (Patient) getIntent().getSerializableExtra(CURRENT_PATIENT);
        }

        if (activityAction == SOURCE_UPDATE){
            saveButtonText.setText(getResources().getString(R.string.btn_update));
            //Display user details and allow editing
            if (currentPatient != null){
                //Patient Exist display patient informations
                displayPatientDetails(currentPatient);
            }else {
                //..Error Patient Object null
            }
        }else if (activityAction == SOURCE_CREATE){
            saveButtonText.setText(getResources().getString(R.string.btn_save));
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

        String genders[] = new String[2];
        if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)){
            genders[0] = "Male";
            genders[1] = "Female";
        }else {
            genders[0] = "Kiume";
            genders[1] = "Kike";
        }
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, genders);
        spinAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        genderSpinner.setAdapter(spinAdapter);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i > 9){
                    phone.setTextColor(getResources().getColor(R.color.red_400));
                    Toast.makeText(ClientRegisterActivity.this, "Phone Number Size Exceeded!", Toast.LENGTH_SHORT).show();
                }

                if (i <= 9){
                    phone.setTextColor(getResources().getColor(R.color.card_title_text));
                }

                if (i == 8){
                    phone.setTextColor(getResources().getColor(R.color.card_title_text));
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(getFragmentManager(),"dateOfBirth");
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        dateOfBirth.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
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

                if (activityAction == SOURCE_CREATE){
                    if (getInputs()){
                        saveData();
                    }else {
                        saveButtonText.setVisibility(View.VISIBLE);
                        circularProgressView.setVisibility(View.GONE);
                        Toast.makeText(ClientRegisterActivity.this,
                                "Weka taarifa zote kabla ya kuhifadhi taarifa",
                                Toast.LENGTH_LONG).show();
                    }
                }else if (activityAction == SOURCE_UPDATE){
                    if (getInputs()){
                        updateData(currentPatient);
                    }else {
                        saveButtonText.setVisibility(View.VISIBLE);
                        circularProgressView.setVisibility(View.GONE);
                        Toast.makeText(ClientRegisterActivity.this,
                                "Weka taarifa zote kabla ya kuhifadhi taarifa",
                                Toast.LENGTH_LONG).show();
                    }
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

    private void displayPatientDetails(Patient patient){

        firstName.setText(patient.getPatientFirstName());
        middleName.setText(patient.getPatientMiddleName());
        surname.setText(patient.getPatientSurname());
        phone.setText(patient.getPhone_number());
        ward.setText(patient.getWard());
        village.setText(patient.getVillage());
        hamlet.setText(patient.getHamlet());
        careTakerName.setText(patient.getCareTakerName());
        careTakerPhone.setText(patient.getCareTakerPhoneNumber());
        careTakerRelationship.setText(patient.getCareTakerRelationship());
        chbsNumber.setText(patient.getCbhs());
        ctcNumber.setText(patient.getCtcNumber());

        dobCalendar.setTimeInMillis(patient.getDateOfBirth());
        dateOfBirth.setText(simpleDateFormat.format(dobCalendar.getTime()));

        Log.d(TAG, "displayPatientDetails: Patient Gender -> "+patient.getGender());

        if (patient.getGender().equals(MALE) || patient.getGender().equals(MALE_VALUE)){
            genderSpinner.setSelection(1);
        }else if (patient.getGender().equals(FEMALE) || patient.getGender().equals(FEMALE_VALUE)){
            genderSpinner.setSelection(2);
        }

    }

    private boolean getInputs(){

        if (firstName.getText().toString().isEmpty()){
            return false;
        }else {
            strFname = firstName.getText().toString();
        }

        strMname = middleName.getText().toString().isEmpty() ? "" : middleName.getText().toString();

        strSurname = surname.getText().toString().isEmpty() ? "" : surname.getText().toString();

        switch (genderSpinner.getSelectedItemPosition()){
            case -1:
                return false;
            case 0:
                strGender = MALE_VALUE;
                break;
            case 1:
                strGender = FEMALE_VALUE;
                break;
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

    private void updateData(Patient patient){

        patient.setGender(strGender);
        patient.setHamlet(strHamlet);
        patient.setVillage(strVillage);
        patient.setWard(strWard);
        patient.setPhone_number(strPhone);
        patient.setPatientFirstName(strFname);
        patient.setPatientMiddleName(strMname);
        patient.setPatientSurname(strSurname);
        patient.setDateOfBirth(dobCalendar.getTimeInMillis());
        patient.setCareTakerName(strCareTakerName);
        patient.setCareTakerPhoneNumber(strCareTakerPhone);
        patient.setCareTakerRelationship(strCareTakerRelationship);
        patient.setCbhs(strCbhsNumber);
        patient.setCtcNumber(strCTCNumber);

        updatePatientEndpointCall(patient);

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
                            saveFirstAppointment(p);
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

    private void updatePatientEndpointCall(Patient patient){
        /*
        Call Server to update current patient details
        If no internet store data locally and add details to post office
         */
        Call<Patient> updatePatient = patientServices.updatePatient(session.getServiceProviderUUID(), getPatientRequestBody(patient, session.getKeyHfid()));
        updatePatient.enqueue(new Callback<Patient>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response != null){
                    if (response.body() != null){

                        Patient updatedPatient = response.body();
                        updatedPatient.setPatientId(patient.getPatientId());

                        Log.d(TAG, "onResponse: "+new Gson().toJson(updatedPatient));

                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                baseDatabase.patientModel().updatePatient(updatedPatient);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                patientSummary(updatedPatient);
                            }
                        }.execute();

                    }else {
                        //Send to postoffice

                        Log.d(TAG, "onResponse: Failed, body Null");

                        savePatientToPostOffice savePatientToPostOffice = new savePatientToPostOffice(baseDatabase);
                        savePatientToPostOffice.execute(patient);
                    }
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

                Log.d(TAG, "onFailure: Message : "+t.getMessage());

                savePatientToPostOffice savePatientToPostOffice = new savePatientToPostOffice(baseDatabase);
                savePatientToPostOffice.execute(patient);
            }
        });

    }


    private void saveFirstAppointment(Patient patient){

        RequestBody requestBody = BaseActivity.getObjectRequestBody(getPatientAppointment(patient));

        Call<PatientAppointment> saveFirstAppointment = patientServices.saveFirstAppointment(requestBody);

        saveFirstAppointment.enqueue(new Callback<PatientAppointment>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<PatientAppointment> call, Response<PatientAppointment> response) {
                if (response != null){
                    if (response.body() != null){
                        Log.d(TAG, "onResponse: Saved first appointment is  : "+new Gson().toJson(response.body()));
                        PatientAppointment patientAppointment = response.body();

                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                baseDatabase.appointmentModelDao().addAppointment(patientAppointment);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                            }
                        }.execute();

                    }else {
                        Log.d(TAG, "onResponse: body is null");
                    }
                }else {
                    Log.d(TAG, "onResponse: Response is null");
                }
            }

            @Override
            public void onFailure(Call<PatientAppointment> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

    private PatientAppointment getPatientAppointment(Patient patient){

        PatientAppointment appointment = new PatientAppointment();

        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);

        appointment.setPatientID(patient.getPatientId());
        appointment.setAppointmentDate(Calendar.getInstance().getTimeInMillis());
        appointment.setAppointmentID(number);
        appointment.setAppointmentType(2);
        appointment.setCancelled(false);
        appointment.set_cancelled(false);
        appointment.setStatus(0);
        appointment.setEncounterNumber(1);
        appointment.setAppointmentEncounterMonth("");

        Log.d(TAG, "getPatientAppointment: Created appointment is "+new Gson().toJson(appointment));

        return appointment;
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

            //Saving the patient data
            PostOffice po = new PostOffice();
            po.setPost_id(p.getPatientId());
            po.setPost_data_type(POST_DATA_TYPE_PATIENT);
            po.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(po);

            //Saving the first appointment
            PatientAppointment appointment = getPatientAppointment(p);
            PostOffice appointmentPostData = new PostOffice();
            appointmentPostData.setPost_id(String.valueOf(appointment.getAppointmentID()));
            appointmentPostData.setPost_data_type(POST_DATA_TYPE_APPOINTMENTS);
            appointmentPostData.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(appointmentPostData);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            patientSummary(p);
        }

    }

}
