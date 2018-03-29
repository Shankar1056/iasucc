package com.tutorialsbuzz.navigationdrawer.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tutorialsbuzz.navigationdrawer.activity.MainActivity;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.LanguageModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by indglobal on 13/3/16.
 */
public class Register extends AppCompatActivity {

    private Spinner spinner;
    private Button buttonSave;
    private EditText name, phone,email_id,password;
    String getname=" ",getphone=" ",getemail_id=" ",getLang=" ",getpassword;
    String spinnervalue=" ", langKey="";
    Locale myLocale;
    String pos="0";
    final Handler mHandler = new Handler();
    ArrayList<LanguageModel> languageModelArrayList=new ArrayList<LanguageModel>();
    List<String> languagelist;
    private LinearLayout alltextLayout;
    private int gcmcount=0;
    private TextView donthaveaccount,signin;
    private boolean internetConn;

    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "185583367692";
    String msg = "";
    Handler h = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register_activity);

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        email_id = (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password);
        alltextLayout=(LinearLayout)findViewById(R.id.alltextLayout);
        donthaveaccount=(TextView)findViewById(R.id.donthaveaccount);
        signin=(TextView)findViewById(R.id.signin);
        spinner = (Spinner) findViewById(R.id.spinner);
        languagelist=new ArrayList<String>();


        name.setText(ClsGeneral.getPreferences(Register.this, "fullname"));
        email_id.setText(ClsGeneral.getPreferences(Register.this, "email"));
        phone.setText(ClsGeneral.getPreferences(Register.this, "phone"));
        password.setText(ClsGeneral.getPreferences(Register.this, "password"));


        h.postDelayed(new Runnable() {
            public void run() {
                internetConn= Utilz.isInternetConnected(Register.this);
                h.postDelayed(this, 1000);
            }
        }, 1000);





        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getLang = spinner.getSelectedItem().toString();
                spinnervalue = languageModelArrayList.get(position).getLid();
                pos = String.valueOf(position);

                if (getLang.equalsIgnoreCase("English")) {
                    setLocale("en");
                }

                if (getLang.equalsIgnoreCase("Hindi")) {

                    setLocale("hi");


                } else if (getLang.equalsIgnoreCase("Kannada")) {

                    setLocale("kn");

                } else if (getLang.equalsIgnoreCase("Telugu")) {

                    setLocale("te");

                } else if (getLang.equalsIgnoreCase("Tamil")) {

                    setLocale("ta");

                }

         ClsGeneral.setPreferences(Register.this,"choosedlanguage",getLang);
            }

            public void setLocale(String lang) {

                myLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Select A language",
                        Toast.LENGTH_SHORT).show();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getname = name.getText().toString();
                getphone = phone.getText().toString();
                getemail_id = email_id.getText().toString();
                getpassword = password.getText().toString();
                // getLan8g=spinner.getSelectedItem().toString();
                if ((ClsGeneral.getPreferences(Register.this, "email").equalsIgnoreCase(""))) {
                    if (getname.equalsIgnoreCase("")) {
                        Toast.makeText(Register.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (getphone.equalsIgnoreCase("")) {
                        Toast.makeText(Register.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (getphone.length() < 10) {
                        Toast.makeText(Register.this, "Enter a Valid Phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (getemail_id.equalsIgnoreCase("")) {
                        Toast.makeText(Register.this, "Enter  Email id", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (isEmailValid(getemail_id) == false){
                        getemail_id=null;
                        Toast.makeText(Register.this, "Enter correct email ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isEmailValid(getemail_id) == true){
                        getemail_id = email_id.getText().toString();
                    }



                    if (getpassword.equalsIgnoreCase("")) {
                        Toast.makeText(Register.this, "Enter Password ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (regid.equalsIgnoreCase("")){
                        getRegId();
                        Toast.makeText(Register.this, "Try again after 2-3 seconds ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }





                    if (buttonSave.getText().equals("Update Language")) {

                        if (internetConn) {
                            new SaveLanguage().execute(WebServices.STUDENT_FORUM);
                        }
                        else {
                            Toast.makeText(Register.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (internetConn) {
                            calltwoapis();
                        }
                        else {
                            Toast.makeText(Register.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        }

                }

            }
        });
        String userid = ClsGeneral.getPreferences(Register.this, "user_id");
        if (!userid.equalsIgnoreCase("")) {
            name.setClickable(false);
            name.setFocusable(false);
            phone.setClickable(false);
            phone.setFocusable(false);
            email_id.setClickable(false);
            email_id.setFocusable(false);
            password.setClickable(false);
            password.setFocusable(false);
            getemail_id = ClsGeneral.getPreferences(Register.this, "email");
            buttonSave.setText("Update Language");
            alltextLayout.setVisibility(View.GONE);
            donthaveaccount.setVisibility(View.GONE);
            signin.setVisibility(View.GONE);
        }
        startTestThread();


        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((languagelist.size() == 0) && (languageModelArrayList.size()==0)) {
                    if (internetConn) {
                        new GetLanguage().execute(WebServices.STUDENT_FORUM);
                    }
                    else {
                        Toast.makeText(Register.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    }

                }

                return false;
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!(ClsGeneral.getPreferences(Register.this,"email").equalsIgnoreCase(""))) {
            Intent i = new Intent(Register.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            finish();
        }
    }

    private void startTestThread() {
        Thread t = new Thread() {
            public void run() {
               if (gcmcount==0) {
                   getRegId();
               }
                mHandler.post(mUpdateResult);
            }
        };

        t.start();
    }

    private void getRegId() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                try {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    GCMRegistrar.checkDevice(getApplicationContext());
                    regid = gcm.register(PROJECT_NUMBER);

                    ClsGeneral.setPreferences(Register.this,"gcm_device_key",regid);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                // profileMgr.setDeviceToken(regid);
            }
        }.execute(null, null, null);
    }


    final Runnable mUpdateResult = new Runnable() {
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //if (internetConn) {
                        new GetLanguage().execute(WebServices.STUDENT_FORUM);
                   // }
                }
            });


        }
    };

    private void calltwoapis() {


        Thread t = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (internetConn) {
                            new SaveData().execute(WebServices.NEWS_IMAGE_TITLE);
                        }
                    }
                });


                mHandler.post(mUpdateResults);
            }
        };

        t.start();
    }
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                }
            });

        }
    };

    private class SaveData extends AsyncTask<String,Void,String> {
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Register.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option" ,"register"));
            nameValuePairs.add(new BasicNameValuePair("fullname" ,getname));
            nameValuePairs.add(new BasicNameValuePair("email" ,getemail_id));
            nameValuePairs.add(new BasicNameValuePair("phone" ,getphone));
            nameValuePairs.add(new BasicNameValuePair("pass" ,getpassword));
            nameValuePairs.add(new BasicNameValuePair("regId" ,regid));
            try {
                result1= Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }


            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("msg");
                    if (status.equalsIgnoreCase("true")){

                        JSONArray jsonArray=jsonObject.getJSONArray("key");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String fullname = jsonObject1.getString("fullname");
                            String email = jsonObject1.getString("email");
                            String phone = jsonObject1.getString("phone");
                            String user_id = jsonObject1.getString("uid");
                            String rate_count = jsonObject1.getString("rate_count");
                            String rate_status = jsonObject1.getString("rate_status");

                            ClsGeneral.setPreferences(Register.this, "fullname", fullname);
                            ClsGeneral.setPreferences(Register.this, "email", email);
                            ClsGeneral.setPreferences(Register.this, "phone", phone);
                            ClsGeneral.setPreferences(Register.this, "user_id", user_id);
                            ClsGeneral.setPreferences(Register.this, "password", getpassword);
                            ClsGeneral.setPreferences(Register.this, "rate_count", rate_count);
                            ClsGeneral.setPreferences(Register.this, "rate_status", rate_status);

                            gcmcount = 1;
                            if (internetConn) {
                                new SaveLanguage().execute(WebServices.STUDENT_FORUM);
                            }
                        }

                    }

                    else{
                        Toast.makeText(Register.this,message,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class SaveLanguage extends AsyncTask<String,Void,String>{
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Register.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option" ,"9"));
            nameValuePairs.add(new BasicNameValuePair("key" ,getemail_id));
            nameValuePairs.add(new BasicNameValuePair("lan" ,spinnervalue));
            try {
                result1= Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }


            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status=jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")){

                        ClsGeneral.setPreferences(Register.this, "lan", pos);

                        if (buttonSave.getText().equals("Update Language")) {
                            ClsGeneral.setPreferences(Register.this, "lan", pos);
                            Intent intent = new Intent(Register.this,MainActivity.class);
                            startActivity(intent);
                            finish();


                        }

                    }

                    else{
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private class GetLanguage extends AsyncTask<String,Void,String>{
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Register.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option" ,""));
            nameValuePairs.add(new BasicNameValuePair("key1" ,getemail_id));
            try {
                result1= Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }


            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("msg");
                    String isLagSel=jsonObject.getString("isLagSel");
                    if (isLagSel.equalsIgnoreCase("true")) {
                        langKey = jsonObject.getString("langKey");
                    }
                    if (status.equalsIgnoreCase("true")){

                        if (languageModelArrayList.size()>0){
                            languageModelArrayList.clear();
                        }

                        if (languagelist.size()>0){
                            languagelist.clear();
                        }

                        JSONArray jsonArray=jsonObject.getJSONArray("language");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String lid=jsonObject1.getString("lid");
                            String language=jsonObject1.getString("language");
                            LanguageModel languageModel=new LanguageModel(lid,language);
                            languageModelArrayList.add(languageModel);
                            languagelist.add(language);
                        }
                        ArrayAdapter<String> adapter_property = new ArrayAdapter<String>(Register.this,
                                android.R.layout.simple_spinner_item, languagelist);
                        adapter_property
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter_property);


                        if (ClsGeneral.getPreferences(Register.this, "lan").equalsIgnoreCase("")) {
                            spinner.setSelection(0);
                        } else {
                            for (int i =0;i<languageModelArrayList.size();i++) {
                                if (languageModelArrayList.get(i).getLid().equalsIgnoreCase(langKey)){
                                    spinner.setSelection(i);
                                }
                            }

                        }



                    }

                    else{
                        Toast.makeText(Register.this,message,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
