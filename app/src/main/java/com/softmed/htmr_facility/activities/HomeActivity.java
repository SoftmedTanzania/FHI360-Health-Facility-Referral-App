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
import android.text.TextUtils;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.customviews.NonSwipeableViewPager;
import com.softmed.htmr_facility.dom.objects.AppData;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import com.softmed.htmr_facility.dom.objects.TbPatient;
import com.softmed.htmr_facility.dom.objects.UserData;
import com.softmed.htmr_facility.dom.responces.EncounterResponse;
import com.softmed.htmr_facility.dom.responces.ReferalResponce;
import com.softmed.htmr_facility.fragments.HivFragment;
import com.softmed.htmr_facility.fragments.LabFragment;
import com.softmed.htmr_facility.fragments.OPDFragment;
import com.softmed.htmr_facility.fragments.TbFragment;
import com.softmed.htmr_facility.services.DataSyncJob;
import com.softmed.htmr_facility.utils.Config;
import com.softmed.htmr_facility.utils.ServiceGenerator;
import com.softmed.htmr_facility.utils.SessionManager;
import com.softmed.htmr_facility.viewmodels.PostOfficeListViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_APPOINTMENTS;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_REFERRAL;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.RESPONCE_SUCCESS;
import static com.softmed.htmr_facility.utils.constants.SYNC_STATUS;
import static com.softmed.htmr_facility.utils.constants.SYNC_STATUS_OFF;
import static com.softmed.htmr_facility.utils.constants.SYNC_STATUS_ON;
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

    private int foundSyncItems = 0;

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
                     Log.d(TAG,"facility ID = "+session.getKeyHfid());
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

        //scheduleBackgroundJob();

//        LiveData<AppData> syncDataObserver = database.appDataModelDao().observeAppDataByName(SYNC_STATUS);
//        syncDataObserver.observe(this, new Observer<AppData>() {
//            @Override
//            public void onChanged(@Nullable AppData appData) {
//                if (appData!=null){
//                    if (appData.getName().equals(SYNC_STATUS)){
//                        if (appData.getValue().equals(SYNC_STATUS_ON)){
//                            //Syncing
//                            manualSync.setVisibility(View.INVISIBLE);
//                            syncProgressBar.setVisibility(View.VISIBLE);
//                        }else if (appData.getValue().equals(SYNC_STATUS_OFF)){
//                            //Stopped Syncing
//                            manualSync.setVisibility(View.VISIBLE);
//                            syncProgressBar.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                }
//            }
//        });

        manualSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        manualSync.setVisibility(View.INVISIBLE);
                        syncProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        Log.d(TAG, "onClick: Manual syncing");

                        AppData appData = new AppData();
                        appData.setName(SYNC_STATUS);
                        appData.setValue(SYNC_STATUS_ON);

                        int postOfficeCount = database.postOfficeModelDao().getUnpostedDataCount();

                        if (postOfficeCount > 0){
                            database.appDataModelDao().insertAppData(appData);
                            syncDataInPostOffice();
                        }
                        refreshAllReferrals();
//                        //Syncing data using firebase's job Dispatcher
//                        Job myJob = dispatcher.newJobBuilder()
//                                .setService(DataSyncJob.class) // the JobService that will be called
//                                .setTag("10002")       // uniquely identifies the job
//                                .setRecurring(false)
//                                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
//                                //.setTrigger(Trigger.NOW)
//                                .setTrigger(Trigger.executionWindow(0, 0)) //60 seconds changed to 0 seconds to dispatch the job instantly
//                                .setReplaceCurrent(false)
//                                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                                .build();
//
//                        dispatcher.mustSchedule(myJob);
                        return null;

                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        //Stopped Syncing
                        manualSync.setVisibility(View.VISIBLE);
                        syncProgressBar.setVisibility(View.INVISIBLE);
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
                .setService(DataSyncJob.class)
                .setTag("10002")
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 0))
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

    private void syncDataInPostOffice(){

        //Check if data is available in the PostOffice
        if (database.postOfficeModelDao().getUnpostedDataCount() > 0){

            /**
             * Get the list of all unposted data that exist at postOffice
             */
            List<PostOffice> unpostedData = new ArrayList<>();
            unpostedData = database.postOfficeModelDao().getUnpostedData();

            /**
             * Sort the unposted data and start syncing to maintain data integrity
             */
            List<PostOffice> patientsData = new ArrayList<>();
            List<PostOffice> tbPatientData = new ArrayList<>();
            List<PostOffice> referralData = new ArrayList<>();
            List<PostOffice> referralFeedbackData = new ArrayList<>();
            List<PostOffice> encounterData = new ArrayList<>();
            List<PostOffice> appointmentData = new ArrayList<>();

            Log.d("HomeActivity", "Size of data in postman is : "+unpostedData.size());

            for (PostOffice data : unpostedData){
                switch (data.getPost_data_type()){
                    case POST_DATA_TYPE_PATIENT:
                        foundSyncItems++;
                        patientsData.add(data);
                        //handlePatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_TB_PATIENT:
                        foundSyncItems++;
                        tbPatientData.add(data);
                        //handleTbPatientDataSync(data);
                        break;
                    case POST_DATA_TYPE_REFERRAL:
                        foundSyncItems++;
                        referralData.add(data);
                        //handleReferralDataSync(data);
                        break;
                    case POST_DATA_REFERRAL_FEEDBACK:
                        foundSyncItems++;
                        referralFeedbackData.add(data);
                        //handleReferralFeedbackDataSync(data);
                        break;
                    case POST_DATA_TYPE_ENCOUNTER:
                        foundSyncItems = foundSyncItems+1;
                        Log.d("track_count", "Added : "+foundSyncItems);
                        encounterData.add(data);
                        //handleEncounterDataSync(data);
                        break;
                    case POST_DATA_TYPE_APPOINTMENTS:
                        foundSyncItems++;
                        appointmentData.add(data);
                        break;
                }
            }

            //Saving patient data
            for (PostOffice data : patientsData){
                handlePatientDataSync(data);
            }

            //Saving TbPatientData
            for (PostOffice data : tbPatientData){
                handleTbPatientDataSync(data);
            }

            //Saving referral data
            for (PostOffice data : referralData){
                handleReferralDataSync(data);
            }

            //Saving referral feefback data
            for (PostOffice data : referralFeedbackData){
                handleReferralFeedbackDataSync(data);
            }

            //Saving encounter data
            for (PostOffice data : encounterData){
                handleEncounterDataSync(data);
            }

            //Saving appointment data
            for (PostOffice data : appointmentData){
                handleAppointmentDataSync(data);
            }

        }
    }

    private void handlePatientDataSync(PostOffice data){

        logthat("Handling Patient data");
        Log.d("HomeActivity", "Patient");



        logthat("Post Data ID = "+data.getPost_id());
        //Get the patient that needs to be synced
        final Patient patient = database.patientModel().getPatientById(data.getPost_id());

        //Get UserData for the currently logged in user
        final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

        //Make a server call to send the current patient to the server
        Call call = patientServices.postPatient(session.getServiceProviderUUID(), getPatientRequestBody(patient, userData.getUserFacilityId()));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                foundSyncItems--;
                isSyncFinished();
                /**
                 * Check to see if response is not null and the server has returned something
                 */
                if (response != null){
                    if (response.body() != null){
                        //Get the patient object from the response
                        Patient rPatient = (Patient) response.body();

                        new AsyncTask<Patient, Void, Void>(){
                            @Override
                            protected Void doInBackground(Patient... patients) {
                                Patient oldPatient = patients[0];
                                Patient receivedPatient = patients[1];

                                // :1: Change referrals patientIds

                                //Get all the referrals associated with the previously locally stored patient
                                List<Referral> oldPatientReferrals = new ArrayList<>();
                                oldPatientReferrals = database.referalModel().getReferalsByPatientId(oldPatient.getPatientId()).getValue();

                                //Loop through all the referrals and change the patient ID
                                if (oldPatientReferrals != null){
                                    for (int i=0; i<oldPatientReferrals.size(); i++){
                                        Referral ref = oldPatientReferrals.get(i);
                                        if (ref.getPatient_id() != rPatient.getPatientId()){
                                            ref.setPatient_id(rPatient.getPatientId());
                                            database.referalModel().addReferal(ref);
                                        }
                                    }

                                }

                                // :2: Changing all appointments associated with the old patient to the new patient

                                List<PatientAppointment> oldPatientAppointments = database.appointmentModelDao().getAppointmentsByTypeAndPatientID(2, oldPatient.getPatientId());
                                if (oldPatientAppointments != null){
                                    for (PatientAppointment appointment : oldPatientAppointments){
                                        appointment.setPatientID(rPatient.getPatientId());
                                        database.appointmentModelDao().updateAppointment(appointment);
                                    }
                                }

                                //Delete the old object
                                database.patientModel().deleteAPatient(oldPatient);

                                //Insert server's patient reference
                                database.patientModel().addPatient(receivedPatient);

                                /**
                                 * Delete the postOffice data from the database after it has been successfully posted to the server
                                 */
                                database.postOfficeModelDao().deletePostData(data);


                                // :3: Change TbPatient HealthFacilityClientIds

                                TbPatient tbPatient = database.tbPatientModelDao().getTbPatientCurrentOnTreatment(oldPatient.getPatientId());
                                if (tbPatient != null){
                                    tbPatient.setHealthFacilityPatientId(Long.valueOf(receivedPatient.getPatientId()));
                                    database.tbPatientModelDao().updateTbPatient(tbPatient);
                                }

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);

                            }
                        }.execute(patient, rPatient);

                    }else {
                        logthat("The body of the response from server is null : Code = "+response.code());
                    }
                }else{
                    //Server returned null
                    logthat("Server has returned Null");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                foundSyncItems--;
                Log.d("patient_response", t.getMessage());
            }
        });
    }

    private void handleTbPatientDataSync(PostOffice data){

        Log.d("HomeActivity", "Tb Patient");
        logthat("Handling TB patient data");

        final TbPatient tbPatient = database.tbPatientModelDao().getTbPatientByTbPatientId(data.getPost_id());
        final Patient patient = database.patientModel().getPatientById(String.valueOf(tbPatient.getHealthFacilityPatientId()));
        final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

        Call call = patientServices.postTbPatient(session.getServiceProviderUUID(), getTbPatientRequestBody(patient, tbPatient, userData));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                foundSyncItems--;
                isSyncFinished();
                //Store Received Patient Information, TbPatient as well as PatientAppointments
                if (response != null){
                    if (response.code() == RESPONCE_SUCCESS){

                        new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... voids) {
                                database.postOfficeModelDao().deletePostData(data);
                                return null;
                            }
                        }.execute();

                        //If response is success then the tpPatient has been saved successfully

                            /*
                            TbPatient receivedPatient = new TbPatient(); //Change this to received patient from the server
                            List<TbEncounters> encounters = baseDatabase.tbEncounterModelDao().getEncounterByPatientID(tbPatient.getTbPatientId());
                            for (TbEncounters encounter : encounters){
                                encounter.setTbPatientID(receivedPatient.getTbPatientId());
                                baseDatabase.tbEncounterModelDao().addEncounter(encounter);
                            }
                             */

                    }else {
                        logthat("Error updating data CODE : "+response.code());
                    }
                }else {
                    logthat("Server returned Null");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                foundSyncItems--;
                isSyncFinished();
                Log.d("patient_response", "Error : "+t.getStackTrace());
            }
        });
    }

    private void handleReferralDataSync(PostOffice data){

        Log.d("HomeActivity", "Referral");
        logthat("Hangling referral data");

        final Referral referral = database.referalModel().getReferalById(data.getPost_id());
        try {
            int patientID = Integer.parseInt(referral.getPatient_id());
        }catch (NullPointerException e){
            e.printStackTrace();
            database.postOfficeModelDao().deletePostData(data);
        }

        Call call = referalService.postReferral(session.getServiceProviderUUID(), BaseActivity.getReferralRequestBody(referral));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                foundSyncItems--;
                isSyncFinished();
                if (response != null){
                    if (response.body() != null) {
                        Referral rReferral = (Referral) response.body();
                        new AsyncTask<Referral, Void, Void>(){
                            @Override
                            protected Void doInBackground(Referral... referrals) {

                                Referral oldReferral = referrals[0];
                                Referral receivedReferral = referrals[1];

                                database.referalModel().deleteReferal(oldReferral); //Delete local referral reference
                                database.referalModel().addReferal(receivedReferral); //Store the server's referral reference
                                database.postOfficeModelDao().deletePostData(data);
                                return null;
                            }
                        }.execute(referral, rReferral);


                    } else {
                        logthat("Server returned an error "+response.code());
                    }
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                foundSyncItems--;
                isSyncFinished();
                logthat("Sync Failed : "+t.getMessage());
            }
        });
    }

    private void handleReferralFeedbackDataSync(PostOffice data){
        Log.d("HomeActivity", "Referral Feedback");
        logthat("Handling referral feedback data");

        final Referral referral = database.referalModel().getReferalById(data.getPost_id());
        final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

        Call call = referalService.sendReferralFeedback(session.getServiceProviderUUID(), BaseActivity.getReferralFeedbackRequestBody(referral, userData));
        call.enqueue(new Callback() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call call, Response response) {
                foundSyncItems--;
                isSyncFinished();
                if (response != null){
                    if (response.body() != null){
                        if (response.code() == 200){
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    database.postOfficeModelDao().deletePostData(data);
                                    return null;
                                }
                            }.execute();
                        }else {
                            logthat("Referral feedback response is "+response.code());
                        }
                    }
                }else{
                    logthat("Responce received is null, Please try again later");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                foundSyncItems--;
                isSyncFinished();
                logthat("Syncing data failed with message : "+t.getMessage());
            }
        });
    }

    private void handleEncounterDataSync(PostOffice data){

        Log.d("track_count", "Called encounters");
        foundSyncItems = foundSyncItems-1;

        /**
         * Get the list of all Encounters with the Id stored on postOffice
         */
        List<TbEncounters> encounter = database.tbEncounterModelDao().getEncounterByLocalId(data.getPost_id());

        /**
         * Loop through all of them to send to server
         */
        for (TbEncounters e : encounter){

            logthat("handleEncounterDataSync: Service Provider UUID : "+session.getServiceProviderUUID());

            Call call = patientServices.postEncounter(session.getServiceProviderUUID(), BaseActivity.getTbEncounterRequestBody(e));
            Log.d("track_count", "Substracting Encounter : "+foundSyncItems);

            call.enqueue(new Callback() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onResponse(Call call, Response response) {

                    Log.d("track_count", "This is response at count : "+foundSyncItems);

                    isSyncFinished();
                    /*
                    Receive an Encounter object as a response
                    Replace the local encounter with the response encounter
                    */
                    if (response != null){
                        if (response.body() != null) {

                            logthat("Posting encounter response : "+response.body());
                            EncounterResponse encResponse = (EncounterResponse) response.body();

                            new AsyncTask<EncounterResponse, Void, Void>(){
                                @Override
                                protected Void doInBackground(EncounterResponse... encounterResponses) {
                                    EncounterResponse encounterResponse = encounterResponses[0];

                                    TbEncounters receivedEncounter =  encounterResponse.getEncounter();
                                    database.tbEncounterModelDao().deleteAnEncounter(e);
                                    database.tbEncounterModelDao().addEncounter(receivedEncounter);
                                    database.postOfficeModelDao().deletePostData(data);

                                    TbPatient tbPatient = database.tbPatientModelDao().getTbPatientByTbPatientId(receivedEncounter.getTbPatientID()+"");
                                    //Delete all appointments associated with the locally stored patient
                                    List<PatientAppointment> appointments = database.appointmentModelDao().getAppointmentsByTypeAndPatientID(2, tbPatient.getHealthFacilityPatientId()+"");
                                    for (PatientAppointment a : appointments){
                                        database.appointmentModelDao().deleteAppointment(a);
                                    }

                                    //Insert the new appointment of patient received from the server
                                    List<PatientAppointment> listOfAppointments = encounterResponse.getPatientAppointments();
                                    for (PatientAppointment appointment : listOfAppointments){
                                        database.appointmentModelDao().addAppointment(appointment);
                                    }

                                    return null;
                                }
                            }.execute(encResponse);

                        }else {
                            logthat("Server returned response null : "+response.code());
                        }
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d("track_count", "This is encounter failure"+foundSyncItems);
                    isSyncFinished();
                    t.printStackTrace();
                }
            });

        }
    }

    private void handleAppointmentDataSync(PostOffice data){

    }

    @SuppressLint("StaticFieldLeak")
    private void isSyncFinished(){

        logthat("Checked now : "+foundSyncItems);
        Log.d("track_count", "Tracking Found "+foundSyncItems);

        if (foundSyncItems == 0){
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    Log.d("track_count", "Updating the database to set sync off");
                    AppData appData = new AppData();
                    appData.setName(SYNC_STATUS);
                    appData.setValue(SYNC_STATUS_OFF);
                    database.appDataModelDao().insertAppData(appData);
                    return null;
                }
            }.execute();

        }
    }

    private void logthat(String message){
        Log.d("HomeActivity", message);
    }

    private void refreshAllReferrals(){

        Call<List<ReferalResponce>> call = referalService.getHealthFacilityReferrals(session.getKeyHfid());
        try {
            List<ReferalResponce> responces = call.execute().body();
            for (ReferalResponce mList : responces){
                database.patientModel().addPatient(mList.getPatient());
                Log.d("InitialSync", "Patient  : "+mList.getPatient().getPatientFirstName());
                for (Referral referral : mList.getPatientReferalList()){
                    Log.d("InitialSync", "Referal  : "+ referral.toString());
                    database.referalModel().addReferal(referral);
                }

                //Delete all appointments with these patient's IDs
                database.appointmentModelDao().deleteAppointmentByPatientID(mList.getPatient().getPatientId());

                List<PatientAppointment> appointments = mList.getPatientAppointments();
                for (PatientAppointment _appointment : appointments){
                    Log.d("Appointment", "Inserting appointment on : "+_appointment.getAppointmentDate());
                    database.appointmentModelDao().addAppointment(_appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
