package com.example.spense.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spense.DB.DBManager;
import com.example.spense.R;
import com.example.spense.adapter.CalendarGvAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDialog extends Dialog implements View.OnClickListener {
    ImageView backIv;
    GridView gv;
    LinearLayout hsvLayout;

    List<TextView> hsvViewList;
    List<Integer> yearList;

    CalendarGvAdapter calendarGvAdapter;

    int curr_month = -1;

    int selectPos = -1;

    OnRefreshListener mOnRefreshListener;

    public interface OnRefreshListener{
        void onRefresh(int selectPos, int year, int month);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;


    }



    public CalendarDialog(@NonNull Context context, int curr_month, int selectPos) {
        super(context);
        this.curr_month = curr_month;
        this.selectPos = selectPos;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        backIv = findViewById(R.id.dialog_calendar_back_iv);
        gv = findViewById(R.id.dialog_calendar_gv);
        hsvLayout = findViewById(R.id.dialog_calendar_layout);
        backIv.setOnClickListener(this);
        //像横向scrollView中添加View的方法
        addViewToLayout();

        initGridView();

        //设置gridView每个item的点击事件
        setGvListener();
    }

    private void setGvListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calendarGvAdapter.selectPos = position;
                calendarGvAdapter.notifyDataSetInvalidated();
                int month = position+1;
                int year = calendarGvAdapter.year;
                //获取被选中的年份和月份
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh(selectPos, year, month);
                }

                cancel();

            }
        });
    }

    private void initGridView() {
        int curr_year = yearList.get(selectPos);
        calendarGvAdapter = new CalendarGvAdapter(getContext(), curr_year);
        if (curr_month == -1) {
            int month = Calendar.getInstance().get(Calendar.MONTH);
            calendarGvAdapter.selectPos = month;
        }else{
            calendarGvAdapter.selectPos = curr_month -1;
        }

        gv.setAdapter(calendarGvAdapter);

    }

    private void addViewToLayout() {
        hsvViewList = new ArrayList<>();
        yearList = DBManager.getYearInfoFromAccounttb();
        if (yearList.size() == 0) {
            int currYear = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(currYear);
        }

        for (int i = 0; i<yearList.size();i++) {
            Integer year = yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialog_calendar_hsv, null);
            hsvLayout.addView(view);
            TextView hsvTv = view.findViewById(R.id.dialog_hsv_tv);
            hsvTv.setText(year + "");
            hsvViewList.add(hsvTv);
        }

        if (selectPos == -1) {
            selectPos = hsvViewList.size()-1;
        }

        changeTvBg(selectPos);
        setHSVClickListener();


    }

    /**
     * 给横向scrollView中每个textView设置点击事件
     */

    private void setHSVClickListener() {
        for (int i = 0; i < hsvViewList.size(); i++) {
            TextView textView = hsvViewList.get(i);
            final int pos = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTvBg(pos);
                    selectPos = pos;
                    Integer curr_year = yearList.get(selectPos);
                    calendarGvAdapter.setYear(curr_year);

                }
            });
        }
    }

    /**
     * 传入被选中的位置，改变此位置的背景
     * @param selectPos
     */

    private void changeTvBg(int selectPos) {
        for (int i = 0; i < hsvViewList.size(); i++) {
            TextView textView = hsvViewList.get(i);
            textView.setBackgroundResource(R.drawable.dlg_btn_bg);
            textView.setTextColor(Color.BLACK);

        }

        TextView currTv = hsvViewList.get(selectPos);
        currTv.setBackgroundResource(R.drawable.main_recordbtn_bg);
        currTv.setTextColor(Color.WHITE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_calendar_back_iv:
                cancel();
                break;
        }
    }

    public void setDialogSize(){
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = display.getWidth();
        attributes.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
    }
}
