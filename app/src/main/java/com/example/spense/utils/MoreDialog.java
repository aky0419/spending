package com.example.spense.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.spense.AboutActivity;
import com.example.spense.GraphActivity;
import com.example.spense.HistoryActivity;
import com.example.spense.R;
import com.example.spense.SettingActivity;

public class MoreDialog extends Dialog implements View.OnClickListener{

    Button aboutBtn, settingBtn, historyBtn, detailsBtn;
    ImageView backIv;


    public MoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        backIv = findViewById(R.id.dialog_more_back);
        aboutBtn = findViewById(R.id.dialog_more_about);
        settingBtn = findViewById(R.id.dialog_more_setting);
        historyBtn = findViewById(R.id.dialog_more_history);
        detailsBtn = findViewById(R.id.dialog_more_details);

        backIv.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        detailsBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.dialog_more_back:

                break;

            case R.id.dialog_more_about:
                intent.setClass(getContext(), AboutActivity.class);
                getContext().startActivity(intent);
                break;

            case R.id.dialog_more_setting:
                intent.setClass(getContext(), SettingActivity.class);
                getContext().startActivity(intent);
                break;

            case R.id.dialog_more_history:
                intent.setClass(getContext(), HistoryActivity.class);
                getContext().startActivity(intent);
                break;

            case R.id.dialog_more_details:
                intent.setClass(getContext(), GraphActivity.class);
                getContext().startActivity(intent);
                break;

        }
        cancel();
    }

    public void setDialogSize(){
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = display.getWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
    }
}
