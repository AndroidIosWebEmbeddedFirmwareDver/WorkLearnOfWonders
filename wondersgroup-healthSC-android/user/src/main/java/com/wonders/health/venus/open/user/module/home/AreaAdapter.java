package com.wonders.health.venus.open.user.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;


/**
 * 类描述：地区Adapter
 * 创建人：hhw
 * 创建时间：2015/8/18 17:21
 */
public class AreaAdapter extends BaseAdapter<AreaEntity, AreaAdapter.DataHolder> {
    private Context mContext;

    public AreaAdapter(Context context, List<AreaEntity> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_select, parent, false);
        DataHolder holder = new DataHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        AreaEntity entity = getItem(position);
        if (entity != null) {
            holder.area_name.setText(entity.name);
//            holder.divider.setVisibility(position == mItems.size() - 1 ? View.GONE : View.VISIBLE);
        }
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        TextView area_name;
        View divider;

        public DataHolder(View itemView) {
            super(itemView);

            area_name = (TextView) itemView.findViewById(R.id.area_name);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}