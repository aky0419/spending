package com.example.spense.frag_graph;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.spense.DB.DBManager;
import com.example.spense.DB.GraphItemBean;
import com.example.spense.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
abstract public class BaseGraphFragment extends Fragment {

    ListView graphLv;
    int year;
    int month;
    List<GraphItemBean> mData;
    GraphItemAdapter adapter;

    BarChart mBarChart;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_income_graph, container, false);
        graphLv = view.findViewById(R.id.frag_graph_lv);
        //get data from activity
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        //set lv date source
        mData = new ArrayList<>();

        // set adapter
        adapter = new GraphItemAdapter(getContext(), mData);
        graphLv.setAdapter(adapter);

        addLvHeaderView();


        return view;
    }

    private void addLvHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.item_graph_frag_top,null);
        graphLv.addHeaderView(view);
        //查找头布局中所包含的控件
        mBarChart = view.findViewById(R.id.item_graphFrag_barChart);
        textView = view.findViewById(R.id.item_graphFrag_topTv);
        //设定柱状图不显示描述
        mBarChart.getDescription().setEnabled(false);
        //设置柱状图内边距
        mBarChart.setExtraOffsets(20,20,20,20);
        setAxis(year, month);
        //设置坐标轴显示数据
        setData(year, month);
    }

    protected abstract void setData(int year, int month);


    //设置柱状图坐标轴显示，方法必须重写
    private void setAxis(int year, final int month) {
        //设置x轴
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴显示在下方
        xAxis.setDrawGridLines(true);//设置显示位置该轴网格线
        xAxis.setLabelCount(31);//x轴标签个数
        xAxis.setTextSize(12f);//x轴标签大小
        //x轴显示格式
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int val = (int) value;
                if (val ==0) {
                    return month+"-1";
                }
                if (val == 14) {
                    return month+"-15";
                }

                //根据月份不同 显示最后一天位置
                if (month==2) {
                    if (val == 27) {
                        return month+"-28";
                    }
                }else if (month ==1 || month ==3 || month ==5 || month == 7|| month == 8|| month == 10 || month ==12){
                    if(val ==30){
                        return month +"-31";
                    }
                }else if (month == 4 || month ==6 || month ==9 || month ==11){
                    if (val ==29) {
                        return month+"-30";
                    }
                }

                return "";
            }

        });

        xAxis.setYOffset(10);

        //Y轴在子类设置
        setYAxis();
    }

    protected abstract void setYAxis();


    public void setDate(int year, int month){
        this.year = year;
        this.month = month;
        mBarChart.clear();
        mBarChart.invalidate();
        setAxis(year, month);
        setData(year, month);
    }



    public void loadData(int year, int month, int kind) {
        List<GraphItemBean> list = DBManager.getGraphItemBeam(year, month, kind);
        mData.clear();
        mData.addAll(list);
        adapter.notifyDataSetChanged();

    }
}
