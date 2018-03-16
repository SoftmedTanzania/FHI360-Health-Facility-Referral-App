package com.softmed.htmr_facility.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.customviews.NonSwipeableViewPager;
import com.softmed.htmr_facility.fragments.reports.ChwReferralsReportFragment;
import com.softmed.htmr_facility.fragments.reports.InterFacilityReferralReportFragment;
import com.softmed.htmr_facility.fragments.reports.IntraFacilityReferralsReportFragment;
import com.softmed.htmr_facility.fragments.reports.ReportChartsFragment;
import com.softmed.htmr_facility.fragments.reports.TbAppointmentReportsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issy on 08/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReportsActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private NonSwipeableViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_layout);
        setUpViews();

        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.reports_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setUpTabs();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setUpViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpTabs(){

        View reportChartsTab = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView chartsTitle = (TextView) reportChartsTab.findViewById(R.id.title_text);
        chartsTitle.setText("Charts"); //TODO language
        tabLayout.getTabAt(0).setCustomView(reportChartsTab);

        View chwReferralsReportTab = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView chwReportTitle = (TextView) chwReferralsReportTab.findViewById(R.id.title_text);
        chwReportTitle.setText("CHW Referrals Report"); //TODO language
        tabLayout.getTabAt(1).setCustomView(chwReferralsReportTab);
        ImageView iv1    = (ImageView) chwReferralsReportTab.findViewById(R.id.icon);
        iv1.setVisibility(View.GONE);

        View interFacilityReferralsReportTab = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView interFacilityReferralsReportTitle = (TextView) interFacilityReferralsReportTab.findViewById(R.id.title_text);
        interFacilityReferralsReportTitle.setText("Inter Facility Referrals Report"); //TODO language
        tabLayout.getTabAt(2).setCustomView(interFacilityReferralsReportTab);
        ImageView iv2    = (ImageView) interFacilityReferralsReportTab.findViewById(R.id.icon);
        iv2.setVisibility(View.GONE);

        View intraFacilityReferralsReportTab = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView intraFacilityReferralsReportTitle = (TextView) intraFacilityReferralsReportTab.findViewById(R.id.title_text);
        intraFacilityReferralsReportTitle.setText("Intra Facility Referrals Report"); //TODO language
        tabLayout.getTabAt(3).setCustomView(intraFacilityReferralsReportTab);
        ImageView iv3 = (ImageView) intraFacilityReferralsReportTab.findViewById(R.id.icon);
        iv3.setVisibility(View.GONE);

        View tbAppointmentsReportsTab = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView reportTitle = (TextView) tbAppointmentsReportsTab.findViewById(R.id.title_text);
        reportTitle.setText("Tb Appointments Report"); //TODO language
        tabLayout.getTabAt(4).setCustomView(tbAppointmentsReportsTab);
        ImageView iv0    = (ImageView) tbAppointmentsReportsTab.findViewById(R.id.icon);
        iv0.setVisibility(View.GONE);

    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReportChartsFragment(), "reports_chard_fragment");
        adapter.addFragment(new ChwReferralsReportFragment(), "chw_referrals_report");
        adapter.addFragment(new InterFacilityReferralReportFragment(), "inter_facility_referrals_report");
        adapter.addFragment(new IntraFacilityReferralsReportFragment(), "intra_facility_referrals_report");
        adapter.addFragment(new TbAppointmentReportsFragment(), "tb_appointments_report");
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
