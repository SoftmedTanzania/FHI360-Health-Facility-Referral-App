package apps.softmed.com.hfreferal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.EditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.ENTRY_NOT_SYNCED;
import static apps.softmed.com.hfreferal.utils.constants.FEMALE;
import static apps.softmed.com.hfreferal.utils.constants.MALE;
import static apps.softmed.com.hfreferal.utils.constants.STATUS_COMPLETED;
import static apps.softmed.com.hfreferal.utils.constants.STATUS_NEW;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbRegisterActivity extends BaseActivity {

    private Toolbar toolbar;
    private MaterialSpinner genderSpinner;
    private EditText firstName, middleName, surname, phone, ward, village, hamlet;
    private Button btnSave;
    private TextView dateOfBirth;
    private CheckBox pregnant;

    private Date dob;
    private String strFname, strMname, strSurname, strGender, strPhone, strWard, strVillage, strHamlet, strDateOfBirth;
    private boolean isPregnant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_register);
        setupview();

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                        Calendar fromCalendar = Calendar.getInstance();
                        fromCalendar.set(year, monthOfYear, dayOfMonth);
                        dob = fromCalendar.getTime();
                    }

                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getInputs()){
                    saveData();
                }else {
                    Toast.makeText(TbRegisterActivity.this,
                            "Weka taarifa zote kabla ya kuhifadhi taarifa",
                            Toast.LENGTH_LONG).show();
                }
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

        return true;
    }

    private void saveData(){
        Patient patient = new Patient();

        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);

        patient.setId(number);
        patient.setPatientId("Temporary "+number);

        patient.setGender(strGender);
        patient.setHamlet(strHamlet);
        patient.setVillage(strVillage);
        patient.setWard(strWard);
        patient.setPhone_number(strPhone);
        patient.setPatientFirstName(strFname);
        patient.setPatientMiddleName(strMname);
        patient.setPatientSurname(strSurname);
        patient.setDateOfBirth(strDateOfBirth);
        patient.setCurrentlyPregnant(isPregnant);
        patient.setCurrentOnTbTreatment(true);

        Date today = new Date();
        long now = today.getTime();

        patient.setUpdatedAt(now);

        AddNewPatient addNewPatient = new AddNewPatient(patient, baseDatabase);
        addNewPatient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private void setupview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        genderSpinner = (MaterialSpinner) findViewById(R.id.spin_gender);

        dateOfBirth = (TextView) findViewById(R.id.date_of_birth);

        firstName = (EditText) findViewById(R.id.fname_et);
        middleName = (EditText) findViewById(R.id.mname_et);
        surname = (EditText) findViewById(R.id.surname_et);
        phone = (EditText) findViewById(R.id.phone_number);
        ward = (EditText) findViewById(R.id.ward);
        village = (EditText) findViewById(R.id.village);
        hamlet = (EditText) findViewById(R.id.hamlet);

        pregnant = (CheckBox) findViewById(R.id.is_pregnant);

        btnSave = (Button) findViewById(R.id.save_button);
    }

    class AddNewPatient extends AsyncTask<Void, Void, Void> {

        Patient p;
        AppDatabase database;

        AddNewPatient(Patient patient, AppDatabase db){
            this.p = patient;
            this.database = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            database.patientModel().addPatient(p);

            //TODO: Add patient to PostOffice and set Sync Status

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(TbRegisterActivity.this, TbClientListActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
