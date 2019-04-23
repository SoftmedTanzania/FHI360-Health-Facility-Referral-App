package com.softmed.htmr_facility_staging.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.softmed.htmr_facility_staging.R;
import com.softmed.htmr_facility_staging.adapters.MissedAppointmentFragment;
import com.softmed.htmr_facility_staging.adapters.UpcomingAppointmentFragment;
import com.softmed.htmr_facility_staging.base.BaseActivity;
import com.softmed.htmr_facility_staging.customviews.NonSwipeableViewPager;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.view.View.GONE;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class AppointmentActivity extends BaseActivity {

    private Toolbar toolbar;
    private MaterialSpinner statusSpinner, appointmentType;
    private mAdapter appointmentTypeAdapter;
    private List<String> appointmentTypeList = new ArrayList<>();
    private TabLayout tabLayout;
    public static NonSwipeableViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        setupviews();

        //initialize viewpager
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    appointmentType.setVisibility(View.VISIBLE);
                }else{

                    appointmentType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //initialize tablayout
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        });


        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.client_appointment));
        }

        appointmentTypeList.add(getResources().getString(R.string.ctc));
        appointmentTypeList.add(getResources().getString(R.string.tb));
        appointmentTypeAdapter = new mAdapter(this, R.layout.subscription_plan_items_drop_down, appointmentTypeList);
        appointmentType.setAdapter(appointmentTypeAdapter);



        appointmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1){
                }else if (i == 0){
                    findViewById(R.id.tb_appointments_header).setVisibility(GONE);
                    findViewById(R.id.ctc_header).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.ctc_header).setVisibility(GONE);
                    findViewById(R.id.tb_appointments_header).setVisibility(View.VISIBLE);
                }

                UpcomingAppointmentFragment upcomingAppointments = (UpcomingAppointmentFragment)viewPager
                        .getAdapter()
                        .instantiateItem(viewPager, viewPager.getCurrentItem());
                upcomingAppointments.updateListType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        appointmentType.setSelection(1);


    }

    private void setupviews(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        appointmentType = (MaterialSpinner) findViewById(R.id.spin_appointment_type);
        statusSpinner = (MaterialSpinner) findViewById(R.id.spin_status);

        viewPager =  findViewById(R.id.viewpager);
        tabLayout =  findViewById(R.id.tabs);

    }

    class mAdapter extends ArrayAdapter<String> {

        List<String> items = new ArrayList<>();
        Context act;

        public mAdapter(Context context, int resource, List<String> mItems) {
            super(context, resource, mItems);
            this.items = mItems;
            act = context;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(R.layout.subscription_plan_items_drop_down, null);

            TextView tvTitle =(TextView)rowView.findViewById(R.id.rowtext);
            tvTitle.setText(items.get(position));

            return rowView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View rowView = convertView;
            LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(R.layout.single_text_spinner_view_item, null);

            TextView tvTitle = (TextView)rowView.findViewById(R.id.rowtext);
            tvTitle.setText(items.get(position));

            return rowView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void updateItems(List<String> newItems){
            this.items = null;
            this.items = newItems;
            this.notifyDataSetChanged();
        }

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

    public void setupTabIcons() {
        View upcomingTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView ctcTabTitle = upcomingTabView.findViewById(R.id.title_text);
        ImageView ctcIcon    = upcomingTabView.findViewById(R.id.icon);
        if (!this.isFinishing())
            Glide.with(this).load(R.mipmap.ic_face).into(ctcIcon);
        ctcTabTitle.setText(getResources().getString(R.string.upcoming_appointments));
        tabLayout.getTabAt(0).setCustomView(upcomingTabView);

        View missedTabView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView opdTabTitle = missedTabView.findViewById(R.id.title_text);
        opdTabTitle.setText(getResources().getString(R.string.missed_appointments));
        ImageView opdIcon    = missedTabView.findViewById(R.id.icon);
        opdIcon.setColorFilter(this.getResources().getColor(R.color.white));
        if (!this.isFinishing()){
            Glide.with(this).load(R.mipmap.ic_hiv).into(opdIcon);
        }
        tabLayout.getTabAt(1).setCustomView(missedTabView);


    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingAppointmentFragment(), "upcomingAppointment");
        adapter.addFragment(new MissedAppointmentFragment(), "missedAppointment");

        viewPager.setAdapter(adapter);
    }

}
