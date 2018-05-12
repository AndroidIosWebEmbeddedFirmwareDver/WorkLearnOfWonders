package com.wonders.health.venus.open.doctor.module.referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;

/**
 * 申请转诊------- 提取患者信息
 * Created by win10 on 2017/6/7.
 */

public class ApplyExtractActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_extract_name,tv_extract_card;
    private Button bt_submit;

    @Override
    protected void initViews() {
        setContentView(R.layout.apply_extract_activity);
        tv_extract_name = (TextView) findViewById(R.id.tv_extract_name);
        tv_extract_card = (TextView) findViewById(R.id.tv_extract_card);
        bt_submit = (Button) findViewById(R.id.bt_submit);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_extract_name:
                //选择器
                break;

            case R.id.bt_submit:
                startActivity(new Intent(ApplyExtractActivity.this,ApplySuffererInfoActivity.class));
                break;
        }
    }
}
