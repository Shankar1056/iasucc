package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tutorialsbuzz.navigationdrawer.activity.NewsActivity;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomVolleyRequest;
import com.tutorialsbuzz.navigationdrawer.activity.model.ScienceImageBean;
import com.ucc.application.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by indglobal on 13/3/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<ScienceImageBean> applications;
    private int rowLayout;
    private ImageLoader imageLoader;
    private NewsActivity mAct;
    private List<ScienceImageBean> arraylist;
    private String currentDate;

    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

    public NewsAdapter(List<ScienceImageBean> applications, String currentDate, int rowLayout, NewsActivity act) {
        this.applications = applications;
        this.rowLayout = rowLayout;
        this.mAct = act;
        this.arraylist = new ArrayList<ScienceImageBean>();
        this.arraylist.addAll(applications);
        this.currentDate=currentDate;
    }


    public void clearApplications() {
        int size = this.applications.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                applications.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addApplications(List<ScienceImageBean> applications, String currentDate) {
        this.applications.addAll(applications);
        this.arraylist.addAll(applications);
        this.currentDate=currentDate;
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final ScienceImageBean appInfo = applications.get(i);
        viewHolder.grid_text_news.setText(appInfo.getTitle());
        imageLoader = CustomVolleyRequest.getInstance(mAct.getApplicationContext())
                .getImageLoader();
        String url="http://63.142.250.192/UCC/"+appInfo.getImage().substring(2);
        imageLoader.get(url, ImageLoader.getImageListener(viewHolder.grid_image_news,
                android.R.drawable.ic_dialog_alert, android.R.drawable
                        .ic_dialog_alert));
        viewHolder.grid_image_news.setImageUrl(url, imageLoader);
        viewHolder.date.setText(appInfo.getDate());

        String alldata =((appInfo.getDescription()).replace("span style=\"color:", "font color=").replace("span style=\"background-color:", "font color=").replace("\"","").replace("</span>", "</font>"));
        viewHolder.description.setText(Html.fromHtml(alldata));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAct.animateActivity(i);
            }
        });

      /*  try {

            Date date1 = simpleDateFormat.parse(appInfo.getDate());
            Date date2 = simpleDateFormat.parse(currentDate);

            viewHolder.date.setText(ClsGeneral.GetDateTimeDifference(date1, date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView grid_text_news,date,description;
        public NetworkImageView grid_image_news;

        public ViewHolder(View itemView) {
            super(itemView);
            grid_text_news = (TextView) itemView.findViewById(R.id.grid_text_news);
            grid_image_news = (NetworkImageView)itemView.findViewById(R.id.grid_image_news);
            date=(TextView)itemView.findViewById(R.id.date);
            description=(TextView)itemView.findViewById(R.id.description);
        }

    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        applications.clear();
        if (charText.length() == 0) {
            applications.addAll(arraylist);
        }
        else
        {
            for (ScienceImageBean wp : arraylist)
            {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    applications.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }





}
