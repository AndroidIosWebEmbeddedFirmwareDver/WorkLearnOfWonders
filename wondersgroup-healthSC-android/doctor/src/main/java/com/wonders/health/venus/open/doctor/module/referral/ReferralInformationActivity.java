package com.wonders.health.venus.open.doctor.module.referral;
/*
 * Created by sunning on 2017/6/6.
 * 转诊信息
 * call intoReferralInformation
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.module.registration.HospitalListActivity;
import com.wondersgroup.hs.healthcloud.common.CommonApp;

import butterknife.OnClick;

public class ReferralInformationActivity extends BaseActivity {

    public static final int OUTPATIENT_REFERRAL_FLAG = 0x1101;
    public static final int INPATIENT_REFERRAL_FLAG = 0x1102;

    private int currentStatus;
    public static final String OUTPATIENT_REFERRAL_EXTRA = "outpatient_referral";

    @Override
    protected void initViews() {
        currentStatus = getIntent().getIntExtra(OUTPATIENT_REFERRAL_EXTRA, -1);
        if (currentStatus != -1) {
            int resId = currentStatus == OUTPATIENT_REFERRAL_FLAG ? R.layout.layout_referral_info : R.layout.layout_inpatient_info;
            setContentView(resId);
        }
    }

    @OnClick(R.id.registration_btn)
    public void startRegistration() {
        if (currentStatus == OUTPATIENT_REFERRAL_FLAG) {
            startActivity(new Intent(this, HospitalListActivity.class));
        } else if (currentStatus == INPATIENT_REFERRAL_FLAG) {
            startActivity(new Intent(this, HospitalListActivity.class));
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * @param context content
     * @param type 门诊 OUTPATIENT_REFERRAL_FLAG
     *             住院 INPATIENT_REFERRAL_FLAG
     */
    public static void intoReferralInformation(Context context, int type) {
        Intent intent = new Intent(context, ReferralInformationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(OUTPATIENT_REFERRAL_EXTRA, type);
        CommonApp.getApp().startActivity(intent);
    }
}
