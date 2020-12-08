package com.example.spense.frag_graph;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.spense.DB.BarChartItemBean;
import com.example.spense.DB.DBManager;
import com.example.spense.DB.GraphItemBean;
import com.example.spense.R;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeGraphFragment extends BaseGraphFragment{
    int kind = 1;

    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
    }


    @Override
    protected void setData(int year, int month) {
        List<IBarDataSet> sets = new ArrayList<>();
        //获取这个月每天的收支总金额
        List<BarChartItemBean> list = DBManager.getDailyBarCharItemBean(year, month, kind);

        if (list.size()==0){
            mBarChart.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }else {
            mBarChart.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            //设置有多少根柱子
            List<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i <31; i++) {
                barEntries.add(new BarEntry(i,0.0f)); //初始化每一根柱子 添加到柱状图
            }

            for (int i = 0; i < list.size(); i++) {
                BarChartItemBean barChartItemBean = list.get(i);
                int day = barChartItemBean.getDay();//获取日期
                //根据天数 获取x轴位置
                BarEntry barEntry = barEntries.get(day - 1);
                barEntry.setY(barChartItemBean.getSumMoney());
            }

                BarDataSet barDataSet = new BarDataSet(barEntries, "");
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(10f);
                barDataSet.setColor(Color.parseColor("#006400"));


                barDataSet.setValueFormatter(new IndexAxisValueFormatter(){
                    @Override
                    public String getFormattedValue(float value) {
                        if (value==0) {
                            return "";
                        }else {
                            return value + "";
                        }

                    }
                });
            sets.add(barDataSet);
            BarData barData = new BarData(sets);
            barData.setBarWidth(0.2f);
            mBarChart.setData(barData);

        }
    }

    @Override
    protected void setYAxis() {
    //获取本月收入最高的一天 将他设定为Y轴最大值
        float maxMoney = DBManager.getDailyMaxForMonth(year, month, kind);
        float ceil = (float) Math.ceil(maxMoney);
        //设置Y轴
        YAxis axisRight = mBarChart.getAxisRight();
        axisRight.setAxisMaximum(ceil); //设置y轴最大值
        axisRight.setAxisMinimum(0f); //设置y轴最小值
        axisRight.setEnabled(false); //不显示右侧的Y轴

        YAxis axisLeft = mBarChart.getAxisLeft();
        axisLeft.setAxisMaximum(ceil);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setEnabled(false);

        //设置不显示图例
        Legend legend = mBarChart.getLegend();
        legend.setEnabled(false);
    }

    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year, month, kind);
    }
}
