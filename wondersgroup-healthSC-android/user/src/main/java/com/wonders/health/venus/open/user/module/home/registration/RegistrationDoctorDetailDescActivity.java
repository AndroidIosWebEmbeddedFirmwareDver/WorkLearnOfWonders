package com.wonders.health.venus.open.user.module.home.registration;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;

/**
 * class:  RegistrationDoctorDetailDescActivity
 * auth:  carrey
 * date: 16-11-22.
 * desc:
 */

public class RegistrationDoctorDetailDescActivity extends BaseActivity {

    public static final String DOCTOR_DESC = "doctor_desc";
    private TextView mContent;

    @Override
    protected void initViews() {
        setContentView(R.layout.register_doctor_detail_desc);
        mTitleBar.setTitle("医生详情");
        mContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mContent.setText("      "+getIntent().getStringExtra(DOCTOR_DESC));

    }
}
