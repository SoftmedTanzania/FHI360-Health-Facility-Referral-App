package com.softmed.htmr_facility.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.customviews.LargeDiagonalCutPathDrawable;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility.dom.objects.ReferralServiceIndicators;
import com.softmed.htmr_facility.dom.objects.ReferralServiceIndicatorsResponse;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.softmed.htmr_facility.dom.objects.UserData;
import com.softmed.htmr_facility.dom.responces.LoginResponse;
import com.softmed.htmr_facility.dom.responces.PatientResponce;
import com.softmed.htmr_facility.dom.responces.ReferalResponce;
import com.softmed.htmr_facility.fragments.HealthFacilityReferralListFragment;
import com.softmed.htmr_facility.fragments.IssueReferralDialogueFragment;
import com.softmed.htmr_facility.utils.Config;
import com.softmed.htmr_facility.utils.ServiceGenerator;
import com.softmed.htmr_facility.utils.SessionManager;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.utils.constants.STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.STATUS_NEW;

/**
 * Created by issy on 11/23/17.
 */

public class LoginActivity extends BaseActivity {

    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginButton;
    private TextView loginMessages;
    private ProgressView loginProgress;
    private ImageView loginBgImage, background;
    private ImageView tanzaniaLogo, usaidLogo, fhiLogo, deloitteLogo;
    private RelativeLayout credentialCard;
    private MaterialSpinner languageSpinner;

    private String usernameValue, passwordValue;
    private String deviceRegistrationId = "";
    private Endpoints.ReferalService referalService;
    private Endpoints.PatientServices patientService;

    // Session Manager Class
    private SessionManager session;
    private UserData userData;
    int flag = 0;
    boolean justInitializing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String localeString = localeSp.getString(LOCALE_KEY, SWAHILI_LOCALE);
        Log.d("language", "From SP : "+localeString);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (localeString.equals(ENGLISH_LOCALE)){

            String language = ENGLISH_LOCALE;
            String country = "US";
            Locale locale = new Locale(language , country);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }else {

            String language = SWAHILI_LOCALE;
            String country = "TZ";
            Locale locale = new Locale(language , country);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        setContentView(R.layout.activity_login);
        setupview();

        // Session Manager
        session = new SessionManager(getApplicationContext());

        final String[] status = {"English", "Kiswahili"};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        justInitializing = true;
        languageSpinner.setAdapter(spinAdapter);
        if (localeString.equals(ENGLISH_LOCALE)){
            justInitializing = true;
            languageSpinner.setSelection(1, false);
        }else {
            justInitializing = true;
            languageSpinner.setSelection(2, false);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAuthenticationCredentials()){
                    loginProgress.setVisibility(View.VISIBLE);
                    loginUser();
                }
            }
        });

        SharedPreferences.Editor editor = localeSp.edit();

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("language", "On listener");
                if (!justInitializing){
                    Log.d("language", "Inside Flag");
                    if (i == 0){
                        //English
                        Log.d("language", "Changing is SP to English");
                        editor.putString(LOCALE_KEY, ENGLISH_LOCALE);
                        editor.apply();
                        setLocale(new Locale(ENGLISH_LOCALE, "US"));
                    }else if (i == 1){
                        Log.d("language", "Changing is SP to Swahili");
                        //Swahili
                        editor.putString(LOCALE_KEY, SWAHILI_LOCALE);
                        editor.apply();
                        setLocale(new Locale(SWAHILI_LOCALE, "TZ"));
                    }else {

                    }
                }else{
                    justInitializing = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void setLocale(Locale locale){
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        Locale.setDefault(locale);
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());
        Log.d("language", "After changing the configuration");
        recreate();

    }

    private boolean getAuthenticationCredentials(){

        if (!isDeviceRegistered()){
            loginMessages.setText("Device is Not Registered for Notifications, please Register");
            return false;
        }
        else if (usernameEt.getText().length() <= 0){
            Toast.makeText(this, "Jina haliwezi kua wazi", Toast.LENGTH_SHORT).show();
            return false;
        }else if (passwordEt.getText().length() <= 0){
            Toast.makeText(this, "Neno la siri haliwezi kuwa wazi", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            usernameValue = usernameEt.getText().toString();
            passwordValue = passwordEt.getText().toString();

            return true;

        }
    }

    private boolean isDeviceRegistered(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        deviceRegistrationId = pref.getString("regId", null);
        if (deviceRegistrationId == null || deviceRegistrationId.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void loginUser(){

        loginButton.setText(getResources().getString(R.string.loading_data));
        loginMessages.setVisibility(View.VISIBLE);
        loginMessages.setText(getResources().getString(R.string.loging_in));

        //Use Retrofit to make http request calls
        Endpoints.LoginService loginService =
                ServiceGenerator.createService(Endpoints.LoginService.class, usernameValue, passwordValue, null);
        Call<LoginResponse> call = loginService.basicLogin();
        call.enqueue(new Callback<LoginResponse >() {


            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    // user object available

                    loginMessages.setTextColor(getResources().getColor(R.color.green_a700));
                    loginMessages.setText(getResources().getString(R.string.success));

                    LoginResponse loginResponse = response.body();

                    userData = new UserData();
                    userData.setUserUIID(loginResponse.getUser().getAttributes().getPersonUUID());
                    userData.setUserName(loginResponse.getUser().getUsername());
                    userData.setUserFacilityId(loginResponse.getTeam().getTeam().getLocation().getUuid());

                    //Convert the list of roles to json string to store in sharedPreference
                    List<String> roles = loginResponse.getUser().getRoles();
                    Gson gson = new Gson();
                    String rolesString = gson.toJson(roles);

                    session.createLoginSession(
                            loginResponse.getUser().getUsername(),
                            loginResponse.getUser().getAttributes().getPersonUUID(),
                            passwordValue,
                            loginResponse.getTeam().getTeam().getLocation().getUuid(),
                            rolesString);

                    referalService = ServiceGenerator.createService(Endpoints.ReferalService.class, session.getUserName(), session.getUserPass(), session.getKeyHfid());
                    patientService = ServiceGenerator.createService(Endpoints.PatientServices.class, session.getUserName(), session.getUserPass(), session.getKeyHfid());

                    sendRegistrationToServer(deviceRegistrationId,
                            loginResponse.getUser().getAttributes().getPersonUUID(),
                            loginResponse.getTeam().getTeam().getLocation().getUuid());

                } else {
                    loginMessages.setText(getResources().getString(R.string.error_logging_in));
                    loginMessages.setTextColor(getResources().getColor(R.color.red_a700));
                    loginProgress.setVisibility(View.GONE);
                    loginButton.setText(getResources().getString(R.string.login));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // something went completely south (like no internet connection)
                try {
                    Log.d("Error", t.getMessage());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

        });

    }

    private void sendRegistrationToServer(String token, String userUiid, String hfid){

        SessionManager sess = new SessionManager(getApplicationContext());

        String datastream = "";
        JSONObject object   = new JSONObject();
        RequestBody body;

        try {
            object.put("userUiid", userUiid);
            object.put("googlePushNotificationToken", token);
            object.put("facilityUiid", hfid);
            object.put("userType", 1);

            datastream = object.toString();
            Log.d("FCMService", "data "+datastream);

            body = RequestBody.create(MediaType.parse("application/json"), datastream);

        }catch (Exception e){
            e.printStackTrace();
            body = RequestBody.create(MediaType.parse("application/json"), datastream);
        }

        Endpoints.NotificationServices notificationServices = ServiceGenerator.createService(Endpoints.NotificationServices.class, session.getUserName(), session.getUserPass(), null);
        retrofit2.Call call = notificationServices.registerDevice(body);
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                new AddUserData(baseDatabase).execute(userData);
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                new AddUserData(baseDatabase).execute(userData);
                loginMessages.setText(getResources().getString(R.string.device_registration_warning));
                loginMessages.setTextColor(getResources().getColor(R.color.red_600));
            }
        });

    }

    private void callReferralList(){
        loginMessages.setText(getResources().getString(R.string.initializing_data));
        loginMessages.setTextColor(getResources().getColor(R.color.amber_a700));

        if (session.isLoggedIn()){

            Call<List<ReferalResponce>> call = referalService.getHfReferrals();
            call.enqueue(new Callback<List<ReferalResponce>>() {

                @Override
                public void onResponse(Call<List<ReferalResponce>> call, Response<List<ReferalResponce>> response) {
                    //Here will handle the responce from the server
                    //createDummyReferralData();
                    Log.d("ReferralCheck", response.body()+"");

                    addReferralsAsyncTask task = new addReferralsAsyncTask(response.body());
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

                @Override
                public void onFailure(Call<List<ReferalResponce>> call, Throwable t) {
                    //Error!
                    //createDummyReferralData();
                    Log.e("", "An error encountered!");
                    Log.d("ReferralCheck", "failed with "+t.getMessage()+" "+t.toString());
                }
            });
        }
    }

    public void callServices(){
        Call<List<ReferralServiceIndicatorsResponse>> call = referalService.getAllServices();
        call.enqueue(new Callback<List<ReferralServiceIndicatorsResponse>>() {
            @Override
            public void onResponse(Call<List<ReferralServiceIndicatorsResponse>> call, Response<List<ReferralServiceIndicatorsResponse>> response) {
                Log.d("SAMPLE", response.body().toString());
                List<ReferralServiceIndicatorsResponse> receivedServices = response.body();

                new AddServices().execute(receivedServices);

            }

            @Override
            public void onFailure(Call<List<ReferralServiceIndicatorsResponse>> call, Throwable t) {
                Log.d("SAMPLE", t.getMessage());

            }
        });
    }

    public void callTbPatientServices(){
        Call<List<PatientResponce>> call = patientService.getTbPatientsList(session.getKeyHfid());
        call.enqueue(new Callback<List<PatientResponce>>() {
            @Override
            public void onResponse(Call<List<PatientResponce>> call, Response<List<PatientResponce>> response) {
                List<PatientResponce> patientList = response.body();
                new addTbPatientTask().execute(patientList);
            }

            @Override
            public void onFailure(Call<List<PatientResponce>> call, Throwable t) {

            }
        });
    }

    public void getHealthFacilities(){

        Call<List<HealthFacilities>> call = referalService.getHealthFacilities();
        call.enqueue(new Callback<List<HealthFacilities>>() {
            @Override
            public void onResponse(Call<List<HealthFacilities>> call, Response<List<HealthFacilities>> response) {
                Log.d("SAMPLE", "HEALTH FACILITIES : "+response.body().toString());
                List<HealthFacilities> receivedHF = response.body();

                new AddHealthFacilities().execute(receivedHF);

            }

            @Override
            public void onFailure(Call<List<HealthFacilities>> call, Throwable t) {
                Log.d("SAMPLE", "HEALTH FACILITIES : "+t.getMessage());
            }
        });

    }

    class addTbPatientTask extends AsyncTask<List<PatientResponce>, Void, Void>{

        List<PatientResponce> responces;

        @Override
        protected Void doInBackground(List<PatientResponce>[] lists) {
            responces = lists[0];

            for (PatientResponce _responce : responces){

                Patient patient = _responce.getPatient();
                patient.setCurrentOnTbTreatment(true);
                baseDatabase.patientModel().addPatient(patient);

                TbPatient tbPatient = _responce.getTbPatient();
                Log.d("msosi", "Inserting tb patient : "+tbPatient.getHealthFacilityPatientId());
                Log.d("msosi", "Sputum Weight : "+tbPatient.getMakohozi());
                baseDatabase.tbPatientModelDao().addPatient(tbPatient);

                //Delete all appointments with these patient's IDs
                baseDatabase.appointmentModelDao().deleteAppointmentByPatientID(patient.getPatientId());

                List<PatientAppointment> appointments = _responce.getPatientAppointments();
                for (PatientAppointment _appointment : appointments){
                    Log.d("msosi", "Inserting appointment on : "+_appointment.getAppointmentDate());
                    baseDatabase.appointmentModelDao().addAppointment(_appointment);
                }

                List<TbEncounters> listOfEncounters = _responce.getTbEncounters();
                for (TbEncounters encounter : listOfEncounters){
                    //TODO: Add TB Encounters
                    baseDatabase.tbEncounterModelDao().addEncounter(encounter);
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loginMessages.setText("");
            loginProgress.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            LoginActivity.this.finish();
        }

    }

    class addReferralsAsyncTask extends AsyncTask<Void, Void, Void> {

        List<ReferalResponce> results;

        addReferralsAsyncTask(List<ReferalResponce> responces){
            this.results = responces;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loginMessages.setText("Finalizing..");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Log.d("InitialSync", "Referal Responce size : "+results.size());

            for (ReferalResponce mList : results){
                baseDatabase.patientModel().addPatient(mList.getPatient());
                Log.d("InitialSync", "Patient  : "+mList.getPatient().getPatientFirstName());
                for (Referral referral : mList.getPatientReferalList()){
                    Log.d("InitialSync", "Referal  : "+ referral.toString());
                    baseDatabase.referalModel().addReferal(referral);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callTbPatientServices();
        }
    }

    class AddUserData extends AsyncTask<UserData, Void, Void>{

        AppDatabase database;

        AddUserData(AppDatabase db){
            this.database = db;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            callServices();

        }

        @Override
        protected Void doInBackground(UserData... userData) {
            database.userDataModelDao().addUserData(userData[0]);
            return null;
        }
    }

    class AddServices extends AsyncTask<List<ReferralServiceIndicatorsResponse>, Void, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getHealthFacilities();

        }

        @Override
        protected Void doInBackground(List<ReferralServiceIndicatorsResponse>[] lists) {

            List<ReferralServiceIndicatorsResponse> receivedServices = lists[0];

            for (ReferralServiceIndicatorsResponse response : receivedServices){
                ReferralServiceIndicators service  = new ReferralServiceIndicators();
                service.setServiceId(response.getServiceId());
                service.setActive(response.isActive());
                service.setCategory(response.getCategory());
                service.setServiceName(response.getServiceName());
                service.setServiceNameSw(response.getServiceNameSw());

                for (ReferralIndicator   indicator : response.getIndicators()){
                    indicator.setServiceID(response.getServiceId());
                    baseDatabase.referralIndicatorDao().addIndicator(indicator);
                }

                baseDatabase.referralServiceIndicatorsDao().addService(service);

            }

            return null;
        }
    }

    class AddHealthFacilities extends AsyncTask<List<HealthFacilities>, Void, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            callReferralList();

        }

        @Override
        protected Void doInBackground(List<HealthFacilities>[] lists) {

            List<HealthFacilities> receivedHF = lists[0];

            List<HealthFacilities> oldHealthFacilities = baseDatabase.healthFacilitiesModelDao().getAllHealthFacilities();
            for (HealthFacilities hf : oldHealthFacilities){
                baseDatabase.healthFacilitiesModelDao().deleteHealthFacility(hf);
            }

            for (HealthFacilities hf : receivedHF){
                baseDatabase.healthFacilitiesModelDao().addHealthFacility(hf);
            }

            return null;
        }
    }

    private void setupview(){

        languageSpinner = (MaterialSpinner) findViewById(R.id.spin_language);

        credentialCard = (RelativeLayout) findViewById(R.id.credential_card);
        credentialCard.setBackground(new LargeDiagonalCutPathDrawable(50));

        tanzaniaLogo = (ImageView) findViewById(R.id.tanzania_logo);

        usaidLogo = (ImageView) findViewById(R.id.usaid_logo);
        fhiLogo = (ImageView) findViewById(R.id.fhi_logo);
        deloitteLogo = (ImageView) findViewById(R.id.deloitte_logo);

        background = (ImageView) findViewById(R.id.background);
        Glide.with(this).load(R.drawable.bg2).into(background);

        loginMessages = (TextView) findViewById(R.id.login_messages);
        loginMessages.setVisibility(View.GONE);

        loginProgress = (ProgressView) findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.GONE);

        loginButton = (Button) findViewById(R.id.login_button);

        usernameEt = (EditText) findViewById(R.id.user_username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
    }

}
