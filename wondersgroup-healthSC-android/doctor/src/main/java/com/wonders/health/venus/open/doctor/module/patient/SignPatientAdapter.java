package com.wonders.health.venus.open.doctor.module.patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.PatientInfoItem;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wang on 2017/6/5.
 */

public class SignPatientAdapter extends BaseAdapter<PatientInfoItem, SignPatientAdapter.SignViewHolder> {

    BitmapTools mBitmapTools;
    Context mContext;
    public SignPatientAdapter(Context context, List<PatientInfoItem> list) {
        super(context, list);
        mContext=context;
    }

    @Override
    public SignViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignViewHolder(mInflater.inflate(R.layout.item_sign_patient, null));
    }

    @Override
    public void onBindViewHolder(SignViewHolder holder, int position) {
        mBitmapTools = new BitmapTools(mContext);
        PatientInfoItem info = mItems.get(position);
        if (info != null) {
            mBitmapTools.display(holder.circleImageView, info.avatar);
            holder.mName.setText(info.name);
            holder.mGender.setText(info.gender);
            holder.mAge.setText(info.age);
            holder.mAddr.setText(info.address);
        }

    }

    public static class SignViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView circleImageView;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_age)
        TextView mAge;
        @BindView(R.id.tv_gender)
        TextView mGender;
        @BindView(R.id.tv_addr)
        TextView mAddr;
//        @BindView(R.id.tv_f)
//        ImageView flag;

        public SignViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

