package com.wonders.health.venus.open.user.module.home.registration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.List;


/**
 * Sunning
 * 2016-11-04
 */
public class HospitalListAdapter extends BaseAdapter<HospitalInfo.Hospital, HospitalListAdapter.HospitalHolder> {
    private BitmapTools mBitmaptool;

    public HospitalListAdapter(Context context, List<HospitalInfo.Hospital> list) {
        super(context, list);
        this.mBitmaptool = new BitmapTools(context);
    }

    @Override
    public HospitalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HospitalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false));
    }

    @Override
    public void onBindViewHolder(HospitalHolder holder, int position) {
        HospitalInfo.Hospital entity = getItem(position);
        if (entity != null) {
            holder.setData(entity);
        }
    }

    class HospitalHolder extends RecyclerView.ViewHolder {
        ImageView iv_hos;//医院图片
        TextView tv_hosName;//医院名字
        TextView appointmentCount;//预约量
        TextView tv_level;//级别

        HospitalHolder(View itemView) {
            super(itemView);
            tv_level = (TextView) itemView.findViewById(R.id.tv_level);
            iv_hos = (ImageView) itemView.findViewById(R.id.iv_hos);
            tv_hosName = (TextView) itemView.findViewById(R.id.tv_hospital_name);
            appointmentCount = (TextView) itemView.findViewById(R.id.tv_appointment_count);

        }

        public void setData(HospitalInfo.Hospital entity) {
            mBitmaptool.display(iv_hos, entity.hospitalPhoto, BitmapTools.SizeType.MEDIUM,R.mipmap.ic_default_hospital,null);
            tv_hosName.setText(entity.hospitalName);
            if (TextUtils.isEmpty(entity.hospitalGrade)) {
                tv_level.setVisibility(View.INVISIBLE);
            } else {
                tv_level.setVisibility(View.VISIBLE);
                tv_level.setText(entity.hospitalGrade);
            }
            appointmentCount.setText("预约量："+entity.receiveCount);
        }
    }
}
