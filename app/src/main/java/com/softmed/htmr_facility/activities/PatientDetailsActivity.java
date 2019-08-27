package com.softmed.htmr_facility.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Client;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.fragments.IssueReferralDialogueFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.softmed.htmr_facility.utils.constants.FEMALE;
import static com.softmed.htmr_facility.utils.constants.FEMALE_SW;
import static com.softmed.htmr_facility.utils.constants.FEMALE_VALUE;
import static com.softmed.htmr_facility.utils.constants.MALE;
import static com.softmed.htmr_facility.utils.constants.MALE_SW;
import static com.softmed.htmr_facility.utils.constants.MALE_VALUE;

/**
 * Created by issy on 06/02/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class PatientDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Button cancelButton, referButton;
    private TextView clientNames, clientAge, clientGender, clientVEO, clientWard, clientVillage, clientMapCue, careTakerName, careTakerPhone, careTakerRelationship, clientPhone;
    private ImageView editUserButton;
    private int service;
    private Patient currentPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        setupviews();

        if (getIntent().getExtras() != null){
            service = getIntent().getIntExtra("service", 0);
            currentPatient = (Patient) getIntent().getSerializableExtra("patient");
        }

        if (currentPatient != null){

            try {
                Calendar cal = Calendar.getInstance();
                Calendar today = Calendar.getInstance();
                cal.setTimeInMillis(currentPatient.getDateOfBirth());

                int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                Integer ageInt = new Integer(age);
                clientAge.setText(ageInt.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            clientNames.setText(currentPatient.getPatientFirstName()+" "+currentPatient.getPatientSurname());

            if (BaseActivity.getLocaleString().endsWith(ENGLISH_LOCALE)){
                if (currentPatient.getGender().equals(MALE) || currentPatient.getGender().equals(MALE_VALUE)){
                    clientGender.setText(MALE);
                }else if (currentPatient.getGender().equals(FEMALE) || currentPatient.getGender().equals(FEMALE_VALUE)){
                    clientGender.setText(FEMALE);
                }
            }else {
                try {
                    if (currentPatient.getGender().equals(MALE) || currentPatient.getGender().equals(MALE_VALUE)) {
                        clientGender.setText(MALE_SW);
                    } else if (currentPatient.getGender().equals(FEMALE) || currentPatient.getGender().equals(FEMALE_VALUE)) {
                        clientGender.setText(FEMALE_SW);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            clientVillage.setText(currentPatient.getVillage() == null?"n/a":currentPatient.getVillage());
            clientWard.setText(currentPatient.getWard() == null ? "n/a" : currentPatient.getWard());
            clientPhone.setText(currentPatient.getPhone_number() == null ? "n/a" : currentPatient.getPhone_number());
            clientVEO.setText("");

            careTakerName.setText(currentPatient.getCareTakerName() == null ? "n/a" : currentPatient.getCareTakerName());
            careTakerPhone.setText(currentPatient.getCareTakerPhoneNumber() == null ? "n/a" : currentPatient.getCareTakerPhoneNumber());
            careTakerRelationship.setText(currentPatient.getCareTakerRelationship() == null ? "n/a" : currentPatient.getCareTakerRelationship());

        }

    }


    @Override
    public void onClick(View view) {
        Context context = PatientDetailsActivity.this;
        switch (view.getId()){
            case R.id.edit_user:
                AlertView alert = new AlertView("Edit Current User Informations", "Are you sure you want to edit this user's details?", AlertStyle.DIALOG);
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    editUserDetails(currentPatient);
                }));
                alert.show(PatientDetailsActivity.this);
                break;

            case R.id.cancel_button:
                finish();
                break;

            case R.id.referal_button:
                AlertView referralAllert = new AlertView(context.getResources().getString(R.string.issue_referral), context.getResources().getString(R.string.issue_referral_prompt), AlertStyle.DIALOG);
                referralAllert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                referralAllert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    callReferralFragmentDialogue(currentPatient);
                }));
                referralAllert.show(PatientDetailsActivity.this);
                break;
        }
    }


    private void editUserDetails(Patient patient){
        Intent intent = new Intent(this, ClientRegisterActivity.class);
        intent.putExtra(ClientRegisterActivity.ORIGIN, ClientRegisterActivity.SOURCE_UPDATE);
        intent.putExtra(ClientRegisterActivity.CURRENT_PATIENT, patient);
        startActivity(intent);
    }

    private void setupviews(){
        cancelButton =  findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        referButton =  findViewById(R.id.referal_button);
        referButton.setOnClickListener(this);

        clientAge =  findViewById(R.id.client_age_value);
        clientNames =  findViewById(R.id.client_name);
        clientGender =  findViewById(R.id.patient_gender_value);
        clientVEO  =  findViewById(R.id.mwenyekiti_name_value);
        clientWard =  findViewById(R.id.client_kata_value);
        clientVillage =  findViewById(R.id.client_kijiji_value);
        clientPhone = findViewById(R.id.client_phone);
        careTakerName =  findViewById(R.id.care_taker_name);
        careTakerPhone =  findViewById(R.id.care_taker_phone);
        careTakerRelationship =  findViewById(R.id.care_taker_relationship);

        editUserButton = findViewById(R.id.edit_user);
        editUserButton.setOnClickListener(this);

    }

    private void callReferralFragmentDialogue(Patient patient){
        FragmentManager fm = this.getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, service, UUID.randomUUID()+"");
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

}
