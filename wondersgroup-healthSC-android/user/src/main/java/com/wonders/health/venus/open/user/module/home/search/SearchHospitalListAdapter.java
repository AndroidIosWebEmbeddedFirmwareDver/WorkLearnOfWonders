package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果--医院列表
 * Created by songzhen on 2016/11/9.
 */
public class SearchHospitalListAdapter extends BaseAdapter {
    private static int MAX_SHOW_SIZE = 2;
    private BitmapTools mBitmaptool;
    private Context mContext;
    private List<HospitalInfo.Hospital> mItems = new ArrayList<>();
    private String keyword = "";

    public SearchHospitalListAdapter(Context mContext, List<HospitalInfo.Hospital> mItems){
        new SearchHospitalListAdapter(mContext, mItems, "");
    }

    public SearchHospitalListAdapter(Context mContext, List<HospitalInfo.Hospital> mItems,String keyword){
        this.mContext = mContext;
        this.mItems = mItems;
        mBitmaptool = new BitmapTools(mContext);
        this.keyword = keyword;
    }
    @Override
    public int getCount() {
        if (mItems.size()>0){
            if (mItems.size()>MAX_SHOW_SIZE){
                return MAX_SHOW_SIZE;
            }else {
                return mItems.size();
            }
        }else {
            return 0;
        }
    }

    @Override
    public HospitalInfo.Hospital getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HospitalHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hospital,parent,false);
            holder = new HospitalHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (HospitalHolder) convertView.getTag();
        }
        HospitalInfo.Hospital entity = getItem(position);
        if (entity != null) {
            mBitmaptool.display(holder.iv_hos, entity.hospitalPhoto, BitmapTools.SizeType.MEDIUM,R.mipmap.ic_default_hospital,null);
            if(!TextUtils.isEmpty(keyword)&&!TextUtils.isEmpty(entity.hospitalName)){
                SpannableStringBuilder builder = new SpannableStringBuilder(entity.hospitalName);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#2E7AF0"));
                int start = entity.hospitalName.indexOf(keyword);
                int end = start + keyword.length();
                if (start>=0){
                    builder.setSpan(blueSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                holder.tv_hosName.setText(builder);
            }else{
                holder.tv_hosName.setText(entity.hospitalName+"");
            }
            if (TextUtils.isEmpty(entity.hospitalGrade)){
                holder.tv_level.setVisibility(View.GONE);
            }else {
                holder.tv_level.setVisibility(View.VISIBLE);
                holder.tv_level.setText(entity.hospitalGrade+"");
            }
            holder.appointmentCount.setText("预约量: "+entity.receiveCount);
        }
        return convertView;
    }

    class HospitalHolder extends RecyclerView.ViewHolder {
//        LinearLayout ll_level;
        ImageView iv_hos;//医院图片
        TextView tv_hosName;//医院名字
//        TextView tv_desc;//医院等级（如 三甲医院）
        TextView appointmentCount;//预约量
        TextView tv_level;//级别

        HospitalHolder(View itemView) {
            super(itemView);

//            ll_level = (LinearLayout) itemView.findViewById(R.id.ll_level);
            tv_level = (TextView) itemView.findViewById(R.id.tv_level);
            iv_hos = (ImageView) itemView.findViewById(R.id.iv_hos);
            tv_hosName = (TextView) itemView.findViewById(R.id.tv_hospital_name);
//            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            appointmentCount = (TextView) itemView.findViewById(R.id.tv_appointment_count);
        }
    }
}
