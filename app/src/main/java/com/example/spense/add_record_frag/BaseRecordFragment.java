package com.example.spense.add_record_frag;


import android.app.Dialog;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spense.DB.AccountBean;
import com.example.spense.DB.DBManager;
import com.example.spense.DB.TypeBean;
import com.example.spense.R;
import com.example.spense.utils.KeyBoardUtils;
import com.example.spense.utils.NoteDialog;
import com.example.spense.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseRecordFragment extends Fragment {
    KeyboardView mKeyboardView;
    ImageView typeIv;
    EditText moneyEt;
    TextView typeTv, noteTv, timeTv;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean mAccountBean; //将需要插入到记账本中的数据保存成对象形势

    public BaseRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountBean = new AccountBean();
        mAccountBean.setTypename("Others");
        mAccountBean.setsImageId(R.mipmap.expense_others_slected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expense, container, false);
        
        initView(view);


        setInitTime();

        //给gridview填充数据
        loadDataToGv();
        setGvListener();

        noteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showNoteDialog();
            }
        });

        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showTimeDialog();
            }
        });

        return view;
        
    }

    private void showTimeDialog(){
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                mAccountBean.setTime(time);
                mAccountBean.setYear(year);
                mAccountBean.setMonth(month);
                mAccountBean.setDay(day);
            }
        });
    };


    private void showNoteDialog() {
        final NoteDialog dialog = new NoteDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new NoteDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String content = dialog.getEtContent();
                if (!TextUtils.isEmpty(content)){
                    noteTv.setText(content);
                    mAccountBean.setNote(content);
                }

                dialog.cancel();
            }
        });
    }


    /**
     * 获取当前时间显示在timeTv上
     */

    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = dateFormat.format(date);
        timeTv.setText(time);
        mAccountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mAccountBean.setYear(year);
        mAccountBean.setMonth(month);
        mAccountBean.setDay(day);

    }


    /**
     * 设置gv每一项点击事件
     */
    private void setGvListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();
                TypeBean typeBean = typeList.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                mAccountBean.setTypename(typename);

                int sImageId = typeBean.getsImageId();
                typeIv.setImageResource(sImageId);
                mAccountBean.setsImageId(sImageId);


            }
        });
    }

    public void loadDataToGv() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initView(View view) {
        mKeyboardView = view.findViewById(R.id.frag_record_keyboard);
        typeIv = view.findViewById(R.id.frag_record_iv);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        noteTv = view.findViewById(R.id.frag_record_tv_node);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        typeGv = view.findViewById(R.id.frag_record_gv);

        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(mKeyboardView, moneyEt);
        keyBoardUtils.showKeyboard();

        //设置接口
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //点击确定按钮，获取信息并保存在数据库，返回上一界面
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr) || moneyStr.equals('0')) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                mAccountBean.setMoney(money);
                //执行插入数据的方法
                saveAccountToDB();


                getActivity().finish();

            }
        });
    }
    //抽象方法 让子类一定要重写这个方法
    public abstract void saveAccountToDB();



}
