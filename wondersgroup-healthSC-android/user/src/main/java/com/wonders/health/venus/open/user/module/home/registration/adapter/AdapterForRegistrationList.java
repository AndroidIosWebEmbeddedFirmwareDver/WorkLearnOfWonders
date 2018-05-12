package com.wonders.health.venus.open.user.module.home.registration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.RegistrationListVO;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;


/*
 * Created by sunning on 16/1/4.
 */
public class AdapterForRegistrationList extends BaseAdapter<RegistrationListVO, AdapterForRegistrationList.RegistrationListHolder> {

    public AdapterForRegistrationList(Context context, List<RegistrationListVO> list) {
        super(context, list);
    }

    @Override
    public RegistrationListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.registration_list_item, parent, false);
        return new RegistrationListHolder(view);
    }

    @Override
    public void onBindViewHolder(RegistrationListHolder holder, int position) {
        RegistrationListVO vo = mItems.get(position);
        holder.mEgistration_list_item_hospital.setText(vo.hospital_name);
        holder.mEgistration_list_item_department.setText(vo.department_name);
        holder.mEgistration_list_item_doctor.setText(vo.doctor_name);
        holder.mEgistration_list_item_date.setText(vo.start_time);
        if ("1".equals(vo.status) || "5".equals(vo.status)) {
            holder.mEgistration_list_item_status.setImageResource(R.mipmap.awaiting_diagnosis);
        } else if ("2".equals(vo.status)) {
            holder.mEgistration_list_item_status.setImageResource(R.mipmap.diagnosised);
        } else if ("3".equals(vo.status)) {
            holder.mEgistration_list_item_status.setImageResource(R.mipmap.shuangyue);
        } else if ("4".equals(vo.status)) {
            holder.mEgistration_list_item_status.setImageResource(R.mipmap.cancel_diagnosis);
        }
    }

    public static class RegistrationListHolder extends RecyclerView.ViewHolder {

        TextView mEgistration_list_item_hospital;
        ImageView mEgistration_list_item_status;
        TextView mEgistration_list_item_department;
        TextView mEgistration_list_item_doctor;
        TextView mEgistration_list_item_date;

        public RegistrationListHolder(View itemView) {
            super(itemView);
            mEgistration_list_item_hospital = (TextView) itemView.findViewById(R.id.egistration_list_item_hospital);
            mEgistration_list_item_status = (ImageView) itemView.findViewById(R.id.egistration_list_item_status);
            mEgistration_list_item_department = (TextView) itemView.findViewById(R.id.egistration_list_item_department);
            mEgistration_list_item_doctor = (TextView) itemView.findViewById(R.id.egistration_list_item_doctor);
            mEgistration_list_item_date = (TextView) itemView.findViewById(R.id.egistration_list_item_date);
        }
    }
}
