package com.example.spense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.spense.adapter.AddRecordPagerAdapter;
import com.example.spense.add_record_frag.BaseRecordFragment;
import com.example.spense.add_record_frag.ExpenseFragment;
import com.example.spense.add_record_frag.IncomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity {
        TabLayout mTabLayout;
        ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        mTabLayout = findViewById(R.id.add_record_tabs);
        mViewPager = findViewById(R.id.add_record_vp);
        //设置viewPager加载页面
        initPager();
        

    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        ExpenseFragment expenseFragment = new ExpenseFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        fragmentList.add(expenseFragment);
        fragmentList.add(incomeFragment);

        //创建适配器
        AddRecordPagerAdapter adapter = new AddRecordPagerAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器对象
        mViewPager.setAdapter(adapter);

        //将tabLayout和viewpager关联
        mTabLayout.setupWithViewPager(mViewPager);
    }


    /*点击事件*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_record_iv_back:
                finish();
                break;
        }
    }
}
