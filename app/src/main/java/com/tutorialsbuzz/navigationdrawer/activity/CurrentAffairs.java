package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorialsbuzz.navigationdrawer.activity.adapter.QuestionCountAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.JsonUrlParser;
import com.tutorialsbuzz.navigationdrawer.activity.common.NetworkUtils;
import com.tutorialsbuzz.navigationdrawer.activity.database.Database;
import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by indglobal on 11/21/2015.
 */
public class CurrentAffairs extends AppCompatActivity {
    /***********************************************************************DECLARATIONS********************************************************************/
    Calendar c = Calendar.getInstance();
    int presignal=1,nxtsignal=0;
    int i=7 ,j=7,k=7,l=7 ,counterPre=0,counterNxt=0,a=0;
    boolean status=false;
    Date currPrev=new Date();
    Date currNext=new Date();
    Date currDate=new Date();
    Date preDate=new Date();
    Date nxtDate =new Date();

    TextView week;
    Button previous,next;
    String date,pre,nxt;
    SimpleDateFormat sdf1;
    SimpleDateFormat sdf2;
    ListView dateList;
    private TextView actionbar_title;
    private String dateString;

    public static ArrayList<String> days = new ArrayList<String>();
    public static ArrayList<String> quesListCount= new ArrayList<String>();
    public static ArrayList<QuestionDataBean> emptyString= new ArrayList<QuestionDataBean>();
    public static ArrayList<QuestionDataBean> dbList= new ArrayList<QuestionDataBean>();
    ImageView refresh;
    public ProgressBar dialog;
    String ApiUrl,choosedlanguage;

    ArrayList<QuestionDataBean> dbQuestionList1,dbQuestionList2,dbQuestionList3,dbQuestionList4,dbQuestionList5,dbQuestionList6,dbQuestionList7;
    ArrayList<QuestionDataBean> questionArrayList1;

    Database handler;
    int questionCount=0,datecount=0;

    /***********************************************************************DECLARATIONS********************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar;
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        choosedlanguage = ClsGeneral.getPreferences(CurrentAffairs.this, "choosedlanguage");
        ClsGeneral.updatelanguage(choosedlanguage);
        setContentView(R.layout.current_affairs);

         toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar_title.setText(R.string.daily_questions);


        refresh=(ImageView)findViewById(R.id.refresh);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/***********************************************************************COMPUTING DATE AND DISPLAY TO LIST********************************************************************/

        dateList = (ListView) findViewById(R.id.listView);
        dbQuestionList1 = new ArrayList<QuestionDataBean>();
        dbQuestionList2 = new ArrayList<QuestionDataBean>();
        dbQuestionList3 = new ArrayList<QuestionDataBean>();
        dbQuestionList4 = new ArrayList<QuestionDataBean>();

        dbQuestionList5 = new ArrayList<QuestionDataBean>();
        dbQuestionList6 = new ArrayList<QuestionDataBean>();
        dbQuestionList7 = new ArrayList<QuestionDataBean>();

        previous = (Button) findViewById(R.id.buttonPrev);
        next = (Button) findViewById(R.id.buttonNext);
        week = (TextView) findViewById(R.id.textViewDate);

        questionArrayList1 = new ArrayList<QuestionDataBean>();


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presignal == 0) {
                    previousAction(currDate);
                    presignal = 1;
                    counterPre = 1;
                    //a=a+7;
                } else if (presignal == 1) {
                    previousAction();
                    counterPre = 1;
                }
                getDateList(preDate);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nxtsignal == 1) {
                    nextAction(currDate);
                    nxtsignal = 0;
                    counterNxt = 1;
                    // b=b+7;
                } else if (nxtsignal == 0) {
                    counterNxt = 1;
                    nextAction();
                }
                getDateList(preDate);

            }
        });
        date = setWeek();

        getDateList(preDate);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils utils = new NetworkUtils(CurrentAffairs.this);

                if (utils.isConnectingToInternet()) {

                    getDateList(preDate);

                }
                else {
                    Toast.makeText(CurrentAffairs.this,R.string.toast_check_connection,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    /*    getMenuInflater().inflate(R.menu.menu_main, menu);*/
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    private void previousAction(Date currDate)
    {
        c.setTime(currDate); // Now use today date.
        c.add(Calendar.DATE, -7);
        currDate=c.getTime();
        c.add(Calendar.DATE, -3);
        preDate=c.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd -");
        String pre = sdf1.format(c.getTime());
        c.setTime(currDate); // Now use today date.
        c.add(Calendar.DATE, 3);
        nxtDate=c.getTime();
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
        String next = sdf2.format(c.getTime());
         dateString = pre + next;
        week.setText(dateString);
        currPrev=currDate;
        l=7;
        k=7;
    }
    private void nextAction(Date currDate) {
        c.setTime(currDate); // Now use today date.
        c.add(Calendar.DATE, 7);
        currDate=c.getTime();
        c.add(Calendar.DATE, -3);
        preDate=c.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd -");
        String pre = sdf1.format(c.getTime());
        c.setTime(currDate); // Now use today date.
        c.add(Calendar.DATE, 3);
        nxtDate=c.getTime();
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
        String next = sdf2.format(c.getTime());
         dateString = pre + next;
        week.setText(dateString);
        i=7;
        j=7;
        currNext=currDate;
    }

    public String setWeek() {
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, -3);
        preDate=c.getTime();
        sdf1 = new SimpleDateFormat("MMM dd -");
        String pre = sdf1.format(c.getTime());
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 3);
        nxtDate=c.getTime();
        sdf2 = new SimpleDateFormat("MMM dd");
        String next = sdf2.format(c.getTime());
         dateString = pre + next;
        week.setText(dateString);
        return dateString;
    }

    void previousAction()
    {

        switch (counterPre)
        {
            case 0:
                presignal=1;
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, -i);
                currNext = c.getTime();
                currDate=c.getTime();
                c.add(Calendar.DATE, -3);
                preDate=c.getTime();
                sdf1 = new SimpleDateFormat("MMM dd -");
                pre = sdf1.format(c.getTime());
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, -i);
                c.add(Calendar.DATE, 3);
                nxtDate=c.getTime();
                SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
                nxt = sdf2.format(c.getTime());
                 dateString = pre + nxt;
                week.setText(dateString);
                counterPre++;
                nxtsignal=1;
                i = i + 7;
                break;
            case 1:
                presignal=1;
                c.setTime(currPrev);

                c.add(Calendar.DATE, -j);
                currNext = c.getTime();
                currDate=c.getTime();
                c.add(Calendar.DATE, -3);
                preDate=c.getTime();
                sdf1 = new SimpleDateFormat("MMM dd -");
                pre = sdf1.format(c.getTime());
                c.setTime(currPrev); // Now use today date.
                c.add(Calendar.DATE, -j);
                c.add(Calendar.DATE, 3);
                nxtDate=c.getTime();
                sdf2 = new SimpleDateFormat("MMM dd");
                nxt = sdf2.format(c.getTime());
                dateString = pre + nxt;
                week.setText(dateString);
                nxtsignal=1;
                j = j + 7;
                break;

        }

    }
    void nextAction() {

        switch (counterNxt) {
            case 0:
                nxtsignal=0;
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, k);

                currPrev = c.getTime();
                currDate=c.getTime();
                c.add(Calendar.DATE, -3);
                preDate=c.getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd -");
                String pre = sdf1.format(c.getTime());
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, k);
                c.add(Calendar.DATE, 3);
                SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
                String nxt = sdf2.format(c.getTime());
                 dateString = pre + nxt;
                week.setText(dateString);
                counterNxt++;
                presignal=0;
                k = k + 7;
                break;
            case 1:
                nxtsignal=0;
                c.setTime(currNext);
                c.add(Calendar.DATE, l);

                currPrev = c.getTime();
                currDate=c.getTime();
                c.add(Calendar.DATE, -3);
                preDate=c.getTime();
                sdf1 = new SimpleDateFormat("MMM dd -");
                pre = sdf1.format(c.getTime());
                c.setTime(currNext); // Now use today date.
                c.add(Calendar.DATE, l);
                c.add(Calendar.DATE, 3);
                sdf2 = new SimpleDateFormat("MMM dd");
                nxt = sdf2.format(c.getTime());
                dateString = pre + nxt;
                week.setText(dateString);
                presignal=0;
                l = l + 7;
                break;
        }

    }

    void getDateList(Date preDate) {

        days.clear();
        for (a = 0; a < 7; a++) {
            if (a == 0) {
                c.setTime(preDate);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
                c.add(Calendar.DATE, 0);
                preDate = c.getTime();
                String temp = sdf1.format(c.getTime());
                days.add(temp);
            } else {
                c.setTime(preDate);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
                c.add(Calendar.DATE, 1);
                preDate = c.getTime();
                String temp = sdf1.format(c.getTime());
                days.add(temp);
            }

            quesListCount.add(" ");
        }
/***********************************************************************COMPUTING DATE AND DISPLAY TO LIST********************************************************************/

        status = false;
        QuestionCountAdapter adapter1= new QuestionCountAdapter(CurrentAffairs.this, status, quesListCount, days, new Date());
        dateList.setAdapter(adapter1);

        handler = new Database(this);
        /***********************************************************************CHECKING NETORK ********************************************************************/
        NetworkUtils utils = new NetworkUtils(CurrentAffairs.this);

        if (utils.isConnectingToInternet()) {
            new DataFetcherTask().execute();


        }
        else {
            if (quesListCount.size()>0){
                quesListCount.clear();
            }
            SimpleDateFormat fromUser = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dbQuestionList1 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(0))),choosedlanguage);

            dbQuestionList2 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(1))),choosedlanguage);
            dbQuestionList3=  handler.getQuestionList(myFormat.format(fromUser.parse(days.get(2))),choosedlanguage);
            dbQuestionList4 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(3))),choosedlanguage);
            dbQuestionList5 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(4))),choosedlanguage);
            dbQuestionList6 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(5))),choosedlanguage);
            dbQuestionList7 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(6))),choosedlanguage);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            quesListCount.add(String.valueOf(dbQuestionList1.size()));
            quesListCount.add(String.valueOf(dbQuestionList2.size()));
            quesListCount.add(String.valueOf(dbQuestionList3.size()));
            quesListCount.add(String.valueOf(dbQuestionList4.size()));
            quesListCount.add(String.valueOf(dbQuestionList5.size()));
            quesListCount.add(String.valueOf(dbQuestionList6.size()));
            quesListCount.add(String.valueOf(dbQuestionList7.size()));
            QuestionCountAdapter adapter = null;
            status = true;
            adapter = new QuestionCountAdapter(CurrentAffairs.this,  status,quesListCount, days, new Date());
            dateList.setAdapter(adapter);

            dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position==0) {

                        if (dbQuestionList1.size()!=0) {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {


                            question.putExtra("allqueList", dbQuestionList1);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }

                    }
                    if (position==1) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList2.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList2);

                            question.putExtra("question_date", days.get(position));

                            startActivity(question);
                        }

                    }
                    if (position==2) {
                        if (dbQuestionList3.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList3);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);

                        }



                    }
                    if (position==3) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList4.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList4);
                            question.putExtra("question_date", days.get(position));


                            startActivity(question);
                        }




                    }
                    if (position==4) {
                        if (dbQuestionList5.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList5);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }



                    }
                    if (position==5) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList6.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList6);
                            question.putExtra("question_date", days.get(position));


                            startActivity(question);

                        }




                    }
                    if (position==6) {
                        if (dbQuestionList7.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList7);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }



                    }
                }
            });

        }
    }


    private class DataFetcherTask extends AsyncTask<Void,Void,Void>
    {   String reformattedDate;
        ProgressDialog dialog;
        String email= ClsGeneral.getPreferences(CurrentAffairs.this, "email");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            previous.setClickable(false);
            next.setClickable(false);

            if (dialog == null) {
                dialog = ProgresDilog.createProgressDialog(CurrentAffairs.this);
               // dialog.show();
            } else {
               // dialog.show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            ApiUrl = WebServices.NEWS_IMAGE_TITLE;
            dbQuestionList1 = new ArrayList<QuestionDataBean>();
            dbQuestionList2 = new ArrayList<QuestionDataBean>();


            if(quesListCount.size()>0) quesListCount.clear();
            if(dbQuestionList1.size()>0 ) dbQuestionList1.clear();
            if(dbQuestionList2.size()>0)  dbQuestionList2.clear();
            if(questionArrayList1.size()>0)

                questionArrayList1.clear();



            JsonUrlParser parser1 = new JsonUrlParser(CurrentAffairs.this, ApiUrl,email,choosedlanguage);
            try {
                questionArrayList1 = parser1.parseUrl(days);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            questionCount=handler.getQuestionCount();

            status = true;

            SimpleDateFormat fromUser = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {

               reformattedDate  = myFormat.format(fromUser.parse(days.get(0)));


            dbQuestionList1 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(0))),choosedlanguage);
            dbQuestionList2 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(1))),choosedlanguage);
            dbQuestionList3=  handler.getQuestionList(myFormat.format(fromUser.parse(days.get(2))),choosedlanguage);
            dbQuestionList4 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(3))),choosedlanguage);
            dbQuestionList5 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(4))),choosedlanguage);
            dbQuestionList6 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(5))),choosedlanguage);
            dbQuestionList7 = handler.getQuestionList(myFormat.format(fromUser.parse(days.get(6))),choosedlanguage);


            quesListCount.add(String.valueOf(dbQuestionList1.size()));
            quesListCount.add(String.valueOf(dbQuestionList2.size()));
            quesListCount.add(String.valueOf(dbQuestionList3.size()));
            quesListCount.add(String.valueOf(dbQuestionList4.size()));
            quesListCount.add(String.valueOf(dbQuestionList5.size()));
            quesListCount.add(String.valueOf(dbQuestionList6.size()));
            quesListCount.add(String.valueOf(dbQuestionList7.size()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null) {
                dialog.dismiss();
            }
            previous.setClickable(true);
            next.setClickable(true);

            QuestionCountAdapter adapter = null;
if (quesListCount.size()>0)
{
    adapter = new QuestionCountAdapter(CurrentAffairs.this,  status, quesListCount, days, new Date());
    dateList.setAdapter(adapter);
    adapter.notifyDataSetChanged();
}
     else
{
    /*adapter = new QuestionCountAdapter(CurrentAffairs.this, status, quesListCount, days, new Date());
    dateList.setAdapter(adapter);
    adapter.notifyDataSetChanged();*/
}


           // Toast.makeText(CurrentAffairs.this,"count = "+questionCount,Toast.LENGTH_LONG).show();

            dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position==0) {

                        if (dbQuestionList1.size()!=0) {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList1);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }

                    }


                    if (position==1) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList2.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList2);
                            question.putExtra("question_date", days.get(position));


                            startActivity(question);
                        }

                    }


                    if (position==2) {
                        if (dbQuestionList3.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList3);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }



                    }


                    if (position==3) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList4.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList4);
                            question.putExtra("question_date", days.get(position));


                            startActivity(question);
                        }




                    }
                    if (position==4) {
                        if (dbQuestionList5.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList5);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }



                    }
                    if (position==5) {

                        //  int count = handler.getQuestionCount();
                        if (dbQuestionList6.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList6);
                            question.putExtra("question_date", days.get(position));


                            startActivity(question);

                        }




                    }
                    if (position==6) {
                        if (dbQuestionList7.size()!=0)
                        {
                            Intent question = new Intent(CurrentAffairs.this, Questions.class);
                            //  if (questionArrayList1.get(position).getDate() == days.get(position)) {
                            question.putExtra("allqueList", dbQuestionList7);
                            question.putExtra("question_date", days.get(position));
                            startActivity(question);
                        }



                    }


                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        String headerlanguage = ClsGeneral.getPreferences(CurrentAffairs.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }
}