package com.tutorialsbuzz.navigationdrawer.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by indglobal on 12/25/2015.
 */
public class QuestionCountAdapter extends BaseAdapter
{

    Context context;

    ArrayList<QuestionDataBean> data;
    boolean status;
    Date currentDate;
    ArrayList<String> days,queListCount;
    public QuestionCountAdapter(Context context, boolean status, ArrayList<String> queListCount, ArrayList<String> days, Date currentDate)
    {
        this.context=context;
        this.currentDate=currentDate;

        this.status=status;
        this.days=days;
        this.queListCount=queListCount;

    }
    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    class ViewHolder {
        private TextView textViewQuestionCount;
        private TextView textViewMonth,textViewDate,textViewYear;
        ProgressBar progressBar;
        LinearLayout progressLayout;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.date_list_row, null);
            viewHolder = new ViewHolder();

            viewHolder.textViewQuestionCount = (TextView) convertView.findViewById(R.id.QuestionData);
            viewHolder.textViewMonth = (TextView) convertView.findViewById(R.id.Month);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.Date);
            viewHolder.textViewYear = (TextView) convertView.findViewById(R.id.Year);
            viewHolder.progressBar=(ProgressBar) convertView.findViewById(R.id.pbLoading);
            viewHolder.progressLayout=(LinearLayout) convertView.findViewById(R.id.progressLayout);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String rawDate=days.get(position);
        String formattedDate[]= rawDate.split("\\s");
        viewHolder.textViewMonth.setText(formattedDate[1]);
        viewHolder.textViewDate.setText(formattedDate[0]);
        viewHolder.textViewYear.setText(formattedDate[2]);
        if(status==false)
            viewHolder.progressLayout.setVisibility(View.VISIBLE);
        else {

            if (queListCount.get(position).equals("0"))
                viewHolder.textViewQuestionCount.setText(context.getResources().getString(R.string.no_ques_avail));
            else
                viewHolder.textViewQuestionCount.setText(context.getResources().getString(R.string.new_questions )+" "+ queListCount.get(position));
        }
        return convertView;

    }
}