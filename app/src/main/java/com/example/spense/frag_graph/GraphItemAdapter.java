package com.example.spense.frag_graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spense.DB.GraphItemBean;
import com.example.spense.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 账单详情页面listView的adapter
 *
 */

public class GraphItemAdapter extends BaseAdapter {
    Context mContext;
    List<GraphItemBean> mData;
    LayoutInflater mInflater;

    public GraphItemAdapter(Context context, List<GraphItemBean> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
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
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_graph_frag_lv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取显示内容

        GraphItemBean graphItemBean = mData.get(position);
        holder.typeIv.setImageResource(graphItemBean.getsImageId());
        holder.typeTv.setText(graphItemBean.getType());
        float ratio = graphItemBean.getRatio();
        BigDecimal bigDecimal = new BigDecimal(ratio * 100);
        float v = bigDecimal.setScale(2, 4).floatValue();
        holder.ratioTv.setText(v +"%");
        holder.totalTv.setText("$" + graphItemBean.getTotalMoney());

        return convertView;
    }

    class ViewHolder{

        TextView typeTv, ratioTv, totalTv;
        ImageView  typeIv;
        public ViewHolder(View view){
            typeTv = view.findViewById(R.id.item_graphFrag_typeTv);
            ratioTv = view.findViewById(R.id.item_graphFrag_percentage);
            totalTv = view.findViewById(R.id.item_graphFrag_sum);
            typeIv = view.findViewById(R.id.item_graphFrag_iv);
        }
    }
}


