package com.wonders.health.venus.open.user.module.mine.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AuthStatus;
import com.wonders.health.venus.open.user.entity.event.VerificationEvent;
import com.wonders.health.venus.open.user.logic.AuthManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by wang on 2016/9/1 21:06.
 * Class Name :AuthChooseActivity
 */
public class AuthChooseActivity extends BaseActivity {


    private int wayFrom;
    private String schemeUrl;

    private AuthStatus authStatus;
    private AuthManager authManager;

    public static final String EXTRA_URL = "scheme_url";//要跳转的activity地址
    private static final String EXTRA_UID = "uid";
    public static final String WAY_FROM = "way_from";

    private AuthStatus authPush;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        authStatus = new AuthStatus();
        authManager = new AuthManager();
        wayFrom = getIntent().getIntExtra(WAY_FROM, 0);
        schemeUrl = getIntent().getStringExtra(EXTRA_URL);
//        String uid = getIntent().getStringExtra(EXTRA_UID);
//        if (!TextUtils.isEmpty(uid)) {//推送信息
//            authPush=new AuthStatus();
//            AuthStaPush();
//        }
        AuthSta();
    }


    private void AuthSta() {
        authManager.getAuthStatus(new ResponseCallback<AuthStatus>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(AuthChooseActivity.this, "加载中");
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
                UIUtil.hideProgressBar(AuthChooseActivity.this);
                finish();
            }


            @Override
            public void onSuccess(AuthStatus status) {
                super.onSuccess(status);
                UIUtil.hideProgressBar(AuthChooseActivity.this);
                if (status.success != null) {
                    AuthStatus at;
                    if (status.success) {//审核通过
                        at = authStatus.getAuthStatus(AuthStatus.verify_success, status);
                        //更新用户信息里面的实名认证状态
                        UserManager.getInstance().setVerifyState(status.status);
                        EventBus.getDefault().post(new VerificationEvent(true));
                        if (0 == wayFrom) {
                            startActivity(new Intent(AuthChooseActivity.this, AuthResultActivity.class).
                                    putExtra(AuthResultActivity.AUTH_ENTITY, at));
                            finish();
                        } else {
                            if (schemeUrl != null) {
                                SchemeUtil.startActivity(AuthChooseActivity.this, schemeUrl);
                            }
                            finish();
                        }
                    } else {
                        if (status.can_submit) {//审核失败
                            if (0 == wayFrom) {
                                at = authStatus.getAuthStatus(AuthStatus.verify_fail, status);
                                startActivity(new Intent(AuthChooseActivity.this, AuthResultActivity.class).
                                        putExtra(AuthResultActivity.AUTH_ENTITY, at));
                                finish();
                            } else {
                                showDialog(getResources().getString(R.string.auth_dialog_title), getResources().getString(R.string.auth_dialog_content), status, AuthStatus.verify_fail);
                            }

                        } else {//审核中
                            if (0 == wayFrom) {
                                at = authStatus.getAuthStatus(AuthStatus.verify_checking, status);
                                startActivity(new Intent(AuthChooseActivity.this, AuthResultActivity.class).
                                        putExtra(AuthResultActivity.AUTH_ENTITY, at));
                                finish();
                            } else {
                                showDialog(getResources().getString(R.string.auth_dialog_title), getResources().getString(R.string.auth_dialog_content), status, AuthStatus.verify_checking);
                            }

//
                        }
                    }
                } else {
                    if (0 == wayFrom) {
                        startActivity(new Intent(AuthChooseActivity.this, AuthActivity.class));
                        finish();
                    } else {
                        showDialog(getResources().getString(R.string.auth_dialog_title), getResources().getString(R.string.auth_dialog_content), status, AuthStatus.verify_to_verify);
                    }
                }

            }
        });

    }

    //认证提示框
    private void showDialog(String title, String content, final AuthStatus status, final int type) {
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_confirm, null);
        TextView tvTitle = (TextView) view3.findViewById(R.id.tv_confirm_title);
        TextView tvMsg = (TextView) view3.findViewById(R.id.tv_confirm_msg);
        Button btnConfirm = (Button) view3.findViewById(R.id.btn_confirm_right);
        Button btnCancel = (Button) view3.findViewById(R.id.btn_confirm_left);
        tvTitle.setText(title);
        btnCancel.setTextColor(getResources().getColor(R.color.tc4));
        tvMsg.setText(content);
        btnCancel.setText("暂不认证");
        btnConfirm.setText("立即认证");
        final Dialog dialog1 = UIUtil.showAlert(this, view3);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == AuthStatus.verify_checking) {
                    AuthStatus at = authStatus.getAuthStatus(AuthStatus.verify_checking, status);
                    startActivity(new Intent(AuthChooseActivity.this, AuthResultActivity.class).
                            putExtra(AuthResultActivity.AUTH_ENTITY, at));
                } else if (type == AuthStatus.verify_fail) {
                    AuthStatus at = authStatus.getAuthStatus(AuthStatus.verify_fail, status);
                    startActivity(new Intent(AuthChooseActivity.this, AuthResultActivity.class).
                            putExtra(AuthResultActivity.AUTH_ENTITY, at));
                } else if (type == AuthStatus.verify_to_verify) {
                    startActivity(new Intent(AuthChooseActivity.this, AuthActivity.class));
                }
                dialog1.dismiss();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                finish();
            }
        });
        dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog1.dismiss();
                finish();
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }
}
