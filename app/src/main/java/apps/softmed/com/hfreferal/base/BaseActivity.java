package apps.softmed.com.hfreferal.base;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.objects.TbEncounters;
import apps.softmed.com.hfreferal.dom.objects.TbPatient;
import apps.softmed.com.hfreferal.dom.objects.UserData;
import apps.softmed.com.hfreferal.utils.SessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by issy on 11/14/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static Typeface Julius, Avenir, RobotoCondenced, RobotoCondencedItalic, RobotoThin, RobotoLight, RosarioRegular, RobotoMedium, athletic, FunRaiser;
    public static Typeface CabinSketchRegular, CabinSketchBold;

    public Retrofit retrofit;
    public Endpoints apiEndpoints;

    public static AppDatabase baseDatabase;

    // Session Manager Class
    public static SessionManager session;

    final public static DatePickerDialog toDatePicker = new DatePickerDialog();
    final public static DatePickerDialog fromDatePicker = new DatePickerDialog();
    final public static DatePickerDialog datePickerDialog = new DatePickerDialog();

    final public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setuptypeface();

        // Session class instance
        session = new SessionManager(getApplicationContext());
        baseDatabase = AppDatabase.getDatabase(this);

        fromDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        toDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiEndpoints = retrofit.create(Endpoints.class);

    }

    public static String getThisFacilityId(){
        return session.getKeyHfid();
    }

    public static RequestBody getReferralRequestBody(Referral referral, UserData userData){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {

            object.put("referralId", referral.getReferral_id());
            object.put("patientId", referral.getPatient_id());
            object.put("communityBasedHivService", referral.getCommunityBasedHivService());
            object.put("referralReason", referral.getReferralReason());
            object.put("serviceId", referral.getServiceId());
            object.put("ctcNumber", referral.getCtcNumber());
            object.put("has2WeeksCough", referral.getHas2WeeksCough());
            object.put("hasbloodCough", referral.getHasBloodCough());
            object.put("hasSevereSweating", referral.getHasSevereSweating());
            object.put("hasFever", referral.getHasFever());
            object.put("hadWeightLoss", referral.getHadWeightLoss());
            object.put("serviceProviderUIID", referral.getServiceProviderUIID());
            object.put("serviceProviderGroup", referral.getServiceProviderGroup());
            object.put("villageLeader", referral.getVillageLeader());
            object.put("otherClinicalInformation", referral.getOtherClinicalInformation());
            object.put("otherNotes", referral.getOtherNotesAndAdvices());
            object.put("testResults", "");
            object.put("fromFacilityId", referral.getFromFacilityId());
            object.put("referralSource", referral.getReferralSource());
            object.put("referralDate", referral.getReferralDate());
            object.put("facilityId", referral.getFacilityId());
            object.put("referralStatus", referral.getReferralStatus());

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

    public static RequestBody getPatientRequestBody(Patient patient, TbPatient tbPatient, UserData userData){

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
            //object.put("healthFacilityCode", "2ff3e6fb-eb85-49eb-b7b3-564ddc26b9d4");
            object.put("healthFacilityCode", userData.getUserFacilityId());

            object.put("patientType", tbPatient.getPatientType());
            object.put("transferType", tbPatient.getTransferType());
            object.put("referralType", tbPatient.getReferralType());
            object.put("veo", tbPatient.getVeo());
            object.put("weight", tbPatient.getWeight());
            object.put("xray", tbPatient.getXray());
            object.put("makohozi", tbPatient.getMakohozi());
            object.put("otherTests", tbPatient.getOtherTests());
            object.put("treatment_type", tbPatient.getTreatment_type());
            object.put("outcome", tbPatient.getOutcome());
            object.put("outcomeDate", tbPatient.getOutcomeDate());
            object.put("outcomeDetails", tbPatient.getOutcomeDetails());

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public static RequestBody getEncounterRequestBody(TbEncounters encounters){
        RequestBody body;
        String datastream = "";
        JSONObject object   = new JSONObject();

        try {

            object.put("", 0);
            object.put("", 0);
            object.put("", 0);
            object.put("", 0);
            object.put("", 0);

            datastream = object.toString();

            Log.d("PostOfficeService", datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        return body;

    }

    public void setuptypeface(){
        Julius = Typeface.createFromAsset(this.getAssets(), "JuliusSansOne-Regular.ttf");
        Avenir = Typeface.createFromAsset(this.getAssets(), "avenir-light.ttf");
        RobotoCondenced = Typeface.createFromAsset(this.getAssets(), "Roboto-Condensed.ttf");
        RobotoCondencedItalic = Typeface.createFromAsset(this.getAssets(), "Roboto-CondensedItalic.ttf");
        RobotoThin = Typeface.createFromAsset(this.getAssets(), "Roboto-Thin.ttf");
        RobotoLight = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        RobotoMedium = Typeface.createFromAsset(this.getAssets(), "Roboto-Medium.ttf");
        RosarioRegular = Typeface.createFromAsset(this.getAssets(), "Rosario-Regular.ttf");
        athletic = Typeface.createFromAsset(this.getAssets(), "athletic.ttf");
        FunRaiser = Typeface.createFromAsset(this.getAssets(), "Fun-Raiser.ttf");
        CabinSketchRegular = Typeface.createFromAsset(this.getAssets(), "CabinSketch-Regular.ttf");
        CabinSketchBold = Typeface.createFromAsset(this.getAssets(), "CabinSketch-Bold.ttf");
    }

}
