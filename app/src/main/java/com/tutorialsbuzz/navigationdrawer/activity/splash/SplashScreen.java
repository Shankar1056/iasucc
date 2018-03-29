package com.tutorialsbuzz.navigationdrawer.activity.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tutorialsbuzz.navigationdrawer.activity.MainActivity;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.login.LoginActivity;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by indglobal on 11/21/2015.
 */
public class SplashScreen extends Activity
{
    private boolean internetConn;
    String email,rate_count,rate_status,status,email1;
    private ProgressBar progressBar;
    private TextView clicklink;
    final Handler mHandler = new Handler();
    int rat_cnt;
    PackageInfo pInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        try {
             pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setContentView(R.layout.splash_activity);
        internetConn= Utilz.isInternetConnected(SplashScreen.this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        clicklink=(TextView)findViewById(R.id.clicklink);
        email= ClsGeneral.getPreferences(SplashScreen.this, "user_id");
        email1= ClsGeneral.getPreferences(SplashScreen.this, "email");
        rate_count=ClsGeneral.getPreferences(SplashScreen.this,"rate_count");
        rate_status=ClsGeneral.getPreferences(SplashScreen.this, "rate_status");
                   if (!rate_count.equalsIgnoreCase("")) {
                     rat_cnt = Integer.parseInt(rate_count);
                      if (rat_cnt <= 2) {
                        rat_cnt = rat_cnt + 1;
                        status = "F";
                       if (internetConn) {
                           new ShowRateApp().execute(WebServices.NEWS_IMAGE_TITLE);
                        }
                        }
                  }

        if (internetConn) {
            new GetVersion().execute(WebServices.GET_VERSIONNUMBER);
        }
        if (!internetConn) {


        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {

                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }

                checklogin();


            }
        };
        timer.start();
        }

        clicklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt=new Intent(Intent.ACTION_VIEW);
                intnt.setData(Uri.parse("http://www.uccindia.org/"));
                startActivity(intnt);

            }
        });

    }





    private void checklogin() {

        if (email.equalsIgnoreCase("")) {
            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(intent);
            ClsGeneral.setPreferences(SplashScreen.this, "rating", "0");
        }
        else {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);

        }

        finish();

    }

    private class GetVersion extends AsyncTask<String,Void,String> {
        String result1;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result1= Utilz.executeHttpGet(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);


            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String version_name=jsonObject.getString("version_name");
                    String version_code=jsonObject.getString("version_code");

                    if (!version_name.equalsIgnoreCase("2.0")){
                        showDialg();
                    }
                    else {
                        checklogin();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ShowRateApp extends AsyncTask<String,Void,String> {
        String result1;
        ProgressDialog progressDialog;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        @Override
        protected String doInBackground(String... params) {
            try {
                nameValuePairs.add(new BasicNameValuePair("option", "17"));
                nameValuePairs.add(new BasicNameValuePair("key", email1));
                nameValuePairs.add(new BasicNameValuePair("rate_count", ""+rat_cnt));
                nameValuePairs.add(new BasicNameValuePair("rate_status", status));
                result1= Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);


            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("rate_count")) {
                        String rate_count = jsonObject.getString("rate_count");
                        ClsGeneral.setPreferences(SplashScreen.this, "rate_count", rate_count);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void showDialg() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreen.this);

        alertDialog.setTitle("UCC");
        alertDialog.setCancelable(false);

        alertDialog.setMessage("IAS UCC updates are available");

        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intnt=new Intent(Intent.ACTION_VIEW);
                intnt.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ucc.application"));
                startActivity(intnt);
                dialog.dismiss();

                finish();

            }
        });

        alertDialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                checklogin();
            }
        });

        alertDialog.show();

    }
}
