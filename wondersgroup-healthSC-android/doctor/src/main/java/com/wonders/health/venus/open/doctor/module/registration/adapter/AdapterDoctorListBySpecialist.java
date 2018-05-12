package com.wonders.health.venus.open.doctor.module.registration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DoctorListVO;
import com.wonders.health.venus.open.doctor.module.registration.DoctorScheduleActivity;
import com.wonders.health.venus.open.doctor.module.registration.RegistrationDoctorHead;
import com.wonders.health.venus.open.doctor.module.registration.RegistrationDoctorListActivity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.List;


/*
 * created by sunning on 16/1/5.
 */
public class AdapterDoctorListBySpecialist extends BaseAdapter<DoctorListVO, AdapterDoctorListBySpecialist.DoctorListHolder> {

    private BitmapTools mBitmapTool;

    public AdapterDoctorListBySpecialist(Context context, List<DoctorListVO> list) {
        super(context, list);
        this.mBitmapTool = new BitmapTools(context);
    }

    @Override
    public DoctorListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoctorListHolder(mInflater.inflate(R.layout.registration_doctor_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DoctorListHolder holder, int position) {
        final DoctorListVO doctorListVO = mItems.get(position);
        if (doctorListVO != null) {
            mBitmapTool.display(holder.headImg, doctorListVO.headphoto, RegistrationDoctorHead.getGenderHeadRes(doctorListVO.gender));
            holder.name.setText(doctorListVO.doctorName);
            holder.num.setText(String.format(mContext.getString(R.string.order_count), String.valueOf(doctorListVO.orderCount)));
            holder.title.setText(doctorListVO.doctorTitle);
            holder.expert.setText(doctorListVO.expertin);
            if (doctorListVO.isFull == 0) {
                holder.status.setText("约满");
                holder.status.setBackgroundResource(R.drawable.shape_res_disable);
                holder.root.setEnabled(false);
            } else {
                holder.root.setEnabled(true);
                holder.status.setText("预约");
                holder.status.setBackgroundResource(R.drawable.shape_res_enable);
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = SchemeUtil.getUri(mContext, R.string.path_doctor_schedule)
                                + "?" + DoctorScheduleActivity.EXTRA_DEPT_CODE + "=" + doctorListVO.hosDeptCode
                                + "&" + DoctorScheduleActivity.EXTRA_DOCTOR_CODE + "=" + doctorListVO.hosDoctCode
                                + "&" + DoctorScheduleActivity.EXTRA_HOSPITAL_CODE + "=" + doctorListVO.hosOrgCode;
                        if (RegistrationDoctorListActivity.verification(mContext,uri)) {
                            SchemeUtil.startActivity(mContext,uri);
//                            Intent intent = new Intent();
//                            intent.setClass(mContext, DoctorScheduleActivity.class);
//                            intent.putExtra(DoctorScheduleActivity.EXTRA_DEPT_CODE, doctorListVO.hosDeptCode);
//                            intent.putExtra(DoctorScheduleActivity.EXTRA_DOCTOR_CODE, doctorListVO.hosDoctCode);
//                            intent.putExtra(DoctorScheduleActivity.EXTRA_HOSPITAL_CODE, doctorListVO.hosOrgCode);
//                            mContext.startActivity(intent);
                        }
                    }
                });
            }
        }
    }

    static class DoctorListHolder extends RecyclerView.ViewHolder {
        TextView status;
        TextView expert;
        CircleImageView headImg;
        TextView name, title, num;
        RelativeLayout root;

        DoctorListHolder(View itemView) {
            super(itemView);
            headImg = (CircleImageView) itemView.findViewById(R.id.iv_item_doctorlist);
            name = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_name);
            title = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_title);
            num = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_num);
            expert = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_expert);
            status = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_duty);
            root = (RelativeLayout) itemView.findViewById(R.id.department_item_root);
        }
    }
}
