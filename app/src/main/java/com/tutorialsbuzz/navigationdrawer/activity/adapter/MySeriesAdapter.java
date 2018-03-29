package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.MyTestSeries;
import com.tutorialsbuzz.navigationdrawer.activity.model.MySeriesModel;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igcs-27 on 22/2/16.
 */
public class MySeriesAdapter extends RecyclerView.Adapter<MySeriesAdapter.ViewHolder>
{
boolean status=false;

    private List<MySeriesModel> applications;
    List<MySeriesModel> review;
    MyTestSeries onlineTestSeries;
    ArrayList<MySeriesModel> reviewDetailsItemArrayList = new ArrayList<MySeriesModel>();
    int ratingAvg[]=null;
    private int rowLayout;


    private LayoutInflater layoutInflater;
    private ArrayList<TestSeriesModel> reviewDetailsItem ;

    public MySeriesAdapter(List<MySeriesModel> applications,int rowLayout,MyTestSeries onlinetstserise)
    {
        this.applications=applications;
        this.rowLayout = rowLayout;
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

    public void addApplications(List<MySeriesModel> applications) {
        this.applications.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final MySeriesModel appInfo = applications.get(i);


        viewHolder.paperName.setText(appInfo.getName());
        viewHolder.paperBy.setText(appInfo.getPublisher());
        viewHolder.priceText.setText("Rs. " + appInfo.getPrice());
        viewHolder.orderid.setText("Purchesed Order Id : "+appInfo.getOrder_id());
        viewHolder.price.setText("Rs : "+appInfo.getPrice());
        if (appInfo.getTransaction_status().equalsIgnoreCase("1")){
            viewHolder.priceflag.setBackgroundResource(R.color.button_green);
            viewHolder.priceText.setText("Success");

        }
        else {
            viewHolder.priceflag.setBackgroundResource(R.color.orange);
            viewHolder.priceText.setText("Fail");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onlineTestSeries.animateActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView paperName,paperBy,priceText,orderid,price;
        RatingBar ratingbar;
        private ImageView priceflag;


        public ViewHolder(View convertView) {
            super(convertView);
            paperName = (TextView) convertView.findViewById(R.id.paperName);
            ratingbar=(RatingBar) convertView.findViewById(R.id.seriesitemRating);
            paperBy = (TextView) convertView.findViewById(R.id.paperBy);
            priceText = (TextView) convertView.findViewById(R.id.priceText);
            priceflag = (ImageView)convertView.findViewById(R.id.priceflag);
            orderid=(TextView)convertView.findViewById(R.id.orderid);
            price=(TextView)convertView.findViewById(R.id.price);
        }

    }

    /*class ViewHolder {
        private TextView paperName,no_of_reviews,paperBy,ratingText,priceText;
        RatingBar ratingbar;
        private ImageView priceflag;
        private LinearLayout onlineSeriesLayout;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
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

         status=false;
        String seriesCheck=data.get(position).getId();

        if (reviewDetailsItemArrayList.size()>0)
            reviewDetailsItemArrayList.clear();
        for (int i=0;i<review.size(); i++) {

            if( (data.get(position).getId().equalsIgnoreCase(review.get(i).getId()))) {

                 String recheck=review.get(position).getId();

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
        viewHolder.no_of_reviews.setText(String.valueOf( reviewDetailsItemArrayList.size())+R.string.reviews);
        viewHolder.priceText.setText(data.get(position).getPrice());
        viewHolder.ratingbar.setRating(Float.parseFloat(reviewDetailsItemArrayList.get(position).getRating()));

        String rating=String.valueOf( viewHolder.ratingbar.getRating());

        final ViewHolder finalViewHolder = viewHolder;


        return convertView;


    }*/


}

