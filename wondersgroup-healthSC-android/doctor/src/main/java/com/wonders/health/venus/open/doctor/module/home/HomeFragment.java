package com.wonders.health.venus.open.doctor.module.home;
/*
 * Created by sunning on 2017/6/1.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.HomePrompt;
import com.wonders.health.venus.open.doctor.logic.HomeManager;
import com.wonders.health.venus.open.doctor.module.consultation.ChatListActivity;
import com.wonders.health.venus.open.doctor.module.patient.SignPatientActivity;
import com.wonders.health.venus.open.doctor.module.referral.ReferralServiceActivity;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.iv_func2_point)
    ImageView func2Point;
    @BindView(R.id.iv_func3_point)
    ImageView func3Point;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        //不是组长加载main_page_no_leader布局
        return inflater.inflate(R.layout.main_page, null);
    }

    @Override
    protected void initViews() {
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeManager.getInstance().prompt(new DialogResponseCallback<HomePrompt>(mBaseActivity) {
            @Override
            public void onSuccess(HomePrompt homePrompt) {
                super.onSuccess(homePrompt);
                showRedPoint(homePrompt.hasNewPatient, homePrompt.hasNewReferral);
            }
        });
    }

    @OnClick({R.id.ll_fucn1, R.id.rl_fucn2, R.id.rl_fucn3})
    public void homeClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fucn1:
                startActivity(new Intent(mBaseActivity, ChatListActivity.class));
                break;
            case R.id.rl_fucn2:
                startActivity(new Intent(mBaseActivity, SignPatientActivity.class));
                break;
            case R.id.rl_fucn3:
                startActivity(new Intent(mBaseActivity, ReferralServiceActivity.class));
                break;
        }
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    private void showRedPoint(boolean hasNewPatient, boolean hasNewReferral) {
        func2Point.setVisibility(hasNewPatient ? View.VISIBLE : View.GONE);
        func3Point.setVisibility(hasNewReferral ? View.VISIBLE : View.GONE);
    }
}
