package apps.softmed.com.hfreferal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rey.material.widget.ProgressView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apps.softmed.com.hfreferal.adapters.ReferalListRecyclerAdapter;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.NonSwipeableViewPager;
import apps.softmed.com.hfreferal.dom.objects.Client;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.fragments.ChwReferalListFragment;
import apps.softmed.com.hfreferal.fragments.HfReferralListFragment;
import apps.softmed.com.hfreferal.fragments.TbReferralListFragment;
import apps.softmed.com.hfreferal.viewmodels.ReferalListViewModel;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.STATUS_COMPLETED;
import static apps.softmed.com.hfreferal.utils.constants.STATUS_NEW;
import static apps.softmed.com.hfreferal.utils.constants.TB_SERVICE_ID;
import static apps.softmed.com.hfreferal.utils.constants.getReferralStatusValue;

/**
 * Created by issy on 12/6/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbReferalListActivity extends BaseActivity {

    private TabLayout tabLayout;
    public static NonSwipeableViewPager viewPager;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal_list);
        setupview();

        database = AppDatabase.getDatabase(this);

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

    private void setupview(){

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
        TbReferalListActivity.ViewPagerAdapter adapter = new TbReferalListActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TbReferralListFragment(), "hf");
        adapter.addFragment(new ChwReferalListFragment(), "chw");
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
