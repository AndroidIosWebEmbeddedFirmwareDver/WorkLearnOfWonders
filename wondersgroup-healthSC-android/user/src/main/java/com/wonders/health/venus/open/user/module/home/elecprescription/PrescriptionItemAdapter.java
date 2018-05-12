package com.wonders.health.venus.open.user.module.home.elecprescription;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.PrescriptionItemEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 * Created by wang on 2016/11/8.
 */

public class PrescriptionItemAdapter extends BaseAdapter<PrescriptionItemEntity,PrescriptionItemAdapter.PrescriptionHolder> {


    public PrescriptionItemAdapter(Context context, List<PrescriptionItemEntity> list) {
        super(context, list);
    }

    @Override
    public PrescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrescriptionHolder(mInflater.inflate(R.layout.item_prescription,null));
    }

    @Override
    public void onBindViewHolder(PrescriptionHolder holder, int position) {
        PrescriptionItemEntity entity =mItems.get(position);
        if(entity!=null){
            holder.tvData.setText(entity.kfsj);
            holder.tvHospital.setText(entity.yljgmc);
            holder.tvDepartment.setText(entity.kfksmc);
            holder.tvMoney.setText("¥"+entity.cfje);
        }

    }

   public static class PrescriptionHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        TextView tvHospital;
        TextView tvDepartment;
        TextView tvMoney;


        public PrescriptionHolder(View itemView) {
            super(itemView);
            tvData = (TextView)itemView.findViewById(R.id.tv_date_content);
            tvHospital = (TextView)itemView.findViewById(R.id.tv_hospital_content);
            tvDepartment = (TextView)itemView.findViewById(R.id.tv_department_content);
            tvMoney = (TextView)itemView.findViewById(R.id.tv_money_content);
        }
    }
}
