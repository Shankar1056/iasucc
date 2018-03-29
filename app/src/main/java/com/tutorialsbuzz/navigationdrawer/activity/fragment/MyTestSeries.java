package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorialsbuzz.navigationdrawer.activity.adapter.MySeriesAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomItemAnimator;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.MySeriesModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 22/2/16.
 */
public class MyTestSeries extends Fragment {
    String email;
    MySeriesAdapter adapter;
    ArrayList<MySeriesModel> testSerieslist=new ArrayList<MySeriesModel>();


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private String user_id;
    private boolean internetConn;
    private TextView ifnodata;


    public static MyTestSeries createInstance() {
        MyTestSeries partThreeFragment = new MyTestSeries();
        Bundle bundle = new Bundle();
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.online_testseries, null);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        email= ClsGeneral.getPreferences(getActivity(), "email");
        user_id=ClsGeneral.getPreferences(getActivity(), "user_id");
        internetConn= Utilz.isInternetConnected(getActivity());
        //online_series_list = (ListView) view.findViewById(R.id.online_series_list);



        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.list);
        ifnodata=(TextView)view.findViewById(R.id.ifnodata);
        ifnodata.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        adapter = new MySeriesAdapter(new ArrayList<MySeriesModel>(), R.layout.my_testseries_listrow, MyTestSeries.this);
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (internetConn) {
                    new GetAllMenuList().execute(WebServices.NEWS_IMAGE_TITLE);
                }
                else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;

    }



    @Override
    public void onResume() {
        super.onResume();

        if (internetConn) {
            new GetAllMenuList().execute(WebServices.NEWS_IMAGE_TITLE);
        }
        else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    class GetAllMenuList extends AsyncTask<String, Void, String> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option", "13"));
            nameValuePairs.add(new BasicNameValuePair("key",email));
            nameValuePairs.add(new BasicNameValuePair("uid",user_id));


            try {

                result1 = Utilz.executeHttpPost(params[0], nameValuePairs);
//                result1="{\"employee\":[{\"name\":\"Land\"},{\"name\":\"Building\"}]}";


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            try {

                Log.d("Response: ", "> " + result);
                if (result != null) {
                    if (testSerieslist.size()>0){
                        testSerieslist.clear();
                    }


                         adapter.clearApplications();
                    JSONObject object = new JSONObject(result);

                    String  status = object.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray jarr =object.getJSONArray("result");
                        if (jarr.length()==0){
                            ifnodata.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }

                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject json_news = jarr.getJSONObject(i);
                            String id = json_news.getString("test_series_id");
                            String name = json_news.getString("book");
                            String price = json_news.getString("amount");
                            String publisher = json_news.getString("author");
                            String created_date = json_news.getString("created_on");
                            String order_id=json_news.getString("order_id");
                            String transaction_status=json_news.getString("transaction_status");

                            MySeriesModel testSeriesModel=new MySeriesModel(id,name,price,publisher,created_date,order_id,transaction_status);
                            testSerieslist.add(testSeriesModel);

                        }


                        adapter.addApplications(testSerieslist);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    } else {

                            ifnodata.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mSwipeRefreshLayout.setRefreshing(false);

                    }

                } else {
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public class Forum
    {
        String id ;
        String topic ;
        String description ;
        String date;

        Forum( String id ,String topic,String description ,String date)
        {

            this.id=id;
            this.topic=topic;
            this.date=date;
            this.description=description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


    }

}
