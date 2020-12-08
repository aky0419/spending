package com.example.spense.add_record_frag;

import com.example.spense.DB.DBManager;
import com.example.spense.R;

public class ExpenseFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();

        typeList.addAll(DBManager.getTypeList(0));
        adapter.notifyDataSetChanged();
        typeTv.setText("Others");
        typeIv.setImageResource(R.mipmap.expense_others_slected);
    }

    @Override
    public void saveAccountToDB() {
        mAccountBean.setKind(0);
        DBManager.insertItemToAccountTb(mAccountBean);
    }
}
