package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.app.Activity;
import android.content.Intent;
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

import com.tutorialsbuzz.navigationdrawer.activity.PracticePaper;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.OnlineSeriesAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomItemAnimator;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 30/1/16.
 */
public class OnlineTestSeries extends Fragment {
    String email;
    OnlineSeriesAdapter adapter;
    ArrayList<TestSeriesModel> testSerieslist=new ArrayList<TestSeriesModel>();
    ArrayList<TestSeriesModel> testreviewlist=new ArrayList<TestSeriesModel>();

    private boolean internetConn;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private TextView ifnodata;



    public static OnlineTestSeries createInstance() {
        OnlineTestSeries partThreeFragment = new OnlineTestSeries();
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

    mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
    mRecyclerView = (RecyclerView)view.findViewById(R.id.list);
    ifnodata=(TextView)view.findViewById(R.id.ifnodata);
    ifnodata.setVisibility(View.GONE);
    internetConn= Utilz.isInternetConnected(getActivity());

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerView.setItemAnimator(new CustomItemAnimator());

    adapter = new OnlineSeriesAdapter(new ArrayList<TestSeriesModel>(), new ArrayList<TestSeriesModel>(),R.layout.online_testseries_listrow, OnlineTestSeries.this);
    mRecyclerView.setAdapter(adapter);
    mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
    mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
    mSwipeRefreshLayout.setRefreshing(true);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (internetConn) {
                new GetAllMenuList().execute(WebServices.TEST_SERIES);
            }
            else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }
    });

  /*  online_series_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id)
{

    Intent i=new Intent(getActivity(), PracticePaper.class);
    i.putExtra("testSerieslist", testSerieslist);
    i.putExtra("testreviewlist",testreviewlist);
    i.putExtra("position",position);
   // i.putExtra("all_data",)
    startActivity(i);
        }
        });
*/
    if (internetConn) {
        mProgressBar.setVisibility(View.VISIBLE);
        new GetAllMenuList().execute(WebServices.TEST_SERIES);
    }
    else {
        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
    }

        return view;

        }

@Override
public void onResume() {
        super.onResume();


        }

    public void animateActivity(int i) {
        Intent intent=new Intent(getActivity(), PracticePaper.class);
        intent.putExtra("testSerieslist", testSerieslist);
        intent.putExtra("testreviewlist", testreviewlist);
        intent.putExtra("position",i);
        // i.putExtra("all_data",)
        startActivity(intent);
    }


    class GetAllMenuList extends AsyncTask<String, Void, String> {

    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    String result1;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        nameValuePairs.add(new BasicNameValuePair("option", "1"));
        nameValuePairs.add(new BasicNameValuePair("key",email));


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
                if (testreviewlist.size()>0){
                    testreviewlist.clear();
                }
                adapter.clearApplications();

                JSONObject object = new JSONObject(result);

                String  status = object.getString("status");
                if (status.equalsIgnoreCase("true")) {
                    JSONArray jarr =object.getJSONArray("series");
                    if (jarr.length()==0){
                        ifnodata.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json_news = jarr.getJSONObject(i);
                        String id = json_news.getString("id");
                        String name = json_news.getString("name");
                        String category = json_news.getString("category");
                        String description = json_news.getString("description");

                        String doc_url = json_news.getString("doc_url");
                        String price = json_news.getString("price");
                        String publisher = json_news.getString("publisher");
                        String created_date = json_news.getString("created_date");
                        String how_it_works= json_news.getString("how_it_works");
                        String table_of_content= json_news.getString("table_of_content");

                        TestSeriesModel testSeriesModel=new TestSeriesModel(id,name,category,description,doc_url,price,publisher,created_date,how_it_works,table_of_content);
                        testSerieslist.add(testSeriesModel);
                        JSONArray quetionset = json_news.getJSONArray("review");
                        for (int j= 0; j < quetionset.length(); j++)
                        {
                            JSONObject reviewobject=quetionset.getJSONObject(j);
                            String reviewid = reviewobject.getString("id");
                            String rating = reviewobject.getString("rating");
                            String review=reviewobject.getString("review");
                            String reviewed_by = reviewobject.getString("reviewed_by");
                            String date = reviewobject.getString("date");

                            TestSeriesModel reviewtestSeriesModel=new TestSeriesModel(id,reviewid,rating,review,reviewed_by,date);
                            testreviewlist.add(reviewtestSeriesModel);
                        }


                    }


                    adapter.addApplications(testSerieslist,testreviewlist);
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