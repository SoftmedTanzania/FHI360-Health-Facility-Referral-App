package apps.softmed.com.hfreferal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.WrapContentHeightViewPager;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PatientAppointment;
import apps.softmed.com.hfreferal.dom.objects.TbEncounters;
import apps.softmed.com.hfreferal.dom.objects.TbPatient;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEFARIKI;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEHAMA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEMALIZA_TIBA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEPONA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMETOROKA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_HAKUPONA;
import static apps.softmed.com.hfreferal.utils.constants.TB_1_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_2_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_3_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_NEGATIVE;
import static apps.softmed.com.hfreferal.utils.constants.TB_SCANTY;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_1;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_2;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_3;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_4;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_5;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientDetailsActivity extends BaseActivity {

    private LinearLayout matokeoLinearLayout;
    private RelativeLayout finishedPreviousMonthLayout;
    private MaterialSpinner matibabuSpinner, matokeoSpinner, makohoziSpinner, encouterMonthSpinner;
    private TextView patientNames, patientGender, patientAge, patientWeight, phoneNumber;
    private TextView ward, village, hamlet, medicationStatusTitle;
    private EditText xray, otherTests, outcomeDetails;
    private Button saveButton;
    public ProgressDialog dialog;
    private CheckBox medicationStatusCheckbox;

    private Patient currentPatient;
    private TbPatient currentTbPatient;

    private boolean patientNew;
    private String strMatibabu, strXray, strVipimoVingine, strMakohozi, strMonth, strOutcome, strOutcomeDetails, strOutcomeDate;
    private int medicatonStatus = -1;
    private int encounterMonth;
    private Context context;
    private String[] treatmentTypes = {TREATMENT_TYPE_1, TREATMENT_TYPE_2, TREATMENT_TYPE_3, TREATMENT_TYPE_4, TREATMENT_TYPE_5};
    private final String[] tbTypes = {TB_NEGATIVE, TB_SCANTY, TB_1_PLUS, TB_2_PLUS, TB_3_PLUS};
    private ArrayAdapter<String> makohoziSpinnerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_client_details);
        setupviews();

        context = this;

        dialog = new ProgressDialog(TbClientDetailsActivity.this, 0);
        dialog.setTitle("Saving");
        dialog.setMessage("Loading. Please wait...");

        if (getIntent().getExtras() != null){
            currentPatient = (Patient) getIntent().getSerializableExtra("patient");
            patientNew = (Boolean) getIntent().getBooleanExtra("isPatientNew", false);
            if (currentPatient != null){

                String names = currentPatient.getPatientFirstName()+
                        " "+ currentPatient.getPatientMiddleName()+
                        " "+ currentPatient.getPatientSurname();

                patientNames.setText(names);
                patientGender.setText(currentPatient.getGender());
                patientAge.setText(currentPatient.getDateOfBirth()+"");
                patientWeight.setText("");
                phoneNumber.setText(currentPatient.getPhone_number()==""? "" : currentPatient.getPhone_number());
                ward.setText(currentPatient.getWard()==""? "" : currentPatient.getWard());
                village.setText(currentPatient.getVillage() == "" ? "" : currentPatient.getVillage());
                hamlet.setText(currentPatient.getHamlet() == "" ? "" : currentPatient.getHamlet());

                GetTbPatient getTbPatient = new GetTbPatient(baseDatabase, patientNew);
                getTbPatient.execute(currentPatient.getPatientId());

            }

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

        final String[] encounterMonths = {"Kabla ya Matibabu", "Month 2", "Month 3", "Month 4", "Month 5", "Month 6", "Month 7", "Month 8"};
        ArrayAdapter<String> encounterMonthSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, encounterMonths);
        encounterMonthSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        encouterMonthSpinner.setAdapter(encounterMonthSpinnerAdapter);

        makohoziSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, tbTypes);
        makohoziSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        makohoziSpinner.setAdapter(makohoziSpinnerAdapter);

        encouterMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentTbPatient == null){
                    Toast.makeText(TbClientDetailsActivity.this, "Tafadhali subiri data zinachakatuliwa", Toast.LENGTH_LONG).show();
                }else {

                    makohoziSpinner.setEnabled(true);

                    if (i >= 5)
                        matokeoLinearLayout.setVisibility(View.VISIBLE);
                    else
                        matokeoLinearLayout.setVisibility(View.GONE);

                    if (i == 0)
                        finishedPreviousMonthLayout.setVisibility(View.GONE);
                    else
                        finishedPreviousMonthLayout.setVisibility(View.VISIBLE);

                    GetEncounterDetails getEncounterDetails = new GetEncounterDetails(baseDatabase);
                    Log.d("Billions", "About to Get Encounters for month "+(i+1));
                    getEncounterDetails.execute((i+1)+"", currentTbPatient.getTempID()+"");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validData()){
                    saveData();
                }
            }
        });

    }

    private void setupviews(){

        matokeoLinearLayout = (LinearLayout) findViewById(R.id.matokep_ll);
        matokeoLinearLayout.setVisibility(View.GONE);

        finishedPreviousMonthLayout = (RelativeLayout) findViewById(R.id.finished_previous_month_layout);

        xray = (EditText) findViewById(R.id.xray_value);
        otherTests = (EditText) findViewById(R.id.other_tests_value);
        outcomeDetails = (EditText) findViewById(R.id.other_information);

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

        matibabuSpinner = (MaterialSpinner) findViewById(R.id.spin_matibabu);
        matokeoSpinner = (MaterialSpinner) findViewById(R.id.spin_matokeo);
        encouterMonthSpinner = (MaterialSpinner) findViewById(R.id.spin_encounter_month);
        makohoziSpinner = (MaterialSpinner) findViewById(R.id.spin_makohozi);

        medicationStatusCheckbox = (CheckBox) findViewById(R.id.medication_status);

    }

    private boolean validData(){

        if (patientNew){

            if (matibabuSpinner.getSelectedItemPosition() == -1){
                Toast.makeText(context, "Tafadhali Jaza Aina ya matibabu", Toast.LENGTH_LONG).show();
                return false;
            }else {
                strMatibabu = (String) matibabuSpinner.getSelectedItem();
                Log.d("BILLION", "Matibabu selected  : "+matibabuSpinner.getSelectedItem());
            }
            if (xray.getText().toString().equals("")){
                Toast.makeText(context, "Tafadhali Jaza Aina ya matibabu", Toast.LENGTH_LONG).show();
                return false;
            }else {
                strXray = xray.getText().toString();
            }

            strVipimoVingine = otherTests.getText().toString();

            if (makohoziSpinner.getSelectedItemPosition() == -1){
                Toast.makeText(
                        context,
                        "Tafadhali jaza hali ya makohozi ya mgonjwa",
                        Toast.LENGTH_LONG
                ).show();
                return false;
            }

        }else {

            if (makohoziSpinner.getSelectedItemPosition() == -1){
                Toast.makeText(
                        context,
                        "Tafadhali jaza hali ya makohozi ya mgonjwa",
                        Toast.LENGTH_LONG
                ).show();
                return false;
            }

        }

        return true;
    }

    private void saveData(){

        if (patientNew){
            currentTbPatient.setOtherTests(strVipimoVingine);
            currentTbPatient.setTreatment_type((String) matibabuSpinner.getSelectedItem());
            currentTbPatient.setXray(strXray);
            currentTbPatient.setMakohozi(strMakohozi);
            saveTbPatient();
        }else {
            saveEncounters();
        }
    }

    public void setEncounterValues(int month, String cough, int mediStatus){
        encounterMonth = month;
        strMakohozi = cough;
        medicatonStatus = mediStatus;

        //TODO:set previous month medication status


    }

    private void saveTbPatient(){
        SaveTbPatientTask saveTbPatientTask = new SaveTbPatientTask(baseDatabase);
        saveTbPatientTask.execute(currentTbPatient);
    }

    private void saveEncounters(){

        TbEncounters tbEncounter = new TbEncounters();
        tbEncounter.setEncounterMonth((encouterMonthSpinner.getSelectedItemPosition())+"");
        tbEncounter.setMakohozi((String) makohoziSpinner.getSelectedItem());
        int hasFinishedPreviousMonth = -1;
        if (medicationStatusCheckbox.isChecked())
            hasFinishedPreviousMonth = 1;
        else
            hasFinishedPreviousMonth = 0;
        tbEncounter.setHasFinishedPreviousMonthMedication(hasFinishedPreviousMonth);

        //This is the medication Status of this encounter to be set on the next visit
        tbEncounter.setMedicationDate("");
        tbEncounter.setMedicationStatus(false);

        //Generate Appointment Schedule and assign temporary appointment ID
        tbEncounter.setAppointmentId("");
        tbEncounter.setAppointmentDate("");
        tbEncounter.setTbPatientID(currentTbPatient.getTempID()+"");

        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();

        tbEncounter.setCreatedAt(today+"");
        tbEncounter.setUpdatedAt(today+"");

        SaveEncounters saveEncounters = new SaveEncounters(baseDatabase);
        saveEncounters.execute(tbEncounter);

    }

    private void clearFields(){
        makohoziSpinner.setSelection(0);
        makohoziSpinner.setEnabled(true);
        medicationStatusCheckbox.setChecked(false);
        medicationStatusCheckbox.setEnabled(true);
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

            List<TbEncounters> allEncounters = database.tbEncounterModelDao().getEncounterByPatientID(Long.parseLong(strings[1]));
            Log.d("Billions", "All Encounters Size "+allEncounters.size());

            List<TbEncounters> encounter = database.tbEncounterModelDao().getMonthEncounter(strings[0], strings[1]);
            if (encounter.size() > 0)
                return encounter.get(0);
            else
                return null;
        }
    }

    class GetTbPatient extends AsyncTask<String, Void, TbPatient> {

        AppDatabase database;
        boolean isPatientNew;
        List<TbEncounters> listOfPatientEncounters;

        GetTbPatient(AppDatabase db, boolean patientNew){
            this.database = db;
            this.isPatientNew = patientNew;
        }

        @Override
        protected TbPatient doInBackground(String... params) {

            TbPatient tbPatient = database.tbPatientModelDao().getTbPatientById(params[0]);
            currentTbPatient = tbPatient;

            return tbPatient;

        }

        @Override
        protected void onPostExecute(TbPatient tbPatient) {
            super.onPostExecute(tbPatient);
            if (!patientNew){

                xray.setText(tbPatient.getXray());
                xray.setEnabled(false);
                otherTests.setText(tbPatient.getOtherTests());
                otherTests.setEnabled(false);
                patientWeight.setText(tbPatient.getWeight()+"");

                for (int i=0; i<treatmentTypes.length; i++){
                    if (treatmentTypes[i].equals(tbPatient.getTreatment_type())){
                        matibabuSpinner.setSelection(i+1);
                        matibabuSpinner.setEnabled(false);
                    }
                }

            }
            patientWeight.setText(tbPatient.getWeight()+"");
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
            saveEncounters();
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

            //Save previous month medication status
            int previousmonth = Integer.parseInt(encounters[0].getEncounterMonth()) - 1;
            List<TbEncounters> encounters1 = database.tbEncounterModelDao().getMonthEncounter(previousmonth+"", currentTbPatient.getTempID()+"");

            boolean previousMonthStatus = false;
            if (encounters[0].getHasFinishedPreviousMonthMedication() == 1){
                previousMonthStatus = true;
            }else {
                previousMonthStatus = false;
            }

            if (encounters1.size() > 0){
                encounters1.get(0).setMedicationStatus(previousMonthStatus);

                Calendar calendar = Calendar.getInstance();
                long today = calendar.getTimeInMillis();
                encounters1.get(0).setMedicationDate(today+"");

                Log.d("Billions", "Saving Previous Encounter Medication Status");
                database.tbEncounterModelDao().updatePreviousMonthMedicationStatus(encounters1.get(0));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new RegenerateAppointments(baseDatabase).execute(Integer.parseInt(currentEncounter.getEncounterMonth()));
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
            Intent intent = new Intent(context, TbClientListActivity.class);
            startActivity(intent);
            finish();
            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Integer... encounterMonth) {

            encMonth = encounterMonth[0];

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
                    if (remainingAppointments.get(i).getAppointmentDate().after(new Date())){
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
                calendar.add(Calendar.DATE, 30);//Adding 30 days to the next Appointment
                Date nextAppointment = calendar.getTime();

                appointment.setAppointmentDate(nextAppointment);
                appointment.setAppointmentEncounterMonth((i+1)+"");
                appointment.setCreatedAt(new Date());
                appointment.setUpdatedAt(new Date());

                long range = 1234567L;
                Random r = new Random();
                long number = (long)(r.nextDouble()*range);

                appointment.setAppointmentID(number);
                appointment.setPatientID(currentPatient.getPatientId());

                database.appointmentModelDao().addAppointment(appointment);

                Log.d("Billions", "Saving appointment for "+simpleDateFormat.format(appointment.getAppointmentDate()));

            }

            return null;
        }

    }

}
