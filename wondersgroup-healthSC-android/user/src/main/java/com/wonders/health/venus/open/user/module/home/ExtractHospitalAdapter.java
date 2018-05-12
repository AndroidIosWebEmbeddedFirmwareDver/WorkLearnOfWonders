package com.wonders.health.venus.open.user.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wang on 2017/5/5.
 */

public class ExtractHospitalAdapter extends BaseAdapter<ExtractHospitalEntity, ExtractHospitalAdapter.HospitalViewHolder> {

    private List<ExtractHospitalEntity> mList;
    private ExtractHospitalEntity entity;
    private ItemSelectListener itemSelectListener;

    public ExtractHospitalAdapter(Context context, List<ExtractHospitalEntity> list,ItemSelectListener listener) {
        super(context, list);
        mList = list;
        itemSelectListener=listener;
    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HospitalViewHolder(mInflater.inflate(R.layout.item_extract_hospital, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList.size()>=5?5:mList.size();
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder holder,final int position) {
        entity = mList.get(position);
        if(entity!=null){
            holder.tv_name.setText(entity.hospitalName);
        }
        if(itemSelectListener!=null){
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectListener.onItemSelect(position);
                }
            });
        }
    }

    class HospitalViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;

        public HospitalViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
        }
    }

    public  interface ItemSelectListener{
         void onItemSelect(int position);
    }

    //本地存储查询历史数据
    public void putPref(List<ExtractHospitalEntity> mExtractData,ExtractHospitalEntity hospitalEntity,String tag) {
        ExtractHospitalEntity tem = hospitalEntity;
        //使用linkedList实现FIFO
        LinkedList<ExtractHospitalEntity> linkedList;
        List<String> hosCode = new ArrayList<>();
        //根据hospitalCode判断数据是否重复
        if (mExtractData != null) {
            linkedList = new LinkedList<>(mExtractData);
            for (int i = 0; i < linkedList.size(); i++) {
                hosCode.add(linkedList.get(i).hospitalCode);
            }
        } else {
            linkedList = new LinkedList<>();
        }
        //将pref数据格式从arraylist转成linklist并去重
        if (linkedList.size() == 0) {
            linkedList.add(tem);
        } else if (linkedList.size() > 0 && linkedList.size() < 5) {
            if (!hosCode.contains(tem.hospitalCode)) {
                linkedList.addFirst(tem);
            }
        } else if (linkedList.size() == 5) {
            if (!hosCode.contains(tem.hospitalCode)) {
                linkedList.pollLast();
                linkedList.addFirst(tem);
            }
        }
        //LinkedList转成ArrayList,并本地存储
        if (mExtractData != null) {
            mExtractData.clear();
            for (int i = 0; i < linkedList.size(); i++) {
                mExtractData.add(linkedList.get(i));
            }
        }
        PrefUtil.putJsonArray(mContext, getUID() + tag, mExtractData);
    }

    private String getUID() {
        return UserManager.getInstance().getUser().uid;
    }
}
