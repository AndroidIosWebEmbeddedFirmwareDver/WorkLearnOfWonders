package com.wonders.health.venus.open.doctor.module.registration.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DepartmentEntity;
import com.wonders.health.venus.open.doctor.module.registration.DepartmentSelectActivity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;


/**
 * 类描述：科室Adapter
 * 创建人：hhw
 * 创建时间：2015/7/13 13:44
 */
public class DepartmentAdapter extends BaseAdapter<DepartmentEntity, RecyclerView.ViewHolder> {
    private int mType;
    private int selectedPosition = -1;

    public DepartmentAdapter(Context context, List<DepartmentEntity> list, int type) {
        super(context, list);
        this.mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (mType) {
            case DepartmentSelectActivity.FIRST_CLASS_DEPARTMENT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_firstclass_dept, parent, false);
                holder = new FirstClassDeptHolder(view);
                break;
            case DepartmentSelectActivity.SECOND_CLASS_DEPARTMENT:
                View otherview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_secondclass_dept, parent, false);
                holder = new SecondClassDeptHolder(otherview);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DepartmentEntity entity = getItem(position);
        switch (mType) {
            case DepartmentSelectActivity.FIRST_CLASS_DEPARTMENT:
                FirstClassDeptHolder firstClassDeptHolder = (FirstClassDeptHolder) holder;
                firstClassDeptHolder.deptname.setText(entity.deptName);
                if (position == selectedPosition) {
                    firstClassDeptHolder.ll_bg.setSelected(true);
                    firstClassDeptHolder.deptname.setTextColor(ContextCompat.getColor(mContext,R.color.tc5));
                } else {
                    firstClassDeptHolder.ll_bg.setSelected(false);
                    firstClassDeptHolder.deptname.setTextColor(ContextCompat.getColor(mContext,R.color.tc1));
                }
                if("-9".equals(entity.hosDeptCode)){
                    firstClassDeptHolder.deptname.setTextColor(ContextCompat.getColor(mContext,android.R.color.holo_red_light));
                }
                break;
            case DepartmentSelectActivity.SECOND_CLASS_DEPARTMENT:
                SecondClassDeptHolder secondClassDeptHolder = (SecondClassDeptHolder) holder;
                secondClassDeptHolder.deptname.setText(entity.deptName);

                break;
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private static class FirstClassDeptHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_bg;
        TextView deptname;
        LinearLayout dateBlock;

        FirstClassDeptHolder(View itemView) {
            super(itemView);
            ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
            deptname = (TextView) itemView.findViewById(R.id.tv_name);
            dateBlock = (LinearLayout) itemView.findViewById(R.id.department_by_date_root);
        }
    }

    private static class SecondClassDeptHolder extends RecyclerView.ViewHolder {
        TextView deptname;

        SecondClassDeptHolder(View itemView) {
            super(itemView);

            deptname = (TextView) itemView.findViewById(R.id.tv_dept_name);
        }
    }

}
