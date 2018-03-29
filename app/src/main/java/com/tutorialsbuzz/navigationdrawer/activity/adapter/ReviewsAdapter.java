package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.model.ReviewOnPaper;

import java.util.ArrayList;

/**
 * Created by igcs-27 on 10/2/16.
 */
public class ReviewsAdapter extends BaseAdapter {


    Context context;
    ArrayList<ReviewOnPaper> reviewsList;
    int i=0;


    public ReviewsAdapter(Context context, ArrayList<ReviewOnPaper> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
        // this.commentcount=commentcount;

    }

    @Override
    public int getCount() {
        return reviewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder {
        private TextView singleMessage, postName, dueTime;
        private ImageView profile_image;
        private LinearLayout onlineSeriesLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        i++;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.comment_on_forum_listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.singleMessage = (TextView) convertView.findViewById(R.id.singleMessage);
            viewHolder.postName = (TextView) convertView.findViewById(R.id.postName);
            viewHolder.dueTime = (TextView) convertView.findViewById(R.id.dueTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.singleMessage.setText(reviewsList.get(position).getPapername());
        viewHolder.postName.setText(reviewsList.get(position).getCreated_by());
        viewHolder.dueTime.setText(reviewsList.get(position).getReview_count());


        return convertView;

    }



}
