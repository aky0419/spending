package com.example.spense.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.spense.R;

//在记录页面弹出时间对话框

public class SelectTimeDialog extends Dialog implements View.OnClickListener {

    EditText hourEt, minuteEt;
    Button cancelBtn, addBtn;
    DatePicker mDatePicker;

    public interface OnEnsureListener{
        public void onEnsure(String time, int year, int month, int day);
    }

    OnEnsureListener mOnEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        mOnEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_etHour);
        minuteEt = findViewById(R.id.dialog_time_etMinute);
        cancelBtn = findViewById(R.id.dialog_time_btnCancel);
        addBtn = findViewById(R.id.dialog_time_btnAdd);
        mDatePicker = findViewById(R.id.dialog_time_dp);

        cancelBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        hideDatePickerHeader();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_time_btnCancel:
            cancel();
            break;

            case R.id.dialog_time_btnAdd:
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth()+1;
                int day = mDatePicker.getDayOfMonth();
                String monthString = String.valueOf(month);
                if (month < 10){
                    monthString = "0" + monthString;
                }
                String dayString = String.valueOf(day);

                if (day<10){
                    dayString = "0" + dayString;
                }
                //获取输入小时和分钟

                String hourStr = hourEt.getText().toString();
                String minuteStr = minuteEt.getText().toString();
                int hour = 0;
                if (!TextUtils.isEmpty(hourStr)) {
                    hour = Integer.parseInt(hourStr);
                    hour = hour % 24;
                }
                int minute = 0;
                if (!TextUtils.isEmpty(minuteStr)) {
                    minute = Integer.parseInt(minuteStr);
                    minute = minute % 60;
                }

                hourStr = String.valueOf(hour);
                if (hour < 10){
                    hourStr = "0" + hourStr;
                }
                minuteStr = String.valueOf(minute);
                if (minute < 10){
                    minuteStr = "0" + minuteStr;
                }

                String timeFormat = year + "-"+monthString + "-" + dayString +" "+hourStr +":"+minuteStr;

            if (mOnEnsureListener != null){
                mOnEnsureListener.onEnsure(timeFormat, year, month, day);
            }
            cancel();
            break;
        }
    }

    //隐藏datepicker头布局
    private void hideDatePickerHeader(){
        ViewGroup root = (ViewGroup) mDatePicker.getChildAt(0);
        if (root == null) return;
        View headerView = root.getChildAt(0);
        if (headerView == null) {
            return;
        }
        // 5.0+
        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            root.setLayoutParams(layoutParams);

            ViewGroup animator = (ViewGroup) root.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
        }

        //6.0+
        headerId = getContext().getResources().getIdentifier("date_picker_header", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }

    }
}
