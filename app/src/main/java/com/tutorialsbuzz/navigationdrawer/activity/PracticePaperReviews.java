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

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.ReviewsAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.ReviewOnPaper;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 10/2/16.
 */
public class PracticePaperReviews extends AppCompatActivity {
    EditText typeComment;
    LinearLayout enter;
    ArrayList<ReviewOnPaper> myist;
    ArrayList<TestSeriesModel> reviewDetails,addReview;
    ListView listView;
    ReviewsAdapter adapter;
    private TextView postName, dueTime, discussion, replylike, replycomment;
    private String id, topic, description, date, likes, created_by, reply_id, reply, created_by_reply, date_reply, commentCount, discossion_id, idd;
    private ReviewOnPaper reviewcount, cmntcount;
    private String email, name;
    TestSeriesModel seriesModel;
    ImageView refresh;
    Toolbar toolbar;
    LinearLayout like_comment_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_on_forum);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeComment = (EditText) findViewById(R.id.typeComment);
        enter = (LinearLayout) findViewById(R.id.enter);
        typeComment.setBackgroundColor(Color.TRANSPARENT);
        listView = (ListView) findViewById(R.id.chatList);
        addReview=new ArrayList<>();
        postName = (TextView) findViewById(R.id.postName);
        dueTime = (TextView) findViewById(R.id.dueTime);
        discussion = (TextView) findViewById(R.id.discussion);
        replylike = (TextView) findViewById(R.id.replylike);
        replycomment = (TextView) findViewById(R.id.replycomment);
        like_comment_layout=(LinearLayout)findViewById(R.id.like_comment_layout);
        refresh=(ImageView)findViewById(R.id.refresh);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PracticePaperReviews.this.finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<10;i++)
                {
                    reviewcount= new ReviewOnPaper(String.valueOf(i),"paper","by ucc","4","created","1 Feb 2016");
                    myist.add(reviewcount);
                }
                reviewDetails=(ArrayList<TestSeriesModel>   )getIntent().getSerializableExtra("reviewDetails");



                postName.setText(getIntent().getExtras().getString("testSeriesName"));
                dueTime.setText(getIntent().getExtras().getString("testSeriesBy"));

                discussion.setText(reviewDetails.size() + R.string.reviews);
            }
        });

        like_comment_layout.setVisibility(View.GONE);

        myist = new ArrayList<ReviewOnPaper>();

        email= ClsGeneral.getPreferences(this, "email");
        myist.clear();

      for (int i=0;i<10;i++)
      {
          reviewcount= new ReviewOnPaper(String.valueOf(i),"paper","by ucc","4","created","1 Feb 2016");
          myist.add(reviewcount);
      }
        reviewDetails=(ArrayList<TestSeriesModel>   )getIntent().getSerializableExtra("reviewDetails");



        postName.setText(getIntent().getExtras().getString("testSeriesName"));
        dueTime.setText(getIntent().getExtras().getString("testSeriesBy"));

        discussion.setText(reviewDetails.size() + R.string.reviews);

     /*   adapter = new ReviewsAdapter(PracticePaperReviews.this, myist);
        listView.setAdapter(adapter);*/
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gettext = typeComment.getText().toString();
                if (gettext.equalsIgnoreCase("")) {
                    Toast.makeText(PracticePaperReviews.this, R.string.write_comment_here, Toast.LENGTH_SHORT).show();

                } else {
                    new sendReview().execute("http://63.142.250.192/UCC/test_series_api.php", gettext);


                }


            }
        });

    }


    class sendReview extends AsyncTask<String, Void, String> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        String result1;
        String selectedLocation;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PracticePaperReviews.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option", "2"));
            nameValuePairs.add(new BasicNameValuePair("key",email));

            nameValuePairs.add(new BasicNameValuePair("test_series_id", (getIntent().getExtras().getString("testSeriesId"))));
            nameValuePairs.add(new BasicNameValuePair("test_series_rating", getIntent().getExtras().getString("rating_text")));
            nameValuePairs.add(new BasicNameValuePair("review_text",params[1]));
            nameValuePairs.add(new BasicNameValuePair("email", email));


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

            if (progressDialog != null) {
                progressDialog.dismiss();
            }



            try {



                Log.d("Response: ", "> " + result);
                if (result != null) {


                    JSONObject object = new JSONObject(result);

                    String  status = object.getString("status");



                        if (status.equalsIgnoreCase("true")){
                            //new GetAllMenuList().execute(ApiHandler.STUDENT_FORUM);
                            Toast.makeText(PracticePaperReviews.this, R.string.review_added, Toast.LENGTH_SHORT).show();

                            typeComment.getText().clear();

                            Intent intent=new Intent(PracticePaperReviews.this,TestSeries.class);
                            startActivity(intent);
                        }

                } else {
                    Toast.makeText(PracticePaperReviews.this, R.string.already_reviewd, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
         finish();
        }

    }
}
