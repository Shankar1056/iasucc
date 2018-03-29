package com.tutorialsbuzz.navigationdrawer.activity.common;

import android.content.Context;
import android.util.Log;

import com.tutorialsbuzz.navigationdrawer.activity.database.Database;
import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by indglobal on 12/2/2015.
 */
public class JsonUrlParser {
    String url,choosedlanguage;
    QuestionDataBean questionDataBean,questionDataBean1;
    Database handler;
    public  static ArrayList<QuestionDataBean> quesList,totalList;

   // ArrayList<String> optionsList=new ArrayList<String>();

Context context;
    String email;


    public JsonUrlParser(Context context, String url, String email, String choosedlanguage)
    {
        this.context=context;
        this.url=url;
        this.email=email;
        this.choosedlanguage=choosedlanguage;
    }


    public ArrayList<QuestionDataBean> parseUrl(ArrayList<String> days) throws JSONException {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");


        try {
            String startdate = myFormat.format(fromUser.parse(days.get(0)));
            String enddate = myFormat.format(fromUser.parse(days.get(6)));




        ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("option","3"));
        nameValuePairs.add(new BasicNameValuePair("key",email));
        nameValuePairs.add(new BasicNameValuePair("startdate",startdate));
        nameValuePairs.add(new BasicNameValuePair("enddate",enddate));
        handler=new Database(context);
        quesList=new ArrayList<QuestionDataBean>();
        totalList=new ArrayList<QuestionDataBean>();
        String jsonStr = null;
        try {
            jsonStr = Utilz.executeHttpPost(url, nameValuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Response: ", "> " + jsonStr);

        totalList.clear();
        if (jsonStr != null) {



            JSONObject object = null;

            object = new JSONObject(jsonStr);
            String status = null;

            status = object.getString("status");
            if (status.equalsIgnoreCase("true")) {



                JSONArray array = object.getJSONArray("question_set");


                for (int i = 0; i < array.length(); i++) {

                    try {
                    JSONObject category = array.getJSONObject(i);

                        String id = category.getString("id");
                        String question_category = category.getString("title");

                    String date = category.getString("date");
                        handler.onUpdate(date,choosedlanguage);

                    String total_question  = category.getString("total_question");

                        JSONArray quetionset = category.getJSONArray("quetionset");
                        for (int j= 0; j < quetionset.length(); j++)
                        {
                            JSONObject questionList = quetionset.getJSONObject(j);



                            String question = questionList.getString("question");
                            String ans_one = questionList.getString("ans_one");
                            String ans_two = questionList.getString("ans_two");
                            String ans_three = questionList.getString("ans_three");
                            String ans_four = questionList.getString("ans_four");
                            String correct_ans = questionList.getString("correct_ans");
                            String isDes = questionList.getString("isDes");
                            String  correct_ans_desc=null;
                            if (isDes.equalsIgnoreCase("true")){
                                correct_ans_desc = questionList.getString("correct_ans_desc");
                            }

                            questionDataBean = new QuestionDataBean(id,question_category,question,correct_ans,ans_one,ans_two,ans_three,ans_four, total_question, date,choosedlanguage,correct_ans_desc);
                            questionDataBean1 = new QuestionDataBean(question,correct_ans,ans_one,ans_two,ans_three,ans_four);

                            totalList.add(questionDataBean);
                            quesList.add(questionDataBean1);

                            handler.insertQuestionData(questionDataBean);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return totalList;
    }


}
