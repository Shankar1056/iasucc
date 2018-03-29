package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.tutorialsbuzz.navigationdrawer.activity.CurrentAffairs;
import com.tutorialsbuzz.navigationdrawer.activity.NewsActivity;
import com.tutorialsbuzz.navigationdrawer.activity.Notification;
import com.tutorialsbuzz.navigationdrawer.activity.StudentForumActivity;
import com.tutorialsbuzz.navigationdrawer.activity.TestSeries;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.BannerPagerAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.SlidePagerAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.GraphicsUtil;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.model.BannerModel;
import com.tutorialsbuzz.navigationdrawer.activity.model.NewReleaseModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;
import com.ucc.application.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout dailyquestionLayout,newsLayout,studentforumLayout,testseriesLayout,guidelineLayout,notificationLayout;
    String email,pos;
    ViewPager imgNewsImg,imgBannerImage;
    public int currentimageindex = -1,currentbannerindex=-1;
    boolean isGoingForaward = true,isBannerForaward = true;
    ArrayList<NewReleaseModel> bannerNews;
    ArrayList<BannerModel> bannerModels;
    GraphicsUtil graphicUtil;
    Timer timer;
    int delay = 1000;
    int period = 5000;
    final Handler mHandler = new Handler();
    private ImageLoader imageLoader;
    int count;
    String ratestatus="F",rate_count,rate_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        email= ClsGeneral.getPreferences(getActivity(), "email");
        rate_count=ClsGeneral.getPreferences(getActivity(), "rate_count");
        rate_status=ClsGeneral.getPreferences(getActivity(), "rate_status");
        if (!rate_count.equalsIgnoreCase("")) {
            count = Integer.parseInt(rate_count);
        }

        dailyquestionLayout=(LinearLayout)v.findViewById(R.id.dailyquestionLayout);
        newsLayout=(LinearLayout)v.findViewById(R.id.newsLayout);
        studentforumLayout=(LinearLayout)v.findViewById(R.id.studentforumLayout);
        testseriesLayout=(LinearLayout)v.findViewById(R.id.testseriesLayout);
        guidelineLayout=(LinearLayout)v.findViewById(R.id.guidelineLayout);
        notificationLayout=(LinearLayout)v.findViewById(R.id.notificationLayout);

        dailyquestionLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        studentforumLayout.setOnClickListener(this);
        testseriesLayout.setOnClickListener(this);
        guidelineLayout.setOnClickListener(this);
        notificationLayout.setOnClickListener(this);


        imgNewsImg = (ViewPager) v.findViewById(R.id.imgNewsImage);
        imgBannerImage= (ViewPager) v.findViewById(R.id.imgBannerImage);
        bannerNews = new ArrayList<NewReleaseModel>();
        bannerModels=new ArrayList<BannerModel>();
        imgNewsImg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currentimageindex = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                for (int i = 0; i < 5; i++) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

        imgBannerImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currentbannerindex = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                for (int i = 0; i < 5; i++) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

        dotwowork();
        String rating= ClsGeneral.getPreferences(getActivity(), "rating");
        if ((count>=3) && (rate_status.equalsIgnoreCase("F"))) {

            rateapp();
        }



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ratestatus.equalsIgnoreCase("t")){
            callapi();
        }
    }

    private void callapi() {


        new ShowRateApp().execute(WebServices.NEWS_IMAGE_TITLE);

    }
    private class ShowRateApp extends AsyncTask<String,Void,String> {
        String result1;
        ProgressDialog progressDialog;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        @Override
        protected String doInBackground(String... params) {
            try {
                nameValuePairs.add(new BasicNameValuePair("option", "17"));
                nameValuePairs.add(new BasicNameValuePair("key", email));
                nameValuePairs.add(new BasicNameValuePair("rate_count","4"));
                nameValuePairs.add(new BasicNameValuePair("rate_status", "T"));
                result1= Utilz.executeHttpPost(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);




        }
    }



    private void rateapp() {

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        alertDialog.setTitle("IAS UCC");

        alertDialog.setMessage("Please rate our app on PlayStore ");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Rate Now", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Intent intnt=new Intent(Intent.ACTION_VIEW);
                intnt.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ucc.application"));
                startActivity(intnt);

ratestatus="t";
                ClsGeneral.setPreferences(getActivity(),"rate_count","");

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Rate Later", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

              alertDialog.cancel();


            }});



             alertDialog.show();
    }
    private void dotwowork() {


        Thread t = new Thread() {
            public void run() {
                new ShowAnimation().execute(WebServices.NEWS_IMAGE_TITLE);
                mHandler.post(mUpdateResults);
            }
        };

        t.start();
    }

    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            new ShowBanner().execute(WebServices.NEWS_IMAGE_TITLE);
        }
    };


    private void callintent(Class<StudentForumActivity> registerClass) {
        Intent i = new Intent(getActivity(),registerClass);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.dailyquestionLayout:
                Intent i=new Intent(getActivity(), CurrentAffairs.class);
                startActivity(i);
                break;
            case R.id.newsLayout:
                Intent news=new Intent(getActivity(), NewsActivity.class);
                startActivity(news);
                break;
            case R.id.studentforumLayout:
                Intent sforum=new Intent(getActivity(), StudentForumActivity.class);
                startActivity(sforum);
                break;
           case R.id.testseriesLayout:
                Intent testseries=new Intent();
                testseries.setClass(getActivity(), TestSeries.class);
                startActivity(testseries);
                break;
            case R.id.guidelineLayout:
                Intent practice=new Intent(getActivity(), Notification.class);
                practice.putExtra("guidelineLayout", "Guidelines");
                startActivity(practice);

                break;
            case R.id.notificationLayout:
                Intent notifi=new Intent(getActivity(), Notification.class);
                notifi.putExtra("guidelineLayout", "Notifications");
                startActivity(notifi);
                break;
           /* case R.id.bannerImage:
               Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bannerModels.get(0).getUrl()));
                startActivity(intent);
                break;*/
            default:
                break;
        }
    }
    private void AnimateandSlideShow() {
        if (isGoingForaward) {
            currentimageindex++;

            if (currentimageindex >= (bannerNews.size() - 1)) {
                isGoingForaward = false;

            }
        } else if (!isGoingForaward) {
            currentimageindex--;
            if (currentimageindex <= 0) {
                isGoingForaward = true;
            }
        }

        Log.d("POSITION", "" + currentimageindex);
        imgNewsImg.setCurrentItem(currentimageindex);
    }

    private void BannerSlideShow() {
        if (isBannerForaward) {
            currentbannerindex++;

            if (currentbannerindex >= (bannerModels.size() - 1)) {
                isBannerForaward = false;

            }
        } else if (!isBannerForaward) {
            currentbannerindex--;
            if (currentbannerindex <= 0) {
                isBannerForaward = true;
            }
        }

        Log.d("POSITION", "" + currentbannerindex);
        imgBannerImage.setCurrentItem(currentbannerindex);
    }

    public void senddata(int bannerModel) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(bannerModels.get(bannerModel).getUrl()));
        startActivity(intent);
    }


    class ShowAnimation extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            nameValuePairs.add(new BasicNameValuePair("option", "14"));
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

            if (result != null) {

                try {
                    Log.e("result1", result1);
                    JSONObject jobj = new JSONObject(result1);
                    String isSuccess = jobj.getString("isSuccess");
                    String success_msg = jobj.getString("mgs");

                    if(isSuccess.equalsIgnoreCase("true")){

                        JSONArray jarr = jobj.getJSONArray("result");
                        for(int i=0; i<jarr.length(); i++){
                            JSONObject json_banner = jarr.getJSONObject(i);

                            String id = json_banner.getString("id");
                            String news_title = json_banner.getString("news_title");
                            String news_description = json_banner.getString("news_description");
                            NewReleaseModel newReleaseModel=new NewReleaseModel(id,news_title,news_description);
                            bannerNews.add(newReleaseModel);
                        }

                        if (bannerNews.size() > 0) {
                            SlidePagerAdapter viewPagerAdapter = new SlidePagerAdapter(
                                    getActivity(), bannerNews,
                                    graphicUtil);
                            imgNewsImg.setAdapter(viewPagerAdapter);
                            // pageSwitcher(2);
                            final Handler mHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                public void run() {
                                    AnimateandSlideShow();
                                }
                            };
                            timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                public void run() {

                                    mHandler.post(mUpdateResults);
                                }

                            }, delay, period);
                        }

                    }
                    else {
                       // Toast.makeText(getActivity(), success_msg, Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getActivity(), "Service is down please try again later", Toast.LENGTH_LONG).show();
            }
        }
    }


    class ShowBanner extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            nameValuePairs.add(new BasicNameValuePair("option", "15"));
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

            if (result != null) {

                try {
                    Log.e("result1", result1);
                    JSONObject jobj = new JSONObject(result1);
                    String isSuccess = jobj.getString("status");
                    String success_msg = jobj.getString("mgs");

                    if(isSuccess.equalsIgnoreCase("true")){

                        JSONArray jarr = jobj.getJSONArray("result");
                        for(int i=0; i<jarr.length(); i++){
                            JSONObject json_banner = jarr.getJSONObject(i);

                            String image = json_banner.getString("image");
                            String url = json_banner.getString("url");
                            BannerModel newReleaseModel=new BannerModel(image,url);
                            bannerModels.add(newReleaseModel);
                        }


                        if (bannerModels.size() > 0) {
                            BannerPagerAdapter viewPagerAdapter = new BannerPagerAdapter(
                                    getActivity(), bannerModels,
                                    graphicUtil,HomeFragment.this);
                            imgBannerImage.setAdapter(viewPagerAdapter);
                            // pageSwitcher(2);
                            final Handler mHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                public void run() {
                                    BannerSlideShow();
                                }
                            };
                            timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                public void run() {

                                    mHandler.post(mUpdateResults);
                                }

                            }, delay, period);
                        }
                    }
                    else {
                       // Toast.makeText(getActivity(), success_msg, Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getActivity(), "Service is down please try again later", Toast.LENGTH_LONG).show();
            }
        }
    }




}
