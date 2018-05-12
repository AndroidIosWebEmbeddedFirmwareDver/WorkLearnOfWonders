package com.wonders.health.venus.open.user.module.home.registration;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * Created by sunning on 16/1/4.
 * 验证码
 */
public class VerifyActivity extends UnifyFinishActivity implements View.OnClickListener {
    // 联系人id
    public static final String EXTRA_CONTACT_ID = "contact_id";
    public static final String EXTRA_PATIENT_TYPE = "extra_patient_type";
    // 排班id
    public static final String EXTRA_ID = "id";

    private EditText mEdit_verify_code;
    private TextView mBtn_verify_code;

    private CountDownTimer mTimer;
    private String mContactId;
    private String mId, registrationID;
    private int patientType;

    @Override
    protected void initViews() {
        setContentView(R.layout.register_verify_layout);
        mEdit_verify_code = (EditText) findViewById(R.id.edit_verify_code);
        mBtn_verify_code = (TextView) findViewById(R.id.btn_verify_code);
        mTitleBar.setTitle("验证信息");
        Button submit = (Button) findViewById(R.id.verify_submit);
        submit.setOnClickListener(this);
        mBtn_verify_code.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mContactId = getIntent().getStringExtra(EXTRA_CONTACT_ID);
        mId = getIntent().getStringExtra(EXTRA_ID);
        patientType = getIntent().getIntExtra(EXTRA_PATIENT_TYPE, 1);
        if (TextUtils.isEmpty(mContactId) || TextUtils.isEmpty(mId)) {
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_submit:
                registration();
                break;
            case R.id.btn_verify_code:
                getVerifyCode();
                break;
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

    // 获取验证码
    private void getVerifyCode() {
//        RegistrationManager.getInstance().getAppointmentVerifyCode(mContactId, new ResponseCallback<String>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                UIUtil.showProgressBar(VerifyActivity.this);
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                UIUtil.hideProgressBar(VerifyActivity.this);
//                if (mSuccess) {
//                    startCountTime();
//                }
//            }
//        });
    }

    private void registration() {
        String code = mEdit_verify_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            UIUtil.toastShort(this, "验证码不能为空！");
            return;
        }
//        RegistrationManager.getInstance().registrationAppointment(mId, mContactId, code, patientType,
//                new ResponseCallback<AppointmentDetail>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        UIUtil.showProgressBar(VerifyActivity.this);
//                    }
//
//                    @Override
//                    public void onSuccess(AppointmentDetail appointmentDetail) {
//                        super.onSuccess(appointmentDetail);
//                        registrationID = appointmentDetail.id;
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        UIUtil.hideProgressBar(VerifyActivity.this);
//                        if (mSuccess) {
//                            Intent intent = new Intent(VerifyActivity.this, AppointmentDetailActivity.class);
//                            intent.putExtra(AppointmentDetailActivity.EXTRA_ID, registrationID);
//                            startActivity(intent);
//                            EventBus.getDefault().post(new RegistrationEvent(false, mId));
//                        }
//                    }
//                });
    }


    private void startCountTime() {
        mBtn_verify_code.setEnabled(false);
        mBtn_verify_code.setTextColor(getResources().getColor(R.color.tc3));
        mTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                mBtn_verify_code.setText("已发送" + l / 1000);
            }

            @Override
            public void onFinish() {
                mBtn_verify_code.setEnabled(true);
                mBtn_verify_code.setText("点击发送");
                mBtn_verify_code.setTextColor(getResources().getColor(R.color.tc3));
            }
        }.start();
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
