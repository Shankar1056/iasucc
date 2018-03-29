package com.tutorialsbuzz.navigationdrawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.NotificationDescriptionPagerAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.model.NotificationModel;
import com.ucc.application.R;
import java.util.ArrayList;

/**
 * Created by indglobal on 11/4/16.
 */
public class NotificationDescription extends AppCompatActivity {
    TextView actionbar_title;
    NotificationDescriptionPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Toolbar toolbar;
    int position,total_length;
    private ImageView refresh;
    String name,currentDate=null;

    public  static ArrayList<NotificationModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String headerlanguage = ClsGeneral.getPreferences(NotificationDescription.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.notification_description_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refresh=(ImageView)findViewById(R.id.refresh);
        refresh.setVisibility(View.GONE);

        Intent i =getIntent();
        name=i.getStringExtra("guidelineLayout");
        if (name.equalsIgnoreCase("Guidelines")) {
            actionbar_title.setText(getResources().getString(R.string.guidlines_description));
        }
        if (name.equalsIgnoreCase("Notifications")) {

            actionbar_title.setText(getResources().getString(R.string.notification_description));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        arrayList = new ArrayList<NotificationModel>();

        arrayList=(ArrayList<NotificationModel>) getIntent().getSerializableExtra("arraylist");

        position = getIntent().getExtras().getInt("position");
        total_length=getIntent().getExtras().getInt("total_length");
        currentDate=getIntent().getStringExtra("currentDate");

        mCustomPagerAdapter = new NotificationDescriptionPagerAdapter(NotificationDescription.this,getSupportFragmentManager(),arrayList,total_length,currentDate);
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
        String headerlanguage = ClsGeneral.getPreferences(NotificationDescription.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}
