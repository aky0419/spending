package com.example.spense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spense.DB.AccountBean;
import com.example.spense.DB.DBManager;
import com.example.spense.adapter.AccountAdapter;
import com.example.spense.utils.BudgetDialog;
import com.example.spense.utils.MoreDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * App知识点：
 * 1.绘制布局，掌握android基本view控件的属性和使用
 * 2.熟练掌握activity页面展示，跳转和传值
 * 3.使用碎片加载界面，滑动试图切换页面
 * 4.自定义对话框
 * 5.自定义软键盘的绘制
 * 6.列表视图以及网络试图的适配器使用和页面加载
 * 7.使用android自带数据库，熟练创建表，并进行增删改查
 * 8.定义drawable文件，设定布局以及控件样式
 * 9.使用MPAndroidChart第三方框架绘制柱状图
 *
 */
public class MainActivity extends AppCompatActivity{

    ListView mainLv;
    List<AccountBean> mData;
    int year, month, day;
    AccountAdapter adapter;


    //头布局相关控件
    View headerView;
    TextView topExpenseTv, topIncomeTv, topBudgetTv, topTodayConTv;
    ImageView topShowIv;

    SharedPreferences SHP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        mainLv = findViewById(R.id.main_lv);
        
        //添加listView头布局
        addLvHeaderView();

        mData = new ArrayList<>();
        SHP = getSharedPreferences("budget", Context.MODE_PRIVATE);


        //设置适配器
        adapter = new AccountAdapter(this, mData);
        mainLv.setAdapter(adapter);

        setLvLongClickListener();

        //头布局点击事件
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        topShowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //切换textView明文或密文
                toggleShow();
            }
        });

        topBudgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetDialog dialog = new BudgetDialog(MainActivity.this);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
                    @Override
                    public void onEnsure(float money) {
                        SharedPreferences.Editor edit = SHP.edit();
                        edit.putFloat("budgetMoney", money);
                        edit.commit();

                        float monthlyExpense = DBManager.getMonthlySummary(year, month, 0);


                        float remainder = money - monthlyExpense;
                        BigDecimal bigDecimal = new BigDecimal(remainder);
                        float res = bigDecimal.setScale(2, 4).floatValue();


                        topBudgetTv.setText("$" + res);
                    }
                });
            }
        });
    }

    private void setLvLongClickListener() {
        mainLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    return false;
                }

                int pos = position -1;
                AccountBean accountBean = mData.get(pos); //获取正在被点击的数据

                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(accountBean);

                return false;
            }
        });

    }


    //弹出是否删除某条记录的对话框
    private void showDeleteItemDialog(final AccountBean accountBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice").setMessage("Do you want to delete this record?").setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteRecordFromAccounttb(accountBean.getId());
                        mData.remove(accountBean); //移除对象
                        adapter.notifyDataSetChanged();
                        refreshTopLvData();

                    }
                });
        builder.create().show();

    }

    boolean isShow = true;

    private void toggleShow() {
        if (isShow) {
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            topIncomeTv.setTransformationMethod(method); //设置隐藏
            topExpenseTv.setTransformationMethod(method); //设置隐藏
            topBudgetTv.setTransformationMethod(method); //设置隐藏
            topShowIv.setImageResource(R.mipmap.hide);
            isShow = false;
        }else {
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();

            topIncomeTv.setTransformationMethod(method); //设置隐藏
            topExpenseTv.setTransformationMethod(method); //设置隐藏
            topBudgetTv.setTransformationMethod(method); //设置隐藏
            topShowIv.setImageResource(R.drawable.ic_visibility_black_24dp);
            isShow = true;

        }
    }


    private void addLvHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        mainLv.addHeaderView(headerView);

        topExpenseTv = headerView.findViewById(R.id.item_mainlv_top_money);
        topIncomeTv = headerView.findViewById(R.id.item_mainlv_top_incomeMoney);
        topBudgetTv = headerView.findViewById(R.id.item_mainlv_top_budgetMoney);
        topTodayConTv = headerView.findViewById(R.id.item_mainlv_top_today);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_visible);


    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        insertDataFromDB();
        refreshTopLvData();
        
    }
        //设置头布局中文本内容的显示
    private void refreshTopLvData() {
        float expenseDailySummary = DBManager.getDailySummary(year, month, day, 0);
        float incomeDailySummary = DBManager.getDailySummary(year, month, day, 1);
        String todayInfo = "Today's expense $"+ expenseDailySummary +" income $"+incomeDailySummary;
        topTodayConTv.setText(todayInfo);

        float expenseMonthlySummary = DBManager.getMonthlySummary(year, month, 0);
        float incomeMonthlySummary = DBManager.getMonthlySummary(year, month, 1);

        topExpenseTv.setText("$" + expenseMonthlySummary);
        topIncomeTv.setText("$" + incomeMonthlySummary);

        float budgetMoney = SHP.getFloat("budgetMoney", 0);
        if (budgetMoney == 0){
            topBudgetTv.setText("$0.0");
        }else {
            float remainder = budgetMoney - expenseMonthlySummary;
            topBudgetTv.setText("$" + remainder);
        }
    }

    private void insertDataFromDB() {
        List<AccountBean> accountListForToday = DBManager.getAccountListForToday(year, month, day);
        mData.clear();
        mData.addAll(accountListForToday);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_iv_search:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                break;

            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;

            case R.id.main_btn_record:
                Intent intent = new Intent(this, AddRecordActivity.class);
                startActivity(intent);
                break;
        }

    }
}
