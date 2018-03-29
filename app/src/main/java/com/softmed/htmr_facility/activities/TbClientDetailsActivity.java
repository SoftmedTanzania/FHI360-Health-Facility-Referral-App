package com.softmed.htmr_facility.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_AMEFARIKI;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_AMEHAMA;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_AMEMALIZA_TIBA;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_AMEPONA;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_AMETOROKA;
import static com.softmed.htmr_facility.utils.constants.MATOKEO_HAKUPONA;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.STATUS_PENDING;
import static com.softmed.htmr_facility.utils.constants.TB_1_PLUS;
import static com.softmed.htmr_facility.utils.constants.TB_2_PLUS;
import static com.softmed.htmr_facility.utils.constants.TB_3_PLUS;
import static com.softmed.htmr_facility.utils.constants.TB_NEGATIVE;
import static com.softmed.htmr_facility.utils.constants.TB_SCANTY;
import static com.softmed.htmr_facility.utils.constants.TREATMENT_TYPE_1;
import static com.softmed.htmr_facility.utils.constants.TREATMENT_TYPE_2;
import static com.softmed.htmr_facility.utils.constants.TREATMENT_TYPE_3;
import static com.softmed.htmr_facility.utils.constants.TREATMENT_TYPE_4;
import static com.softmed.htmr_facility.utils.constants.TREATMENT_TYPE_5;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientDetailsActivity extends BaseActivity {

    LinearLayout matokeoLinearLayout;
    RelativeLayout finishedPreviousMonthLayout, makohoziWrapper, othersWrapper, makohoziEncounterWrap;
    MaterialSpinner matibabuSpinner, matokeoSpinner, makohoziSpinner, encouterMonthSpinner, monthOneMakohoziSpinner;
    TextView patientNames, patientGender, patientAge, patientWeight, phoneNumber;
    TextView ward, village, hamlet, medicationStatusTitle, resultsDate, emptyPreviousMonthEncounter;
    EditText outcomeDetails, otherTestValue, monthlyPatientWeightEt;
    Button saveButton, cancelButton;
    ProgressDialog dialog;
    CheckBox medicationStatusCheckbox;
    ToggleSwitch testTypeToggle;
    View encounterUI, testUI, treatmentUI, resultsUI, demographicUI, clickBlocker;
    RecyclerView previousEncounters;

    Patient currentPatient;
    TbPatient currentTbPatient;
    TbEncounters currentPatientEncounter;
    Referral currentReferral;

    boolean patientNew;
    boolean activityCanExit = false;
    int selectedTestType = 0;
    final String[] tbTypes = {TB_NEGATIVE, TB_SCANTY, TB_1_PLUS, TB_2_PLUS, TB_3_PLUS};
    String strMatibabu, strXray, strVipimoVingine, strMakohozi, strMonth, strOutcome, strOutcomeDetails, strOutcomeDate;
    String otherTestValueString = "";
    String[] treatmentTypes = {TREATMENT_TYPE_1, TREATMENT_TYPE_2, TREATMENT_TYPE_3, TREATMENT_TYPE_4, TREATMENT_TYPE_5};
    ArrayAdapter<String> makohoziSpinnerAdapter, monthOneMakohoziAdapter;
    Context context;
    Calendar resultCalendar;
    long outcomeDate = 0;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_client_details);
        setupviews();

        context = this;

        dialog = new ProgressDialog(TbClientDetailsActivity.this, 0);
        dialog.setTitle(getResources().getString(R.string.saving));
        dialog.setMessage(getResources().getString(R.string.loading_please_wait));

        if (getIntent().getExtras() != null){
            patientNew = (Boolean) getIntent().getBooleanExtra("isPatientNew", false);
            if (patientNew){
                currentReferral = (Referral) getIntent().getSerializableExtra("referral");
                new AsyncTask<String, Void, Void>(){

                    Patient _patient;

                    @Override
                    protected Void doInBackground(String... strings) {
                        _patient = baseDatabase.patientModel().getPatientById(strings[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        displayPatientInformation(_patient);
                    }
                }.execute(currentReferral.getPatient_id());
            }else {
                currentPatient = (Patient) getIntent().getSerializableExtra("patient");
                displayPatientInformation(currentPatient);
            }
        }
        calibrateUI(patientNew);

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, treatmentTypes);
        spinAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        matibabuSpinner.setAdapter(spinAdapter);

        matibabuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("BILLION", "position "+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final String[] matokeo = {MATOKEO_AMEPONA, MATOKEO_AMEMALIZA_TIBA, MATOKEO_AMEFARIKI, MATOKEO_AMETOROKA, MATOKEO_AMEHAMA, MATOKEO_HAKUPONA};
        ArrayAdapter<String> matokepSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, matokeo);
        matokepSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        matokeoSpinner.setAdapter(matokepSpinnerAdapter);

        final String[] encounterMonths = {"Month 1", "Month 2", "Month 3", "Month 4", "Month 5", "Month 6", "Month 7", "Month 8"};
        ArrayAdapter<String> encounterMonthSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, encounterMonths);
        encounterMonthSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        encouterMonthSpinner.setAdapter(encounterMonthSpinnerAdapter);

        makohoziSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, tbTypes);
        makohoziSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        makohoziSpinner.setAdapter(makohoziSpinnerAdapter);

        monthOneMakohoziAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, tbTypes);
        monthOneMakohoziAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        monthOneMakohoziSpinner.setAdapter(makohoziSpinnerAdapter);

        encouterMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentTbPatient == null){
                    Toast.makeText(TbClientDetailsActivity.this, "Tafadhali subiri data zinachakatuliwa", Toast.LENGTH_LONG).show();
                    encouterMonthSpinner.setSelection(0);
                }else {
                    makohoziSpinner.setEnabled(true);

                    //Hide Finished previous month medication for month one
                    if (i == 0){
                        finishedPreviousMonthLayout.setVisibility(View.INVISIBLE);
                    }else {
                        finishedPreviousMonthLayout.setVisibility(View.VISIBLE);
                    }

                    //Enable treatment results capture if encounter month is after the fifth month
                    if (i >= 5)
                        matokeoLinearLayout.setVisibility(View.VISIBLE);
                    else
                        matokeoLinearLayout.setVisibility(View.GONE);

                    //Enable capturing of makohozi weight if test done was through makohozi and if encounter month is month one or three
                    if (currentTbPatient.getTestType() == 1){
                        if (i == 0 || i == 2){
                            makohoziEncounterWrap.setVisibility(View.VISIBLE);
                        } else {
                            makohoziEncounterWrap.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        makohoziEncounterWrap.setVisibility(View.INVISIBLE);
                    }

                    new GetEncounterDetails(baseDatabase, (i+1)+"").execute(currentTbPatient.getHealthFacilityPatientId());
                    Log.d("Billions", "About to Get Encounters for month "+(i+1));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (patientNew){
            selectedTestType = 1;
        }

        testTypeToggle.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 0 && isChecked){
                    makohoziWrapper.setVisibility(View.VISIBLE);
                    othersWrapper.setVisibility(View.GONE);
                    selectedTestType = 1;
                }else if (position == 1 && isChecked){
                    makohoziWrapper.setVisibility(View.GONE);
                    othersWrapper.setVisibility(View.GONE);
                    selectedTestType = 2;
                }else {
                    makohoziWrapper.setVisibility(View.GONE);
                    othersWrapper.setVisibility(View.VISIBLE);
                    selectedTestType = 3;
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (patientNew){
                    //Save Test Information
                    //Save Treatment
                    activityCanExit = true;

                    /**
                     *  Steps:
                     *
                     *  End current referral with default values : Service Given = RECEIVED SUCCESSFULLY
                     *  Add referral entry to postman
                     *  Update Existing Patient Object to CURRENTLY ON TB CLINIC
                     *  Add patient entry to postman
                     *  Create a new TB Client with current patient ID
                     *  Add TbPatient entry to postman
                     *
                     *  Call @TbPatientActivity.java passing current Patient and the newly created TbPatient
                     *
                     */

                    new EnrollPatientToTbClinic(baseDatabase).execute(currentReferral);

                }else {
                    //Save Encounter
                    /*If month >= 6
                     Save Results
                    */
                    if (saveEncounters(currentTbPatient.getTestType())){
                        if (currentPatientEncounter.getEncounterMonth() > 6){
                            //Save Treatment Results;
                            activityCanExit = false;
                            if (saveResults()){
                                Log.d("Billions", "Saving tb patient for outcome");
                                new SaveTbPatientTask(baseDatabase).execute(currentTbPatient);
                            }
                        }else {
                            activityCanExit = true;
                        }
                        new SaveEncounters(baseDatabase).execute(currentPatientEncounter);
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        resultsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(getFragmentManager(),"dateOfBirth");
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        resultsDate.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        resultCalendar = Calendar.getInstance();
                        resultCalendar.set(year, monthOfYear, dayOfMonth);
                        outcomeDate = resultCalendar.getTimeInMillis();
                    }

                });
            }
        });

    }

    void displayPatientInformation(Patient _patient){

        String names = _patient.getPatientFirstName()+
                " "+ _patient.getPatientMiddleName()+
                " "+ _patient.getPatientSurname();

        patientNames.setText(names);
        patientGender.setText(_patient.getGender());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(_patient.getDateOfBirth());

        patientAge.setText(getDiffYears(calendar.getTime(), new Date())+"");
        phoneNumber.setText(_patient.getPhone_number()==""? "" : _patient.getPhone_number());
        ward.setText(_patient.getWard()==""? "" : _patient.getWard());
        village.setText(_patient.getVillage() == "" ? "" : _patient.getVillage());
        hamlet.setText(_patient.getHamlet() == "" ? "" : _patient.getHamlet());
        patientWeight.setText(""); //save patient weight in patient object so as to be able to display it here

        if (!patientNew){
            new GetTbPatientByPatientID(baseDatabase).execute(_patient.getPatientId());
        }

    }

    void setupviews(){
        encounterUI = (View) findViewById(R.id.enconter_ui);
        treatmentUI = (View) findViewById(R.id.treatment_ui);
        testUI = (View) findViewById(R.id.test_ui);
        resultsUI = (View) findViewById(R.id.results_ui);
        demographicUI = (View) findViewById(R.id.demographic_ui);
        clickBlocker = (View) findViewById(R.id.view_click_blocker);
        clickBlocker.setVisibility(View.GONE);
        clickBlocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        testTypeToggle = (ToggleSwitch) findViewById(R.id.test_type);
        matokeoLinearLayout = (LinearLayout) findViewById(R.id.matokep_ll);
        makohoziWrapper = (RelativeLayout) findViewById(R.id.makohozi_wrapper);
        othersWrapper = findViewById(R.id.others_wrapper);
        otherTestValue = (EditText) findViewById(R.id.other_test_value);
        makohoziEncounterWrap = (RelativeLayout) findViewById(R.id.makohozi_encounter_wrap);
        finishedPreviousMonthLayout = (RelativeLayout) findViewById(R.id.finished_previous_month_layout);
        outcomeDetails = (EditText) findViewById(R.id.other_information);
        monthlyPatientWeightEt = (EditText) findViewById(R.id.monthly_uzito_value);
        saveButton = findViewById(R.id.hifadhi_taarifa);
        cancelButton = findViewById(R.id.cancel_button);
        patientNames = (TextView) findViewById(R.id.names_text);
        patientGender = (TextView) findViewById(R.id.gender_text);
        patientAge = (TextView) findViewById(R.id.age_text);
        patientWeight = (TextView) findViewById(R.id.weight_text);
        phoneNumber = (TextView) findViewById(R.id.phone_text);
        ward = (TextView) findViewById(R.id.ward_text);
        village = (TextView) findViewById(R.id.village_text);
        hamlet = (TextView) findViewById(R.id.hamlet_text);
        medicationStatusTitle = (TextView) findViewById(R.id.medication_status_title);
        resultsDate = (TextView) findViewById(R.id.date);
        emptyPreviousMonthEncounter = (TextView) findViewById(R.id.empty_previous_encounters);
        matibabuSpinner = (MaterialSpinner) findViewById(R.id.spin_matibabu);
        matokeoSpinner = (MaterialSpinner) findViewById(R.id.spin_matokeo);
        encouterMonthSpinner = (MaterialSpinner) findViewById(R.id.spin_encounter_month);
        makohoziSpinner = (MaterialSpinner) findViewById(R.id.spin_makohozi);
        monthOneMakohoziSpinner = (MaterialSpinner) findViewById(R.id.spin_makohozi_month_one);
        medicationStatusCheckbox = (CheckBox) findViewById(R.id.medication_status);

        previousEncounters = (RecyclerView) findViewById(R.id.previous_encounters_recycler_view);
        previousEncounters.setLayoutManager(new LinearLayoutManager(this));
        previousEncounters.hasFixedSize();
    }

    void calibrateUI(boolean b){
        if (b){
            encounterUI.setVisibility(View.GONE);
            resultsUI.setVisibility(View.GONE);
        }else {
            encounterUI.setVisibility(View.VISIBLE);
        }
    }

    boolean saveTestData(){
        //Patient Test Variables
        String makohoziWeight = "";
        String otherTestDescription = "";

        TbPatient patient = currentTbPatient;

        Log.d("saveTestData", "Saving test data");

        //Validating input test data
        if (selectedTestType==1){
            if (monthOneMakohoziSpinner.getSelectedItemPosition() == 0){
                Toast.makeText(
                        context,
                        "Tafadhali jaza uzito wa makohozi kabla ya matibabu",
                        Toast.LENGTH_LONG
                ).show();
                Log.d("saveTestData", "Test type is makohozi and weight is not selected");
                return false;
            }else {
                makohoziWeight = (String) monthOneMakohoziSpinner.getSelectedItem();
            }
        }else if (selectedTestType==3){
            if (otherTestValue.getText().toString().isEmpty()){
                Toast.makeText(
                        context,
                        "Tafadhali eleza vipimo vilivyofanyika",
                        Toast.LENGTH_LONG
                ).show();
                Log.d("saveTestData", "Test type is other and there is no description");
                return false;
            }else {
                otherTestDescription = otherTestValue.getText().toString();
            }
        }else if (selectedTestType == 2){
            otherTestDescription = "";
            makohoziWeight = "";
        }
        patient.setTestType(selectedTestType);
        patient.setOtherTestDetails(otherTestDescription);
        patient.setMakohozi(makohoziWeight);
        currentTbPatient = patient;

        Log.d("saveTestData", "Done updating Patient object with test info");

        return true;
    }

    boolean saveTreatmentData (){
        //Patient Treatment Variables
        String selectedMatibabu = "";
        TbPatient patient = currentTbPatient;
        if (matibabuSpinner.getSelectedItemPosition() == 0){
            toastThis("Tafadhali Jaza Aina ya matibabu");
            return false;
        }else {
            selectedMatibabu = (String) matibabuSpinner.getSelectedItem();
        }
        patient.setTreatment_type(selectedMatibabu);
        currentTbPatient = patient;
        return true;
    }

    boolean saveEncounters(int testType){
        //TbEncounter Variables
        String makohoziValue = "";
        String monthlyWeight = "";
        int encMonth;
        int encYear;

        //Encounter Month
        if (encouterMonthSpinner.getSelectedItemPosition() == 0){
            toastThis("Tafadhali chagua mwezi!");
            return false;
        }else {
            encMonth = encouterMonthSpinner.getSelectedItemPosition();
        }

        //Sputum for AFB
        if (testType == 1 && (encMonth == 1 || encMonth == 3) ){
            if (makohoziSpinner.getSelectedItemPosition() == 0){
                toastThis("Tafadhali jaza hali ya makohozi ya mgonjwa");
                return false;
            }else {
                makohoziValue = (String) makohoziSpinner.getSelectedItem();
            }
        }

        //Monthly Weight
        if (monthlyPatientWeightEt.getText().toString().isEmpty()){
            toastThis("Tafadhali jaza uzito wa mteja.");
            return false;
        }else {
            monthlyWeight = monthlyPatientWeightEt.getText().toString();
        }

        TbEncounters tbEncounter = new TbEncounters();
        tbEncounter.setTbPatientID(currentTbPatient.getHealthFacilityPatientId()); //TODO:Revisit | Needs to get encounter based on TbPatientId which is assigned on the server
        tbEncounter.setEncounterMonth(encMonth);
        tbEncounter.setEncounterYear(Calendar.getInstance().get(Calendar.YEAR));
        String newID = tbEncounter.getTbPatientID()+"_"+tbEncounter.getEncounterMonth()+"_"+tbEncounter.getEncounterYear();
        Log.d("EncID", "New ID Structure : "+newID);
        tbEncounter.setId(newID);

        if (testType == 1){
            if (encMonth == 1 || encMonth == 3){
                tbEncounter.setMakohozi(makohoziValue);
            }else {
                tbEncounter.setMakohozi("");
            }
        }
        tbEncounter.setHasFinishedPreviousMonthMedication(medicationStatusCheckbox.isChecked());
        tbEncounter.setWeight(monthlyWeight);

        //This is the medication Status of this encounter to be set on the next visit
        tbEncounter.setMedicationDate(Calendar.getInstance().getTimeInMillis());
        tbEncounter.setMedicationStatus(false);

        //Generate Appointment Schedule and assign temporary appointment ID
        tbEncounter.setAppointmentId(0);
        tbEncounter.setScheduledDate(Calendar.getInstance().getTimeInMillis());

        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();

        tbEncounter.setId(currentPatient.getPatientId()+"_"+encouterMonthSpinner.getSelectedItemPosition());

        currentPatientEncounter = tbEncounter;
        return true;
    }

    boolean saveResults(){

        String outcomeDetailsStr = "";
        String resultsStr = "";
        String dateOfResultsStr = "";

        if (outcomeDetails.getText().toString().isEmpty()){
            toastThis("Tafadhali elezea majibu ya matibabu");
            return false;
        }else {
            outcomeDetailsStr = outcomeDetails.getText().toString();
        }

        if (matokeoSpinner.getSelectedItemPosition() == 0){
            toastThis("Tafadhali chagua matokeo");
            return false;
        }else {
            resultsStr = (String) matokeoSpinner.getSelectedItem();
        }

        if (resultsDate.getText().toString().isEmpty()){
            toastThis("Tafadhali chagua tarehe ya Majibu ya matibabu");
            return false;
        }else {
            dateOfResultsStr = resultsDate.getText().toString();
        }

        currentTbPatient.setOutcome(resultsStr);
        currentTbPatient.setOutcomeDate(outcomeDate);
        currentTbPatient.setOutcomeDetails(outcomeDetailsStr);

        return true;
    }

    private void clearFields(){
        makohoziSpinner.setSelection(0);
        makohoziSpinner.setEnabled(true);
        medicationStatusCheckbox.setChecked(false);
        medicationStatusCheckbox.setEnabled(true);
        monthlyPatientWeightEt.setText("");
        monthlyPatientWeightEt.setEnabled(true);
        medicationStatusTitle.setText("Amemaliza Dawa Za Mwezi Uliopita Kikamilifu");
    }

    class GetPreviousEncounters extends AsyncTask<Long, Void, List<TbEncounters>>{

        @Override
        protected List<TbEncounters> doInBackground(Long... ids) {
            List<TbEncounters> previousEncounters = new ArrayList<>();
            Long tbPatientID = ids[0];

            previousEncounters = baseDatabase.tbEncounterModelDao().getEncounterByPatientID(tbPatientID);

            return previousEncounters;
        }

        @Override
        protected void onPostExecute(List<TbEncounters> tbEncounters) {
            super.onPostExecute(tbEncounters);
            //Display encounters in recycler view
            Log.d("PreviousEncounters", tbEncounters.size()+"");
            if (tbEncounters.size() > 0){
                emptyPreviousMonthEncounter.setVisibility(View.GONE);
                previousEncounters.setVisibility(View.VISIBLE);

                PreviousEncountersRecyclerAdapter adapter = new PreviousEncountersRecyclerAdapter(TbClientDetailsActivity.this, tbEncounters);
                previousEncounters.setAdapter(adapter);

            }else {
                previousEncounters.setVisibility(View.GONE);
                emptyPreviousMonthEncounter.setVisibility(View.VISIBLE);
            }
        }
    }

    class GetEncounterDetails extends AsyncTask<Long, Void, TbEncounters>{

        AppDatabase database;
        String encMonth;

        GetEncounterDetails(AppDatabase db, String encounterMonth){
            this.database = db;
            this.encMonth = encounterMonth;
        }

        @Override
        protected void onPostExecute(TbEncounters encounter) {
            super.onPostExecute(encounter);
            if (encounter != null){

                medicationStatusCheckbox.setChecked(encounter.isMedicationStatus());
                medicationStatusTitle.setText("Alimaliza Dawa za Mwezi Huu Kikamilifu");
                medicationStatusCheckbox.setEnabled(false);

                monthlyPatientWeightEt.setText(encounter.getWeight());
                monthlyPatientWeightEt.setEnabled(false);

                for (int i=0; i<tbTypes.length; i++){
                    if (tbTypes[i].equals(encounter.getMakohozi())){
                        Log.d("Billions", "tb type : "+tbTypes[i]);
                        makohoziSpinner.setSelection(i+1);
                        makohoziSpinner.setEnabled(false);
                    }
                }
            }else {
                Log.d("Billions", "Sorry Encounter is Null");
                clearFields();
            }
        }

        @Override
        protected TbEncounters doInBackground(Long... ids) {
            Log.d("Billions", "On Encounters background");

            List<TbEncounters> allEncounters = database.tbEncounterModelDao().getEncounterByPatientID(ids[0]);
            Log.d("Billions", "All Encounters Size "+allEncounters.size());

            List<TbEncounters> encounter = database.tbEncounterModelDao().getMonthEncounter(encMonth, ids[0]);
            if (encounter.size() > 0)
                return encounter.get(0);
            else
                return null;
        }
    }

    class GetTbPatientByPatientID extends AsyncTask<String, Void, TbPatient>{

        AppDatabase database;

        GetTbPatientByPatientID(AppDatabase db){
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TbPatient doInBackground(String... strings) {

            TbPatient patient = database.tbPatientModelDao().getTbPatientById(strings[0]);
            currentTbPatient = patient;

            return patient;
        }

        @Override
        protected void onPostExecute(TbPatient tbPatient) {
            super.onPostExecute(tbPatient);

            if (tbPatient != null){

                new GetPreviousEncounters().execute(tbPatient.getHealthFacilityPatientId()); //TODO:Revisit

                int testType = tbPatient.getTestType();
                clickBlocker.setVisibility(View.VISIBLE);
                Log.d("EncID", "Test Type Outside = "+testType);

                if (testType > 0){
                    Log.d("EncID", "Test Type = "+testType);

                    testTypeToggle.setCheckedTogglePosition(testType-1);
                    testTypeToggle.setFocusableInTouchMode(false);

                    if (testType == 3){
                        otherTestValue.setText(tbPatient.getOtherTestDetails());
                    }else if (testType == 1){
                        for (int i=0; i<tbTypes.length; i++){

                            Log.d("EncID", "Patient Spatum = "+tbPatient.getMakohozi()+" : Current = "+tbTypes[i]);
                            if (tbPatient.getMakohozi().equals(tbTypes[i])){
                                monthOneMakohoziSpinner.setSelection(i);
                                monthOneMakohoziSpinner.setEnabled(false);
                            }
                        }
                    }
                }

                patientWeight.setText(tbPatient.getWeight()+"" == null ? "" : tbPatient.getWeight()+"");
                for (int i=0; i<treatmentTypes.length; i++){
                    if (treatmentTypes[i].equals(tbPatient.getTreatment_type())){
                        matibabuSpinner.setSelection(i+1);
                    }
                }

                if (patientNew){
                    matibabuSpinner.setEnabled(true);
                    clickBlocker.setVisibility(View.GONE);
                }else {
                    matibabuSpinner.setEnabled(false);
                    clickBlocker.setVisibility(View.VISIBLE);
                }

            }

        }
    }

    class SaveTbPatientTask extends AsyncTask<TbPatient, Void, Void> {

        AppDatabase database;

        SaveTbPatientTask(AppDatabase db){
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
            dialog.setMessage("Loading. Saving patient data...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (activityCanExit){
                Intent intent = new Intent(context, TbClientListActivity.class);
                startActivity(intent);
                finish();
            }else {
                activityCanExit = true;
            }
        }

        @Override
        protected Void doInBackground(TbPatient... tbPatients) {
            database.tbPatientModelDao().updateTbPatient(tbPatients[0]);
            return null;
        }

    }

    class SaveEncounters extends AsyncTask<TbEncounters, Void, Void> {

        AppDatabase database;
        TbEncounters currentEncounter;
        int mEncMonth = 0;

        SaveEncounters(AppDatabase db){
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading. Saving Encounters...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(TbEncounters... encounters) {

            currentEncounter = encounters[0];

            //Save current encounter
            Log.d("Billions", "Saving current Encounter for month "+encounters[0].getEncounterMonth());
            database.tbEncounterModelDao().addEncounter(encounters[0]);

            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(encounters[0].getId());
            postOffice.setPost_data_type(POST_DATA_TYPE_ENCOUNTER);
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

            database.postOfficeModelDao().addPostEntry(postOffice);

            //Save previous month medication status
            int previousmonth = encounters[0].getEncounterMonth() - 1;
            mEncMonth = encounters[0].getEncounterMonth();

            List<TbEncounters> encounters1 = database.tbEncounterModelDao().getMonthEncounter(previousmonth+"", currentTbPatient.getHealthFacilityPatientId());

            boolean previousMonthStatus = encounters[0].isHasFinishedPreviousMonthMedication();

            if (encounters1.size() > 0){
                encounters1.get(0).setMedicationStatus(previousMonthStatus);

                Calendar calendar = Calendar.getInstance();
                long today = calendar.getTimeInMillis();
                encounters1.get(0).setMedicationDate(today);

                database.tbEncounterModelDao().updatePreviousMonthMedicationStatus(encounters1.get(0));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new RegenerateAppointments(baseDatabase).execute(currentEncounter.getEncounterMonth());
        }

    }

    class RegenerateAppointments extends AsyncTask<Integer, Void, Void>{

        int encMonth;
        long now;
        AppDatabase database;

        RegenerateAppointments(AppDatabase db){
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (activityCanExit){
                Intent intent = new Intent(context, TbClientListActivity.class);
                startActivity(intent);
                finish();
            }else {
                activityCanExit = true;
            }
        }

        @Override
        protected Void doInBackground(Integer... encounterMonth) {

            encMonth = encounterMonth[0] == null ? 0 : encounterMonth[0];

            /**
             * Delete Appointments From encMonth to month 8
             */
            Date date = new Date();
            String todayDate = simpleDateFormat.format(date);

            //List<PatientAppointment> remainingAppointments = database.appointmentModelDao().getRemainingAppointments(currentPatient.getPatientId(), todayDate);
            List<PatientAppointment> remainingAppointments = database.appointmentModelDao().getThisPatientAppointments(currentPatient.getPatientId());
            if (remainingAppointments.size() > 0){
                Log.d("Billion", "Remaining appointments size is "+remainingAppointments.size());
                for (int i=0;i<remainingAppointments.size();i++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(remainingAppointments.get(i).getAppointmentDate());
                    if (calendar.getTime().after(new Date())){
                        database.appointmentModelDao().deleteAppointment(remainingAppointments.get(i));
                        Log.d("Billion", "Deleted for "+remainingAppointments.get(i).getAppointmentDate());
                    }
                }
            }else {
                Log.d("Billion", "Remaining appointments size is "+remainingAppointments.size());
            }

            /**
             * Get the current visit month
             * Generate appointments from this month to month 8
             */

            Calendar calendar = Calendar.getInstance();
            now = calendar.getTimeInMillis();
            for (int i = encMonth; i<=8; i++){

                PatientAppointment appointment = new PatientAppointment();
                calendar.add(DATE, 30);//Adding 30 days to the next Appointment

                appointment.setAppointmentDate(calendar.getTimeInMillis());
                appointment.setAppointmentEncounterMonth((i+1)+"");
                appointment.setStatus(STATUS_PENDING);

                long range = 1234567L;
                Random r = new Random();
                long number = (long)(r.nextDouble()*range);

                appointment.setAppointmentID(number);
                appointment.setPatientID(currentPatient.getPatientId());

                database.appointmentModelDao().addAppointment(appointment);

                Log.d("Billions", "Saving appointment for "+simpleDateFormat.format(appointment.getAppointmentDate()));

            }

            /*
            Appointment is the last thing so send patient data to PostOffice

            UPDATED: This sends patient object to server every time an encounter and hence an appointment
            is saved, remove this
            */

            /*
            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(currentPatient.getPatientId());
            postOffice.setPost_data_type(POST_DATA_TYPE_PATIENT);
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

            database.postOfficeModelDao().addPostEntry(postOffice);

            */

            return null;
        }

    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    void toastThis (String toastString){
        Toast.makeText(TbClientDetailsActivity.this, toastString, Toast.LENGTH_LONG).show();
    }

    class PreviousEncountersRecyclerAdapter extends RecyclerView.Adapter<PreviousEncountersRecyclerAdapter.ViewHolder> {

        private List<TbEncounters> mData;
        private LayoutInflater mInflater;
        //private ItemClickListener mClickListener;

        // data is passed into the constructor
        PreviousEncountersRecyclerAdapter(Context context, List<TbEncounters> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.previous_encounters_list_item, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TbEncounters encounter = mData.get(position);
            holder.month.setText(encounter.getEncounterMonth()+"");
            holder.weight.setText(encounter.getWeight());
            holder.spatum.setText(encounter.getMakohozi());
            if (encounter.isMedicationStatus()){
                holder.finishedMedications.setText(context.getResources().getString(R.string.finished));
            }else {
                holder.finishedMedications.setText(context.getResources().getString(R.string.didnt_finished));
                holder.finishedMedications.setTextColor(context.getResources().getColor(R.color.red_500));
            }
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView month, weight, spatum, finishedMedications;

            ViewHolder(View itemView) {
                super(itemView);
                month = itemView.findViewById(R.id.previous_month);
                weight = itemView.findViewById(R.id.previous_weight);
                spatum = itemView.findViewById(R.id.previous_spatum);
                finishedMedications = itemView.findViewById(R.id.finished_previous_medications);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        TbEncounters getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught
        /*
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }*/

        private void updateData(List<TbEncounters> updatedData){
            this.mData = updatedData;
        }

    }

    class EnrollPatientToTbClinic extends AsyncTask<Referral, Void, Void>{

        AppDatabase database;
        Patient patient;
        TbPatient tbPatient;

        EnrollPatientToTbClinic(AppDatabase db){
            this.database = db;
        }

        @Override
        protected Void doInBackground(Referral... referrals) {
            Referral currentReferral = referrals[0];

            //End Current Referral
            currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
            currentReferral.setServiceGivenToPatient(context.getResources().getString(R.string.received_at_tb_clinic));
            currentReferral.setOtherNotesAndAdvices("");
            database.referalModel().updateReferral(currentReferral);

            //Add Post office entry
            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(currentReferral.getReferral_id());
            postOffice.setPost_data_type(POST_DATA_REFERRAL_FEEDBACK);
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(postOffice);

            //Update patient to currently on TB Clinic
            patient = database.patientModel().getPatientById(currentReferral.getPatient_id());
            patient.setCurrentOnTbTreatment(true);
            //TODO : handle CTC Number input at the clinic
            database.patientModel().updatePatient(patient);

            PostOffice patientPost = new PostOffice();
            patientPost.setPost_id(patient.getPatientId());
            patientPost.setPost_data_type(POST_DATA_TYPE_PATIENT);
            patientPost.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(patientPost);

            //Create a new Tb Patient and add to post office
            tbPatient = new TbPatient();
            tbPatient.setTbPatientId(0);
            tbPatient.setHealthFacilityPatientId(Long.parseLong(patient.getPatientId()));
            tbPatient.setTempID(UUID.randomUUID()+"");
            database.tbPatientModelDao().addPatient(tbPatient);
            currentTbPatient = tbPatient;

            PostOffice tbPatientPost = new PostOffice();
            tbPatientPost.setPost_id(patient.getPatientId());
            tbPatientPost.setPost_data_type(POST_DATA_TYPE_TB_PATIENT);
            tbPatientPost.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(tbPatientPost);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (saveTestData()){
                Log.d("saveTestData", "Saved test data!");
                if (saveTreatmentData()){
                    Log.d("saveTreatmentData", "Saved treatment data!");
                    new SaveTbPatientTask(baseDatabase).execute(currentTbPatient);
                }
            }

        }
    }

}
