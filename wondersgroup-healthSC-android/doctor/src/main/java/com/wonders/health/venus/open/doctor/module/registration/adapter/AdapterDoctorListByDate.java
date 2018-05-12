package com.wonders.health.venus.open.doctor.module.registration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DoctorListVO;
import com.wonders.health.venus.open.doctor.module.registration.RegistrationDoctorHead;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.List;
import static com.wonders.health.venus.open.doctor.R.id.iv_item_doctorlist;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_date;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_duty;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_expert;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_fee;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_name;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_title;
import static com.wonders.health.venus.open.doctor.R.id.tv_item_doctorlist_visit_level;




/*
 * created by sunning on 16/1/5.
 */
public class AdapterDoctorListByDate extends BaseAdapter<DoctorListVO, AdapterDoctorListByDate.DoctorListHolder> {

    private BitmapTools mBitmapTool;


    public AdapterDoctorListByDate(Context context, List<DoctorListVO> list) {
        super(context, list);
        this.mBitmapTool = new BitmapTools(context);
    }

    @Override
    public DoctorListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoctorListHolder(mInflater.inflate(R.layout.registration_doctor_list_by_date_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DoctorListHolder holder, int position) {
        final DoctorListVO doctorListVO = mItems.get(position);
        if (doctorListVO != null) {
            mBitmapTool.display(holder.headImg, doctorListVO.headphoto, RegistrationDoctorHead.getGenderHeadRes(doctorListVO.gender));
            holder.name.setText(doctorListVO.doctorName);
            holder.fee.setText(doctorListVO.schedule.visitCost + "元") ;
            holder.expert.setText(doctorListVO.expertin);
            holder.title.setText(doctorListVO.doctorTitle);
            holder.date.setText(doctorListVO.schedule.scheduleDate + "  " + ("1".equals(doctorListVO.schedule.timeRange) ? "上午" : "下午"));
            holder.level.setText(doctorListVO.schedule.visitLevel);
            if (doctorListVO.isFull == 0) {
                holder.status.setText("约满");
                holder.status.setBackgroundResource(R.drawable.shape_res_disable);
            } else {
                holder.status.setText("剩余" + doctorListVO.schedule.numSource + "个");
                holder.status.setBackgroundResource(R.drawable.shape_res_enable);
            }
        }
    }

    static class DoctorListHolder extends RecyclerView.ViewHolder {
        TextView status, fee, expert, date, level;
        CircleImageView headImg;
        TextView name, title;

        DoctorListHolder(View itemView) {
            super(itemView);
            headImg = (CircleImageView) itemView.findViewById(iv_item_doctorlist);
            name = (TextView) itemView.findViewById(tv_item_doctorlist_name);
            title = (TextView) itemView.findViewById(tv_item_doctorlist_title);
            expert = (TextView) itemView.findViewById(tv_item_doctorlist_expert);
            status = (TextView) itemView.findViewById(tv_item_doctorlist_duty);
            fee = (TextView) itemView.findViewById(tv_item_doctorlist_fee);
            date = (TextView) itemView.findViewById(tv_item_doctorlist_date);
            level = (TextView) itemView.findViewById(tv_item_doctorlist_visit_level);
        }
    }
}
