package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucc.application.R;


/**
 * Created by indglobal on 11/21/2015.
 */
public class ShowWeek extends Fragment {


    View view;
   // ListView dateList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // items=new ArrayList<String>();
        view=inflater.inflate(R.layout.show_week, container, false);
        return view;

    }


}
