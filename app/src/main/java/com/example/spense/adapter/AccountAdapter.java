package com.example.spense.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spense.DB.AccountBean;
import com.example.spense.R;

import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class AccountAdapter extends BaseAdapter {
    Context mContext;
    List<AccountBean> mData;
    int year, month, day;

    public AccountAdapter(Context context, List<AccountBean> data) {
        mContext = context;
        mData = data;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mainlv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }
        AccountBean accountBean = mData.get(position);
        holder.typeIv.setImageResource(accountBean.getsImageId());
        holder.typeTv.setText(accountBean.getTypename());
        holder.noteTv.setText(accountBean.getNote());
        holder.moneyTv.setText("$" + accountBean.getMoney());

        if (year == accountBean.getYear() && month == accountBean.getMonth() && day == accountBean.getDay()){
            String time = accountBean.getTime().split(" ")[1];
            holder.timeTv.setText("Today " + time);

        }else{
            holder.timeTv.setText(accountBean.getTime());
        }

        return convertView;
    }

    class ViewHolder{
        ImageView typeIv;
        TextView typeTv, noteTv, timeTv, moneyTv;

        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            typeTv = view.findViewById(R.id.item_mainlv_title);
            noteTv = view.findViewById(R.id.item_mainlv_note);
            timeTv = view.findViewById(R.id.item_mainlv_time);
            moneyTv = view.findViewById(R.id.item_mainlv_money);
        }
    }
}
