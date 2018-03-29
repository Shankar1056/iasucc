package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.OnlineTestSeries;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by igcs-27 on 30/1/16.
 */
public class OnlineSeriesAdapter extends RecyclerView.Adapter<OnlineSeriesAdapter.ViewHolder>
{
    private List<TestSeriesModel> applications;
    List<TestSeriesModel> review;
    OnlineTestSeries onlineTestSeries;
     ArrayList<TestSeriesModel> reviewDetailsItemArrayList = new ArrayList<TestSeriesModel>();
    int ratingAvg[]=null;
    private int rowLayout;
    TestSeriesModel testSeriesModel;
    double average;
    public OnlineSeriesAdapter(List<TestSeriesModel> applications,List<TestSeriesModel> review,int rowLayout,OnlineTestSeries onlinetstserise)
    {
        this.applications=applications;
        this.rowLayout = rowLayout;
        this.review=review;
        this.onlineTestSeries=onlinetstserise;


    }


    public void clearApplications() {
        int size = this.applications.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                applications.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addApplications(List<TestSeriesModel> applications,List<TestSeriesModel> review) {
        this.applications.addAll(applications);
        this.review = review;
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final TestSeriesModel appInfo = applications.get(i);
        TestSeriesModel reviewinfo = null;
        if (review.size()>0) {
              reviewinfo = review.get(i);
        }

        /*imageLoader = CustomVolleyRequest.getInstance(mAct.getApplicationContext())
                .getImageLoader();
        String url="http://63.142.250.192/UCC/"+appInfo.getImage().substring(2);
        imageLoader.get(url, ImageLoader.getImageListener(viewHolder.grid_image_news,
                R.drawable.splash, android.R.drawable
                        .ic_dialog_alert));
        viewHolder.grid_image_news.setImageUrl(url, imageLoader);*/


        if (reviewDetailsItemArrayList.size()>0)
            reviewDetailsItemArrayList.clear();
        for (int j=0;j<review.size(); j++) {
            if (reviewinfo.getId().equalsIgnoreCase(review.get(i).getId())) {
                testSeriesModel=review.get(j);
                reviewDetailsItemArrayList.add(testSeriesModel);

            }
        }


        ratingAvg = new int[reviewDetailsItemArrayList.size()];
        Arrays.fill(ratingAvg, -1);

        for (int k=0;k<reviewDetailsItemArrayList.size();k++)
            ratingAvg[k]=Integer.parseInt(reviewDetailsItemArrayList.get(k).getRating());


        int sum = 0;

        for(int l=0; l < ratingAvg.length ; l++)
            sum = sum + ratingAvg[l];

        //calculate average value
        if (ratingAvg.length>0) {
             average = sum / ratingAvg.length;
            viewHolder.ratingbar.setRating(Float.parseFloat(reviewDetailsItemArrayList.get(i).getRating()));
        }



        viewHolder.paperName.setText(appInfo.getName());
        viewHolder.ratingText.setText(String.valueOf(Float.parseFloat(String.valueOf(average))));
        viewHolder.paperBy.setText(appInfo.getPublisher());
        viewHolder.no_of_reviews.setText(String.valueOf(reviewDetailsItemArrayList.size()) + " " + onlineTestSeries.getResources().getString(R.string.reviewsno));
        viewHolder.priceText.setText("Rs. " + appInfo.getPrice());





        String rating=String.valueOf( viewHolder.ratingbar.getRating());



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlineTestSeries.animateActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView paperName,no_of_reviews,paperBy,ratingText,priceText;
        RatingBar ratingbar;
        private ImageView priceflag;
        private LinearLayout onlineSeriesLayout;

        public ViewHolder(View convertView) {
            super(convertView);
            paperName = (TextView) convertView.findViewById(R.id.paperName);
            ratingbar=(RatingBar) convertView.findViewById(R.id.seriesitemRating);
            ratingText = (TextView) convertView.findViewById(R.id.ratingText);
            paperBy = (TextView) convertView.findViewById(R.id.paperBy);
            no_of_reviews = (TextView) convertView.findViewById(R.id.no_of_reviews);
            priceText = (TextView) convertView.findViewById(R.id.priceText);
            onlineSeriesLayout=(LinearLayout) convertView.findViewById(R.id.onlineSeriesLayout);
            priceflag = (ImageView)convertView.findViewById(R.id.priceflag);
        }

    }
}

    /*

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if(convertView == null){
           // LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.online_testseries_listrow, null);
            viewHolder = new ViewHolder();
            viewHolder.paperName = (TextView) convertView.findViewById(R.id.paperName);
            viewHolder.ratingbar=(RatingBar) convertView.findViewById(R.id.seriesitemRating);
            viewHolder.ratingText = (TextView) convertView.findViewById(R.id.ratingText);
            viewHolder.paperBy = (TextView) convertView.findViewById(R.id.paperBy);
            viewHolder.no_of_reviews = (TextView) convertView.findViewById(R.id.no_of_reviews);
            viewHolder.priceText = (TextView) convertView.findViewById(R.id.priceText);
            viewHolder.onlineSeriesLayout=(LinearLayout) convertView.findViewById(R.id.onlineSeriesLayout);
            viewHolder.priceflag = (ImageView)convertView.findViewById(R.id.priceflag);

            convertView.setTag(viewHolder);

            convertView.setTag(R.id.paperName, viewHolder.paperName);
            convertView.setTag(R.id.seriesitemRating,viewHolder.ratingbar);
            convertView.setTag(R.id.ratingText, viewHolder.ratingText);
            convertView.setTag(R.id.paperBy,viewHolder.paperBy);
            convertView.setTag(R.id.no_of_reviews, viewHolder.no_of_reviews);
            convertView.setTag(R.id.priceText,viewHolder.priceText);
            convertView.setTag(R.id.onlineSeriesLayout, viewHolder.onlineSeriesLayout);
            convertView.setTag(R.id.priceflag,viewHolder.priceflag);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.paperName.setTag(position);
        viewHolder.ratingbar.setTag(position);
        viewHolder.ratingText.setTag(position);
        viewHolder.paperBy.setTag(position);
        viewHolder.no_of_reviews.setTag(position);
        viewHolder.priceText.setTag(position);
        viewHolder.onlineSeriesLayout.setTag(position);
        viewHolder.priceflag.setTag(position);



        if (reviewDetailsItemArrayList.size()>0)
        reviewDetailsItemArrayList.clear();
        for (int i=0;i<review.size(); i++) {
            if (data.get(position).getId().equalsIgnoreCase(review.get(i).getId())) {
                testSeriesModel=review.get(i);
                reviewDetailsItemArrayList.add(testSeriesModel);

            }
        }


        ratingAvg = new int[reviewDetailsItemArrayList.size()];
        Arrays.fill(ratingAvg, -1);

        for (int i=0;i<reviewDetailsItemArrayList.size();i++)
            ratingAvg[i]=Integer.parseInt(reviewDetailsItemArrayList.get(i).getRating());


        int sum = 0;

        for(int i=0; i < ratingAvg.length ; i++)
            sum = sum + ratingAvg[i];

        //calculate average value
        double average = sum / ratingAvg.length;

        viewHolder.paperName.setText(data.get(position).getName());
        viewHolder.ratingText.setText(String.valueOf(Float.parseFloat(String.valueOf(average))));
        viewHolder.paperBy.setText(data.get(position).getPublisher());
        viewHolder.no_of_reviews.setText(String.valueOf(reviewDetailsItemArrayList.size())+" "+c.getResources().getString(R.string.reviewsno));
        viewHolder.priceText.setText("Rs. "+data.get(position).getPrice());


        viewHolder.ratingbar.setRating(Float.parseFloat(reviewDetailsItemArrayList.get(position).getRating()));




        String rating=String.valueOf( viewHolder.ratingbar.getRating());

        final ViewHolder finalViewHolder = viewHolder;


        return convertView;

    }


}*/

