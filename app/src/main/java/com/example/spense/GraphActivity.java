package com.example.spense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spense.DB.DBManager;
import com.example.spense.adapter.GraphVPAdapter;
import com.example.spense.frag_graph.ExpenseGraphFragment;
import com.example.spense.frag_graph.IncomeGraphFragment;
import com.example.spense.utils.CalendarDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    Button expenseBtn, incomeBtn;
    TextView dateTv, expenseTv, incomeTv;
    ViewPager chartVp;
    private int year, month;
    String[] monthEnglish = new String[]{"January","February", "March", "April","May","June","July","August","September","October","November","December"};
    int selectedPos = -1, selectedMonth = -1;

    List<Fragment> mFragments;
    ExpenseGraphFragment expenseGraphFragment;
    IncomeGraphFragment incomeGraphFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        initView();
        initTime();
        initData(year, month);
        initFrag();

        setVPSelectListener();
    }

    private void setVPSelectListener() {
        chartVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setBottonStyle(position);

            }
        });
    }

    private void initFrag() {
        mFragments = new ArrayList<>();
        expenseGraphFragment = new ExpenseGraphFragment();
        incomeGraphFragment = new IncomeGraphFragment();

        //add data to fragment
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        incomeGraphFragment.setArguments(bundle);
        expenseGraphFragment.setArguments(bundle);
        //add fragment to list
        mFragments.add(expenseGraphFragment);
        mFragments.add(incomeGraphFragment);

        // use viewPager adapter
        GraphVPAdapter graphVPAdapter = new GraphVPAdapter(getSupportFragmentManager(), 0, mFragments);
        chartVp.setAdapter(graphVPAdapter);


    }

    /**
     * 初始化时间
     */

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 吃实话某年某月收支情况
     * @param year
     * @param month
     */
    private void initData(int year, int month) {
        float monthlyExpense = DBManager.getMonthlySummary(year, month, 0);
        float monthlyIncome = DBManager.getMonthlySummary(year, month, 1);
        int monthlyExpenseCount = DBManager.getMonthlyCount(year, month, 0);
        int monthlyIncomeCount = DBManager.getMonthlyCount(year, month, 1);

        dateTv.setText(monthEnglish[month-1] +" "+year + " Bill");
        if (monthlyExpenseCount>1){
            expenseTv.setText("Total "+monthlyExpenseCount+" Spend Records: $"+monthlyExpense);
        }else {
            expenseTv.setText("Total "+monthlyExpenseCount+" Spend Record: $"+monthlyExpense);
        }

        if (monthlyIncomeCount>1){
            incomeTv.setText("Total "+monthlyIncomeCount+" Income Records: $"+monthlyIncome);
        }else {
            incomeTv.setText("Total "+monthlyIncomeCount+" Income Record: $"+monthlyIncome);
        }



    }

    private void initView() {
        expenseBtn = findViewById(R.id.graph_expense_btn);
        incomeBtn = findViewById(R.id.graph_income_btn);
        dateTv = findViewById(R.id.graph_tv_date);
        expenseTv = findViewById(R.id.graph_tv_expense);
        incomeTv = findViewById(R.id.graph_tv_income);
        chartVp = findViewById(R.id.graph_vp);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graph_calendar:
                showCalendarDialog();
                break;
            case R.id.graph_iv_back:
                finish();
                break;
            case R.id.graph_expense_btn:
                setBottonStyle(0);
                chartVp.setCurrentItem(0);
                break;
            case R.id.graph_income_btn:
                setBottonStyle(1);
                chartVp.setCurrentItem(1);
                break;

        }
    }

    private void showCalendarDialog() {
        CalendarDialog dialog = new CalendarDialog(this, selectedMonth, selectedPos);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
            @Override
            public void onRefresh(int selectPos, int year, int month) {
                selectedPos = selectPos;
                selectedMonth = month;
                initData(year, month);
                incomeGraphFragment.setDate(year, month);
                expenseGraphFragment.setDate(year, month);

            }
        });
    }

    /**
     * 设置按钮样式改变
     * 支出 0； 收入 1
     */
    public void setBottonStyle(int kind){
        if (kind == 0) {
            expenseBtn.setBackgroundResource(R.drawable.main_recordbtn_bg);
            expenseBtn.setTextColor(Color.WHITE);
            incomeBtn.setBackgroundResource(R.drawable.dlg_btn_bg);
            incomeBtn.setTextColor(Color.BLACK);
        }else{
            incomeBtn.setBackgroundResource(R.drawable.main_recordbtn_bg);
            incomeBtn.setTextColor(Color.WHITE);
            expenseBtn.setBackgroundResource(R.drawable.dlg_btn_bg);
            expenseBtn.setTextColor(Color.BLACK);
        }
    }
}
