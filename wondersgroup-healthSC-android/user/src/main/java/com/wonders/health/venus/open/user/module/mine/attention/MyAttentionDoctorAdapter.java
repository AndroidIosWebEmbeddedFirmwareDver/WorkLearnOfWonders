package com.wonders.health.venus.open.user.module.mine.attention;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorDetailInfo;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.List;

/**
 * Created by szy
 */
public class MyAttentionDoctorAdapter extends BaseAdapter<DoctorDetailInfo,MyAttentionDoctorAdapter.DoctorHolder> {
    BitmapTools mBitmapTools;

    public MyAttentionDoctorAdapter(Context context, List<DoctorDetailInfo> list) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);

    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_myattent_doctorlist,parent,false);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorHolder holder, int position) {
        DoctorDetailInfo data = mItems.get(position);
        if (data!=null){
            if (!TextUtils.isEmpty(data.doctorName)&&data.doctorName.length()>5) {
                data.doctorName=data.doctorName.substring(0,5)+"...";
            }
            if (!TextUtils.isEmpty(data.doctorTitle)&&data.doctorTitle.length()>5) {
                data.doctorTitle=data.doctorTitle.substring(0,5)+"...";
            }
            holder.tv_doctor_name.setText(data.doctorName);
            holder.tv_doctor_job_title.setText(data.doctorTitle);
            holder.tv_doctor_accept_amount.setText("接诊量  "+data.orderCount);
            holder.tv_doctor_good_at.setText("擅长："+data.expertin);
            if ("2".equals(data.gender)) {//1男2女
                mBitmapTools.display(holder.iv_doctor_image,data.headphoto,R.mipmap.ic_doctor_default_woman);
            } else {
                mBitmapTools.display(holder.iv_doctor_image,data.headphoto,R.mipmap.ic_doctor_default_man);
            }

        }
    }

    class DoctorHolder extends RecyclerView.ViewHolder{
        private com.wondersgroup.hs.healthcloud.common.view.CircleImageView iv_doctor_image;
        private TextView tv_doctor_name;
        private TextView tv_doctor_job_title;
        private TextView tv_doctor_accept_amount;
        private TextView tv_doctor_good_at;
        public DoctorHolder(View itemView) {
            super(itemView);
            iv_doctor_image = (com.wondersgroup.hs.healthcloud.common.view.CircleImageView) itemView.findViewById(R.id.iv_doctor_image);
            tv_doctor_name = (TextView) itemView.findViewById(R.id.tv_doctor_name);
            tv_doctor_job_title = (TextView) itemView.findViewById(R.id.tv_doctor_job_title);
            tv_doctor_accept_amount = (TextView) itemView.findViewById(R.id.tv_doctor_accept_amount);
            tv_doctor_good_at = (TextView) itemView.findViewById(R.id.tv_doctor_good_at);
        }
    }
}
