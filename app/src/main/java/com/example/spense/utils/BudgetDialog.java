package com.example.spense.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.spense.R;

public class BudgetDialog extends Dialog implements View.OnClickListener{

    ImageView cancelIv;
    EditText budgetEt;
    Button addBtn;
    OnEnsureListener mOnEnsureListener;

    public interface OnEnsureListener{
        void onEnsure(float money);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        mOnEnsureListener = onEnsureListener;
    }

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_budget);
        cancelIv = findViewById(R.id.dialog_add_budget_back);
        budgetEt = findViewById(R.id.dialog_set_budget_et);
        addBtn = findViewById(R.id.dialog_set_budget_btn);

        addBtn.setOnClickListener(this);
        cancelIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_set_budget_btn:
                String money = budgetEt.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(getContext(), "Can not enter empty value!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float budget = Float.parseFloat(money);
                if (budget <= 0) {
                    Toast.makeText(getContext(), "Budget must be greater than 0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mOnEnsureListener != null) {
                    mOnEnsureListener.onEnsure(budget);
                }
                cancel();
                break;

            case R.id.dialog_add_budget_back:
                cancel();
                break;
        }
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
