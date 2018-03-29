package com.tutorialsbuzz.navigationdrawer.activity.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tutorialsbuzz.navigationdrawer.activity.MainActivity;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by indglobal on 10/3/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView forgetpassword,showPassword;
    private EditText email_id,password;
    private Button buttonSave,newucc;
    String get_email,get_password,langKey=null;
    private boolean internetConn;
    final Handler mHandler = new Handler();

    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "185583367692";
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        forgetpassword=(TextView)findViewById(R.id.forgetpassword);




        email_id=(EditText)findViewById(R.id.email_id);
        password=(EditText)findViewById(R.id.password);
        buttonSave=(Button)findViewById(R.id.buttonSave);
        newucc=(Button)findViewById(R.id.newucc);
        showPassword=(TextView)findViewById(R.id.showPassword);
        newucc.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
        showPassword.setOnClickListener(this);

        internetConn= Utilz.isInternetConnected(LoginActivity.this);
        if (internetConn) {
            try
            {
                startTestThread();
            }
            catch (Exception e){

            }

        }
        else {
            Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }


    }
    private void startTestThread() {
        Thread t = new Thread() {
            public void run() {

                    getRegId();

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

                    ClsGeneral.setPreferences(LoginActivity.this,"gcm_device_key",regid);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSave:

                get_email=email_id.getText().toString();
                get_password=password.getText().toString();

                if (get_email.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter  Email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (get_password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter Password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (regid.equalsIgnoreCase("")) {
                    getRegId();
                    Toast.makeText(LoginActivity.this, "Try again after 2-3 seconds ", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    if (internetConn) {
                        new SaveData().execute(WebServices.NEWS_IMAGE_TITLE);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }


                }

                break;
            case R.id.newucc:
                Intent i = new Intent(LoginActivity.this, Register.class);
                startActivity(i);
                finish();
            break;
            case R.id.forgetpassword:
                openDialog();
                break;
            case R.id.showPassword:
                if (showPassword.getText().toString().equalsIgnoreCase("SHOW")){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setText("HIDE");
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setText("SHOW");
                }

                break;

            default:
                break;
        }
    }





    private class SaveData extends AsyncTask<String,Void,String> {
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option" ,"login"));
            nameValuePairs.add(new BasicNameValuePair("email" ,get_email));
            nameValuePairs.add(new BasicNameValuePair("pass" ,get_password));
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
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String fullname=jsonObject1.getString("fullname");
                            String email=jsonObject1.getString("email");
                            String phone=jsonObject1.getString("phone");
                            String user_id=jsonObject1.getString("uid");
                            String rate_count=jsonObject1.getString("rate_count");
                            String rate_status=jsonObject1.getString("rate_status");


                            ClsGeneral.setPreferences(LoginActivity.this, "fullname", fullname);
                            ClsGeneral.setPreferences(LoginActivity.this, "email", email);
                            ClsGeneral.setPreferences(LoginActivity.this, "phone", phone);
                            ClsGeneral.setPreferences(LoginActivity.this, "user_id", user_id);
                            ClsGeneral.setPreferences(LoginActivity.this, "rate_count", rate_count);
                            ClsGeneral.setPreferences(LoginActivity.this, "rate_status", rate_status);
                        }

                        new GetLanguage().execute(WebServices.STUDENT_FORUM);



                    }

                    else{
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(R.drawable.logo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UCC");
        builder.setMessage("password will be sent to your mail id");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = subEditText.getText().toString();
                if (mail.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter your Email ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    new ForgetPassword().execute(WebServices.NEWS_IMAGE_TITLE,mail);
                    dialog.cancel();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    private class ForgetPassword extends AsyncTask<String,Void,String> {
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option" ,"forget"));
            nameValuePairs.add(new BasicNameValuePair("email" ,params[1]));
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

                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();


                    }

                    else{
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
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
            progressDialog = ProgressDialog.show(LoginActivity.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option" ,""));
            nameValuePairs.add(new BasicNameValuePair("key1" ,get_email));
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

                        JSONArray jsonArray=jsonObject.getJSONArray("language");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String lid=jsonObject1.getString("lid");
                            String language=jsonObject1.getString("language");

                        }

                        if (langKey.equalsIgnoreCase("1")){
                            ClsGeneral.setPreferences(LoginActivity.this,"choosedlanguage","English");
                        }
                        if (langKey.equalsIgnoreCase("2")){
                            ClsGeneral.setPreferences(LoginActivity.this,"choosedlanguage","Hindi");
                        }
                        if (langKey.equalsIgnoreCase("3")){
                            ClsGeneral.setPreferences(LoginActivity.this,"choosedlanguage","Telugu");
                        }
                        if (langKey.equalsIgnoreCase("4")){
                            ClsGeneral.setPreferences(LoginActivity.this,"choosedlanguage","Tamil");
                        }
                        if (langKey.equalsIgnoreCase("5")){
                            ClsGeneral.setPreferences(LoginActivity.this,"choosedlanguage","Kannada");
                        }


                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                     /*   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                        startActivity(i);
                        finish();



                    }

                    else{
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(LoginActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}
