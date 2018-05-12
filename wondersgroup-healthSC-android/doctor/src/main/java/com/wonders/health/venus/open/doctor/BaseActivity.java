package com.wonders.health.venus.open.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wonders.health.venus.open.doctor.entity.AccountChangeEvent;
import com.wonders.health.venus.open.doctor.entity.User;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.module.MainActivity;
import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.entity.event.TokenEmptyEvent;
import com.wondersgroup.hs.healthcloud.common.entity.event.TokenExpiredEvent;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.util.UmengClickAgent;

import de.greenrobot.event.EventBus;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/20 17:30
 */
public abstract class BaseActivity extends CommonActivity {
    /**
     * 类描述：
     * 创建人：Bob
     * 创建时间：2016/7/20 17:30
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengClickAgent.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengClickAgent.stop(this);
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        if (isStatusBarDarkMode()) {
            mTitleBar.setDividerColor(getResources().getColor(R.color.divider_color));
        }
    }

    @Override
    protected boolean isValidate() {
//        // 如果用户没有登录，并且这个界面必须检查登录
        if (needCheckLogin() && !UserManager.getInstance().isLogin()) {
//            startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return false;
        }
        return true;
    }

    protected boolean needCheckLogin() {
        return false;
    }

    @Override
    protected boolean isStatusBarDarkMode() {
        return true;
    }

    // 如果token过期，删除本地用户信息
    public void onEvent(TokenExpiredEvent event) {
        UIUtil.toastShort(this, event.response.msg);
        User user = null;
        if (event.response.data instanceof JSONObject) {
            user = JSON.parseObject(event.response.data.toString(), User.class);
        }
//        UserManager.getInstance().dealLogout(user);
//        if (!(this instanceof MainActivity) && !(this instanceof SplashActivity)) {
//            toMain();
//            finish();
//        }
//        if (needCheckLogin()) {
//            //本地判断是否设置过密码
//            startActivity(new Intent(this, LoginActivity.class));
//        }
    }

    public void onEvent(TokenEmptyEvent event) {
        User user = null;
        if (event.data instanceof JSONObject) {
            user = JSON.parseObject(event.data.toString(), User.class);
        }
//        UserManager.getInstance().dealLogout(user);
//        if (!(this instanceof MainActivity) && !(this instanceof SplashActivity)) {
//            toMain();
//            finish();
//        }
//        if (needCheckLogin()) {
//            //本地判断是否设置过密码
//            startActivity(new Intent(this, LoginActivity.class));
//        }
    }

    public void onEvent(AccountChangeEvent event) {
        if (!event.isLogin) {
//            if (!(this instanceof MainActivity) && !(this instanceof SplashActivity)) {
//                toMain();
//                finish();
//            }
//            if (needCheckLogin()) {
//                //本地判断是否设置过密码
//                startActivity(new Intent(this, LoginActivity.class));
//            }
        }
    }

    @Override
    public void onBackPressed() {
        toMain();
        super.onBackPressed();
    }

    public boolean toMain() {
        if (isTaskRoot() && !(this instanceof MainActivity)) {
            startActivity(new Intent(BaseActivity.this, MainActivity.class));
            return true;
        }
        return false;
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (this.getCurrentFocus() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
