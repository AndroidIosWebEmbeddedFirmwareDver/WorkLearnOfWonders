package com.wonders.health.venus.open.doctor.module.referral;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.InsertReferralList;
import com.wonders.health.venus.open.doctor.entity.ReferralServiceList;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.List;

/**
 * Created by win10 on 2017/6/8.
 */

class InsertReferralAdapter  extends BaseAdapter<InsertReferralList,InsertReferralAdapter.ViewHolder> {


    private Context mContext;
    public List<InsertReferralList> getItems(){
        return mItems;
    }

    public InsertReferralAdapter(Context ctx, List<InsertReferralList> list) {
        super(ctx, list);
        mContext = ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.fragment_all_item,parent ,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView all_name;
        private CircleImageView civ_all_photo;
        private  TextView tv_all_urgent;//一般情况
        private  TextView tv_all_commonly;//紧急情况
        private  TextView tv_all_time;//时间
        private  TextView tv_all_in;//转入
        private  TextView tv_all_out;//转出
        private  TextView tv_all_state;//申请的状态

        public ViewHolder(View itemView) {
            super(itemView);
            civ_all_photo  = (CircleImageView) itemView.findViewById(R.id.civ_all_photo);
            all_name = (TextView) itemView.findViewById(R.id.all_name);
            tv_all_urgent = (TextView) itemView.findViewById(R.id.tv_all_urgent);
            tv_all_commonly = (TextView) itemView.findViewById(R.id.tv_all_commonly);
            tv_all_time = (TextView) itemView.findViewById(R.id.tv_all_time);
            tv_all_in = (TextView) itemView.findViewById(R.id.tv_all_in);
            tv_all_out = (TextView) itemView.findViewById(R.id.tv_all_out);
            tv_all_state = (TextView) itemView.findViewById(R.id.tv_all_state);
        }
    }

}
