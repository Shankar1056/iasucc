package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.CommentOnForumAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomRequest;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.CommentForum;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by igcs-27 on 25/1/16.
 */
public class CommentOnForum extends AppCompatActivity {
    EditText typeComment;
    LinearLayout enter,rating_bar;
    ArrayList<CommentForum> myist,mylistreply,commentcnt,forumList;
    ListView listView;
    Toolbar toolbar;
    TextView actionbar_title;
    CommentOnForumAdapter adapter;
    ImageView refresh;
    private TextView postName,dueTime,discussion,replylike,replycomment;
    private  String   id,topic,description,date,likes,created_by,reply_id,reply,created_by_reply,date_reply,commentCount,discossion_id,idd;
    private CommentForum forumreply,cmntcount;
    private String email,name,currentDate=null;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-M-dd hh:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_on_forum);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        rating_bar.setVisibility(View.GONE);
        typeComment=(EditText)findViewById(R.id.typeComment);
        enter=(LinearLayout) findViewById(R.id.enter);
        typeComment.setBackgroundColor(Color.TRANSPARENT);
        listView =(ListView) findViewById(R.id.chatList);
        refresh=(ImageView)findViewById(R.id.refresh);
        postName=(TextView)findViewById(R.id.postName);
        dueTime=(TextView)findViewById(R.id.dueTime);
        discussion=(TextView)findViewById(R.id.discussion);
        replylike=(TextView)findViewById(R.id.replylike);
        replycomment=(TextView)findViewById(R.id.replycomment);

        enter.setClickable(true);
        enter.setFocusable(true);

        email= ClsGeneral.getPreferences(CommentOnForum.this, "email");
        name= ClsGeneral.getPreferences(CommentOnForum.this, "fullname");

        actionbar_title.setText(name);
        myist=new ArrayList<CommentForum>();
        mylistreply=new ArrayList<CommentForum>();
        commentcnt=new ArrayList<CommentForum>();
        forumList=new ArrayList<CommentForum>();

        Intent i=getIntent();
        String position=i.getStringExtra("position");
        mylistreply.clear();

        myist = (ArrayList<CommentForum>)getIntent().getSerializableExtra("forumList");
        mylistreply=(ArrayList<CommentForum>)getIntent().getSerializableExtra("forumreply");
        commentcnt=(ArrayList<CommentForum>)getIntent().getSerializableExtra("commentcnt");
        currentDate=getIntent().getStringExtra("currentDate");
        discossion_id=myist.get(Integer.parseInt(position)).getId();
       /* Date date1=null,date2=null;
        try {

             date1 = simpleDateFormat.parse(myist.get(Integer.parseInt(position)).getDate());
             date2 = simpleDateFormat.parse(currentDate);



        } catch (ParseException e) {
            e.printStackTrace();
        }
         dueTime.setText(ClsGeneral.GetDateTimeDifference(date1,date2));
         */


        postName.setText(myist.get(Integer.parseInt(position)).getCreated_by());
        dueTime.setText(myist.get(Integer.parseInt(position)).getDate());
        discussion.setText(myist.get(Integer.parseInt(position)).getTopic());
        if (myist.get(Integer.parseInt(position)).getIslikes().equalsIgnoreCase("true")) {
            replylike.setText(myist.get(Integer.parseInt(position)).getLikes()+" "+getResources().getString(R.string.liked));
        }
        else {
            replylike.setText(myist.get(Integer.parseInt(position)).getLikes()+" "+getResources().getString(R.string.like));
        }
        replycomment.setText(commentcnt.get(Integer.parseInt(position)).getCommentCount()+" "+getResources().getString(R.string.comments));



        adapter=new CommentOnForumAdapter(CommentOnForum.this,mylistreply, commentcnt);
        listView.setAdapter(adapter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gettext = typeComment.getText().toString().trim();

             //   gettext  = gettext.replaceAll(" ", "%20");

                if (gettext.equalsIgnoreCase("")) {
                    Toast.makeText(CommentOnForum.this, R.string.edittext_your_comment, Toast.LENGTH_SHORT).show();

                } else {
                    enter.setClickable(false);
                    enter.setFocusable(false);
                    sendcomment(WebServices.STUDENT_FORUM, "2", gettext);
                }


            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mylistreply.size()>0){
                    mylistreply.clear();
                }
                String gettext = typeComment.getText().toString();
                new GetAllMenuList().execute(WebServices.STUDENT_FORUM);
            }
        });

    }
    private void sendcomment(String ApiUrl, String s, String gettext) {



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();
        params.put("option", s);
        params.put("key", email);
        params.put("discussion_id", discossion_id);
        params.put("reply", gettext);
        params.put("by",name);
        params.put("active_status", "yes");
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ApiUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if (mylistreply.size()>0){
                                mylistreply.clear();
                            }
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("true")){
                                enter.setClickable(true);
                                enter.setFocusable(true);
                                new GetAllMenuList().execute(WebServices.STUDENT_FORUM);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // stopping swipe refresh


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
            }
        });

        requestQueue.add(jsObjRequest);

    }
    class GetAllMenuList extends AsyncTask<String, Void, String> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;
        String selectedLocation;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CommentOnForum.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option", "3"));
            nameValuePairs.add(new BasicNameValuePair("key", email));


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

            if (progressDialog != null) {
                progressDialog.dismiss();
            }


            try {
                Log.d("Response: ", "> " + result);
                if (result != null) {
                    JSONObject object = null;

                    object = new JSONObject(result);
                    String status = null;

                    status = object.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        if (mylistreply.size()>0){
                            mylistreply.clear();
                        }

                        JSONArray jarr =object.getJSONArray("forum");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject json_news = jarr.getJSONObject(i);
                            if (json_news.has("id")){
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
                                likes  = json_news.getString("likes");
                            }
                            CommentForum  forumCreate=new CommentForum(id,topic,description,date,created_by,likes);
                            forumList.add(forumCreate);


                            JSONArray replyarray = json_news.getJSONArray("replys");
                            commentCount=""+replyarray.length();
                            for (int j= 0; j < replyarray.length(); j++)
                            {
                                JSONObject replyobj=replyarray.getJSONObject(j);
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

                                if (discossion_id.equalsIgnoreCase(idd)) {
                                    forumreply = new CommentForum(reply_id, reply, created_by_reply, date_reply, idd);
                                    mylistreply.add(forumreply);
                                }
                            }
                            cmntcount=new CommentForum(commentCount);
                            commentcnt.add(cmntcount);

                        }



                        replycomment.setText(""+mylistreply.size());

                        adapter=new CommentOnForumAdapter(CommentOnForum.this,mylistreply,commentcnt);
                        listView.setAdapter(adapter);
                        typeComment.getText().clear();

                    } else {
                        Toast.makeText(CommentOnForum.this, "", Toast.LENGTH_LONG).show();
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

}
