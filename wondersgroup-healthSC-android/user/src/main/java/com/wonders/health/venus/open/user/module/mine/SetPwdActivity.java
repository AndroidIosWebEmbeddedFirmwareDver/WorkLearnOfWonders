package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;


/**
 * chenbo
 * 设置密码
 */
public class SetPwdActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener {
    public static final String EXTRA_CODE = "code";
    private EditText mEdit_password;
    private EditText mEdit_password_confirm;
    private View mDiPwd;
    private View mDiPwd1;
    private ImageView mIvSeePwd;
    private ImageView mIvSeePwd1;
    private TextView mTvPwdErr;
    private Button mBtn_submit;

    private int[] mDiColors;
    private User mUser;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_set_pwd);

        mEdit_password = (EditText) findViewById(R.id.edit_pwd);
        mEdit_password_confirm = (EditText) findViewById(R.id.edit_pwd1);
        mBtn_submit = (Button) findViewById(R.id.btn_submit);
        mDiPwd = findViewById(R.id.di_pwd);
        mDiPwd1 = findViewById(R.id.di_pwd1);
        mIvSeePwd = (ImageView) findViewById(R.id.iv_see_pwd);
        mIvSeePwd1 = (ImageView) findViewById(R.id.iv_see_pwd1);
        mTvPwdErr = (TextView) findViewById(R.id.tv_pwd_err);

        mEdit_password.setOnFocusChangeListener(this);
        mEdit_password_confirm.setOnFocusChangeListener(this);

        mBtn_submit.setOnClickListener(this);
        mIvSeePwd.setOnClickListener(this);
        mIvSeePwd1.setOnClickListener(this);

        mEdit_password.addTextChangedListener(new LoginTextWatcher());
        mEdit_password_confirm.addTextChangedListener(new LoginTextWatcher());

        mTitleBar.setLeftVisible(false);
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.bc7));
        mTitleBar.setTitleColor(getResources().getColor(R.color.tc0));
        mTitleBar.setActionTextColor(getResources().getColor(R.color.tc0));
        mTitleBar.setTitle("设置密码");
        mTitleBar.addAction(new TitleBar.TextAction("跳过") {
            @Override
            public void performAction(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUser = new User();
        mUser.verify_code = getIntent().getStringExtra(EXTRA_CODE);
        if (TextUtils.isEmpty(mUser.verify_code)) {
            finish();
        }
        mDiColors = new int[] {getResources().getColor(R.color.tc3),
                getResources().getColor(R.color.bc7),
                getResources().getColor(R.color.sbc2)};
        mBtn_submit.setEnabled(isSubmitBtnEnable());
    }

    @Override
    public void onClick(View v) {
        if (v == mBtn_submit) {
            modifyPassword();
        } else if (v == mIvSeePwd) {
            mIvSeePwd.setSelected(!mIvSeePwd.isSelected());
            int index = mEdit_password.getSelectionStart();
            mEdit_password.setTransformationMethod(
                    mIvSeePwd.isSelected() ? HideReturnsTransformationMethod.getInstance()
                            : PasswordTransformationMethod.getInstance());
            mEdit_password.setSelection(index);
        } else if (v == mIvSeePwd1) {
            mIvSeePwd1.setSelected(!mIvSeePwd1.isSelected());
            int index1 = mEdit_password_confirm.getSelectionStart();
            mEdit_password_confirm.setTransformationMethod(
                    mIvSeePwd1.isSelected() ? HideReturnsTransformationMethod.getInstance()
                            : PasswordTransformationMethod.getInstance());
            mEdit_password_confirm.setSelection(index1);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mEdit_password) {
            mDiPwd.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
            mIvSeePwd.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (v == mEdit_password_confirm) {
            mDiPwd1.setBackgroundColor(hasFocus ? mDiColors[1] : mDiColors[0]);
            mIvSeePwd1.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    private void modifyPassword() {
        mDiPwd1.setBackgroundColor(mEdit_password_confirm.isFocused() ? mDiColors[1] : mDiColors[0]);
        mDiPwd.setBackgroundColor(mEdit_password.isFocused() ? mDiColors[1] : mDiColors[0]);
        mTvPwdErr.setText("");
        final String pwd = mEdit_password.getText().toString();
        final String confirmPwd = mEdit_password_confirm.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            mDiPwd.setBackgroundColor(mDiColors[2]);
            mTvPwdErr.setText("密码不能为空");
            return;
        }
        if (!StringUtil.isPasswordValidate(pwd)) {
            mDiPwd.setBackgroundColor(mDiColors[2]);
            mTvPwdErr.setText("密码（长度6-16位，字母与数字组合）");
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            mDiPwd1.setBackgroundColor(mDiColors[2]);
            mTvPwdErr.setText("两次输入的密码不一致");
            return;
        }
        mUser.pwd = pwd;
        mUser.mobile = UserManager.getInstance().getUser().mobile;
        UserManager.getInstance().resetPassword(mUser, new DialogResponseCallback<String>(this) {

            @Override
            public void onSuccess(String user) {
                super.onSuccess(user);
                finish();
            }
        });
    }


    @Override
    protected boolean isStatusBarDarkMode() {
        return false;
    }

    private boolean isSubmitBtnEnable() {
        boolean result = !TextUtils.isEmpty(mEdit_password.getText().toString());
        result &= (!TextUtils.isEmpty(mEdit_password_confirm.getText().toString()));
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
            mBtn_submit.setEnabled(isSubmitBtnEnable());
        }

    }
}
