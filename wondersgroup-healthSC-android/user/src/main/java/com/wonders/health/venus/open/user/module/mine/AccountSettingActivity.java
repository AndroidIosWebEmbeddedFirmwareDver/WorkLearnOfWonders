package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.UserUpdateEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;

/**
 * 类描述：账号设置Activity
 * 创建人：hhw
 * 创建时间：2016/11/7 11:14
 */

public class AccountSettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTv_account, mTv_pwd, mTv_mobile, mTv_action;

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_account_setting);

        mTitleBar.setTitle("账号设置");
        mTv_account = (TextView) findViewById(R.id.tv_account);
        mTv_pwd = (TextView) findViewById(R.id.tv_pwd);
        mTv_mobile = (TextView) findViewById(R.id.tv_mobile);
        mTv_action = (TextView) findViewById(R.id.tv_action);
//        findViewById(R.id.ll_account).setOnClickListener(this);
        findViewById(R.id.ll_modify_pwd).setOnClickListener(this);
        findViewById(R.id.ll_bind_mobile).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showData();
    }

    private void showData() {
        User user = UserManager.getInstance().getUser();
        if (user != null) {
            mTv_account.setText(StringUtil.getCheckedMobile(user.mobile));
            if (user.password_complete) {
                mTv_action.setText("修改密码");
            } else {
                mTv_action.setText("设置密码");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_account: //账号
                break;
            case R.id.ll_modify_pwd: //修改密码(设置密码)
                if (UserManager.getInstance().getUser() != null) {
                    if (UserManager.getInstance().getUser().password_complete) {
                        startActivity(new Intent(AccountSettingActivity.this, ModifyPwdActivity.class));
                    } else {
                        startActivity(new Intent(AccountSettingActivity.this, PwdSetActivity.class));
                    }
                }
                break;
            case R.id.ll_bind_mobile: //绑定手机
                break;
        }
    }

    public void onEvent(UserUpdateEvent event) {
        showData();
    }
}
