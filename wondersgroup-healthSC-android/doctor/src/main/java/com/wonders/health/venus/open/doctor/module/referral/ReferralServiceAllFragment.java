package com.wonders.health.venus.open.doctor.module.referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wonders.health.venus.open.doctor.R;

import com.wonders.health.venus.open.doctor.entity.ReferralServiceList;
import com.wonders.health.venus.open.doctor.logic.ReferralServiceManager;

import com.wonders.health.venus.open.doctor.logic.UserManager;

import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;


import butterknife.BindView;

/**
 * Created by win10 on 2017/6/6.
 */


public class ReferralServiceAllFragment extends BaseFragment {
    @BindView(R.id.pull_view)
    PullToRefreshView pullToRefreshView;
    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;

    private String type;
    private ReferralServiceAllAdapter adapter;

    private ReferralServiceManager reManager;
    private ReferralServiceList serviceList;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, null, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(ReferralServiceActivity.REFERRAL_TYPE);
    }

    @Override
    protected void initViews() {

    }


    private void loadData(int type) {
        reManager = new ReferralServiceManager();
        reManager.queryReferralServiceList(UserManager.getInstance().getUser().uid, new FinalResponseCallback<ReferralServiceList>(this) {
            @Override
            public void onSuccess(ReferralServiceList referralServiceList) {
                super.onSuccess(referralServiceList);
                serviceList = referralServiceList;
                if (adapter == null) {
                    adapter = new ReferralServiceAllAdapter(mBaseActivity, serviceList.getList());
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
            }
        });
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadData(Constant.TYPE_INIT);
    }
}
