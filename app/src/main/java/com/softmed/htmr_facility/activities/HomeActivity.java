package com.softmed.htmr_facility.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.customviews.NonSwipeableViewPager;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.softmed.htmr_facility.dom.objects.UserData;
import com.softmed.htmr_facility.dom.responces.EncounterResponse;
import com.softmed.htmr_facility.dom.responces.PatientResponce;
import com.softmed.htmr_facility.fragments.HivFragment;
import com.softmed.htmr_facility.fragments.LabFragment;
import com.softmed.htmr_facility.fragments.OPDFragment;
import com.softmed.htmr_facility.fragments.TbFragment;
import com.softmed.htmr_facility.services.DataSyncJob;
import com.softmed.htmr_facility.utils.AlarmReceiver;
import com.softmed.htmr_facility.utils.Config;
import com.softmed.htmr_facility.utils.ServiceGenerator;
import com.softmed.htmr_facility.utils.SessionManager;
import com.softmed.htmr_facility.viewmodels.PostOfficeListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES;
import static com.softmed.htmr_facility.utils.constants.DATA_CONFLICT;
import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_REFERRAL;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.RESPONCE_SUCCESS;
import static com.softmed.htmr_facility.utils.constants.USER_ROLE_ADMIN;
import static com.softmed.htmr_facility.utils.constants.USER_ROLE_CTC;
import static com.softmed.htmr_facility.utils.constants.USER_ROLE_LAB;
import static com.softmed.htmr_facility.utils.constants.USER_ROLE_OPD;
import static com.softmed.htmr_facility.utils.constants.USER_ROLE_TB_CLINIC;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class HomeActivity extends BaseActivity {

    private final String TAG = HomeActivity.class.getSimpleName();

    /**
     * The Authority for the sync adapter's content provider
     */
    public static final String AUTHORITY = "com.softmed.htmr_facility.provider";

    /**
     * An account type in the form of a domain
     */
    public static final String ACCOUNT_TYPE = "htmr_facility.softmed.com";


    /**
     * The Account name
     */
    public static final String ACCOUNT = "trcmis";

    /**
     * Account Instance field
     */
    Account mAccount;

    /**
     *  Sync interval constants
     */
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL = SYNC_INTERVAL_IN_MINUTES * SECONDS_PER_MINUTE;

    /**
     *  Global variables
     *  A content resolver for accessing the provider
     */
    ContentResolver mResolver;


    private TabLayout tabLayout;
    public static NonSwipeableViewPager viewPager;
    private Toolbar toolbar;
    private TextView toolbarTitle, unsynced, currentFacilityName;
    private CircularProgressView syncProgressBar;
    private ImageView manualSync;

    private PostOfficeListViewModel viewModel;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AppDatabase database;
    private Endpoints.PatientServices patientServices;
    private Endpoints.ReferalService referalService;

    private RequestManager mRequestManager;


    Boolean hivTbAdded = false;
    Boolean tbTabAdded = false;
    Boolean opdTabAdded = false;
    Boolean labTabAdded = false;

    List<String> indexes = new ArrayList<>();

    private FirebaseJobDispatcher dispatcher;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupviews();

        //Initialization of variables
        houseInit();

        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        /*
         * Turning on periodic syncing for the sync adapter
         */
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);

        //User Session Manager Initialization
        SessionManager sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()){
            sessionManager.checkLogin();
            finish();
        }

        if (session.isLoggedIn()){
            toolbarTitle.setText(session.getUserName());
            new AsyncTask<Void, Void, Void>(){
                String hfName = "";
                @Override
                protected Void doInBackground(Void... voids) {
                    List<HealthFacilities> hfList = database.healthFacilitiesModelDao().getFacilityByOpenMRSID(session.getKeyHfid());
                    if (hfList!=null && hfList.size() > 0){
                        hfName = hfList.get(0).getFacilityName();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    currentFacilityName.setText(hfName);
                }
            }.execute();
        }

        //initialize viewpager
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        //initialize tablayout
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        });

        checkPostOfficeData();

        scheduleBackgroundJob();

        manualSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Log.d(TAG, "onClick: Manual syncing");

                        //new SyncPostOfficeData().execute();

                        // Pass the settings flags by inserting them in a bundle
                        /*Bundle settingsBundle = new Bundle();
                        settingsBundle.putBoolean(
                                ContentResolver.SYNC_EXTRAS_MANUAL, true);
                        settingsBundle.putBoolean(
                                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                        ContentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);
                        */

                        //Syncing data using firebase's job Dispatcher
                        Job myJob = dispatcher.newJobBuilder()
                                .setService(DataSyncJob.class) // the JobService that will be called
                                .setTag("10002")        // uniquely identifies the job
                                .setRecurring(true)
                                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                                .setTrigger(Trigger.executionWindow(0, 0)) //60 seconds changed to 0 seconds to dispatch the job instantly
                                .setReplaceCurrent(false)
                                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                                .build();

                        dispatcher.mustSchedule(myJob);

                        return null;

                    }
                }.execute();
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    String regId = pref.getString("regId", null);
                    Log.d(TAG, "Registration ID broadcasted in HomeActivity : "+regId);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    Log.d(TAG, "Message received in HomeActivity : "+message);

                }
            }
        };

        LiveData<List<PostOffice>> postOfficeWatcher = database.postOfficeModelDao().getPostOfficeEntries();
        postOfficeWatcher.observe(this, new Observer<List<PostOffice>>() {
            @Override
            public void onChanged(@Nullable List<PostOffice> postOffices) {
                /**
                 * Post Office data has changed, trigger sync adapter to sync data
                 */
                ContentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);
            }
        });

        /**
         * Commented to stop postOfficeService from running as sync adapter is being used
         */
        //scheduleAlarm();

    }


    private void houseInit(){

        //initialize the database
        database = AppDatabase.getDatabase(this);

        //Glide context request manager
        mRequestManager= Glide.with(this);

        //Create Dummy account
        mAccount = CreateSyncAccount(this);

        //.. Initialize ContentResolver for SyncAdapter
        mResolver = getContentResolver();

        //Initializing Firebase Job Dispatcher
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        //Initialize patient api call services
        patientServices = ServiceGenerator.createService(Endpoints.PatientServices.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());

        //Initialize referral api call services
        referalService = ServiceGenerator.createService(Endpoints.ReferalService.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());

    }

    private void setupviews(){

        currentFacilityName = findViewById(R.id.current_facility_name);

        syncProgressBar =  findViewById(R.id.manual_sync_loader);
        syncProgressBar.setVisibility(View.INVISIBLE);

        manualSync =  findViewById(R.id.manual_sync);

        toolbarTitle =  findViewById(R.id.toolbar_user_name);
        unsynced =  findViewById(R.id.unsynced);

        toolbar =  findViewById(R.id.toolbar);

        viewPager =  findViewById(R.id.viewpager);

        tabLayout =  findViewById(R.id.tabs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout){
            session.logoutUser();
            finish();
        }

        if (id == R.id.reports){
            startActivity(new Intent(this, ReportsActivity.class));
        }

        if (id == R.id.sync_data){
            //Sync data from PostOffice
        }

        if (id == R.id.reminder){
            //Open Reminder Activity
            startActivity(new Intent(this, AppointmentActivity.class));
        }

        return true;
    }

    public void setupTabIcons() {

        for (int i=0; i<indexes.size(); i++){
            if (indexes.get(i).equals("hiv")){
                View ctcTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
                TextView ctcTabTitle = ctcTabView.findViewById(R.id.title_text);
                ImageView ctcIcon    = ctcTabView.findViewById(R.id.icon);
                if (!HomeActivity.this.isFinishing())
                    Glide.with(this).load(R.mipmap.ic_hiv).into(ctcIcon);
                ctcTabTitle.setText(getResources().getString(R.string.fragment_hiv));
                tabLayout.getTabAt(i).setCustomView(ctcTabView);
            }else if (indexes.get(i).equals("opd")){
                View opdTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
                TextView opdTabTitle = opdTabView.findViewById(R.id.title_text);
                opdTabTitle.setText(getResources().getString(R.string.fragment_opd));
                ImageView opdIcon    = opdTabView.findViewById(R.id.icon);
                opdIcon.setColorFilter(this.getResources().getColor(R.color.white));
                if (!HomeActivity.this.isFinishing()){
                    Glide.with(this).load(R.mipmap.ic_face).into(opdIcon);
                }
                tabLayout.getTabAt(i).setCustomView(opdTabView);
            }else if (indexes.get(i).equals("tb")){
                View tbTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
                TextView tbTabTitle = tbTabView.findViewById(R.id.title_text);
                tbTabTitle.setText(getResources().getString(R.string.fragment_tb));
                ImageView tbIcon    = tbTabView.findViewById(R.id.icon);
                if (!HomeActivity.this.isFinishing())
                    Glide.with(this).load(R.mipmap.ic_tb).into(tbIcon);
                tabLayout.getTabAt(i).setCustomView(tbTabView);
            }else if (indexes.get(i).equals("lab")){
                View labTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
                TextView labTitle = labTabView.findViewById(R.id.title_text);
                labTitle.setText(getResources().getString(R.string.fragment_lab));
                ImageView labIcon    = labTabView.findViewById(R.id.icon);
                labIcon.setColorFilter(this.getResources().getColor(R.color.white));
                if (!HomeActivity.this.isFinishing())
                    Glide.with(this).load(R.mipmap.ic_lab).into(labIcon);
                tabLayout.getTabAt(i).setCustomView(labTabView);
            }
        }

    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int tabIndex = 0;

        List<String> userRoles = BaseActivity.getUserRoles();
        for (String role : userRoles){
            if (role.equals(USER_ROLE_ADMIN)){

                if (!opdTabAdded){
                    adapter.addFragment(new OPDFragment(), "opd");
                    opdTabAdded = true;
                    indexes.add("opd");
                }

                if (!hivTbAdded){
                    adapter.addFragment(new HivFragment(), "hiv");
                    hivTbAdded = true;
                    indexes.add("hiv");
                }

                if (!tbTabAdded){
                    adapter.addFragment(new TbFragment(), "tb");
                    tbTabAdded = true;
                    indexes.add("tb");
                }

                if (!labTabAdded){
                    adapter.addFragment(new LabFragment(), "lab");
                    labTabAdded = true;
                    indexes.add("lab");
                }

            }else if (role.equals(USER_ROLE_CTC) && !hivTbAdded){
                adapter.addFragment(new HivFragment(), "hiv");
                hivTbAdded = true;
                indexes.add("hiv");
            }else if (role.equals(USER_ROLE_OPD) && !opdTabAdded){
                adapter.addFragment(new OPDFragment(), "opd");
                opdTabAdded = true;
                indexes.add("opd");
            }else if (role.equals(USER_ROLE_TB_CLINIC) && !tbTabAdded){
                adapter.addFragment(new TbFragment(), "tb");
                tbTabAdded = true;
                indexes.add("tb");
            }else if (role.equals(USER_ROLE_LAB) && !labTabAdded){
                adapter.addFragment(new LabFragment(), "lab");
                labTabAdded = true;
                indexes.add("lab");
            }
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
            return null;
        }
    }

    private void scheduleBackgroundJob(){
        Job myJob = dispatcher.newJobBuilder()
                .setService(DataSyncJob.class) // the JobService that will be called
                .setTag("10002")        // uniquely identifies the job
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 10)) //60 seconds changed to 10 seconds for development purposes
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();

        dispatcher.mustSchedule(myJob);
    }

    private void checkPostOfficeData(){

        viewModel = ViewModelProviders.of(this).get(PostOfficeListViewModel.class);
        viewModel.getUnpostedDataList().observe(HomeActivity.this, new Observer<List<PostOffice>>() {
            @Override
            public void onChanged(@Nullable List<PostOffice> postOffices) {
                if (postOffices.size() > 0){
                    unsynced.setText(getResources().getString(R.string.unsynced_data)+" "+postOffices.size());
                    unsynced.setTextColor(getResources().getColor(R.color.orange_400));
                }else {
                    unsynced.setText(getResources().getString(R.string.data_synced));
                    unsynced.setTextColor(getResources().getColor(R.color.green_600));
                }

            }
        });

    }

    private void syncDataInPostOffice(){
        //Check if data is available in the PostOffice
        if (database.postOfficeModelDao().getUnpostedDataCount() > 0){
            List<PostOffice> unpostedData = database.postOfficeModelDao().getUnpostedData();
            for (int i=0; i<unpostedData.size(); i++){

                final PostOffice data = unpostedData.get(i);
                Log.d("PostOfficeService", "Post Office Data Type "+data.getPost_data_type());

                if (data.getPost_data_type().equals(POST_DATA_TYPE_PATIENT)){

                    final Patient patient = database.patientModel().getPatientById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = patientServices.postPatient(session.getServiceProviderUUID(), getPatientRequestBody(patient, userData.getUserFacilityId()));
                    call.enqueue(new Callback() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onResponse(Call call, Response response) {
                            //Store Received Patient Information, TbPatient as well as PatientAppointments
                            if (response.body()!=null){

                                Patient receivedPatient = (Patient) response.body();
                                Log.d("POST_DATA_TYPE_PATIENT", receivedPatient.getPatientFirstName());

                                new ReplacePatientObject().execute(patient, receivedPatient);

                                new DeletePOstData(database).execute(data); //This can be removed and data may be set synced status to SYNCED

                            }else {
                                Log.d("POST_DATA_TYPE_PATIENT","Patient Responce is null "+response.body());

                                new AsyncTask<PostOffice, Void, Void>(){

                                    @Override
                                    protected Void doInBackground(PostOffice... args) {
                                        //Increase the failed sync count
                                        PostOffice data = args[0];
                                        if (data.getFailedSyncCount() > 3){
                                            data.setSyncStatus(DATA_CONFLICT);
                                        }else {
                                            data.setFailedSyncCount(data.getFailedSyncCount()+1);
                                        }

                                        database.postOfficeModelDao().updatePostData(data);

                                        return null;
                                    }
                                }.execute(data);

                            }


                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("patient_response", t.getMessage());
                        }
                    });

                }else if (data.getPost_data_type().equals(POST_DATA_TYPE_TB_PATIENT)){

                    final Patient patient = database.patientModel().getPatientById(data.getPost_id());
                    final TbPatient tbPatient = database.tbPatientModelDao().getTbPatientById(patient.getPatientId());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = patientServices.postTbPatient(session.getServiceProviderUUID(), getTbPatientRequestBody(patient, tbPatient, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            //PatientResponce patientResponce = (PatientResponce) response.body();
                            //Store Received Patient Information, TbPatient as well as PatientAppointments
                            Log.d("POST_DATA_TYPE_TP","Responce Code : "+response.code());
                            if (response.code() == RESPONCE_SUCCESS){
                                //Tb Patient have been saved

                                //new ReplaceTbPatientAndAppointments(database, patient, tbPatient).execute(patientResponce);
                                new DeletePOstData(database).execute(data); //Remove PostOffice Entry, set synced SYNCED may also be used to flag data as already synced

                            }else {
                                try{
                                    Log.d("POST_DATA_TYPE_TP","Patient Responce is null "+response.body());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("patient_response", "Error : "+t.getStackTrace());
                        }
                    });

                } else if (data.getPost_data_type().equals(POST_DATA_TYPE_REFERRAL)){

                    final Referral referral = database.referalModel().getReferalById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = referalService.postReferral(session.getServiceProviderUUID(), BaseActivity.getReferralRequestBody(referral));
                    call.enqueue(new Callback() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onResponse(Call call, Response response) {
                            Referral receivedReferral = (Referral) response.body();
                            if (response.body() != null) {

                                Log.d("PostReferral", response.body().toString());
                                new ReplaceReferralObject().execute(referral, receivedReferral);

                                new DeletePOstData(database).execute(data); //This can be removed and data may be set synced status to SYNCED

                            } else {
                                Log.d("PostReferral", "Responce is Null : " + response.body());
                                /**
                                 * If response is Null ->
                                 *      Count to see how many times response returns null, if its 3 times flag the postOffice data as conflict
                                 */
                                new AsyncTask<PostOffice, Void, Void>(){

                                    @Override
                                    protected Void doInBackground(PostOffice... args) {
                                        //Increase the failed sync count
                                        PostOffice data = args[0];
                                        if (data.getFailedSyncCount() > 3){
                                            data.setSyncStatus(DATA_CONFLICT);
                                        }else {
                                            data.setFailedSyncCount(data.getFailedSyncCount()+1);
                                        }

                                        database.postOfficeModelDao().updatePostData(data);

                                        return null;
                                    }
                                }.execute(data);

                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("PostReferral", "Failed "+t.getMessage());
                        }
                    });

                }else if (data.getPost_data_type().equals(POST_DATA_REFERRAL_FEEDBACK)){

                    Log.d("POST_DATA_REFERRAL_FB", data.getPost_data_type());

                    final Referral referral = database.referalModel().getReferalById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = referalService.sendReferralFeedback(session.getServiceProviderUUID(), BaseActivity.getReferralFeedbackRequestBody(referral, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.d("POST_DATA_REFERRAL_FB", "Outside 200 : "+response.body());
                            //database.postOfficeModelDao().deletePostData(data);
                            if (response.code() == 200){
                                Log.d("POST_DATA_REFERRAL_FB", "Saved to seerver : "+response.body());
                                new DeletePOstData(database).execute(data);
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("POST_RESPOMCES", "Failed with message : " + t.getMessage());
                        }
                    });

                }else if(data.getPost_data_type().equals(POST_DATA_TYPE_ENCOUNTER)){

                    Log.d("POST_DATA_REFERRAL_FB", data.getPost_data_type());

                    //List<TbEncounters> encounter = database.tbEncounterModelDao().getEncounterByPatientID(data.getPost_id());

                    List<TbEncounters> encounter = database.tbEncounterModelDao().getEncounterByLocalId(data.getPost_id());

                    for (TbEncounters e : encounter){

                        Call call = patientServices.postEncounter(session.getServiceProviderUUID(), BaseActivity.getTbEncounterRequestBody(e));
                        call.enqueue(new Callback() {
                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onResponse(Call call, Response response) {
                                /*
                                Receive an Encounter object as a response
                                Replace the local encounter with the response encounter
                                TODO: Update appointments also
                                */
                                if (response.body() != null) {

                                    Log.d("POST_DATA_TE", "Response Received : "+response.body());
                                    EncounterResponse encounterResponse = (EncounterResponse) response.body();
                                    List<PatientAppointment> listOfAppointments = encounterResponse.getPatientAppointments();

                                    TbEncounters receivedEncounter =  encounterResponse.getEncounter();
                                    new AsyncTask<TbEncounters, Void, Void>(){
                                        @Override
                                        protected Void doInBackground(TbEncounters... tbEncounters) {
                                            database.tbEncounterModelDao().deleteAnEncounter(tbEncounters[0]);
                                            database.tbEncounterModelDao().addEncounter(tbEncounters[1]);
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            super.onPostExecute(aVoid);
                                            Log.d("POST_DATA_TE", "Encounter Replaced");
                                            new DeletePOstData(database).execute(data);
                                        }
                                    }.execute(e, receivedEncounter);

                                    TbPatient tbPatient = baseDatabase.tbPatientModelDao().getTbPatientByTbPatientId(receivedEncounter.getTbPatientID()+"");

                                    List<PatientAppointment> appointments = baseDatabase.appointmentModelDao().getAppointmentsByTypeAndPatientID(2, tbPatient.getHealthFacilityPatientId()+"");
                                    for (PatientAppointment a : appointments){
                                        baseDatabase.appointmentModelDao().deleteAppointment(a);
                                    }

                                    for (PatientAppointment appointment : listOfAppointments){
                                        baseDatabase.appointmentModelDao().addAppointment(appointment);
                                    }

                                }else {
                                    Log.d("HomeActivity", "Responce is null");
                                }

                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                t.printStackTrace();

                            }
                        });

                    }

                }
            }

        }
    }

    /**
     * Create a dummy account for the sync Adapter
     * @param context
     * @return created account
     */
    public static Account CreateSyncAccount(Context context){

        boolean isNewAccount = false;

        //Create the account type and default account
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        //Get the instance of the android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        /**
         * Add the account and the account type no password or user data
         * If successfull return the account instance if not return the error
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)){

            /**
             * If you do not set the android syncable to true in the <provider> element in manifest then call
             *          context.setIsSyncable(account, AUTHORITY, 1) here
             */
            Log.d("", "Account has been created successfully");

            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(newAccount, AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    newAccount, AUTHORITY, new Bundle(),(60*2));

            isNewAccount = true;

        }else {
            /**
             * The account already exists or some other error
             */
            Log.d("", "Account already exist");
        }

        return newAccount;

    }

    class ReplacePatientObject extends AsyncTask<Patient, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Patient... patients) {


            //RE
            List<Referral> oldPatientReferrals = new ArrayList<>();
            oldPatientReferrals = database.referalModel().getReferalsByPatientId(patients[0].getPatientId()).getValue();

            if (oldPatientReferrals != null){
                for (int i=0; i<oldPatientReferrals.size(); i++){
                    Referral ref = oldPatientReferrals.get(i);
                    if (ref.getPatient_id() != patients[1].getPatientId()){
                        ref.setPatient_id(patients[1].getPatientId());
                    }
                }

            }

            //Delete the old object
            database.patientModel().deleteAPatient(patients[0]);

            //Insert server's patient reference
            database.patientModel().addPatient(patients[1]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class SyncPostOfficeData extends AsyncTask<Void,Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            manualSync.setVisibility(View.INVISIBLE);
            syncProgressBar.setVisibility(View.VISIBLE);
            unsynced.setText(getResources().getString(R.string.data_syncing));
            unsynced.setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Thread.sleep(2000, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            syncDataInPostOffice();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            manualSync.setVisibility(View.VISIBLE);
            syncProgressBar.setVisibility(View.INVISIBLE);
            unsynced.setText(getResources().getString(R.string.data_synced));
            unsynced.setTextColor(getResources().getColor(R.color.white));
            checkPostOfficeData();
            super.onPostExecute(aVoid);
        }
    }

    class DeletePOstData extends AsyncTask<PostOffice, Void, Void>{

        AppDatabase database;

        DeletePOstData(AppDatabase db){
            this.database = db;
        }

        @Override
        protected Void doInBackground(PostOffice... postOffices) {
            database.postOfficeModelDao().deletePostData(postOffices[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    class ReplaceReferralObject extends AsyncTask<Referral, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Referral... referrals) {

            database.referalModel().deleteReferal(referrals[0]); //Delete local referral reference
            database.referalModel().addReferal(referrals[1]); //Store the server's referral reference

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class ReplaceTbPatientAndAppointments extends AsyncTask<PatientResponce, Void, Void>{

        Patient patient;
        TbPatient tbPatient;
        PatientAppointment patientAppointment;
        AppDatabase database;

        ReplaceTbPatientAndAppointments(AppDatabase db, Patient pt, TbPatient tp){
            this. database = db;
            this.patient = pt;
            this.tbPatient = tp;
        }

        @Override
        protected Void doInBackground(PatientResponce... patientResponces) {

            database.tbPatientModelDao().deleteAPatient(tbPatient);

            TbPatient tbPatient1 = patientResponces[0].getTbPatient();
            List<PatientAppointment> appointments = patientResponces[0].getPatientAppointments();

//            List<PatientAppointment> oldAppointments = database.appointmentModelDao().getThisPatientAppointments(patient.getPatientId());
//            for (int i=0; i<oldAppointments.size(); i++){
//                database.appointmentModelDao().deleteAppointment(oldAppointments.get(i));
//            }

            //Insert server's patient reference
            database.tbPatientModelDao().addPatient(tbPatient1);
            for (int j=0; j<appointments.size(); j++){
                database.appointmentModelDao().addAppointment(appointments.get(j));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
