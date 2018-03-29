package com.tutorialsbuzz.navigationdrawer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.MyTestSeries;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.OnlineTestSeries;
import com.ucc.application.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indglobal on 27/1/16.
 */
public class TestSeries extends AppCompatActivity {
    Toolbar mToolbar;
    ImageView refresh;
    private TextView actionbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String headerlanguage = ClsGeneral.getPreferences(TestSeries.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.test_series);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.student_forum));
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        actionbar_title.setText(getResources().getString(R.string.test_series));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        initViewPagerAndTabs();

        refresh = (ImageView) findViewById(R.id.refresh);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestSeries.this.finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViewPagerAndTabs();
            }
        });


    }
    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TestSeriesPagerAdapter pagerAdapter = new TestSeriesPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(OnlineTestSeries.createInstance(), getString(R.string.online_test_series));
        pagerAdapter.addFragment(MyTestSeries.createInstance(), getString(R.string.my_test_series));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    static class TestSeriesPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public TestSeriesPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }



        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refresh=(ImageView)findViewById(R.id.refresh);
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar_title.setText(getResources().getString(R.string.test_series));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.testSeriesPager);
         tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TestSeriesPagerAdapter adapter = new TestSeriesPagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(adapter);
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.my_test_series);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText(R.string.online_test_series));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_test_series));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final TestSeriesPagerAdapter adapter = new TestSeriesPagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(TestSeries.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }

}
