package com.wonders.health.venus.open.user.module.mine.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AuthStatus;
import com.wondersgroup.hs.healthcloud.common.util.UmengClickAgent;

/**
 * Created by wang on 2016/8/11 17:01.
 * Class Name :AuthResultActivity
 * 实名认证结果页
 */
public class AuthResultActivity extends BaseActivity implements View.OnClickListener {

    public static String AUTH_ENTITY = "auth_entity";

    private AuthStatus authStatus;

    private ImageView ivCheckStatus;
    private TextView tvCheckStatus;
    private TextView tvCheckExplain;
    private RelativeLayout rlAuthInfo;
    private TextView tvName;
    private TextView tvCardID;
    private TextView tvCheck;
    private LinearLayout llFail;
    private TextView tvFailReason;
    private Button btnReAuth;
    private LinearLayout llCheck;



    @Override
    protected void initViews() {
        mTitleBar.setTitle("实名认证");
        setContentView(R.layout.activity_auth_result);
        ivCheckStatus = (ImageView) findViewById(R.id.iv_check_state);
        tvCheckStatus = (TextView) findViewById(R.id.tv_auth_progress);
        tvCheckExplain = (TextView) findViewById(R.id.tv_check_explain);

        rlAuthInfo = (RelativeLayout) findViewById(R.id.rl_auth_info);
        tvName = (TextView) findViewById(R.id.tv_real_name);
        tvCardID = (TextView) findViewById(R.id.tv_cardID);
        tvCheck = (TextView) findViewById(R.id.tv_check);
        llCheck =(LinearLayout) findViewById(R.id.ll_check);
        llCheck.setVisibility(View.GONE);

        llFail = (LinearLayout) findViewById(R.id.ll_authentication_failure);
        tvFailReason = (TextView) findViewById(R.id.tv_check_failure_reason);
        btnReAuth = (Button) findViewById(R.id.btn_reauthe);
        btnReAuth.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        authStatus = (AuthStatus) getIntent().getSerializableExtra(AUTH_ENTITY);
        setView();
    }


    private void setView() {
        if (authStatus != null) {

            if (authStatus.verify == AuthStatus.verify_success) {
                ivCheckStatus.setImageResource(R.mipmap.ic_auth_check_pass);
                tvCheckStatus.setText(R.string.auth_check_success);
                tvCheckExplain.setVisibility(View.GONE);
                tvName.setText(authStatus.name);
                tvCardID.setText(authStatus.idcard);
                tvCheck.setText(authStatus.statusSpec);

            } else if (authStatus.verify == AuthStatus.verify_fail) {
                rlAuthInfo.setVisibility(View.GONE);
                llFail.setVisibility(View.VISIBLE);
                ivCheckStatus.setImageResource(R.mipmap.ic_auth_check_fail);
                tvCheckStatus.setText(R.string.auth_check_fail);
                tvCheckExplain.setVisibility(View.GONE);
                tvFailReason.setText(authStatus.msg);

            } else if (authStatus.verify == AuthStatus.verify_checking) {
                rlAuthInfo.setVisibility(View.VISIBLE);
                llFail.setVisibility(View.GONE);
                ivCheckStatus.setImageResource(R.mipmap.ic_auth_checking);
                tvCheckStatus.setText(R.string.auth_checking);
                tvCheckExplain.setVisibility(View.VISIBLE);
                tvCheckExplain.setText(authStatus.msg);
                tvName.setText(authStatus.name);
                tvCardID.setText(authStatus.idcard);
                tvCheck.setText(authStatus.statusSpec);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_reauthe) {
            UmengClickAgent.onEvent(this,"JkyBZMyReApplyForRealNameAuthentication");
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
