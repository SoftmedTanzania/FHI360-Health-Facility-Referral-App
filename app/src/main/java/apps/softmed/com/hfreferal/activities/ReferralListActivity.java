package apps.softmed.com.hfreferal.activities;

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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.NonSwipeableViewPager;
import apps.softmed.com.hfreferal.fragments.HealthFacilityReferralListFragment;

import static apps.softmed.com.hfreferal.utils.constants.SOURCE_CHW;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_HF;

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

    }

    private void setupviews(){

    }

    public void setupTabIcons() {

        View homeView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView homeTitle = (TextView) homeView.findViewById(R.id.title_text);
        ImageView iv    = (ImageView) homeView.findViewById(R.id.icon);
        iv.setColorFilter(getResources().getColor(R.color.white));
        Glide.with(this).load(R.mipmap.ic_referals_list).into(iv);
        homeTitle.setText("Health Facility Referrals");
        tabLayout.getTabAt(0).setCustomView(homeView);

        View newsView = getLayoutInflater().inflate(R.layout.custom_tabs, null);
        TextView newsTitle = (TextView) newsView.findViewById(R.id.title_text);
        newsTitle.setText("CHW Referrals");
        ImageView iv2    = (ImageView) newsView.findViewById(R.id.icon);
        iv2.setColorFilter(getResources().getColor(R.color.white));
        Glide.with(this).load(R.mipmap.ic_referals_list).into(iv2);
        tabLayout.getTabAt(1).setCustomView(newsView);

    }

    public void setupViewPager(ViewPager viewPager) {

        ReferralListActivity.ViewPagerAdapter adapter = new ReferralListActivity.ViewPagerAdapter(getSupportFragmentManager());
        
        adapter.addFragment(HealthFacilityReferralListFragment.newInstance(SOURCE_HF, serviceID), "hf");
        adapter.addFragment(HealthFacilityReferralListFragment.newInstance(SOURCE_CHW, serviceID), "chw");

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
