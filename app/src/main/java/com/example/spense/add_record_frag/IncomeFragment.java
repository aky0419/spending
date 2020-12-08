package com.example.spense.add_record_frag;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.spense.DB.DBManager;
import com.example.spense.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();

        typeList.addAll(DBManager.getTypeList(1));
        adapter.notifyDataSetChanged();
        typeTv.setText("Others");
        typeIv.setImageResource(R.mipmap.income_others_selected);
    }

    @Override
    public void saveAccountToDB() {
        mAccountBean.setKind(1);
        DBManager.insertItemToAccountTb(mAccountBean);
    }

}
