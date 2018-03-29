package com.tutorialsbuzz.navigationdrawer.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.adapter.ViewPagerAdapter;
import com.tutorialsbuzz.navigationdrawer.activity.common.SlidingTabLayout;

/**
 * Created by indglobal on 14/3/16.
 */
public class AllThreeFragment extends Fragment {

    private ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout mSlidingTabs;

    GridView homegridview;

    ViewPager mPagerCartyMenu;
    SlidingTabLayout tabs;
    int Numboftabs =3;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all_threefragment, container, false);


        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),getActivity());
        mPagerCartyMenu = (ViewPager) view.findViewById(R.id.pager_carty_menu);
        mPagerCartyMenu.setAdapter(viewPagerAdapter);

        mSlidingTabs = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs_carty_menu);
        // mSlidingTabs.setDistributeEvenly(true);

        mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primary);
            }
        });
        mSlidingTabs.setViewPager(mPagerCartyMenu);



        return view;
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position){

            }
            return null;
        }

        @Override
        public int getCount() {

            return Numboftabs;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return getActivity().getResources().getString( R.string.info);
                case 1 :
                    return getActivity().getResources().getString( R.string.series_content);
                case 2 :
                    return getActivity().getResources().getString( R.string.reviews_tab);
            }
            return null;
        }
    }

}
