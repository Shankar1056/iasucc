package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.common.GraphicsUtil;
import com.tutorialsbuzz.navigationdrawer.activity.model.NewReleaseModel;

import java.util.ArrayList;

/**
 * Created by indglobal on 12/3/16.
 */
public class SlidePagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<NewReleaseModel> bannerNews;
    GraphicsUtil graphicsUtil;
    private ImageLoader imageLoader;


    public SlidePagerAdapter(Context context, ArrayList<NewReleaseModel> bannerNews,
                           GraphicsUtil graphicsUtil) {
        this.context = context;
        this.bannerNews = bannerNews;
        this.graphicsUtil = graphicsUtil;

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
        NetworkImageView imgNewsImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.sliderpager_adapter, container,
                false);

        imgNewsImage = (NetworkImageView) itemView.findViewById(R.id.imgNewsImage);
//        imgNewsImage.setScaleType(ImageView.ScaleType.FIT_XY);
//
//        imgNewsImage.setTag(bannerNews.get(position));
       /* imageLoader.DisplayImage(bannerNews.get(position),
                imgNewsImage, graphicsUtil);
*/

//        imageLoader.get("", ImageLoader.getImageListener(imgNewsImage,
//                R.drawable.splash, android.R.drawable
//                        .ic_dialog_alert));
//        imgNewsImage.setImageUrl("", imageLoader);

        TextView txtTitleBannerNews = (TextView) itemView
                .findViewById(R.id.txtTitleBannerNews);
        txtTitleBannerNews.setText(bannerNews.get(position).getNews_title());

		/*imgNewsImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bannerNews.size() > 0) {
					Intent intent = new Intent(context,
							NewsDetailActivity.class);
					intent.putExtra("NEWSFEED", bannerNews.get(position));
					context.startActivity(intent);
				}
			}
		});*/

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
