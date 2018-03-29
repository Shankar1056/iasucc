package com.tutorialsbuzz.navigationdrawer.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.tutorialsbuzz.navigationdrawer.activity.NewsDescription;
import com.tutorialsbuzz.navigationdrawer.activity.model.ScienceImageBean;
import com.ucc.application.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by indglobal on 12/25/2015.
 */
public class NewsDescriptionPagerFragment extends Fragment
{
    ArrayList<ScienceImageBean> arrayList;
    ImageView itemImageHeader,imageView8;
    TextView previous_news,next_news,alltypetext;
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

        View rootView = inflater.inflate(R.layout.news_description_fragment, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        final Bundle args = getArguments();
         total_length=args.getInt("total_length");
         currentDate= args.getString("currentDate");

      /*  Date date1 = null,date2=null;
        try {
            date1 = simpleDateFormat.parse(args.getString("date"));
            date2 = simpleDateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         ((TextView) rootView.findViewById(R.id.itemHeaderDate)).setText(ClsGeneral.GetDateTimeDifference(date1,date2));
*/


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id. collapsing);
        collapsingToolbar.setTitle(args.getString("title"));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        ((TextView) rootView.findViewById(R.id.itemHeaderTitle)).setText(args.getString("title"));
        ((TextView) rootView.findViewById(R.id.itemHeaderDate)).setText(args.getString("date"));
        ((TextView)rootView.findViewById(R.id.textTitle)).setText(args.getString("title"));

       /* String htmlText = " %s ";
        String alldata =("<html><body>"
                + "<text align=\"justify\">"+(args.getString("description")).replace("span style=\"color:", "font color=").replace("\"","").replace("</span>", "</font>") + "</p> "
                + "</body></html>");
        WebView webView = (WebView)rootView.findViewById(R.id.webView1);
        webView.loadData(String.format(htmlText,alldata), "text/html", "utf-8");*/

        String alldata =((args.getString("description")).replace("span style=\"color:", "font color=").replace("span style=\"background-color:", "font color=").replace("\"","").replace("</span>", "</font>"));

        ((TextView) rootView.findViewById(R.id.itemHeaderDescription)).setText(Html.fromHtml(alldata));


        /*String alldata =("<p><strong><big>Today's</big></strong> <strong>News</strong>\n" +
                "       <font color=#FF0000\">tgn68tyug</font> y7<font color=#B22222\">ng7guiy</font></p>");
        ((TextView) rootView.findViewById(R.id.itemHeaderDescription)).setText(Html.fromHtml(alldata));*/


        previous_news= (TextView)rootView.findViewById(R.id.previous_news);

        next_news=(TextView) rootView.findViewById(R.id.next_news);
        int pos=0;
        previous_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NewsDescription) getActivity()).setCurrentItem(args.getInt("pos") - 1, true);

            }
        });
        next_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NewsDescription) getActivity()).setCurrentItem(args.getInt("pos") + 1, true);

            }
        });
         // activity.getSupportActionBar().setTitle(args.getString("title"));

       // collapsingToolbar.setTitle(args.getString("title"));
        itemImageHeader=(ImageView) rootView.findViewById(R.id.itemImageHeader);

        arrayList = new ArrayList<ScienceImageBean>();

        Picasso.with(getActivity()).load("http://63.142.250.192/UCC/"+args.getString("image").substring(2)).into(itemImageHeader);

        if ((args.getInt("pos")+1)==total_length){
            next_news.setVisibility(View.GONE);

        }
        if ((args.getInt("pos"))==0){
            previous_news.setVisibility(View.GONE);

        }
        Log.i("pos", String.valueOf((args.getInt("pos"))));

        imageView8 = (ImageView)rootView.findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String afterdata=null;
                String beforedata = ((args.getString("description")).replace("span style=\"color:", "font color=").replace("span style=\"background-color:", "font color=").replace("\"","").replace("</span>", "</font>"));
              if (beforedata.length()>200){
                   afterdata = beforedata.substring(0, 200) + "...";
              }
                else {
                  afterdata = beforedata;
              }
               String allqodata= (Html.fromHtml(args.getString("title")) +"\n\n"+Html.fromHtml(afterdata)+"\n \n"+
                        " Install 'IAS UCC' app from google play store or click on this link"+" \n" +"https://play.google.com/store/apps/details?id=com.ucc.application&hl=en");

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "IAS UCC");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, allqodata);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


        return rootView;
    }
}
