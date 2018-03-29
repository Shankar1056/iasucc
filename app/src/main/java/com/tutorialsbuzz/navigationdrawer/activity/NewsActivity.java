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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorialsbuzz.navigationdrawer.activity.adapter.NewsAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomItemAnimator;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.ScienceImageBean;
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
 * Created by indglobal on 13/3/16.
 */
public class NewsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private NewsAdapter mAdapter;
    TextView actionbar_title;
    String email,currentDate=null;
    private  static ArrayList<ScienceImageBean> imageTitleList=new ArrayList<ScienceImageBean>();
    ScienceImageBean imageApiBean;
    private ImageView refresh,searchicon;
    private boolean internetConn;
    private TextView ifnodata;
    int total_length;
    private LinearLayout searchlayout,searchcross;
    private EditText search_edittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String headerlanguage = ClsGeneral.getPreferences(NewsActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
        setContentView(R.layout.news_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        email= ClsGeneral.getPreferences(NewsActivity.this, "email");
        actionbar_title.setText(R.string.news);
        refresh=(ImageView)findViewById(R.id.refresh);
        internetConn= Utilz.isInternetConnected(NewsActivity.this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        ifnodata=(TextView)findViewById(R.id.ifnodata);
        searchicon=(ImageView)findViewById(R.id.searchicon);
        searchlayout=(LinearLayout)findViewById(R.id.searchlayout);
        search_edittext=(EditText)findViewById(R.id.search_edittext);
        searchcross=(LinearLayout)findViewById(R.id.searchcross);
        search_edittext.setHint(getResources().getString(R.string.search_news));
        searchicon.setVisibility(View.VISIBLE);

        ifnodata.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        mAdapter = new NewsAdapter(new ArrayList<ScienceImageBean>(),  currentDate, R.layout.news_adapter, NewsActivity.this);


        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checknfetch();
            }
        });

        checknfetch();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsActivity.this.finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                checknfetch();
            }
        });


        search_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_edittext.getText().toString().toLowerCase(Locale.getDefault());
                mAdapter.filter(text);
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



    private void checknfetch(){
        if (internetConn) {
            mProgressBar.setVisibility(View.VISIBLE);
           new AllNews().execute(WebServices.NEWS_IMAGE_TITLE);
        }
        else {
            Toast.makeText(NewsActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public void animateActivity(int pos) {
       /* Intent i = new Intent(this, NewsDescription.class);
        i.putExtra("position", pos);
        i.putExtra("arraylist", imageTitleList);
        i.putExtra("total_length",total_length);
        i.putExtra("currentDate",currentDate);
        startActivity(i);*/
        Intent i = new Intent(NewsActivity.this, NewsDescription.class);
        Bundle bundle = new Bundle();
        bundle.putString("position", ""+pos);
        bundle.putParcelableArrayList("arraylist", imageTitleList);
        bundle.putString("total_length",""+total_length);
        bundle.putString("currentDate",currentDate);
        i.putExtras(bundle);
        startActivity(i);
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

            nameValuePairs.add(new BasicNameValuePair("option" ,"2"));
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
                    String status=jsonObject.getString("status");

                    if (status.equalsIgnoreCase("true")) {

                        if (imageTitleList.size() > 0) {
                            imageTitleList.clear();
                        }
                        mAdapter.clearApplications();
                        JSONArray array = jsonObject.getJSONArray("news");
                        total_length = array.length();
                        if (array.length() == 0) {
                            ifnodata.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }


                        for (int i = 0; i <array.length(); i++) {
                            JSONObject category = null;

                            category = array.getJSONObject(i);


                                String image = category.getString("image");


                                String description = category.getString("description");


                                String title = category.getString("title");
                                String date = category.getString("date");


                                imageApiBean = new ScienceImageBean(title, image, description, date);
                                imageTitleList.add(imageApiBean);

                            }
                            // mAdapter.notifyDataSetChanged();
                            mAdapter.addApplications(imageTitleList, currentDate);
                            mSwipeRefreshLayout.setRefreshing(false);
                            mProgressBar.setVisibility(View.GONE);

                        }else{
                            ifnodata.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mSwipeRefreshLayout.setRefreshing(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(NewsActivity.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
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
}
