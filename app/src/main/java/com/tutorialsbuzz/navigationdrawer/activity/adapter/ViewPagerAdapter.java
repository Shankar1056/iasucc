package com.tutorialsbuzz.navigationdrawer.activity.adapter;

/**
 * Created by indglobal on 28/1/16.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.InfoFragment;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.ReviewsDetailsFragment;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.SeriesContentFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    CharSequence Titles[] ;
    int NumbOfTabs = 3;
    Context context;

    // Build a Constructor and assign the passed Values to appropriate values in the class

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
      //  CharSequence Titles[] =getResources().getStringArray(R.array.series_item_description);
       // this.Titles = titles;
//        this.NumbOfTabs = mNumbOfTabsumb;
this.context=context;

     Titles=context.getResources().getStringArray(R.array.series_item_description);
    }

    @Override
    public Fragment getItem(int position) {
        ReviewsDetailsFragment reviewsDetailsFragment;

        switch (position) {

            case 0:

                InfoFragment infoFragmentTab1 = new InfoFragment();
                return infoFragmentTab1;


            case 1:

                SeriesContentFragment infoFragmentTab2 = new SeriesContentFragment();
                return infoFragmentTab2;





            case 2:

                reviewsDetailsFragment = new ReviewsDetailsFragment();
                return reviewsDetailsFragment;

        }
        return null;

    }



    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
