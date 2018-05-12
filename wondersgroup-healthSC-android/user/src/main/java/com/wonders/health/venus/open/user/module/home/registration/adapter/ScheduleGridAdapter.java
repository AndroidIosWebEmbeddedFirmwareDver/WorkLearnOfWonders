package com.wonders.health.venus.open.user.module.home.registration.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wonders.health.venus.open.user.entity.ScheduleInfo;
import com.wonders.health.venus.open.user.entity.ScheduleItem;
import com.wonders.health.venus.open.user.module.home.registration.DoctorListBySpecialistFragment;
import com.wonders.health.venus.open.user.module.home.registration.OrderedInformationActivity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：11/9/16 10:35 AM
 */
public class ScheduleGridAdapter extends BaseAdapter<ScheduleItem, ScheduleGridAdapter.ScheduleGridViewHolder> {
    private ScheduleInfo.DoctorInfoEntity mDoctorInfoEntity;

    public ScheduleGridAdapter(Context context, List<ScheduleItem> list) {
        super(context, list);
    }

    public ScheduleGridAdapter(Context context, ScheduleInfo.DoctorInfoEntity doctorInfoEntity, List<ScheduleItem> list) {
        super(context, list);
        mDoctorInfoEntity = doctorInfoEntity;
    }

    @Override
    public ScheduleGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleGridViewHolder(mContext, mInflater.inflate(R.layout.item_doctor_schedule,null,false), mDoctorInfoEntity);
    }

    @Override
    public void onBindViewHolder(ScheduleGridViewHolder holder, int position) {
        ScheduleItem item = getItem(position);
        holder.bindView(item);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) == null ? ScheduleItem.STATUS_DISABLED : getItem(position).status;
    }

    public static class ScheduleGridViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ScheduleInfo.DoctorInfoEntity mDoctorInfoEntity;
        private TextView tv_title;
        private TextView tv_desc;
        public ScheduleGridViewHolder(Context context,View itemView, ScheduleInfo.DoctorInfoEntity doctorInfoEntity) {
            super(itemView);
            mContext = context;
            mDoctorInfoEntity = doctorInfoEntity;
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        }

        public void bindView(final ScheduleItem item) {
            if (item != null) {
                if (item.status == ScheduleItem.STATUS_ENABLED) {
                    if (item.schedule != null && item.schedule.isFull == 0) {
                        tv_title.setText("约满");
                        tv_title.setTextColor(mContext.getResources().getColor(R.color.tc3));
                        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        tv_desc.setVisibility(View.GONE);
                        itemView.setBackgroundColor(mContext.getResources().getColor(R.color.bc5));
                        itemView.setClickable(false);
                    } else {
                        tv_title.setText(item.title);
                        tv_title.setTextColor(mContext.getResources().getColor(R.color.tc0));
                        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        tv_desc.setText(item.desc + "元");
                        tv_desc.setTextColor(mContext.getResources().getColor(R.color.tc0));
                        itemView.setBackgroundColor(mContext.getResources().getColor(R.color.bc7));
                        UIUtil.setTouchEffect(itemView);
                        itemView.setClickable(true);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, OrderedInformationActivity.class);
                                DoctorListVO doctorListVO = new DoctorListVO();
                                if (mDoctorInfoEntity != null) {
                                    doctorListVO.doctorName = mDoctorInfoEntity.doctorName;
                                    doctorListVO.doctorTitle = mDoctorInfoEntity.doctorTitle;
                                    doctorListVO.hosOrgCode = mDoctorInfoEntity.hosOrgCode;
                                    doctorListVO.hosDeptCode = mDoctorInfoEntity.hosDeptCode;
                                    doctorListVO.hosDoctCode = mDoctorInfoEntity.hosDoctCode;
                                    doctorListVO.headphoto = mDoctorInfoEntity.headphoto;
                                    doctorListVO.hosName = mDoctorInfoEntity.hosName;
                                    doctorListVO.deptName = mDoctorInfoEntity.deptName;
                                    doctorListVO.gender = mDoctorInfoEntity.gender;
                                }
                                doctorListVO.schedule = item.schedule;
                                intent.putExtra(DoctorListBySpecialistFragment.ORDER_INFO, doctorListVO);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                } else {
                    tv_title.setText(item.title);
                    tv_title.setTextColor(mContext.getResources().getColor(R.color.tc3));
                    tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    tv_desc.setText(item.desc);
                    tv_desc.setTextColor(mContext.getResources().getColor(R.color.tc3));
                    itemView.setBackgroundColor(-1);
                    itemView.setClickable(false);
                }
            } else {
                tv_title.setText("");
                tv_desc.setText("");
                itemView.setBackgroundColor(-1);
                itemView.setClickable(false);
            }

        }
    }
}
