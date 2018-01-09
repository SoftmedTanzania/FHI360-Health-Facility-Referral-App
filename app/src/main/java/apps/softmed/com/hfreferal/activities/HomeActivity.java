package apps.softmed.com.hfreferal.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.NonSwipeableViewPager;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PatientAppointment;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.objects.TbPatient;
import apps.softmed.com.hfreferal.dom.objects.UserData;
import apps.softmed.com.hfreferal.dom.responces.PatientResponce;
import apps.softmed.com.hfreferal.fragments.HivFragment;
import apps.softmed.com.hfreferal.fragments.MalariaFragment;
import apps.softmed.com.hfreferal.fragments.TbFragment;
import apps.softmed.com.hfreferal.utils.AlarmReceiver;
import apps.softmed.com.hfreferal.utils.Config;
import apps.softmed.com.hfreferal.utils.ServiceGenerator;
import apps.softmed.com.hfreferal.viewmodels.PostOfficeListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_ENCOUNTER;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_PATIENT;
import static apps.softmed.com.hfreferal.utils.constants.POST_DATA_TYPE_REFERRAL;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class HomeActivity extends BaseActivity {

    private final String TAG = HomeActivity.class.getSimpleName();

    private TabLayout tabLayout;
    public static NonSwipeableViewPager viewPager;
    private Toolbar toolbar;
    private TextView toolbarTitle, unsynced;
    private CircularProgressView syncProgressBar;
    private ImageView manualSync;

    private PostOfficeListViewModel viewModel;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AppDatabase database;
    private Endpoints.PatientServices patientServices;
    private Endpoints.ReferalService referalService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupviews();

        database = AppDatabase.getDatabase(this);

        session.checkLogin();

        patientServices = ServiceGenerator.createService(Endpoints.PatientServices.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());

        referalService = ServiceGenerator.createService(Endpoints.ReferalService.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        if (session.isLoggedIn()){
            toolbarTitle.setText(session.getUserName());
        }

        checkPostOfficeData();

        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        });

        manualSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Manual Sync the data in postman
                new SyncPostOfficeData().execute();
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


        scheduleAlarm();

    }

    private void checkPostOfficeData(){

        viewModel = ViewModelProviders.of(this).get(PostOfficeListViewModel.class);
        viewModel.getUnpostedDataList().observe(HomeActivity.this, new Observer<List<PostOffice>>() {
            @Override
            public void onChanged(@Nullable List<PostOffice> postOffices) {
                if (postOffices.size() > 0){
                    unsynced.setText("Unsynced Data : "+postOffices.size());
                    unsynced.setTextColor(getResources().getColor(R.color.orange_400));
                }else {
                    unsynced.setText("Data Synced");
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
                Log.d("PostOfficeService", data.getPost_data_type());

                if (data.getPost_data_type().equals(POST_DATA_TYPE_PATIENT)){

                    final Patient patient = database.patientModel().getPatientById(data.getPost_id());
                    final TbPatient tbPatient = database.tbPatientModelDao().getTbPatientById(patient.getPatientId());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = patientServices.postPatient(getPatientRequestBody(patient, tbPatient, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            PatientResponce patientResponce = (PatientResponce) response.body();
                            //Store Received Patient Information, TbPatient as well as PatientAppointments
                            if (response.body()!=null){
                                Log.d("patient_response", response.body().toString());

                                Patient patient1 = patientResponce.getPatient();
                                TbPatient tbPatient1 = patientResponce.getTbPatient();
                                List<PatientAppointment> appointments = patientResponce.getPatientAppointments();

                                //Delete local patient reference
                                database.patientModel().deleteAPatient(patient);
                                database.tbPatientModelDao().deleteAPatient(tbPatient);
                                List<PatientAppointment> oldAppointments = database.appointmentModelDao().getThisPatientAppointments(patient.getPatientId());
                                for (int i=0; i<oldAppointments.size(); i++){
                                    database.appointmentModelDao().deleteAppointment(oldAppointments.get(i));
                                }

                                //Insert server's patient reference
                                database.patientModel().addPatient(patient1);
                                database.tbPatientModelDao().addPatient(tbPatient1);
                                for (int j=0; j<appointments.size(); j++){
                                    database.appointmentModelDao().addAppointment(appointments.get(j));
                                }

                            }else {
                                Log.d("patient_response","Patient Responce is null "+response.body());
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("patient_response", t.getMessage());
                        }
                    });
                }else if (data.getPost_data_type().equals(POST_DATA_TYPE_REFERRAL)){

                    final Referral referral = database.referalModel().getReferalById(data.getPost_id());
                    final UserData userData = database.userDataModelDao().getUserDataByUserUIID(session.getUserDetails().get("uuid"));

                    Call call = referalService.postReferral(getReferralRequestBody(referral, userData));
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Referral receivedReferral = (Referral) response.body();
                            if (response.body() != null){

                                Log.d("PostReferral", response.body().toString());
                                database.referalModel().deleteReferal(referral); //Delete local referral reference
                                database.referalModel().addReferal(receivedReferral); //Store the server's referral reference

                            }else {
                                Log.d("PostReferral", "Responce is Null : "+response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("PostReferral", t.getMessage());
                        }
                    });


                }else if(data.getPost_data_type().equals(POST_DATA_TYPE_ENCOUNTER)){
                    //TbEncounters encounter = database.tbEncounterModelDao().getEncounterByPatientID(data.getPost_id());
                }
            }

        }
    }

    private void setupviews(){

        syncProgressBar = (CircularProgressView) findViewById(R.id.manual_sync_loader);
        syncProgressBar.setVisibility(View.INVISIBLE);

        manualSync = (ImageView) findViewById(R.id.manual_sync);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_user_name);
        unsynced = (TextView) findViewById(R.id.unsynced);
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
        }

        if (id == R.id.notifications){
            //Display List Of Reminders
            View menuItemView = findViewById(R.id.notifications); // SAME ID AS MENU ID
            PopupMenu popupMenu = new PopupMenu(this, menuItemView);
            popupMenu.inflate(R.menu.notification_list);
            // ...
            popupMenu.show();
            // ...
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

        View homeView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView homeTitle = (TextView) homeView.findViewById(R.id.title_text);
        ImageView iv    = (ImageView) homeView.findViewById(R.id.icon);
//        iv.setColorFilter(this.getResources().getColor(R.color.colorPrimary));
        Glide.with(this).load(R.mipmap.ic_hiv).into(iv);
        homeTitle.setText("CTC");
        tabLayout.getTabAt(0).setCustomView(homeView);

        View newsView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView newsTitle = (TextView) newsView.findViewById(R.id.title_text);
        newsTitle.setText("Kifua Kikuu");
        ImageView iv2    = (ImageView) newsView.findViewById(R.id.icon);
//        iv2.setColorFilter(this.getResources().getColor(R.color.colorPrimary));
        Glide.with(this).load(R.mipmap.ic_tb).into(iv2);
        tabLayout.getTabAt(1).setCustomView(newsView);

        View ccmView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView ccmTitle = (TextView) ccmView.findViewById(R.id.title_text);
        ccmTitle.setText("Malaria");
        ImageView iv3    = (ImageView) ccmView.findViewById(R.id.icon);
//        iv3.setColorFilter(this.getResources().getColor(R.color.colorPrimary));
        Glide.with(this).load(R.mipmap.ic_malaria).into(iv3);
        tabLayout.getTabAt(2).setCustomView(ccmView);

    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HivFragment(), "Hiv");
        adapter.addFragment(new TbFragment(), "Tb");
        adapter.addFragment(new MalariaFragment(), "Malaria");
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

    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                INTERVAL_FIFTEEN_MINUTES, pIntent);
    }

    class SyncPostOfficeData extends AsyncTask<Void,Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            manualSync.setVisibility(View.INVISIBLE);
            syncProgressBar.setVisibility(View.VISIBLE);
            unsynced.setText("Taarifa zinaoanishwa..");
            unsynced.setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            syncDataInPostOffice();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            manualSync.setVisibility(View.VISIBLE);
            syncProgressBar.setVisibility(View.INVISIBLE);
            unsynced.setText("Data Synced!");
            unsynced.setTextColor(getResources().getColor(R.color.white));
            checkPostOfficeData();
            super.onPostExecute(aVoid);
        }
    }

}
