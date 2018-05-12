package com.wonders.health.venus.open.user.module.home.extractreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractReportEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractReportAdapter extends BaseAdapter<ExtractReportEntity, ExtractReportAdapter.ReportHolder> {

    public ExtractReportAdapter(Context context, List<ExtractReportEntity> list) {
        super(context, list);
    }

    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportHolder(mInflater.inflate(R.layout.item_extract_report, parent,false));
    }

    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {

        holder.bindData(mItems.get(position));


    }

    public static class ReportHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        TextView tvHospital;
        TextView tvDepartment;
        TextView tvCategory;


        public ReportHolder(View itemView) {
            super(itemView);
            tvData = (TextView) itemView.findViewById(R.id.tv_date_content);
            tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_content);
            tvDepartment = (TextView) itemView.findViewById(R.id.tv_department_content);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category_content);
        }

        public void bindData(ExtractReportEntity entity) {
            tvData.setText(entity.date);
            tvHospital.setText(entity.hospital_name);
            tvDepartment.setText(entity.department_name);
            tvCategory.setText(entity.item_name);
        }
    }
}
