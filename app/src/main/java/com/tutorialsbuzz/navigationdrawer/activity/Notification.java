package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorialsbuzz.navigationdrawer.activity.adapter.NotificationAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomItemAnimator;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.NotificationModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by indglobal on 1/2/16.
 */
public class Notification extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ArrayList<NotificationModel> imageTitleList=new ArrayList<NotificationModel>();
    NotificationModel imageApiBean;
    NotificationAdapter adapter;
    String name;
    String email,currentDate=null;
    private TextView actionbar_title;
    ImageView refresh,searchicon;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private boolean internetConn;
    private TextView ifnodata;
    int total_length;
    private LinearLayout searchlayout,searchcross;
    private EditText search_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String headerlanguage = ClsGeneral.getPreferences(Notification.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refresh=(ImageView)findViewById(R.id.refresh);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        searchicon=(ImageView)findViewById(R.id.searchicon);
        searchlayout=(LinearLayout)findViewById(R.id.searchlayout);
        search_edittext=(EditText)findViewById(R.id.search_edittext);
        searchcross=(LinearLayout)findViewById(R.id.searchcross);
        search_edittext.setHint(getResources().getString(R.string.search_notification));
        searchicon.setVisibility(View.VISIBLE);



        ifnodata=(TextView)findViewById(R.id.ifnodata);
        ifnodata.setVisibility(View.GONE);
        internetConn= Utilz.isInternetConnected(Notification.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        email= ClsGeneral.getPreferences(Notification.this, "email");
        Intent i =getIntent();
         name=i.getStringExtra("guidelineLayout");
        if (name.equalsIgnoreCase("Guidelines")){
            search_edittext.setHint(getResources().getString(R.string.search_guidance));
            actionbar_title.setText(getResources().getString(R.string.guidlines));

            if (internetConn) {
                mProgressBar.setVisibility(View.VISIBLE);
                //fetchNewsData(WebServices.NEWS_IMAGE_TITLE, "6");
                new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "6");
            }

            else {
                Toast.makeText(Notification.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }

        }
        if (name.equalsIgnoreCase("Notifications")){
            actionbar_title.setText(getResources().getString(R.string.notifications));
            search_edittext.setHint(getResources().getString(R.string.search_notification));

            if (internetConn) {
                mProgressBar.setVisibility(View.VISIBLE);
                //fetchNewsData(WebServices.NEWS_IMAGE_TITLE, "5");
                new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "5");
            }
            else {
                Toast.makeText(Notification.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }


        }

       // getSupportActionBar().setTitle(getResources().getString(R.string.notifications));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adapter = new NotificationAdapter(new ArrayList<NotificationModel>(),currentDate, R.layout.notification_adapter, Notification.this);
        mRecyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (name.equalsIgnoreCase("Guidelines")){

                    if (internetConn) {
                        //fetchNewsData(WebServices.NEWS_IMAGE_TITLE, "6");
                        new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "6");
                    }
                    else {
                        Toast.makeText(Notification.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                }
                if (name.equalsIgnoreCase("Notifications")){
                    if (internetConn) {
                        //fetchNewsData(WebServices.NEWS_IMAGE_TITLE,"5");
                        new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "5");
                    }
                    else {
                        Toast.makeText(Notification.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.equalsIgnoreCase("Guidelines")){
                    mProgressBar.setVisibility(View.VISIBLE);
                   // fetchNewsData(WebServices.NEWS_IMAGE_TITLE,"6");
                    new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "6");
                }
                if (name.equalsIgnoreCase("Notifications")){
                    mProgressBar.setVisibility(View.VISIBLE);
                   // fetchNewsData(WebServices.NEWS_IMAGE_TITLE,"5");
                    new AllNews().execute(WebServices.NEWS_IMAGE_TITLE, "5");
                }

            }
        });


        search_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_edittext.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


        searchicon.setOnClickListener(this);
        searchcross.setOnClickListener(this);

        currentDate=ClsGeneral.GetTodaysDate();

    }

    private class AllNews extends AsyncTask<String,Void,String> {
        String result1;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option" , params[1]));
            nameValuePairs.add(new BasicNameValuePair("key" ,email));
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



            if (s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");
                    JSONArray array = null;

                    if (status.equalsIgnoreCase("false")) {
                        ifnodata.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (status.equalsIgnoreCase("true")) {
                        if (imageTitleList.size() > 0) {
                            imageTitleList.clear();
                        }
                        adapter.clearApplications();
                        if (name.equalsIgnoreCase("Guidelines")) {
                            array = jsonObject.getJSONArray("guidelines");
                            total_length=array.length();

                        }
                        if (name.equalsIgnoreCase("Notifications")) {
                            array = jsonObject.getJSONArray("notifications");
                            total_length=array.length();

                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject category = null;

                            category = array.getJSONObject(i);


                            String title = category.getString("title");


                            String description = category.getString("description");


                            String date = category.getString("created_on");


                            imageApiBean = new NotificationModel(title, date, description);
                            imageTitleList.add(imageApiBean);

                        }
                        adapter.addApplications(imageTitleList,currentDate);
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);

                    } else {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }




    public void animateActivity(int pos) {

        Intent i = new Intent(this, NotificationDescription.class);
        i.putExtra("position", pos);
        i.putExtra("arraylist", imageTitleList);
        i.putExtra("total_length",total_length);
        i.putExtra("total_length",total_length);
        i.putExtra("guidelineLayout",name);
        i.putExtra("currentDate",currentDate);

        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchicon:
                searchlayout.setVisibility(View.VISIBLE);
                break;
            case R.id.searchcross:
                searchlayout.setVisibility(View.GONE);

                break;
            default:
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(Notification.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}
