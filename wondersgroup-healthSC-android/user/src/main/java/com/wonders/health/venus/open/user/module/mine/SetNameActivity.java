package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;

/**
 * 类描述：修改昵称
 * 创建人：tanghaihua
 * 创建时间：5/30/16 8:06 PM
 */
public class SetNameActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText mEt_nickname;
    private Button mBtn_confirm;
    private String mNickName;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_nickname);
        mEt_nickname = (ClearEditText) findViewById(R.id.et_nickname);
        mBtn_confirm = (Button) findViewById(R.id.btn_confirm);
        mBtn_confirm.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("编辑姓名");

        if (!TextUtils.isEmpty(UserManager.getInstance().getUser().name)) {
            mNickName = UserManager.getInstance().getUser().name;
        } else if (!TextUtils.isEmpty(UserManager.getInstance().getUser().nickname)) {
            mNickName = UserManager.getInstance().getUser().nickname;
        }
        if(!TextUtils.isEmpty(mNickName)){
            mEt_nickname.setText(mNickName);
            mEt_nickname.setSelection(mNickName.length());
        }

        mBtn_confirm.setEnabled(TextUtils.isEmpty(mNickName) ? false : true);

        mEt_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBtn_confirm.setEnabled(!mEt_nickname.getText().toString().isEmpty());
            }
        });
        mEt_nickname.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
                if(source.equals(" ")){
                    return "";
                }else{
                    return null;
                }
            }
        }});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                updateUser();
                break;
        }
    }

    /**
     * 保存用户信息
     */
    private void updateUser() {
        final String nickname = mEt_nickname.getText().toString();
        if (nickname.length() > 20) {
            UIUtil.toastShort(this, "昵称不能超过20个字~");
            return;
        }

        User user = UserManager.getInstance().getUser();
        user.nickname = nickname;

        UserManager.getInstance().updateUser(user, new ResponseCallback<User>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(SetNameActivity.this);
            }

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                finish();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (!isFinishing()) {
                    UIUtil.hideProgressBar(SetNameActivity.this);
                }
            }
        });
    }
}
