package com.example.spense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spense.DB.AccountBean;
import com.example.spense.DB.DBManager;
import com.example.spense.adapter.AccountAdapter;
import com.example.spense.utils.CalendarDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView historyLv;
    TextView timeTv;
    List<AccountBean> mData;
    AccountAdapter mAdapter;
    int year, month;
    String[] monthEnglish = new String[]{"January","February", "March", "April","May","June","July","August","September","October","November","December"};
    int dialogSelYear = -1;
    int dialogSelMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyLv = findViewById(R.id.history_lv);
        timeTv = findViewById(R.id.history_tv_date);
        mData = new ArrayList<>();
        mAdapter = new AccountAdapter(this, mData);
        historyLv.setAdapter(mAdapter);
        initTime();
        insertDataToLv(year, month);

        setLvClickListener();

        

    }
//设置listView的长按事件
    private void setLvClickListener() {
        historyLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean accountBean = mData.get(position);
                deleteItem(accountBean);
                return false;


            }
        });
    }

    private void deleteItem(final AccountBean accountBean) {
        final int id = accountBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice").setMessage("Do you want to delete this record?").setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteRecordFromAccounttb(id);
                        mData.remove(accountBean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();

    }

    private void insertDataToLv(int year, int month) {
        List<AccountBean> monthlyAccountList = DBManager.getMonthlyAccountList(year, month);
        mData.clear();
        mData.addAll(monthlyAccountList);
        mAdapter.notifyDataSetChanged();

    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        timeTv.setText(monthEnglish[month-1] +" " + year);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_calendar:
                CalendarDialog dialog = new CalendarDialog(this, dialogSelMonth, dialogSelYear);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selectPos, int year, int month) {
                        timeTv.setText(monthEnglish[month-1] +" " + year);
                        insertDataToLv(year, month);
                        dialogSelMonth = month;
                        dialogSelYear = selectPos;
                    }
                });
                break;
        }
    }
}
