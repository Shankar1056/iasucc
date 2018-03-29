package com.tutorialsbuzz.navigationdrawer.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

import com.tutorialsbuzz.navigationdrawer.activity.adapter.NewDescriptionPagerAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.model.ScienceImageBean;
import com.ucc.application.R;

import java.util.ArrayList;

/**
 * Created by indglobal on 12/22/2015.
 */
public class NewsDescription extends AppCompatActivity {

    TextView actionbar_title;
    NewDescriptionPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Toolbar toolbar;
    int position,total_length;
    private String currentDate=null;

    public  static ArrayList<ScienceImageBean> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String headerlanguage = ClsGeneral.getPreferences(NewsDescription.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.news_description_layout);


        arrayList = new ArrayList<ScienceImageBean>();

       /* arrayList=(ArrayList<ScienceImageBean>) getIntent().getSerializableExtra("arraylist");
        position = getIntent().getExtras().getInt("position");
        total_length=getIntent().getExtras().getInt("total_length");
        currentDate=getIntent().getStringExtra("currentDate");*/

        Bundle bundle = null;
        bundle = NewsDescription.this.getIntent().getExtras();
        position = Integer.parseInt(bundle.getString("position"));
        total_length = Integer.parseInt(bundle.getString("total_length"));
        currentDate = bundle.getString("currentDate");
        arrayList = bundle.getParcelableArrayList("arraylist");



        mCustomPagerAdapter = new NewDescriptionPagerAdapter(NewsDescription.this,getSupportFragmentManager(),arrayList,total_length,currentDate);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);


    }
    public void setCurrentItem (int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(NewsDescription.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}
