package com.wonders.health.venus.open.doctor.module.registration.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.ScheduleItem;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;

import java.util.List;

public class RegisterByDateAdapter extends BaseAdapter<ScheduleItem, RegisterByDateAdapter.ViewHolder> {

    private int screenWidth;

    private int selectedPosition = -1;

    private final static int DATE_SIZE = 7;

    public RegisterByDateAdapter(Context context, List<ScheduleItem> list) {
        super(context, list);
        screenWidth = SystemUtil.getScreenWidth();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.register_by_date_item,null,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScheduleItem item = getItem(position);
        LinearLayout layout = (LinearLayout) holder.date.getParent();
        if (layout != null) {
            int width = layout.getLayoutParams().width;
            if (width * DATE_SIZE < screenWidth) {
                layout.getLayoutParams().width = screenWidth / DATE_SIZE;
            }
        }
        if (item != null) {
            holder.bindView(item);
        }
        if (position == selectedPosition) {
            holder.dateBlock.setSelected(true);
            holder.week.setTextColor(ContextCompat.getColor(mContext,R.color.tc0));
            holder.date.setTextColor(ContextCompat.getColor(mContext,R.color.tc0));
        } else {
            holder.dateBlock.setSelected(false);
            holder.week.setTextColor(ContextCompat.getColor(mContext,R.color.tc3));
            holder.date.setTextColor(ContextCompat.getColor(mContext,R.color.tc3));
        }
    }


    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView week;
        private TextView date;
        private LinearLayout dateBlock;
        public ViewHolder(View itemView) {
            super(itemView);
            week = (TextView) itemView.findViewById(R.id.register_by_date_week);
            date = (TextView) itemView.findViewById(R.id.register_by_date);
            dateBlock = (LinearLayout) itemView.findViewById(R.id.department_by_date_root);
        }

        public void bindView(ScheduleItem item) {
            week.setText(item.title);
            date.setText(item.desc);
        }
    }
}
