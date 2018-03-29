package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 23/1/16.
 */
public class ForumCreate extends AppCompatActivity {

    EditText write_forum_edit_text,cmntrName;
    TextView saveForum,actionbar_title;
    String email, name;
    Toolbar toolbar;
    ImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_create_fragment);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        write_forum_edit_text=(EditText)findViewById(R.id.write_forum_edit_text);
        saveForum=(TextView)findViewById(R.id.saveForum);
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar_title.setText(R.string.create_forum);
        refresh=(ImageView)findViewById(R.id.refresh);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        email= ClsGeneral.getPreferences(ForumCreate.this, "email");
        name= ClsGeneral.getPreferences(ForumCreate.this, "fullname");

        refresh.setClickable(true);
        refresh.setFocusable(true);
        saveForum.setClickable(true);
        saveForum.setFocusable(true);


        final String url= WebServices.STUDENT_FORUM;

        saveForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setClickable(false);
                refresh.setFocusable(false);

                String myQuestion=write_forum_edit_text.getText().toString().trim();
                if (myQuestion.equalsIgnoreCase("")){
                    Toast.makeText(ForumCreate.this, R.string.edittext_your_comment, Toast.LENGTH_SHORT).show();

                }

                else
{
    refresh.setClickable(false);
    refresh.setFocusable(false);
    saveForum.setClickable(false);
    saveForum.setFocusable(false);
    sendData(myQuestion,url);

}
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myQuestion=write_forum_edit_text.getText().toString().trim();
                if (myQuestion.equalsIgnoreCase("")){
                    Toast.makeText(ForumCreate.this, R.string.edittext_your_comment, Toast.LENGTH_SHORT).show();

                }

                else
                {
                    refresh.setClickable(false);
                    refresh.setFocusable(false);
                    saveForum.setClickable(false);
                    saveForum.setFocusable(false);
                    sendData(myQuestion,url);
                }
            }
        });
        write_forum_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    private void sendData(String myQuestion,String url) {

        ProgressDialog progressDialog = ProgressDialog.show(this, "Loading data...",
                "");
        progressDialog.setCancelable(false);
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("option","1"));
        nameValuePairs.add(new BasicNameValuePair("key",email));
        nameValuePairs.add(new BasicNameValuePair("discussion_topic",myQuestion));
        nameValuePairs.add(new BasicNameValuePair("description","text"));
        nameValuePairs.add(new BasicNameValuePair("active_status","yes"));
        nameValuePairs.add(new BasicNameValuePair("by",name));

        String jsonStr = null;
        try {
            jsonStr = Utilz.executeHttpPost(url, nameValuePairs);


        Log.d("Response: ", "> " + jsonStr);
        if (jsonStr != null) {
            JSONObject object = null;

            object = new JSONObject(jsonStr);
            String status = null;

            status = object.getString("status");
            if (status.equalsIgnoreCase("true"))
            {
                Intent i = new Intent(ForumCreate.this,StudentForumActivity.class);
                startActivity(i);
                finish();



            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }
}
