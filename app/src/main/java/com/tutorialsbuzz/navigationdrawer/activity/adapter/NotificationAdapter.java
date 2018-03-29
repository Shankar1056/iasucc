package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tutorialsbuzz.navigationdrawer.activity.Notification;
import com.tutorialsbuzz.navigationdrawer.activity.model.NotificationModel;
import com.ucc.application.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by indglobal on 13/3/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> applications;
    private int rowLayout;
    private Notification mAct;
    private ImageLoader imageLoader;
    private List<NotificationModel> arraylist;
    private String currentDate;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

    public NotificationAdapter(List<NotificationModel> applications, String currentDate, int rowLayout, Notification act) {
        this.applications = applications;
        this.rowLayout = rowLayout;
        this.mAct = act;
        this.arraylist = new ArrayList<NotificationModel>();
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

    public void addApplications(List<NotificationModel> applications, String currentDate) {
        this.applications.addAll(applications);
        this.arraylist.addAll(applications);
        this.currentDate= currentDate;
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final NotificationModel appInfo = applications.get(i);
        viewHolder.textView.setText(appInfo.getTitle());
      //  viewHolder.description.setText(appInfo.getDescription());
        viewHolder.date.setText(appInfo.getDate());

        String alldata =((appInfo.getDescription()).replace("span style=\"color:", "font color=").replace("span style=\"background-color:", "font color=").replace("\"","").replace("</span>", "</font>"));
        viewHolder.description.setText(Html.fromHtml(alldata));


      /*  try {

            Date date1 = simpleDateFormat.parse(appInfo.getDate());
            Date date2 = simpleDateFormat.parse(currentDate);

            viewHolder.date.setText(ClsGeneral.GetDateTimeDifference(date1, date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAct.animateActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,date,description;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.grid_text_news);
            date = (TextView)itemView.findViewById(R.id.date);
            description = (TextView)itemView.findViewById(R.id.description);
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
            for (NotificationModel wp : arraylist)
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
