package com.softmed.htmr_facility.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.PostOffice;
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
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
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
    TextView ward, village, hamlet, medicationStatusTitle, resultsDate;
    EditText outcomeDetails, otherTestValue, monthlyPatientWeightEt;
    Button saveButton;
    ProgressDialog dialog;
    CheckBox medicationStatusCheckbox;
    ToggleSwitch testTypeToggle;
    View encounterUI, testUI, treatmentUI, resultsUI, demographicUI, clickBlocker;

    Patient currentPatient;
    TbPatient currentTbPatient;
    TbEncounters currentPatientEncounter;

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
            currentPatient = (Patient) getIntent().getSerializableExtra("patient");
            patientNew = (Boolean) getIntent().getBooleanExtra("isPatientNew", false);
        }

        calibrateUI(patientNew);

        if (currentPatient != null){

            String names = currentPatient.getPatientFirstName()+
                    " "+ currentPatient.getPatientMiddleName()+
                    " "+ currentPatient.getPatientSurname();

            patientNames.setText(names);
            patientGender.setText(currentPatient.getGender());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentPatient.getDateOfBirth());

            patientAge.setText(getDiffYears(calendar.getTime(), new Date())+"");
            phoneNumber.setText(currentPatient.getPhone_number()==""? "" : currentPatient.getPhone_number());
            ward.setText(currentPatient.getWard()==""? "" : currentPatient.getWard());
            village.setText(currentPatient.getVillage() == "" ? "" : currentPatient.getVillage());
            hamlet.setText(currentPatient.getHamlet() == "" ? "" : currentPatient.getHamlet());
            patientWeight.setText(""); //save patient weight in patient object so as to be able to display it here

            new GetTbPatientByPatientID(baseDatabase).execute(currentPatient.getPatientId());

        }

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

                    new GetEncounterDetails(baseDatabase).execute((i+1)+"", currentTbPatient.getTempID()+"");
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

                    if (saveTestData()){
                        Log.d("saveTestData", "Saved test data!");
                        if (saveTreatmentData()){
                            Log.d("saveTreatmentData", "Saved treatment data!");
                            new SaveTbPatientTask(baseDatabase).execute(currentTbPatient);
                        }
                    }
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

    private void setupviews(){
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
        saveButton = (Button) findViewById(R.id.hifadhi_taarifa);
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
        matibabuSpinner = (MaterialSpinner) findViewById(R.id.spin_matibabu);
        matokeoSpinner = (MaterialSpinner) findViewById(R.id.spin_matokeo);
        encouterMonthSpinner = (MaterialSpinner) findViewById(R.id.spin_encounter_month);
        makohoziSpinner = (MaterialSpinner) findViewById(R.id.spin_makohozi);
        monthOneMakohoziSpinner = (MaterialSpinner) findViewById(R.id.spin_makohozi_month_one);
        medicationStatusCheckbox = (CheckBox) findViewById(R.id.medication_status);
    }

    private void calibrateUI(boolean b){
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
        TbEncounters tbEncounter = new TbEncounters();
        if (encouterMonthSpinner.getSelectedItemPosition() == 0){
            toastThis("Tafadhali chagua mwezi!");
            return false;
        }else {
            encMonth = encouterMonthSpinner.getSelectedItemPosition();
        }

        if (testType == 1 && (encMonth == 1 || encMonth == 3) ){
            if (makohoziSpinner.getSelectedItemPosition() == 0){
                toastThis("Tafadhali jaza hali ya makohozi ya mgonjwa");
                return false;
            }else {
                makohoziValue = (String) makohoziSpinner.getSelectedItem();
            }
        }

        if (monthlyPatientWeightEt.getText().toString().isEmpty()){
            toastThis("Tafadhali jaza uzito wa mteja.");
            return false;
        }else {
            monthlyWeight = monthlyPatientWeightEt.getText().toString();
        }

        tbEncounter.setEncounterMonth(encMonth);

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
        tbEncounter.setAppointmentId(-1);
        tbEncounter.setScheduledDate(Calendar.getInstance().getTimeInMillis());
        tbEncounter.setTbPatientID(currentTbPatient.getTempID()+"");

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

    class GetEncounterDetails extends AsyncTask<String, Void, TbEncounters>{

        AppDatabase database;

        GetEncounterDetails(AppDatabase db){
            this.database = db;
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
        protected TbEncounters doInBackground(String... strings) {
            Log.d("Billions", "On Encounters background");

            List<TbEncounters> allEncounters = database.tbEncounterModelDao().getEncounterByPatientID(strings[1]);
            Log.d("Billions", "All Encounters Size "+allEncounters.size());

            List<TbEncounters> encounter = database.tbEncounterModelDao().getMonthEncounter(strings[0], strings[1]);
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

                int testType = tbPatient.getTestType();
                clickBlocker.setVisibility(View.VISIBLE);
                if (testType > 0){
                    testTypeToggle.setCheckedTogglePosition(testType-1);
                    testTypeToggle.setFocusableInTouchMode(false);

                    if (testType == 3){
                        otherTestValue.setText(tbPatient.getOtherTestDetails());
                    }else if (testType == 1){
                        for (int i=0; i<tbTypes.length; i++){
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

            List<TbEncounters> encounters1 = database.tbEncounterModelDao().getMonthEncounter(previousmonth+"", currentTbPatient.getTempID()+"");

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
             */
            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(currentPatient.getPatientId());
            postOffice.setPost_data_type(POST_DATA_TYPE_PATIENT);
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

            database.postOfficeModelDao().addPostEntry(postOffice);

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

}
