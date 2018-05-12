package com.wonders.health.venus.open.doctor.module.mine;
/*
 * Created by sunning on 2017/6/2.
 */

import android.os.Bundle;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;

public class SettingActivity extends BaseActivity{
    @Override
    protected void initViews() {
        setContentView(R.layout.layout_setting);
        mTitleBar.setTitle(getString(R.string.setting_title));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
