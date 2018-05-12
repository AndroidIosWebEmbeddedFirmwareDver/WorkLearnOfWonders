package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * 类描述：意见反馈Activity
 * 创建人：hhw
 * 创建时间：2016/11/7 16:35
 */

public class SubmitSuggestActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEt_content;
    private EditText mEt_contact;
    private Button mBtn_submit;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_submit_suggest);

        mTitleBar.setTitle("意见反馈");
        mEt_content = (EditText) findViewById(R.id.et_content);
        mEt_contact = (EditText) findViewById(R.id.et_contact_way);
        mBtn_submit = (Button) findViewById(R.id.btn_submit);
        mBtn_submit.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(UserManager.getInstance().getUser().mobile))
            mEt_contact.setText(UserManager.getInstance().getUser().mobile);
    }

    /**
     * 提交意见
     */
    private void submitSuggest() {
        String contact = "";
        if (!TextUtils.isEmpty(mEt_contact.getText().toString().trim())) {
            contact = mEt_contact.getText().toString().trim();
        } else if (!TextUtils.isEmpty(UserManager.getInstance().getUser().mobile)) {
            contact = UserManager.getInstance().getUser().mobile;
        }
        UserManager.getInstance().feedBack(UserManager.getInstance().getUser().uid, mEt_content.getText().toString(),
                contact, new ResponseCallback<String>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        UIUtil.showProgressBar(SubmitSuggestActivity.this);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        UIUtil.hideProgressBar(SubmitSuggestActivity.this);
                        if (mSuccess) {
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (TextUtils.isEmpty(mEt_content.getText().toString().trim())) {
                    UIUtil.toastShort(this, "请输入建议内容！");
                    return;
                }
                submitSuggest();
                break;
        }
    }
}
