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
import com.tutorialsbuzz.navigationdrawer.activity.model.CommentForum;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/1/2016.
 */


    public class CommentOnForumAdapter extends BaseAdapter {


    Context context;
    ArrayList<CommentForum> commentList,commentcount;
   int i=0;


    public CommentOnForumAdapter(Context context, ArrayList<CommentForum> commentList, ArrayList<CommentForum> commentcnt) {
        this.context = context;
        this.commentList = commentList;
       // this.commentcount=commentcount;

    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder {
        private TextView singleMessage, postName, dueTime;
        private TextView likecount,commentcount;

        private ImageView profile_image;
        private LinearLayout onlineSeriesLayout;
        private LinearLayout submenuLayout,comment,like;
        ImageView favourites,liked;
        private ImageView submenu;

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
            viewHolder.submenu = (ImageView)convertView.findViewById(R.id.submenu);
            viewHolder.like = (LinearLayout)convertView.findViewById(R.id.like);
            viewHolder.comment = (LinearLayout)convertView.findViewById(R.id.comment);
            viewHolder.likecount = (TextView)convertView.findViewById(R.id.likecount);
            viewHolder.commentcount = (TextView)convertView.findViewById(R.id.commentcount);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(R.id.submenu, viewHolder.submenu);
        convertView.setTag(R.id.likecount, viewHolder.likecount);
        convertView.setTag(R.id.commentcount, viewHolder.commentcount);
        viewHolder.singleMessage.setText(commentList.get(position).getReply());
        viewHolder.postName.setText(commentList.get(position).getCreated_by_reply());
        viewHolder.dueTime.setText(commentList.get(position).getDate_reply());
        return convertView;

    }



}
