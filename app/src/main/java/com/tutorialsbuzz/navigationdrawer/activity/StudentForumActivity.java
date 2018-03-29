package com.tutorialsbuzz.navigationdrawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.AllFragment;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.FavouriteFragment;
import com.ucc.application.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indglobal on 13/3/16.
 */
public class StudentForumActivity extends AppCompatActivity{
    private TextView actionbar_title;
    private ImageView refresh;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String headerlanguage = ClsGeneral.getPreferences(StudentForumActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.studentforum_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.student_forum));
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar_title.setText(getResources().getString(R.string.student_forum));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        initViewPagerAndTabs();

        refresh=(ImageView)findViewById(R.id.refresh);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentForumActivity.this.finish();
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
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(AllFragment.createInstance(), getString(R.string.all));
        pagerAdapter.addFragment(FavouriteFragment.createInstance(), getString(R.string.favourites));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

     class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==1){
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(StudentForumActivity.this);
                Intent i = new Intent("TAG_REFRESH");
                lbm.sendBroadcast(i);
            }
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
    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(StudentForumActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}




        /* Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar_title.setText(getResources().getString(R.string.student_forum));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager );
        refresh=(ImageView)findViewById(R.id.refresh);

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentForumActivity.this.finish();
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(getdata(i))
                            .setTabListener(this)
            );

        }
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new ViewPagerAdapter (getSupportFragmentManager());
                pager.setAdapter(adapter);
            }
        });
    }

    private CharSequence getdata(int i) {
        String name;
        if (i==0){
             name=getResources().getString(R.string.all);

        }
        else {
            name=getResources().getString(R.string.favourites);
        }
        return name;
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new AllFragment();
            }
            else {
                return new FavouriteFragment();
            }

        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "ALL" + position;
        }

    }
}*/
