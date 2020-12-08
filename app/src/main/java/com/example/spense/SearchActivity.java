package com.example.spense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spense.DB.AccountBean;
import com.example.spense.DB.DBManager;
import com.example.spense.adapter.AccountAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView searchLv;
    EditText searchEt;
    List<AccountBean> mData;
    AccountAdapter mAdapter;
    TextView emptyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mData = new ArrayList<>();
        mAdapter = new AccountAdapter(this, mData);
        searchLv.setAdapter(mAdapter);
        searchLv.setEmptyView(emptyTv); //设置无数据时显示的控件
    }

    private void initView() {
        searchLv = findViewById(R.id.search_lv);
        searchEt = findViewById(R.id.search_et);
        emptyTv = findViewById(R.id.search_tv);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_search:
                //执行搜索操作
                String msg = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //开始搜索
                List<AccountBean> accountListByNote = DBManager.getAccountListByNote(msg);
                mData.clear();
                mData.addAll(accountListByNote);
                mAdapter.notifyDataSetChanged();


                break;
        }
    }
}
