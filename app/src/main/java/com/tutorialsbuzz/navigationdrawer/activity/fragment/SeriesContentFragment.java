package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.SeriesAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.model.SeriesContentItem;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;

/**
 * Created by indglobal38 on 28/1/16.
 */
public class SeriesContentFragment extends Fragment {

    SeriesContentItem seriesContentItem;
    ArrayList<TestSeriesModel> reviewDetailsItemArrayList = new ArrayList<TestSeriesModel>();
    ListView list_reviews_details;
    TestSeriesModel testSeriesModel;
    ArrayList<TestSeriesModel> testSerieslist,testreviewlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_details, container, false);
        list_reviews_details=(ListView)view.findViewById(R.id.list_reviews_details);

        testSerieslist=(ArrayList<TestSeriesModel>) getActivity().getIntent().getSerializableExtra("testSerieslist");


        testreviewlist=(ArrayList<TestSeriesModel>) getActivity().getIntent().getSerializableExtra("testreviewlist");

        int position=getActivity().getIntent().getExtras().getInt("position");





        SeriesAdapter adapter=new SeriesAdapter(getActivity(),testSerieslist,position);
        list_reviews_details.setAdapter(adapter);

        return view;
    }


}
