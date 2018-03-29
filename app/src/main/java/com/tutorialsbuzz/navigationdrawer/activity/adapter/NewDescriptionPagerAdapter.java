package com.tutorialsbuzz.navigationdrawer.activity.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tutorialsbuzz.navigationdrawer.activity.NewsDescription;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.NewsDescriptionPagerFragment;
import com.tutorialsbuzz.navigationdrawer.activity.model.ScienceImageBean;

import java.util.ArrayList;

/**
 * Created by indglobal on 12/25/2015.
 */
public class NewDescriptionPagerAdapter extends FragmentStatePagerAdapter {

    protected Context mContext;

    ArrayList<ScienceImageBean> arrayList;
    int total_length;
    String currentDate;

    public NewDescriptionPagerAdapter(NewsDescription context, FragmentManager fm, ArrayList<ScienceImageBean> arrayList, int total_length, String currentDate) {
        super(fm);
        mContext = context;
        this.arrayList=arrayList;
        this.total_length=total_length;
        this.currentDate=currentDate;

    }

    @Override

    public Fragment getItem(int position) {

        Fragment fragment = new NewsDescriptionPagerFragment();
        Bundle args = new Bundle();

        args.putString("title",arrayList.get(position).getTitle());
        args.putString("image",arrayList.get(position).getImage());
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