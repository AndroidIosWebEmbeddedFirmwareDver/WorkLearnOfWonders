package com.wonders.health.venus.open.user.module.referral.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;

import com.wonders.health.venus.open.user.module.referral.TurnDiagnosisInformationActivity;
import com.wonders.health.venus.open.user.module.referral.bean.TurnDiagnosisRecordEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 * Created by win10 on 2017/5/31.
 */

public class TurnDiagnosisAdapter extends  BaseAdapter<TurnDiagnosisRecordEntity.Referral,TurnDiagnosisAdapter.ViewHolder> {

    private Context mContext;
    public List<TurnDiagnosisRecordEntity.Referral> getItems(){
        return  mItems;
    }


    public TurnDiagnosisAdapter(Context context, List<TurnDiagnosisRecordEntity.Referral> list) {
        super(context, list);
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.item_referral, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TurnDiagnosisRecordEntity.Referral   mReferral= getItem(position);
        //设置监听
        holder.ll_referral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动页面
               // SchemeUtil.startActivity(mContext, mReferral.url);
                mContext.startActivity(new Intent(mContext,TurnDiagnosisInformationActivity.class));
            }
        });
      //  holder.tv_apply();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView apply_time,tv_apply;//申请状态
        private  TextView tv_referral_out_name,tv_referral_in_name,tv_referral_in_state;//转入 转出
        private  LinearLayout ll_referral;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_referral = (LinearLayout) itemView.findViewById(R.id.ll_referral);
           apply_time = (TextView) itemView.findViewById(R.id.tv_referral_set_time);
            tv_referral_in_name = (TextView) itemView.findViewById(R.id.tv_referral_in_name);
            tv_referral_out_name = (TextView) itemView.findViewById(R.id.tv_referral_out_name);
            tv_referral_in_state = (TextView) itemView.findViewById(R.id.tv_referral_in_state);//申请状态
            tv_apply = (TextView) itemView.findViewById(R.id.tv_apply);//向上转，向下转 图标及字体
        }
    }


}
