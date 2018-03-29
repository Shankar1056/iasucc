package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 22/1/16.
 */
public class Results extends AppCompatActivity {
    TextView score, no_of_correct, no_of_wrong, not_answered, tryAgain, home;
    String correct, wrong, notAnswered, totalQuestions, category_id;
    Toolbar toolbar;
    private TextView actionbar_title, questionDate, total_candidates, best_score, rank;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

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
        email = ClsGeneral.getPreferences(Results.this, "email");
        new GetAllMenuList().execute("http://63.142.250.192/UCC/api.php");

        score = (TextView) findViewById(R.id.score);
        no_of_correct = (TextView) findViewById(R.id.no_of_correct);
        no_of_wrong = (TextView) findViewById(R.id.no_of_wrong);
        not_answered = (TextView) findViewById(R.id.not_answered);
        tryAgain = (TextView) findViewById(R.id.tryAgain);
        home = (TextView) findViewById(R.id.home);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        questionDate = (TextView) findViewById(R.id.questionDate);

        total_candidates = (TextView) findViewById(R.id.total_candidates);
        best_score = (TextView) findViewById(R.id.best_score);
        rank = (TextView) findViewById(R.id.rank);


        actionbar_title.setText(R.string.results);

        correct = getIntent().getStringExtra("no_of_correct");
        totalQuestions = getIntent().getStringExtra("number_of_questions");
        notAnswered = getIntent().getStringExtra("not_answered");
        wrong = getIntent().getStringExtra("no_of_wrong");

        score.setText(correct + " /" + totalQuestions);
        no_of_correct.setText(" "+correct);
        no_of_wrong.setText(" "+wrong);
        not_answered.setText(" "+notAnswered);

        category_id = getIntent().getStringExtra("category_id");

        questionDate.setText(" : "+getIntent().getStringExtra("question_date"));
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent home = new Intent(Results.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        });
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
            progressDialog = ProgressDialog.show(Results.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("option", "9"));
            nameValuePairs.add(new BasicNameValuePair("key", email));
            nameValuePairs.add(new BasicNameValuePair("total_questions", totalQuestions));
            nameValuePairs.add(new BasicNameValuePair("correct_ans", correct));
            nameValuePairs.add(new BasicNameValuePair("wrong_ans", wrong));
            nameValuePairs.add(new BasicNameValuePair("question_series_id", category_id));


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

                    String status = object.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray jarr = object.getJSONArray("ranking");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject json_news = jarr.getJSONObject(i);
                            String totalNum = json_news.getString("totalNum");
                            String ranks = json_news.getString("rank");
                            String best = json_news.getString("best");

                            total_candidates.setText(" : "+totalNum);

                            best_score.setText(" : "+best);
                            rank.setText(" : "+ranks);


                        }


                    } else {

                        Toast.makeText(Results.this, "", Toast.LENGTH_LONG).show();
                    }

                } else {
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
