package com.example.spense.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.spense.R;


public class NoteDialog extends Dialog implements View.OnClickListener {
    EditText mEditText;
    Button cancelBtn, addBtn;
    OnEnsureListener mOnEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        mOnEnsureListener = onEnsureListener;
    }

    public NoteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note); //设置对话框显示布局
        mEditText = findViewById(R.id.dialog_note_et);
        cancelBtn = findViewById(R.id.dialog_note_cancelBtn);
        addBtn = findViewById(R.id.dialog_note_addBtn);

        cancelBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

    }

    public interface OnEnsureListener{
        void onEnsure();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_note_cancelBtn:
                cancel();
                break;
            case R.id.dialog_note_addBtn:
                if (mOnEnsureListener != null){
                    mOnEnsureListener.onEnsure();
                }
                break;
        }
    }

    public String getEtContent(){
        return mEditText.getText().toString().trim();
    }

    public void setDialogSize(){
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = display.getWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
        mHandler.sendEmptyMessageDelayed(1, 100);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        }
    };
}
