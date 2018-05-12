package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.UserUpdateEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;

import de.greenrobot.event.EventBus;

import static com.wonders.health.venus.open.user.R.id.edit_code;
import static com.wonders.health.venus.open.user.R.id.edit_mobile;

public class PwdSetActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private ClearEditText mEditMobile;
    private ClearEditText mEditCode;
    private TextView mTvGetCode;
    private ClearEditText mEditPwd;
    private ImageView mIvSeePwd;
    private ClearEditText mEditPwd1;
    private ImageView mIvSeePwd1;
    private TextView mTvPwdErr;
    private Button mBtnSubmit;
    private View mDiMobile;
    private View mDiCode;
    private View mDiPwd;
    private View mDiPwd1;

    private CountDownTimer mTimer;
    private UserManager mUserManager;
    private int[] mDiColors;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_pwd_set);
        mEditMobile = (ClearEditText) findViewById(edit_mobile);
        mEditCode = (ClearEditText) findViewById(edit_code);
        mTvGetCode = (TextView) findViewById(R.id.tv_get_code);
        mEditPwd = (ClearEditText) findViewById(R.id.edit_pwd);
        mIvSeePwd = (ImageView) findViewById(R.id.iv_see_pwd);
        mEditPwd1 = (ClearEditText) findViewById(R.id.edit_pwd1);
        mIvSeePwd1 = (ImageView) findViewById(R.id.iv_see_pwd1);
        mTvPwdErr = (TextView) findViewById(R.id.tv_pwd_err);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mDiCode = findViewById(R.id.di_code);
        mDiMobile = findViewById(R.id.di_mobile);
        mDiPwd = findViewById(R.id.di_pwd);
        mDiPwd1 = findViewById(R.id.di_pwd1);

        mBtnSubmit.setOnClickListener(this);
        mTvGetCode.setOnClickListener(this);
        mIvSeePwd.setOnClickListener(this);
        mIvSeePwd1.setOnClickListener(this);

        mTitleBar.setTitle("设置密码");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUserManager = UserManager.getInstance();
        mDiColors = new int[]{getResources().getColor(R.color.tc3),
                getResources().getColor(R.color.bc7),
                getResources().getColor(R.color.sbc2)};
        mEditMobile.setText(StringUtil.getCheckedMobile(UserManager.getInstance().getUser().mobile));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void getVerifyCode() {
        String mobile = UserManager.getInstance().getUser().mobile;
        if (TextUtils.isEmpty(mobile)) {
            mTvPwdErr.setText("手机号不能为空");
            return;
        }
        mUserManager.getVerifyCode(mobile, UserManager.CODE_FOR_SET_PWD, new DialogResponseCallback<String>(this) {

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                startCountTime();
            }
        });
    }

    private void startCountTime() {
        mTvGetCode.setEnabled(false);
        mTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                mTvGetCode.setText(l / 1000 + "S");
            }

            @Override
            public void onFinish() {
                mTvGetCode.setEnabled(true);
                mTvGetCode.setText("获取验证码");
            }
        }.start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mEditCode) {
            mDiCode.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
        } else if (v == mEditMobile) {
            mDiMobile.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
        } else if (v == mEditPwd) {
            mDiPwd.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
            mIvSeePwd.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (v == mEditPwd1) {
            mDiPwd1.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
            mIvSeePwd1.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_get_code:
                getVerifyCode();
                break;
            case R.id.iv_see_pwd:
                mIvSeePwd.setSelected(!mIvSeePwd.isSelected());
                int index = mEditPwd.getSelectionStart();
                mEditPwd.setTransformationMethod(
                        mIvSeePwd.isSelected() ? HideReturnsTransformationMethod.getInstance()
                                : PasswordTransformationMethod.getInstance());
                mEditPwd.setSelection(index);
                break;
            case R.id.iv_see_pwd1:
                mIvSeePwd1.setSelected(!mIvSeePwd1.isSelected());
                int index1 = mEditPwd1.getSelectionStart();
                mEditPwd1.setTransformationMethod(
                        mIvSeePwd1.isSelected() ? HideReturnsTransformationMethod.getInstance()
                                : PasswordTransformationMethod.getInstance());
                mEditPwd1.setSelection(index1);
                break;
        }
    }

    private void submit() {
        User user = new User();
        user.mobile = UserManager.getInstance().getUser().mobile;
        user.pwd = mEditPwd.getText().toString();
        user.verify_code = mEditCode.getText().toString();
        String confirmPwd = mEditPwd1.getText().toString();
        mTvPwdErr.setText("");
        if (TextUtils.isEmpty(user.mobile)) {
            mTvPwdErr.setText("手机号不能为空");
            return;
        }
        if (!StringUtil.isMobileNumber(user.mobile)) {
            mTvPwdErr.setText("手机号码格式错误");
            return;
        }
        if (TextUtils.isEmpty(user.verify_code)) {
            mTvPwdErr.setText("验证码不能为空");
            return;
        }
        if (!StringUtil.isVerifyCodeValidate(user.verify_code)) {
            mTvPwdErr.setText("验证码格式错误");
            return;
        }
        if (TextUtils.isEmpty(user.pwd)) {
            mTvPwdErr.setText("密码不能为空");
            return;
        }
        if (!StringUtil.isPasswordValidate(user.pwd)) {
            mTvPwdErr.setText("密码（长度6-16位，字母与数字组合）");
            return;
        }
        if (!user.pwd.equals(confirmPwd)) {
            mTvPwdErr.setText("两次输入的密码不一致");
            return;
        }
        mUserManager.resetPassword(user, new DialogResponseCallback<String>(this) {
            @Override
            public void onFinish() {
                super.onFinish();
                if (mSuccess) {
                    UIUtil.toastShort(PwdSetActivity.this, "设置成功");
                    EventBus.getDefault().post(new UserUpdateEvent());
                    finish();
                }
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
