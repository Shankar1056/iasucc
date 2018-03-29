package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.ReviewsDetailsAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;

/**
 * Created by indglobal38 on 28/1/16.
 */
public class ReviewsDetailsFragment extends Fragment {


    private ListView mListReviewDetails;
    private TestSeriesModel testSeriesModel;
    private ReviewsDetailsAdapter reviewsDetailsAdapter;
    private ArrayList<TestSeriesModel> reviewDetailsItemArrayList = new ArrayList<TestSeriesModel>();

    ArrayList<TestSeriesModel> testreviewlist,testSerieslist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_details,container,false);

       // new GetReviewsDetailsListItems().execute();
        testreviewlist=(ArrayList<TestSeriesModel>) getActivity().getIntent().getSerializableExtra("testreviewlist");
        testSerieslist=(ArrayList<TestSeriesModel>) getActivity().getIntent().getSerializableExtra("testSerieslist");
        int position=getActivity().getIntent().getExtras().getInt("position");


        for (int i=0;i<testreviewlist.size();i++)
        {
            if (testSerieslist.get(position).getId().equalsIgnoreCase(testreviewlist.get(i).getId()))
            {
                testSeriesModel=testreviewlist.get(i);
                reviewDetailsItemArrayList.add(testSeriesModel);
            }
        }

        mListReviewDetails = (ListView) view.findViewById(R.id.list_reviews_details);
        reviewsDetailsAdapter = new ReviewsDetailsAdapter(getActivity(), reviewDetailsItemArrayList,position );
        mListReviewDetails.setAdapter(reviewsDetailsAdapter);


        return view;

    }




}