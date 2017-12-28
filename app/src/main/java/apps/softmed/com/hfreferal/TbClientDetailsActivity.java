package apps.softmed.com.hfreferal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.customviews.NonSwipeableViewPager;
import apps.softmed.com.hfreferal.customviews.WrapContentHeightViewPager;
import apps.softmed.com.hfreferal.fragments.tb.MonthEight;
import apps.softmed.com.hfreferal.fragments.tb.MonthOne;
import apps.softmed.com.hfreferal.fragments.tb.MonthSeven;
import apps.softmed.com.hfreferal.fragments.tb.MonthTwoToSix;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.FEMALE;
import static apps.softmed.com.hfreferal.utils.constants.MALE;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEFARIKI;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEHAMA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEMALIZA_TIBA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMEPONA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_AMETOROKA;
import static apps.softmed.com.hfreferal.utils.constants.MATOKEO_HAKUPONA;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_1;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_2;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_3;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_4;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_5;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientDetailsActivity extends BaseActivity {

    private WrapContentHeightViewPager pager;
    private TabLayout tabLayout;
    private MaterialSpinner matibabuSpinner, matokeoSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_client_details);
        setupviews();

        setupViewPager(pager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(pager);
                setupTabIcons();
            }
        });

        final String[] treatmentTypes = {TREATMENT_TYPE_1, TREATMENT_TYPE_2, TREATMENT_TYPE_3, TREATMENT_TYPE_4, TREATMENT_TYPE_5};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, treatmentTypes);
        spinAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        matibabuSpinner.setAdapter(spinAdapter);

        final String[] matokeo = {MATOKEO_AMEPONA, MATOKEO_AMEMALIZA_TIBA, MATOKEO_AMEFARIKI, MATOKEO_AMETOROKA, MATOKEO_AMEHAMA, MATOKEO_HAKUPONA};
        ArrayAdapter<String> matokepSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item_black, matokeo);
        matokepSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        matokeoSpinner.setAdapter(matokepSpinnerAdapter);

    }

    private void setupviews(){
        pager = (WrapContentHeightViewPager) findViewById(R.id.encounters_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        matibabuSpinner = (MaterialSpinner) findViewById(R.id.spin_matibabu);
        matokeoSpinner = (MaterialSpinner) findViewById(R.id.spin_matokeo);
    }

    public void setupTabIcons() {

        View homeView = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView homeTitle = (TextView) homeView.findViewById(R.id.title_text);
        homeTitle.setText("Kabla Matibabu");
        tabLayout.getTabAt(0).setCustomView(homeView);

        View month2 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month2Title = (TextView) month2.findViewById(R.id.title_text);
        month2Title.setText("Mwezi 2");
        tabLayout.getTabAt(1).setCustomView(month2);

        View month3 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month3Title = (TextView) month3.findViewById(R.id.title_text);
        month3Title.setText("Mwezi 3");
        tabLayout.getTabAt(2).setCustomView(month3);

        View month4 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month4Title = (TextView) month4.findViewById(R.id.title_text);
        month4Title.setText("Mwezi 4");
        tabLayout.getTabAt(3).setCustomView(month4);

        View month5 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month5Title = (TextView) month5.findViewById(R.id.title_text);
        month5Title.setText("Mwezi 5");
        tabLayout.getTabAt(4).setCustomView(month5);

        View month6 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month6Title = (TextView) month6.findViewById(R.id.title_text);
        month6Title.setText("Mwezi 6");
        tabLayout.getTabAt(5).setCustomView(month6);

        View month7 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month7Title = (TextView) month7.findViewById(R.id.title_text);
        month7Title.setText("Mwezi 7");
        tabLayout.getTabAt(6).setCustomView(month7);

        View month8 = getLayoutInflater().inflate(R.layout.custom_tab_white, null);
        TextView month8Title = (TextView) month8.findViewById(R.id.title_text);
        month8Title.setText("Mwezi 8");
        tabLayout.getTabAt(7).setCustomView(month8);

    }

    public void setupViewPager(ViewPager viewPager) {
        TbClientDetailsActivity.ViewPagerAdapter adapter = new TbClientDetailsActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonthOne(),     "MonthOne");
        adapter.addFragment(new MonthTwoToSix(),      "MonthTwo");
        adapter.addFragment(new MonthTwoToSix(), "MonthThree");
        adapter.addFragment(new MonthTwoToSix(), "MonthFour");
        adapter.addFragment(new MonthTwoToSix(), "MonthFive");
        adapter.addFragment(new MonthTwoToSix(), "MonthSix");
        adapter.addFragment(new MonthTwoToSix(), "monthSeven");
        adapter.addFragment(new MonthTwoToSix(), "monthEight");
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
