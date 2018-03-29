package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;

/**
 * Created by indglobal38 on 28/1/16.
 */
public class InfoFragment extends Fragment {

    ArrayList<TestSeriesModel> testSerieslist;
TextView _name,info_description,byucc,price_ucc_text,How_it_works;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);


        testSerieslist=(ArrayList<TestSeriesModel>) getActivity().getIntent().getSerializableExtra("testSerieslist");


        int position=getActivity().getIntent().getExtras().getInt("position");
        _name=(TextView)view.findViewById(R.id._name);
        price_ucc_text=(TextView)view.findViewById(R.id.price_ucc_text);
        info_description=(TextView)view.findViewById(R.id.info_description);
        byucc=(TextView)view.findViewById(R.id.byucc);
        How_it_works=(TextView)view.findViewById(R.id.How_it_works);

       _name.setText(testSerieslist.get(position).getName());
        info_description.setText(testSerieslist.get(position).getDescription());
        price_ucc_text.setText("Rs." +(testSerieslist.get(position).getPrice()));
        How_it_works.setText((testSerieslist.get(position).getHow_it_works()));
        return view;

    }



}