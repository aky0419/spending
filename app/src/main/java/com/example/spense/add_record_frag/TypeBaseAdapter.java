package com.example.spense.add_record_frag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spense.DB.TypeBean;
import com.example.spense.R;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {

    Context mContext;
    List<TypeBean> mTypeBaseList;
    int selectPos = 0;

    public TypeBaseAdapter(Context context, List<TypeBean> typeBaseList) {
        mContext = context;
        mTypeBaseList = typeBaseList;
    }

    @Override
    public int getCount() {
        return mTypeBaseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTypeBaseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //此适配器不用考虑复用，因为所有的数据都显示在Gv里，不会因为滑动而消失,所以没有剩余的convertView，所以不用复写
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addrecord_gv, parent, false);
        ImageView iv = convertView.findViewById(R.id.item_addrecord_gv_iv);
        TextView tv = convertView.findViewById(R.id.item_addrecord_gv_tv);
        TypeBean typeBean = mTypeBaseList.get(position);

        tv.setText(typeBean.getTypename());
        if (selectPos == position) {
            iv.setImageResource(typeBean.getsImageId());
        }else{
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}


