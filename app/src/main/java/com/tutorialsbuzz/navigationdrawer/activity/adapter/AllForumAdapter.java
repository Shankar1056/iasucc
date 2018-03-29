package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.AllFragment;
import com.tutorialsbuzz.navigationdrawer.activity.model.CommentForum;
import com.ucc.application.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by indglobal on 14/3/16.
 */
public class AllForumAdapter extends RecyclerView.Adapter<AllForumAdapter.ViewHolder> {

    private List<CommentForum> applications;
    private List<CommentForum> commentcnt;
    private int rowLayout;
    private AllFragment mAct;
    private ImageLoader imageLoader;
    boolean state=false;
    Context c;
    private String currentDate;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-M-dd hh:mm:ss");


    public AllForumAdapter(Context c, List<CommentForum> applications, List<CommentForum> commentcnt, String currentDate, int rowLayout, AllFragment act) {
        this.applications = applications;
        this.commentcnt=commentcnt;
        this.rowLayout = rowLayout;
        this.mAct = act;
        this.c=c;
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




    public void addApplications(List<CommentForum> applications, List<CommentForum> commentcnt, String currentDate) {

            this.applications.addAll(applications);
            this.commentcnt.addAll(commentcnt);
            this.currentDate=currentDate;
            this.notifyItemRangeInserted(0, applications.size() - 1);
            this.notifyItemRangeInserted(0, commentcnt.size() - 1);


    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final CommentForum appInfo = applications.get(i);
        final CommentForum cmntcnt = commentcnt.get(i);



        viewHolder.discussion.setText(appInfo.getTopic());
        viewHolder.dueTime.setText(appInfo.getDate());
        viewHolder.postName.setText(appInfo.getCreated_by());

       /* try {

            Date date1 = simpleDateFormat.parse(appInfo.getDate());
            Date date2 = simpleDateFormat.parse(currentDate);

            viewHolder.dueTime.setText(ClsGeneral.GetDateTimeDifference(date1, date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        if (appInfo.getIslikes()!=null) {
            String islike=appInfo.getIslikes();
            String count =appInfo.getLikes();
            if (appInfo.getIslikes().equalsIgnoreCase("true")) {
                viewHolder.likecount.setText(appInfo.getLikes() + " " + mAct.getResources().getString(R.string.liked));
            }
            else if (appInfo.getIslikes().equalsIgnoreCase("false")) {
                viewHolder.likecount.setText(appInfo.getLikes() + " " + mAct.getResources().getString(R.string.like));
            }
        }
        viewHolder.commentcount.setText(cmntcnt.getCommentCount() + " " + mAct.getResources().getString(R.string.comment));
        if (("1").equalsIgnoreCase(appInfo.getFavourite())){
            viewHolder.favourites.setImageDrawable(c.getResources().getDrawable(R.drawable.add2fav));

        }
        if (("0").equalsIgnoreCase(appInfo.getFavourite())){
            viewHolder.favourites.setImageDrawable(mAct.getResources().getDrawable(R.drawable.heart));
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.submenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(c,finalViewHolder.submenu);
                popup.getMenuInflater().inflate(R.menu.forum_submenu, popup.getMenu());
                popup.show();

            }
        });

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    CommentForum model = appInfo;

                    mAct.like(model.getId());


            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAct.senddata(i);
            }
        });

        viewHolder.favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int positin=(Integer) viewHolder.getAdapterPosition();
                CommentForum model = applications.get(positin);
                if( applications.get(positin).getFavourite().equalsIgnoreCase("0"))
                {
                    model.setFavourite("1");
                    mAct.add2fav(applications.get(positin).getId());

                }
                else
                {
                    model.setFavourite("0");
                    mAct.removeFromFav(applications.get(positin).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView discussion;
        private TextView dueTime,postName,likecount,commentcount;
        private ImageView submenu;
        private LinearLayout submenuLayout,comment,like;
        ImageView favourites,liked;

        public ViewHolder(View convertView) {
            super(convertView);

            favourites = (ImageView) convertView.findViewById(R.id.favourites);
            liked = (ImageView) convertView.findViewById(R.id.liked);
               // favourites.setVisibility(View.GONE);
            discussion = (TextView) convertView.findViewById(R.id.discussion);
            dueTime = (TextView) convertView.findViewById(R.id.dueTime);
            postName = (TextView) convertView.findViewById(R.id.postName);
            submenu = (ImageView)convertView.findViewById(R.id.submenu);
            like = (LinearLayout)convertView.findViewById(R.id.like);
            comment = (LinearLayout)convertView.findViewById(R.id.comment);
            likecount = (TextView)convertView.findViewById(R.id.likecount);
            commentcount = (TextView)convertView.findViewById(R.id.commentcount);
        }

    }
}

