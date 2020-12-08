package com.example.spense.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spense.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史账单界面，点击日历表，弹出对话框，当中的GridView对应的适配器
 */

public class CalendarGvAdapter extends BaseAdapter {
    Context mContext;
    List<String> mData;
    public int year;
    public int selectPos = -1;

    public void setYear(int year) {
        this.year = year;
        mData.clear();
        loatData(year);
        notifyDataSetChanged();
    }



    public CalendarGvAdapter(Context context, int year) {
        mContext = context;
        this.year = year;
        mData = new ArrayList<>();
        loatData(year);
    }

    private void loatData(int year) {
        for (int i = 1; i < 13; i++) {
            String each;
            if (i<10){
                each = year + "-0" +i;
            }else {
                each = year + "-" + i;
            }

            mData.add(each);

        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_calendar_gv, parent, false);
        TextView tv = convertView.findViewById(R.id.dialog_gv_tv);
        tv.setText(mData.get(position));
        tv.setBackgroundResource(R.color.grey_f3f3f3);
        tv.setTextColor(Color.BLACK);

        if (selectPos == position){
            tv.setBackgroundResource(R.color.green_006400);
            tv.setTextColor(Color.WHITE);
        }


        return convertView;
    }
}
