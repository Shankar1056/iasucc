package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.HomeFragment;
import com.tutorialsbuzz.navigationdrawer.activity.login.LoginActivity;
import com.tutorialsbuzz.navigationdrawer.activity.login.Register;
import com.ucc.application.R;

import java.util.Locale;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Locale myLocale;
    boolean doubleBackToExitPressedOnce = false;
    String headername, headeremail, headerphone, headerlanguage;
    private TextView name, user_mail;
    private ImageView camera, profile_image, backimage;
    public static final int IMAGEREQUESTCODE = 0;
    String profile_background;
    final Handler mHandler = new Handler();
    String pro_image,back_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        headerlanguage = ClsGeneral.getPreferences(MainActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        headername = ClsGeneral.getPreferences(MainActivity.this, "fullname");
        headeremail = ClsGeneral.getPreferences(MainActivity.this, "email");
        headerphone = ClsGeneral.getPreferences(MainActivity.this, "phone");


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.user_name);
        user_mail = (TextView) header.findViewById(R.id.user_mail);
        camera = (ImageView) header.findViewById(R.id.camera);
        profile_image = (ImageView) header.findViewById(R.id.profile_image);
        backimage=(ImageView)header.findViewById(R.id.backimage);
        name.setText(headername);
        user_mail.setText(headeremail);



        updateDisplay(new HomeFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        updateDisplay(new HomeFragment());
                        break;

                    case R.id.nav_aboutucc:
                        Intent intent = new Intent(MainActivity.this, AboutUcc.class);
                        intent.putExtra("token", "about");
                        startActivity(intent);
                        break;


                    case R.id.nav_contactus:
                        Intent contact = new Intent(MainActivity.this, AboutUcc.class);
                        contact.putExtra("token", "contactus");
                        startActivity(contact);
                        break;


                    case R.id.nav_logout:
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        ClsGeneral.setPreferences(MainActivity.this, "user_id", "");
                        ClsGeneral.setPreferences(MainActivity.this, "fullname", "");
                        ClsGeneral.setPreferences(MainActivity.this, "email", "");
                        ClsGeneral.setPreferences(MainActivity.this, "phone", "");
                        ClsGeneral.setPreferences(MainActivity.this, "password", "");
                        ClsGeneral.setPreferences(MainActivity.this, "password", "");
                        finish();
                        break;
                    case R.id.nav_share:
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "string");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ucc.application&hl=en");
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        break;


                    case R.id.nav_rateapp:

                        Intent intnt=new Intent(Intent.ACTION_VIEW);
                        intnt.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ucc.application"));
                        startActivity(intnt);



                        break;

                }
                return true;
            }
        });




    }




    private void updateDisplay(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                callintent(Register.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callintent(Class<Register> registerClass) {
        Intent i = new Intent(MainActivity.this, registerClass);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.clickagaintoback, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        headerlanguage = ClsGeneral.getPreferences(MainActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }








}