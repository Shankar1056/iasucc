package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tutorialsbuzz.navigationdrawer.activity.NotificationDescription;
import com.tutorialsbuzz.navigationdrawer.activity.model.NotificationModel;
import com.ucc.application.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by indglobal on 11/4/16.
 */
public class NotificationDescriptionPagerFragment extends Fragment
{
    ArrayList<NotificationModel> arrayList;
    NetworkImageView itemImageHeader;
    TextView previous_news,next_news;
    private ImageLoader imageLoader;
    int total_length;
    private String currentDate;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.notification_description_fragment, container, false);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        final Bundle args = getArguments();
        total_length=args.getInt("total_length");
        currentDate=args.getString("currentDate");

       /* Date date1 = null,date2=null;
        try {
            date1 = simpleDateFormat.parse(args.getString("date"));
            date2 = simpleDateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         ((TextView) rootView.findViewById(R.id.itemHeaderDate)).setText(ClsGeneral.GetDateTimeDifference(date1,date2));
*/

        ((TextView) rootView.findViewById(R.id.itemHeaderTitle)).setText(args.getString("title"));
        ((TextView) rootView.findViewById(R.id.itemHeaderDate)).setText(args.getString("date"));
      //  ((TextView) rootView.findViewById(R.id.itemHeaderDescription)).setText(args.getString("description"));
        previous_news= (TextView)rootView.findViewById(R.id.previous_news);


        String alldata =((args.getString("description")).replace("span style=\"color:", "font color=").replace("span style=\"background-color:", "font color=").replace("\"","").replace("</span>", "</font>"));

        ((TextView) rootView.findViewById(R.id.itemHeaderDescription)).setText(Html.fromHtml(alldata));



        next_news=(TextView) rootView.findViewById(R.id.next_news);
        int pos=0;
        previous_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NotificationDescription) getActivity()).setCurrentItem(args.getInt("pos") - 1, true);

            }
        });
        next_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NotificationDescription) getActivity()).setCurrentItem(args.getInt("pos") + 1, true);

            }
        });
        // activity.getSupportActionBar().setTitle(args.getString("title"));



        arrayList = new ArrayList<NotificationModel>();


        if ((args.getInt("pos")+1)==total_length){
            next_news.setVisibility(View.GONE);

        }
        if ((args.getInt("pos"))==0){
            previous_news.setVisibility(View.GONE);

        }
        Log.i("pos", String.valueOf((args.getInt("pos"))));

        return rootView;
    }
}

