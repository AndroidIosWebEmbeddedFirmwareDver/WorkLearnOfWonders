package com.wonders.health.venus.open.doctor.module.mine;
/*
 * Created by sunning on 2017/5/26.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.module.MainActivity;
import com.wonders.health.venus.open.doctor.module.consultation.ChatConstant;
import com.wonders.health.venus.open.doctor.module.consultation.ChatListActivity;
import com.wonders.health.venus.open.doctor.module.consultation.MyChatActivity;
import com.wondersgroup.hs.healthcloud.common.huanxin.EaseConstant;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.doctor_login_btn)
    Button loginBtn;
    @BindView(R.id.edit_mobile)
    ClearEditText mobile;
    @BindView(R.id.edit_password)
    ClearEditText pwd;
    @BindView(R.id.ib_bg_phone)
    ImageView bgPhone;
    @BindView(R.id.ib_bg_pwd)
    ImageView bgPwd;


    @Override
    protected void initViews() {
        mTitleBar.setVisibility(View.GONE);
        setContentView(R.layout.doctor_login);

    }

    @OnClick(R.id.doctor_login_btn)
    public void startLogin() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setIconStatus(mobile, bgPhone, R.mipmap.ic_login_no_, R.mipmap.ic_login_no_enable);
        setIconStatus(pwd, bgPwd, R.mipmap.ic_login_pwd, R.mipmap.ic_login_pwd_enable_);
    }


    private void setIconStatus(EditText editText, final ImageView iconView, final int resSelect, final int resUnSelect) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iconView.setImageResource(resSelect);
                } else {
                    iconView.setImageResource(resUnSelect);
                }
            }
        });
    }


}
