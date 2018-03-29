package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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

import com.tutorialsbuzz.navigationdrawer.activity.CommentOnForum;
import com.tutorialsbuzz.navigationdrawer.activity.StudentForumActivity;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.FavouriteFragmentAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomItemAnimator;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.FavModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by indglobal on 13/3/16.
 */
public class FavouriteFragment  extends Fragment {
    ArrayList<FavModel> forumListreply = new ArrayList<FavModel>();
    String id, topic, description, date, likes, created_by, reply_id, reply, created_by_reply, date_reply, idd, commentCount;
    String email, currentDate = null;
    private TextView actionbar_title;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    FavouriteFragmentAdapter adapter;
    FavModel forumCreate, forumreply, cmntcount;
    ArrayList<FavModel> commentcnt = new ArrayList<FavModel>();
    ArrayList<FavModel> forumList = new ArrayList<FavModel>();
    ArrayList<FavModel> newforumListreply = new ArrayList<FavModel>();
    private boolean internetConn;
    private TextView ifnodata;


    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    MyReceiver favouriteFragment;
    public static FavouriteFragment createInstance() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        Bundle bundle = new Bundle();
        favouriteFragment.setArguments(bundle);
        return favouriteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourite_fragment, container, false);

        email = ClsGeneral.getPreferences(getActivity(), "email");

        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        ifnodata = (TextView) v.findViewById(R.id.ifnodata);

        internetConn = Utilz.isInternetConnected(getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        adapter = new FavouriteFragmentAdapter(getActivity(), new ArrayList<FavModel>(), new ArrayList<FavModel>(), currentDate, R.layout.favforum_fragment, FavouriteFragment.this);
        mRecyclerView.setAdapter(adapter);


        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (internetConn) {

                    new FetchData().execute(WebServices.STUDENT_FORUM);
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


        currentDate = ClsGeneral.GetTodaysDate();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        favouriteFragment = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(favouriteFragment,
                new IntentFilter("TAG_REFRESH"));

        if (internetConn) {
            mProgressBar.setVisibility(View.VISIBLE);
            new FetchData().execute(WebServices.STUDENT_FORUM);
          //  Toast.makeText(getActivity(), "On Resumme", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        favouriteFragment = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(favouriteFragment);
    }

    public void senddata(int j) {

        if (newforumListreply.size() > 0) {
            newforumListreply.clear();
        }

        String d = forumList.get(j).getId();
        for (int i = 0; i < forumListreply.size(); i++) {

            String dd = forumListreply.get(i).getIdd();
            if (d.equalsIgnoreCase(dd)) {

                forumreply = new FavModel(forumListreply.get(i).getReply_id(), forumListreply.get(i).getReply(), forumListreply.get(i).getCreated_by_reply(), forumListreply.get(i).getDate_reply(), forumListreply.get(i).getIdd());

                newforumListreply.add(forumreply);
            }

        }
        Intent comment_on_topic = new Intent(getActivity(), CommentOnForum.class);
        comment_on_topic.putExtra("position", "" + j);
        comment_on_topic.putExtra("forumList", forumList);
        comment_on_topic.putExtra("forumreply", newforumListreply);
        comment_on_topic.putExtra("commentcnt", commentcnt);
        startActivity(comment_on_topic);

    }

    private class FetchData extends AsyncTask<String, Void, String> {
        String result1;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("option", "8"));
            nameValuePairs.add(new BasicNameValuePair("key", email));
            try {
                result1 = Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    if (status.equalsIgnoreCase("true")) {
                        ifnodata.setVisibility(View.GONE);

                        if (forumListreply.size() > 0) {
                            forumListreply.clear();
                        }

                        if (forumList.size() > 0) {
                            forumList.clear();
                        }

                        if (commentcnt.size() > 0) {
                            commentcnt.clear();
                        }
                        adapter.clearApplications();

                        JSONArray jarr = jsonObject.getJSONArray("forum");
                        for (int i = 0; i < jarr.length(); i++) {
                            if (jarr.length() == 0) {
                                ifnodata.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            JSONObject json_news = jarr.getJSONObject(i);

                            if (json_news.has("id")) {
                                id = json_news.getString("id");
                            }
                            if (json_news.has("topic")) {
                                topic = json_news.getString("topic");
                            }
                            if (json_news.has("description")) {
                                description = json_news.getString("description");
                            }
                            if (json_news.has("date")) {
                                date = json_news.getString("date");
                            }
                            if (json_news.has("created_by")) {
                                created_by = json_news.getString("created_by");
                            }
                            if (json_news.has("likes")) {
                                likes = json_news.getString("likes");

                            }

                            forumCreate = new FavModel(id, topic, description, date, created_by, likes);
                            forumList.add(forumCreate);

                            JSONArray replyarray = json_news.getJSONArray("replys");
                            commentCount = "" + replyarray.length();
                            for (int j = 0; j < replyarray.length(); j++) {
                                JSONObject replyobj = replyarray.getJSONObject(j);
                                if (replyobj.has("reply_id")) {
                                    reply_id = replyobj.getString("reply_id");
                                }
                                if (replyobj.has("reply")) {
                                    reply = replyobj.getString("reply");
                                }
                                if (replyobj.has("created_by")) {
                                    created_by_reply = replyobj.getString("created_by");
                                }
                                if (replyobj.has("date")) {
                                    date_reply = replyobj.getString("date");
                                }
                                if (replyobj.has("idd")) {
                                    idd = replyobj.getString("idd");
                                }

                                forumreply = new FavModel(reply_id, reply, created_by_reply, date_reply, idd);
                                forumListreply.add(forumreply);
                            }
                            cmntcount = new FavModel(commentCount);
                            commentcnt.add(cmntcount);

                        }
                        adapter.addApplications(forumList, commentcnt, currentDate);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);


                    } else {
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


    public void like(String id) {
        if (internetConn) {
            new Like().execute(WebServices.STUDENT_FORUM, id);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    public void removeFromFav(String id) {
        new RemoveFromFav().execute(WebServices.STUDENT_FORUM, id);

    }

    class Like extends AsyncTask<String, Void, String> {

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

            nameValuePairs.add(new BasicNameValuePair("option", "5"));
            nameValuePairs.add(new BasicNameValuePair("key", email));
            nameValuePairs.add(new BasicNameValuePair("topic_id", params[1]));
            nameValuePairs.add(new BasicNameValuePair("by", email));


            try {

                result1 = Utilz.executeHttpPost(params[0], nameValuePairs);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);


            try {
                Log.d("Response: ", "> " + result);
                if (result != null) {
                    JSONObject object = null;

                    object = new JSONObject(result);
                    String status = null;

                    status = object.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        String likes = object.getString("likes");

                        Intent i = new Intent(getActivity(), StudentForumActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
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

    class RemoveFromFav extends AsyncTask<String, Void, String> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;
        String selectedLocation;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option", "7"));
            nameValuePairs.add(new BasicNameValuePair("key", email));
            nameValuePairs.add(new BasicNameValuePair("discussion_id", params[1]));


            try {

                result1 = Utilz.executeHttpPost(params[0], nameValuePairs);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);


            try {
                Log.d("Response: ", "> " + result);
                if (result != null) {
                    JSONObject object = null;

                    object = new JSONObject(result);
                    String status = null;

                    status = object.getString("status");

                    if (status.equalsIgnoreCase("true")) {
                        Intent i = new Intent(getActivity(), StudentForumActivity.class);
                        startActivity(i);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "successfully removed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "failed to remove", Toast.LENGTH_LONG).show();
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


    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //FavouriteFragment.this.refresh();
        }
    }

    private void refresh() {
        if (internetConn) {

            new FetchData().execute(WebServices.STUDENT_FORUM);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }
}