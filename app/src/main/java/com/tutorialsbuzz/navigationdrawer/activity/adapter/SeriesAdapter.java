package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;

import java.util.ArrayList;

/**
 * Created by indglobal on 29/1/16.
 */
public class SeriesAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<TestSeriesModel> mCartCountItemArrayList;
    private TestSeriesModel mCartCountItem;
    private static LayoutInflater mInflater;
    int pos;
    public SeriesAdapter(Activity activity, ArrayList<TestSeriesModel> cartCountItemArrayList, int pos){
        this.mActivity = activity;
        this.mCartCountItemArrayList = cartCountItemArrayList;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pos=pos;
    }


    @Override
    public int getCount() {
        return 1;
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
        TextView seriesText;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;

        if(convertView == null){
            view = mInflater.inflate(R.layout.series_adapter, null);
            viewHolder = new ViewHolder();

            viewHolder.seriesText = (TextView) view.findViewById(R.id.seriesText);



            view.setTag(viewHolder);
            view.setTag(R.id.seriesText, viewHolder.seriesText);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(mCartCountItemArrayList.size() <= 0){

        }else{
            mCartCountItem = mCartCountItemArrayList.get(pos);
            viewHolder.seriesText.setText(mCartCountItem.getTable_of_content());


        }

        return view;
    }
}