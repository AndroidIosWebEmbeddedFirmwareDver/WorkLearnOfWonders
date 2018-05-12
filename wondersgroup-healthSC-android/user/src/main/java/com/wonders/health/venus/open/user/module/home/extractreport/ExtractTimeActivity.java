package com.wonders.health.venus.open.user.module.home.extractreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractTimeEntity;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractTimeActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXT_TIME = "key_time";


    private RadioGroup rg_time;

    private ExtractTimeEntity result;

    @Override
    protected void initViews() {

        setContentView(R.layout.activity_extract_time_layout);
        mTitleBar.setTitle("选择时间段");
        rg_time = (RadioGroup) findViewById(R.id.rg_time);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (ExtractTimeEntity) bundle.getSerializable(EXT_TIME);
        }
        initChecked();

        rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_today:
                        setData("0", "今天");
//                        UIUtil.toastShort(ExtractTimeActivity.this, "today");
                        break;
                    case R.id.rb_three:
                        setData("1", "最近三天");
//                        UIUtil.toastShort(ExtractTimeActivity.this, "three");
                        break;
                    case R.id.rb_week:
                        setData("2", "最近一周");
//                        UIUtil.toastShort(ExtractTimeActivity.this, "week");
                        break;
                    case R.id.rb_month:
                        setData("3", "最近一个月");
//                        UIUtil.toastShort(ExtractTimeActivity.this, "month");
                        break;
                }

            }


        });
    }

    private void initChecked() {
        if (result != null) {
            if (result.code.equals("0")) {
                rg_time.check(R.id.rb_today);
            } else if (result.code.equals("1")) {
                rg_time.check(R.id.rb_three);
            } else if (result.code.equals("2")) {
                rg_time.check(R.id.rb_week);
            } else if (result.code.equals("3")) {
                rg_time.check(R.id.rb_month);
            }
        }
    }

    private void setData(String i, String r) {
        if (result == null) {
            result = new ExtractTimeEntity();
        }
        result.code = i;
        result.name = r;

        setResult(RESULT_OK, new Intent().putExtra(ExtractActivity.KEY_EXTRACT_ENTITY, result));
        finish();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

}
