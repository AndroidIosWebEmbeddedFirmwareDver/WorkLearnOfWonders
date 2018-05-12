package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.pay.PayOrderInfo;
import com.wonders.health.venus.open.user.component.pay.PayUtil;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.OrderManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.Callback;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;


public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private RadioButton mRdLoginByCode;
    private RadioButton mRdLoginByPwd;
    private RadioGroup mTabLogin;
    private ClearEditText mEditMobile;
    private ClearEditText mEditCode;
    private TextView mTvGetCode;
    private TextView mTvCodeErr;
    private ClearEditText mEditPwd;
    private ImageView mIvSeePwd;
    private TextView mTvPwdErr;
    private Button mBtnLogin;
    private TextView mTvForgetPwd;
    private TextView mTvRegisterUrl;
    private RelativeLayout mRlGetCode;
    private RelativeLayout mRlPwd;
    private View mDiMobile;
    private View mDiCode;
    private View mDiPwd;
    private TextView mTvHotLine;

    private CountDownTimer mTimer;
    private UserManager mUserManager;

    private int[] mDiColors;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);
        mRdLoginByCode = (RadioButton) findViewById(R.id.rd_login_by_code);
        mRdLoginByPwd = (RadioButton) findViewById(R.id.rd_login_by_pwd);
        mTabLogin = (RadioGroup) findViewById(R.id.tab_login);
        mEditMobile = (ClearEditText) findViewById(R.id.edit_mobile);
        mEditCode = (ClearEditText) findViewById(R.id.edit_code);
        mEditPwd = (ClearEditText) findViewById(R.id.edit_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mRlGetCode = (RelativeLayout) findViewById(R.id.rl_get_code);
        mRlPwd = (RelativeLayout) findViewById(R.id.rl_pwd);
        mTvGetCode = (TextView) findViewById(R.id.tv_get_code);
        mIvSeePwd = (ImageView) findViewById(R.id.iv_see_pwd);
        mTvCodeErr = (TextView) findViewById(R.id.tv_code_err);
        mTvPwdErr = (TextView) findViewById(R.id.tv_pwd_err);
        mTvRegisterUrl = (TextView) findViewById(R.id.tv_regist_url);
        mDiCode = findViewById(R.id.di_code);
        mDiMobile = findViewById(R.id.di_mobile);
        mDiPwd = findViewById(R.id.di_pwd);
        mTvHotLine = (TextView) findViewById(R.id.tv_hot_line);

        mTitleBar.setBackgroundColor(getResources().getColor(R.color.bc7));
        mTitleBar.setLeftImageResource(R.mipmap.ic_close_light);
        mTvForgetPwd.getPaint().setFlags(mTvForgetPwd.getPaint().getFlags() | Paint.UNDERLINE_TEXT_FLAG);
        bindRegisterUrlText();

        mBtnLogin.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
        mTvGetCode.setOnClickListener(this);
        mIvSeePwd.setOnClickListener(this);
        mTvHotLine.setOnClickListener(this);

        mEditMobile.setOnFocusChangeListener(this);
        mEditPwd.setOnFocusChangeListener(this);
        mEditCode.setOnFocusChangeListener(this);

        mTabLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                focusView();
                mTvPwdErr.setText("");
                mTvCodeErr.setText("");
                if (checkedId == R.id.rd_login_by_code) {
                    mRlGetCode.setVisibility(View.VISIBLE);
                    mRlPwd.setVisibility(View.GONE);
                    mTvForgetPwd.setVisibility(View.GONE);
                    mTvRegisterUrl.setVisibility(View.VISIBLE);
                } else {
                    mRlGetCode.setVisibility(View.GONE);
                    mRlPwd.setVisibility(View.VISIBLE);
                    mTvForgetPwd.setVisibility(View.VISIBLE);
                    mTvRegisterUrl.setVisibility(View.GONE);
                }
                mBtnLogin.setEnabled(isSubmitBtnEnable());
            }
        });

        mEditMobile.addTextChangedListener(new LoginTextWatcher());
        mEditCode.addTextChangedListener(new LoginTextWatcher());
        mEditPwd.addTextChangedListener(new LoginTextWatcher());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUserManager = UserManager.getInstance();
        mDiColors = new int[] {getResources().getColor(R.color.tc3),
                getResources().getColor(R.color.bc7),
                getResources().getColor(R.color.sbc2)};
        String mobile = PrefUtil.getString(this, Constant.KEY_USER_MOBILE);
        if (!TextUtils.isEmpty(mobile)) {
            mEditMobile.setText(mobile);
            mEditMobile.setSelection(mobile.length());
        }
        if (!TextUtils.isEmpty(AppConfigManager.getInstance().getAppConfig().consumerHotline)) {
            mTvHotLine.setText(AppConfigManager.getInstance().getAppConfig().consumerHotline);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                submit();
//                test();
                break;
            case R.id.iv_see_pwd:
                mIvSeePwd.setSelected(!mIvSeePwd.isSelected());
                int index = mEditPwd.getSelectionStart();
                mEditPwd.setTransformationMethod(
                        mIvSeePwd.isSelected() ? HideReturnsTransformationMethod.getInstance()
                                : PasswordTransformationMethod.getInstance());
                mEditPwd.setSelection(index);
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.tv_get_code:
                getVerifyCode();
                break;
            case R.id.tv_hot_line:
                SchemeUtil.callPhone(this, mTvHotLine.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mEditCode) {
            mDiCode.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
        } else if (v == mEditMobile) {
            mDiMobile.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
        } else if (v == mEditPwd) {
            mDiPwd.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
        }
    }

    private void getVerifyCode() {
        String mobile = mEditMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            mDiMobile.setBackgroundColor(mDiColors[2]);
            mTvCodeErr.setText("手机号不能为空");
            return;
        }
        if (!StringUtil.isMobileNumber(mobile)) {
            mDiMobile.setBackgroundColor(mDiColors[2]);
            mTvCodeErr.setText("请输入正确的11位手机号码");
            return;
        }
        mUserManager.getVerifyCode(mobile, UserManager.CODE_FOR_FAST_LOGIN, new DialogResponseCallback<String>(this) {

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                startCountTime();
            }
        });
    }

    private void submit() {
        User user = new User();
        user.mobile = mEditMobile.getText().toString();
        focusView();
        mTvPwdErr.setText("");
        mTvCodeErr.setText("");
        if (!StringUtil.isMobileNumber(user.mobile)) {
            mDiMobile.setBackgroundColor(mDiColors[2]);
            mTvCodeErr.setText("请输入正确的11位手机号码");
            mTvPwdErr.setText("请输入正确的11位手机号码");
            return;
        }
        if (mRdLoginByCode.isChecked()) {
            user.verify_code = mEditCode.getText().toString();
            if (TextUtils.isEmpty(user.verify_code)) {
                mDiCode.setBackgroundColor(mDiColors[2]);
                mTvCodeErr.setText("验证码不能为空");
                return;
            }
            if (!StringUtil.isVerifyCodeValidate(user.verify_code)) {
                mDiCode.setBackgroundColor(mDiColors[2]);
                mTvCodeErr.setText("验证码格式不正确");
                return;
            }
            UserManager.getInstance().fastLogin(user, new DialogResponseCallback<User>(this) {
                @Override
                public void onSuccess(User o) {
                    super.onSuccess(o);
                    if (o.first_login) {
                        mActivity.startActivity(
                                new Intent(mActivity, SetPwdActivity.class)
                                        .putExtra(SetPwdActivity.EXTRA_CODE, mEditCode.getText().toString()));
                    }
                }
            });
        } else {
            user.pwd = mEditPwd.getText().toString();
            if (TextUtils.isEmpty(user.pwd)) {
                mDiPwd.setBackgroundColor(mDiColors[2]);
                mTvPwdErr.setText("密码不能为空");
                return;
            }
            if (!StringUtil.isPasswordValidate(user.pwd)) {
                mDiPwd.setBackgroundColor(mDiColors[2]);
                mTvPwdErr.setText("密码（长度6-16位，字母与数字组合）");
                return;
            }

            UserManager.getInstance().login(user, new DialogResponseCallback(this));
        }
    }

    private void bindRegisterUrlText() {
        String text = mTvRegisterUrl.getText().toString();
        int start = text.indexOf("《");
        int end = text.indexOf("》") + 1;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (AppConfigManager.getInstance().getAppConfig() != null) {
                    SchemeUtil.startActivity(LoginActivity.this, AppConfigManager.getInstance().getAppConfig().userAgreement);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false); //去掉下划线
            }
        };
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tc5)), start, end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvRegisterUrl.setText(spannableString);
        mTvRegisterUrl.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void focusView() {
        mDiCode.setBackgroundColor(mEditCode.isFocused() ? mDiColors[1] : mDiColors[0]);
        mDiMobile.setBackgroundColor(mEditMobile.isFocused() ? mDiColors[1] : mDiColors[0]);
        mDiPwd.setBackgroundColor(mEditPwd.isFocused() ? mDiColors[1] : mDiColors[0]);
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
    public void onEvent(AccountChangeEvent event) {
        if (event.isLogin) {
            finish();
        }
    }

    @Override
    protected boolean isStatusBarDarkMode() {
        return false;
    }

    private void test() {
        new OrderManager().testGetOrder(new ResponseCallback<PayOrderInfo>() {
            @Override
            public void onSuccess(PayOrderInfo payInfo) {
                super.onSuccess(payInfo);
                PayUtil.payByWeixin(LoginActivity.this, payInfo, new Callback<String>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String s) {
                        UIUtil.toastShort(LoginActivity.this, s);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        UIUtil.toastShort(LoginActivity.this, e.toString());
                    }

                    @Override
                    public void onFinish() {

                    }
                });
//                    PayUtil.payByAli(LoginActivity.this, payInfo, new PayTypeActivity.PayCallback());
            }
        });
    }

    private boolean isSubmitBtnEnable() {
        boolean result = !TextUtils.isEmpty(mEditMobile.getText().toString());
        if (mRdLoginByCode.isChecked()) {
            result &= (!TextUtils.isEmpty(mEditCode.getText().toString()));
        } else {
            result &= (!TextUtils.isEmpty(mEditPwd.getText().toString()));
        }
        return result;
    }

    class LoginTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mBtnLogin.setEnabled(isSubmitBtnEnable());
        }

    }
}
