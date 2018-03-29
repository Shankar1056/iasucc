package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;

/**
 * Created by indglobal38 on 28/1/16.
 */
public class ReviewsDetailsAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<TestSeriesModel> testSerieslist;
    private TestSeriesModel testSeriesModel;
    private static LayoutInflater mInflater;
    int pos;

    public ReviewsDetailsAdapter(Activity activity, ArrayList<TestSeriesModel> testSerieslist, int pos)
    {
        this.mActivity = activity;
        this.testSerieslist = testSerieslist;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return testSerieslist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        TextView reviewed_by, comboDetailsCartCount, rating_text, reviews_details_desc, circularCartCount;
        TextView comboTotalPrice, comboPrice;
        ImageView image_cart_count;

        RatingBar vendor_rating;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;

        if(convertView == null){
            view = mInflater.inflate(R.layout.reviews_details_item, null);
            viewHolder = new ViewHolder();

            viewHolder.rating_text = (TextView) view.findViewById(R.id.rating_text);
            viewHolder.reviews_details_desc = (TextView) view.findViewById(R.id.reviews_details_desc);
            viewHolder.image_cart_count = (ImageView) view.findViewById(R.id.image_cart_count);
            viewHolder.reviewed_by = (TextView) view.findViewById(R.id.reviewed_by);
            viewHolder.rating_text = (TextView) view.findViewById(R.id.rating_text);
            viewHolder.vendor_rating=(RatingBar)view.findViewById(R.id.vendor_rating);

            view.setTag(viewHolder);
            view.setTag(R.id.reviewed_by, viewHolder.reviewed_by);
            view.setTag(R.id.reviews_details_desc, viewHolder.comboDetailsCartCount);
            view.setTag(R.id.image_cart_count, viewHolder.image_cart_count);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(testSerieslist.size() <= 0){

        }else{
            testSeriesModel = testSerieslist.get(position);
            viewHolder.rating_text.setText( String.valueOf(Float.parseFloat(testSerieslist.get(position).getRating())));
            viewHolder.reviewed_by.setText(testSerieslist.get(position).getReviewed_by());
            viewHolder.reviews_details_desc.setText(testSerieslist.get(position).getReview());
            viewHolder.reviewed_by.setText(testSerieslist.get(position).getReviewed_by());
            viewHolder.vendor_rating.setRating(Float.parseFloat(testSerieslist.get(position).getRating()));
            Bitmap icon = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.national);

          //  viewHolder.comboImageCartCount.setImageBitmap(icon);



        }

        return view;
    }
}