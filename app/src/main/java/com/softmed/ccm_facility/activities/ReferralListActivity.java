package com.softmed.ccm_facility.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.base.BaseActivity;
import com.softmed.ccm_facility.customviews.NonSwipeableViewPager;
import com.softmed.ccm_facility.fragments.ReferralListFragment;

import static com.softmed.ccm_facility.utils.constants.CHW_TO_FACILITY;
import static com.softmed.ccm_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.ccm_facility.utils.constants.INTERFACILITY;
import static com.softmed.ccm_facility.utils.constants.INTRAFACILITY;
import static com.softmed.ccm_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.ccm_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.ccm_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 12/10/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferralListActivity extends BaseActivity {

    private TabLayout tabLayout;
    public static NonSwipeableViewPager viewPager;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    private AppDatabase database;
    private int serviceID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal_list);
        setupviews();

        database = AppDatabase.getDatabase(this);

        if (getIntent().getExtras() != null){
            serviceID = getIntent().getExtras().getInt("service");
        }

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String title = "";
            if(serviceID==OPD_SERVICE_ID){
                title = getString(R.string.referral_list_opd_title);
            }else if(serviceID==HIV_SERVICE_ID){
                title = getString(R.string.referral_list_hiv_title);
            }else if(serviceID==TB_SERVICE_ID){
                title = getString(R.string.referral_list_tb_title);
            }else if (serviceID==LAB_SERVICE_ID){
                title = getResources().getString(R.string.referral_list_lab_title);
            }

            toolbarTitle.setText(title);

        }

        tabLayout =  findViewById(R.id.tabs);
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupviews(){
        toolbarTitle = findViewById(R.id.activity_title);
    }

    public void setupTabIcons() {

        //If service is OPD show CHW tab if its any other show only the default HF Tab
        if (serviceID == HIV_SERVICE_ID || serviceID == LAB_SERVICE_ID){

            View newsView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
            TextView newsTitle = (TextView) newsView.findViewById(R.id.title_text);
            newsTitle.setText(getResources().getString(R.string.health_facility_referrals));
            ImageView iv2    = (ImageView) newsView.findViewById(R.id.icon);
            iv2.setColorFilter(getResources().getColor(R.color.white));
            Glide.with(this).load(R.mipmap.ic_referals_list).into(iv2);
            tabLayout.getTabAt(0).setCustomView(newsView);

        }else {

            View homeView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
            TextView homeTitle = (TextView) homeView.findViewById(R.id.title_text);
            ImageView iv    = (ImageView) homeView.findViewById(R.id.icon);
            iv.setColorFilter(getResources().getColor(R.color.white));
            Glide.with(this).load(R.mipmap.ic_referals_list).into(iv);
            homeTitle.setText(getResources().getString(R.string.chw_referrals));
            tabLayout.getTabAt(0).setCustomView(homeView);

            View newsView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
            TextView newsTitle = (TextView) newsView.findViewById(R.id.title_text);
            newsTitle.setText(getResources().getString(R.string.health_facility_referrals));
            ImageView iv2    = (ImageView) newsView.findViewById(R.id.icon);
            iv2.setColorFilter(getResources().getColor(R.color.white));
            Glide.with(this).load(R.mipmap.ic_referals_list).into(iv2);
            tabLayout.getTabAt(1).setCustomView(newsView);

        }

    }

    public void setupViewPager(ViewPager viewPager) {

        ReferralListActivity.ViewPagerAdapter adapter = new ReferralListActivity.ViewPagerAdapter(getSupportFragmentManager());

        //If service is OPD show CHW tab if its any other show only the default HF Tab
        if (serviceID == HIV_SERVICE_ID || serviceID == LAB_SERVICE_ID){
            adapter.addFragment(ReferralListFragment.newInstance(INTRAFACILITY, serviceID), "hf");
            tabLayout.setVisibility(View.GONE);
        }else {
            adapter.addFragment(ReferralListFragment.newInstance(CHW_TO_FACILITY, serviceID), "chw");
            adapter.addFragment(ReferralListFragment.newInstance(INTERFACILITY, serviceID), "hf");
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

}
