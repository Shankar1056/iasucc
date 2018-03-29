package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tutorialsbuzz.navigationdrawer.activity.NotificationDescription;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.NotificationDescriptionPagerFragment;
import com.tutorialsbuzz.navigationdrawer.activity.model.NotificationModel;

import java.util.ArrayList;

/**
 * Created by indglobal on 11/4/16.
 */
public class NotificationDescriptionPagerAdapter extends FragmentStatePagerAdapter {

    protected Context mContext;

    ArrayList<NotificationModel> arrayList;
    int total_length;
    private String currentDate;

    public NotificationDescriptionPagerAdapter(NotificationDescription context, FragmentManager fm, ArrayList<NotificationModel> arrayList, int total_length, String currentDate) {
        super(fm);
        mContext = context;
        this.arrayList=arrayList;
        this.total_length=total_length;
        this.currentDate=currentDate;

    }

    @Override

    public Fragment getItem(int position) {

        Fragment fragment = new NotificationDescriptionPagerFragment();
        Bundle args = new Bundle();

        args.putString("title",arrayList.get(position).getTitle());
        args.putString("description",arrayList.get(position).getDescription());
        args.putString("date",arrayList.get(position).getDate());
        args.putInt("pos", position);
        args.putInt("total_length",total_length);
        args.putString("currentDate",currentDate);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

}