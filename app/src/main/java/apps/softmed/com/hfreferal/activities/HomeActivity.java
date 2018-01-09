package apps.softmed.com.hfreferal.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.NonSwipeableViewPager;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.fragments.HivFragment;
import apps.softmed.com.hfreferal.fragments.MalariaFragment;
import apps.softmed.com.hfreferal.fragments.TbFragment;
import apps.softmed.com.hfreferal.utils.AlarmReceiver;
import apps.softmed.com.hfreferal.utils.Config;
import apps.softmed.com.hfreferal.viewmodels.PostOfficeListViewModel;

import static android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES;

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

    private PostOfficeListViewModel viewModel;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupviews();

        database = AppDatabase.getDatabase(this);

        session.checkLogin();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        if (session.isLoggedIn()){
            toolbarTitle.setText(session.getUserName());
        }

        viewModel = ViewModelProviders.of(this).get(PostOfficeListViewModel.class);
        viewModel.getUnpostedDataList().observe(HomeActivity.this, new Observer<List<PostOffice>>() {
            @Override
            public void onChanged(@Nullable List<PostOffice> postOffices) {
                unsynced.setText("Unsynced Data : "+postOffices.size());
            }
        });

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

    private void setupviews(){
        toolbarTitle = (TextView) findViewById(R.id.toolbar_user_name);
        unsynced = (TextView) findViewById(R.id.unsynced);
        unsynced.setTextColor(getResources().getColor(R.color.orange_400));
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

}
