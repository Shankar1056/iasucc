package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.common.GraphicsUtil;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.HomeFragment;
import com.tutorialsbuzz.navigationdrawer.activity.model.BannerModel;

import java.util.ArrayList;

/**
 * Created by indglobal on 26/3/16.
 */
public class BannerPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<BannerModel> bannerNews;
    GraphicsUtil graphicsUtil;
    private ImageLoader imageLoader;
    HomeFragment homeFragment;


    public BannerPagerAdapter(Context context, ArrayList<BannerModel> bannerNews,
                             GraphicsUtil graphicsUtil,HomeFragment homeFragment) {
        this.context = context;
        this.bannerNews = bannerNews;
        this.graphicsUtil = graphicsUtil;
        this.homeFragment=homeFragment;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return bannerNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // Declare Variables
        ImageView bannerImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.bannerpager_adapter, container,
                false);

        bannerImage = (ImageView) itemView.findViewById(R.id.bannerImage);
       Picasso.with(context).load("http://63.142.250.192/UCC/"+bannerNews.get(position).getImage()).into(bannerImage);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
              int pos=position;
                    homeFragment.senddata(position);

            }
        });

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
