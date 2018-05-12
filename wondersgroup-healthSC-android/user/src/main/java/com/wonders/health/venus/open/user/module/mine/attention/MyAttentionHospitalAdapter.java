package com.wonders.health.venus.open.user.module.mine.attention;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalDetailInfo;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.List;

/**
 * Created by szy
 */
public class MyAttentionHospitalAdapter extends BaseAdapter<HospitalDetailInfo,MyAttentionHospitalAdapter.HospitalViewholder> {
    private BitmapTools mBitmapTools;
    public MyAttentionHospitalAdapter(Context context, List<HospitalDetailInfo> list) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);
    }

    @Override
    public HospitalViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HospitalViewholder(mInflater.inflate(R.layout.item_attention_hospital,null,false));
    }

    @Override
    public void onBindViewHolder(HospitalViewholder holder, int position) {
        HospitalDetailInfo item = getItem(position);
        if (item != null) {
            holder.tv_hospital_name.setText(item.hospitalName);
            holder.tv_hospital_level.setText(item.hospitalGrade);
            holder.tv_hospital_reservations.setText("预约量"+item.receiveCount);
            mBitmapTools.display(holder.iv_hospital_image,item.hospitalPhoto,R.mipmap.ic_default_hospital);
        }
    }

    public class HospitalViewholder extends RecyclerView.ViewHolder {
        private com.wondersgroup.hs.healthcloud.common.view.CircleImageView iv_hospital_image;
        private TextView tv_hospital_name;
        private TextView tv_hospital_level;
        private TextView tv_hospital_reservations;
        public HospitalViewholder(View itemView) {
            super(itemView);
            iv_hospital_image = (com.wondersgroup.hs.healthcloud.common.view.CircleImageView) itemView.findViewById(R.id.iv_hospital_image);
            tv_hospital_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
            tv_hospital_level = (TextView) itemView.findViewById(R.id.tv_hospital_level);
            tv_hospital_reservations = (TextView) itemView.findViewById(R.id.tv_hospital_reservations);
        }
    }

}
