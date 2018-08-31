package com.softmed.htmr_facility.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.softmed.htmr_facility.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.softmed.htmr_facility.dom.objects.UserData;
import com.softmed.htmr_facility.utils.SessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 11/14/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static Typeface  Avenir_Light;

    public static final String LOCALE_KEY = "localekey";
    public static final String LOCALE_PREF_KEY = "localePref";
    //public static final String ENGLISH_LOCALE = "en-rUS";
    //public static final String SWAHILI_LOCALE = "sw";
    public static final String ENGLISH_LOCALE = "en";
    public static final String SWAHILI_LOCALE = "sw";
    public Retrofit retrofit;
    public Endpoints apiEndpoints;
    public static AppDatabase baseDatabase;
    public Locale locale;
    public static SharedPreferences localeSp;
    // Session Manager Class
    public static SessionManager session;
    final public static DatePickerDialog toDatePicker = new DatePickerDialog();
    final public static DatePickerDialog fromDatePicker = new DatePickerDialog();
    final public static DatePickerDialog datePickerDialog = new DatePickerDialog();
    final public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static String localeString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localeSp = getSharedPreferences(LOCALE_PREF_KEY, MODE_PRIVATE);

        localeString = localeSp.getString(LOCALE_KEY, SWAHILI_LOCALE);
        Log.d("language", "From SP : "+localeString);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (! "".equals(localeString) && ! config.locale.getLanguage().equals(localeString)) {
            Locale locale = new Locale(localeString);
            Locale.setDefault(locale);
            config.locale = locale;
            Log.d("language", "Setting Swahili locale");
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        setupTypeface(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        baseDatabase = AppDatabase.getDatabase(this);

        fromDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        toDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

    }

    public static String getThisFacilityId(){
        return session.getKeyHfid();
    }

    public String getServiceName(int serviceID){
        if (serviceID == HIV_SERVICE_ID){
            return "CTC";
        }else if (serviceID == TB_SERVICE_ID){
            return "Kifua Kikuu";
        }else {
            return "Nyingine";
        }
    }

    public static List<String> getUserRoles(){
        List<String> roles = new ArrayList<>();

        Gson gson = new Gson();
        String[] rolesArray = gson.fromJson(session.getUserRoles(), String[].class);
        if (rolesArray != null && rolesArray.length > 0){
            for (String role : rolesArray){
                roles.add(role);
            }
        }
        return roles;
    }

    public static String getLocaleString(){
        return localeString;
    }

    public static RequestBody getReferralFeedbackRequestBody(Referral referral, UserData userData){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {

            object.put("patientId", referral.getPatient_id());
            object.put("communityBasedHivService", referral.getCommunityBasedHivService());
            object.put("referralReason", referral.getReferralReason());
            object.put("serviceId", referral.getServiceId());
            object.put("ctcNumber", referral.getCtcNumber());
            object.put("referralUUID", referral.getReferralUUID());
            object.put("serviceProviderUIID", referral.getServiceProviderUIID());
            object.put("serviceProviderGroup", referral.getServiceProviderGroup());
            object.put("villageLeader", referral.getVillageLeader());
            object.put("otherClinicalInformation", referral.getOtherClinicalInformation());
            object.put("fromFacilityId", referral.getFromFacilityId());
            object.put("referralSource", referral.getReferralSource());
            object.put("referralType", referral.getReferralType());
            object.put("referralDate", referral.getReferralDate());
            object.put("facilityId", referral.getFacilityId());
            object.put("referralStatus", referral.getReferralStatus());

            JSONArray serviceIndicatorsArray = new JSONArray();

            if (referral.getServiceIndicatorIds() != null){
                for (int i=0; i< referral.getServiceIndicatorIds().size(); i++){
                    long indicator = Long.parseLong(referral.getServiceIndicatorIds().get(i)+"");
                    serviceIndicatorsArray.put(indicator);
                }
            }

            object.put("serviceIndicatorIds", serviceIndicatorsArray);

            object.put("referralId", referral.getReferral_id());
            object.put("serviceGivenToPatient", referral.getServiceGivenToPatient());
            object.put("otherNotes", referral.getOtherNotesAndAdvices());
            object.put("testResults", referral.getTestResults());
            object.put("healthFacilityCode", userData.getUserFacilityId());

            datastream = object.toString();
            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public static RequestBody getTbEncounterRequestBody(TbEncounters encounters){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {

            object.put("tbPatientId", encounters.getTbPatientID());
            object.put("makohozi", encounters.getMakohozi());
            object.put("appointmentId", encounters.getAppointmentId());
            object.put("encounterMonth", encounters.getEncounterNumber());
            object.put("hasFinishedPreviousMonthMedication", encounters.isHasFinishedPreviousMonthMedication());
            object.put("scheduledDate", encounters.getScheduledDate());
            object.put("medicationDate", encounters.getMedicationDate());
            object.put("medicationStatus", encounters.isMedicationStatus());
            object.put("weight", encounters.getWeight());
            object.put("encounterYear", encounters.getEncounterYear());
            object.put("localID", encounters.getLocalID());

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public static RequestBody getReferralRequestBody(Referral referral){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        Log.d("PostOfficeService", new Gson().toJson(referral));

        try {

            int patientID = Integer.parseInt(referral.getPatient_id());

            object.put("patientId", patientID);
            object.put("communityBasedHivService", referral.getCommunityBasedHivService());
            object.put("referralReason", referral.getReferralReason());
            object.put("serviceId", referral.getServiceId());
            object.put("referralUUID", referral.getReferralUUID());
            object.put("serviceProviderUIID", referral.getServiceProviderUIID());
            object.put("serviceProviderGroup", referral.getServiceProviderGroup());
            object.put("villageLeader", referral.getVillageLeader());
            object.put("otherClinicalInformation", referral.getOtherClinicalInformation());
            object.put("otherNotes", referral.getOtherNotesAndAdvices() == null ? "" : referral.getOtherNotesAndAdvices());
            object.put("serviceGivenToPatient", referral.getServiceGivenToPatient() == null ? "" : referral.getServiceGivenToPatient());
            object.put("testResults", referral.getTestResults());
            object.put("fromFacilityId", referral.getFromFacilityId());
            object.put("referralSource", referral.getReferralSource());
            object.put("referralType", referral.getReferralType());
            object.put("referralDate", referral.getReferralDate());
            object.put("facilityId", referral.getFacilityId());
            object.put("referralStatus", referral.getReferralStatus());
            object.put("labTest", referral.getLabTest());

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public static RequestBody getPatientRequestBody(Patient patient, String userFacilityID){

        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {
            object.put("firstName", patient.getPatientFirstName());
            object.put("middleName", patient.getPatientMiddleName());
            object.put("phoneNumber", patient.getPhone_number());
            object.put("ward", patient.getWard());
            object.put("village", patient.getVillage());
            object.put("hamlet", patient.getHamlet());
            object.put("dateOfBirth", patient.getDateOfBirth());
            object.put("surname", patient.getPatientSurname());
            object.put("gender", patient.getGender());
            object.put("currentOnTbTreatment", patient.isCurrentOnTbTreatment());
            object.put("healthFacilityCode", userFacilityID);
            object.put("communityBasedHivService", patient.getCbhs());
            object.put("ctcNumber", patient.getCtcNumber());
            object.put("hivStatus", patient.isHivStatus());
            object.put("careTakerName", patient.getCareTakerName());
            object.put("careTakerPhoneNumber", patient.getCareTakerPhoneNumber());
            object.put("careTakerRelationship", patient.getCareTakerRelationship());

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public static RequestBody getTbPatientRequestBody(Patient patient, TbPatient tbPatient, UserData userData){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {
            object.put("patientId", patient.getPatientId());
            object.put("firstName", patient.getPatientFirstName());
            object.put("middleName", patient.getPatientMiddleName());
            object.put("phoneNumber", patient.getPhone_number());
            object.put("ward", patient.getWard());
            object.put("village", patient.getVillage());
            object.put("hamlet", patient.getHamlet());
            object.put("dateOfBirth", patient.getDateOfBirth());
            object.put("surname", patient.getPatientSurname());
            object.put("gender", patient.getGender());
            object.put("hivStatus", patient.isHivStatus());
            object.put("isPRegnant", false);
            object.put("dateOfDeath", patient.getDateOfDeath());
            object.put("healthFacilityPatientId", 0);
            object.put("patientType", tbPatient.getPatientType());
            object.put("transferType", tbPatient.getTransferType());
            object.put("referralType", tbPatient.getReferralType());
            object.put("testType", tbPatient.getTestType());
            object.put("veo", tbPatient.getVeo());
            object.put("weight", tbPatient.getWeight());
            object.put("xray", tbPatient.getXray());
            object.put("makohozi", tbPatient.getMakohozi());
            object.put("otherTests", tbPatient.getOtherTests());
            object.put("treatment_type", tbPatient.getTreatment_type());
            object.put("outcome", tbPatient.getOutcome());
            object.put("outcomeDate", tbPatient.getOutcomeDate());
            object.put("outcomeDetails", tbPatient.getOutcomeDetails());
            object.put("healthFacilityCode", userData.getUserFacilityId());

            //object.put("healthFacilityCode", "2ff3e6fb-eb85-49eb-b7b3-564ddc26b9d4");
            //object.put("currentOnTbTreatment", patient.isCurrentOnTbTreatment());
            //object.put("communityBasedHivService", patient.getCbhs());
            //object.put("ctcNumber", patient.getCtcNumber());
            //object.put("careTakerName", patient.getCareTakerName());
            //object.put("careTakerPhoneNumber", patient.getCareTakerPhoneNumber());
            //object.put("careTakerRelationship", patient.getCareTakerRelationship());

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;
    }

    public static void setupTypeface(Context ctx){
        //2017
        Avenir_Light    = Typeface.createFromAsset(ctx.getAssets(), "avenir-light.ttf");

    }

    public static String getMonthName(int monthNumber){
        String monthName = "";
        if(localeString.equals(ENGLISH_LOCALE)){
            switch (monthNumber){
                case 1:
                    monthName = "January";
                    break;
                case 2:
                    monthName = "February";
                    break;
                case 3:
                    monthName = "March";
                    break;
                case 4:
                    monthName = "April";
                    break;
                case 5:
                    monthName = "May";
                    break;
                case 6:
                    monthName = "June";
                    break;
                case 7:
                    monthName = "July";
                    break;
                case 8:
                    monthName = "August";
                    break;
                case 9:
                    monthName = "September";
                    break;
                case 10:
                    monthName = "October";
                    break;
                case 11:
                    monthName = "November";
                    break;
                case 12:
                    monthName = "December";
                    break;
            }
        }else if (localeString.equals(SWAHILI_LOCALE)){
            switch (monthNumber){
                case 1:
                    monthName = "Januari";
                break;
                case 2:
                    monthName = "Februari";
                break;
                case 3:
                    monthName = "Machi";
                break;
                case 4:
                    monthName = "Aprili";
                break;
                case 5:
                    monthName = "Mei";
                break;
                case 6:
                    monthName = "Juni";
                break;
                case 7:
                    monthName = "Julai";
                break;
                case 8:
                    monthName = "Agosti";
                break;
                case 9:
                    monthName = "Septemba";
                break;
                case 10:
                    monthName = "Oktoba";
                break;
                case 11:
                    monthName = "Novemba";
                break;
                case 12:
                    monthName = "Disemba";
                break;
            }
        }
        return monthName;
    }

}
