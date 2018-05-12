package com.wonders.health.venus.open.user.module.mine;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.util.UmengClickAgent;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 类描述：
 * 创建人：chenbo
 * 创建时间：2015/6/29 11:17
 */
public class ThirdPartLoginLayout extends LinearLayout implements View.OnClickListener {
    private TextView mWechatLogin;
    private TextView mQQLogin;
    private BaseActivity mActivity;

    private UserManager mUserManager;

    public ThirdPartLoginLayout(Context context) {
        super(context);
        init();
    }

    public ThirdPartLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ThirdPartLoginLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ThirdPartLoginLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_thirdpart_login, this);

        mWechatLogin = (TextView) findViewById(R.id.tv_wechat_login);
        mQQLogin = (TextView) findViewById(R.id.tv_qq_login);

        mWechatLogin.setOnClickListener(this);
        mQQLogin.setOnClickListener(this);

        UIUtil.setTouchEffect(mWechatLogin);
        UIUtil.setTouchEffect(mQQLogin);

        mUserManager = UserManager.getInstance();
    }

    public void setParent(BaseActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat_login:
                if (mActivity instanceof LoginActivity) {
                    UmengClickAgent.onEvent(mActivity, "JkyLogInWechat");
                } else {
                    UmengClickAgent.onEvent(mActivity, "JkyRegistrationLogInWechat");
                }
                mUserManager.thirdPartLogin(Wechat.NAME, new LoginCallback() {
                    @Override
                    public void onFailure(Exception e) {
                        super.onFailure(e);
                        if (e instanceof HttpException) {
                            HttpException e1 = (HttpException) e;
                            UIUtil.toastShort(getContext(), e1.getMessage());
                        }
                    }
                });
                break;
            case R.id.tv_qq_login:
                if (mActivity instanceof LoginActivity) {
                    UmengClickAgent.onEvent(mActivity, "JkyLogInQQ");
                } else {
                    UmengClickAgent.onEvent(mActivity, "JkyRegistrationLogInQQ");
                }
                mUserManager.thirdPartLogin(QQ.NAME, new LoginCallback());
                break;
            default:
                break;
        }
    }

    class LoginCallback extends ResponseCallback<User> {

        @Override
        public void onStart() {
            super.onStart();
            UIUtil.showProgressBar(mActivity);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtil.hideProgressBar(mActivity);
        }
    }
}
