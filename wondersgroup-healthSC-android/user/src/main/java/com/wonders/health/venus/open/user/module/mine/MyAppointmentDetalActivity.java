package com.wonders.health.venus.open.user.module.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.MyAppointmentDetalEntity;
import com.wonders.health.venus.open.user.entity.event.MyAppointChangeEvent;
import com.wonders.health.venus.open.user.logic.MyAppointmentManager;
import com.wonders.health.venus.open.user.view.HintEditTextView;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import de.greenrobot.event.EventBus;

/**
 * class MyAppointmentDetalActivity
 * auth carrey
 * 16-11-8.
 */

public class MyAppointmentDetalActivity extends BaseActivity implements View.OnClickListener {
    public static final String KEY_ORDERID = "key_orderId";
    public static final String KEY_PAY_FROM = "key_pay_from";

    private String orderId;


    private TextView mTvAppointmentState;
    private TextView mTvPatientName;
    private TextView mTvHospitalName;
    private TextView mTvDepartmentName;
    private TextView mTvDoctorName;
    private TextView mTvDepartmentType;
    private TextView mTvPrice;
    private TextView mTvDate;
    private TextView mTvOrderNumber;
    private Button mBtnCancel;
    private Button mBtnEvaluate;

    private MyAppointmentDetalEntity mEntity;
    private MyAppointmentManager mManager;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_order_detal);
        mTitleBar.setTitle("预约详情");
        initView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager = new MyAppointmentManager();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(KEY_ORDERID)) {
                orderId = bundle.getString(KEY_ORDERID);
                bundle.remove(KEY_ORDERID);
            }
            if (bundle.containsKey(KEY_PAY_FROM)) {
                if (bundle.getBoolean(KEY_PAY_FROM, false)) {
                    mTitleBar.setLeftText("首页");
                    mTitleBar.setLeftTextColor(ContextCompat.getColor(MyAppointmentDetalActivity.this,R.color.tc5));
                    mTitleBar.setLeftClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }
        }

        if ("".equals(orderId)) {
            UIUtil.toastShort(this, "订单号不正确");
            finish();
        }
        loadData();
    }

    private void loadData() {

        mManager.getAppointmentDetal(orderId, new FinalResponseCallback<MyAppointmentDetalEntity>(this) {

            @Override
            public void onSuccess(MyAppointmentDetalEntity entity) {
                super.onSuccess(entity);
                if (entity != null) {
                    mEntity = entity;
                    bindViews(entity);
                }

            }
        });
    }

    private void bindViews(MyAppointmentDetalEntity entity) {

        mTvAppointmentState.setTextColor(getResources().getColor(entity.getStatusColor()));
        mTvAppointmentState.setText(entity.getStatusString());

        mTvPatientName.setText(entity.patientName);
        mTvHospitalName.setText(entity.hosOrgName);
        mTvDepartmentName.setText(entity.deptName);
        mTvDoctorName.setText(entity.doctName);
        mTvDepartmentType.setText(entity.visitLevel);
        mTvPrice.setText(entity.visitCost + "元");
        mTvDate.setText(entity.orderTime);
        mTvOrderNumber.setText(entity.showOrderId);

        mBtnCancel.setVisibility(View.GONE);
        mBtnEvaluate.setVisibility(View.GONE);
        if (entity.canCancel()) {
            mBtnCancel.setVisibility(View.VISIBLE);
        } else if (entity.isCanEvaluate()) {
            mBtnEvaluate.setText("评价医生");
            mBtnEvaluate.setVisibility(View.VISIBLE);
        } else if (entity.isEvaluated()) {
            mBtnEvaluate.setVisibility(View.VISIBLE);
            mBtnEvaluate.setEnabled(false);
            mBtnEvaluate.setText("已评价");
        }


    }

    private void initView() {
        mTvAppointmentState = (TextView) findViewById(R.id.tv_appointment_state);
        mTvPatientName = (TextView) findViewById(R.id.tv_patient_name);
        mTvHospitalName = (TextView) findViewById(R.id.tv_hospital_name);
        mTvDepartmentName = (TextView) findViewById(R.id.tv_department_name);
        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mTvDepartmentType = (TextView) findViewById(R.id.tv_department_type);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);
        mBtnEvaluate = (Button) findViewById(R.id.btn_evaluate);
        mBtnEvaluate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                showCancelDialog();

                break;
            case R.id.btn_evaluate:
                showEvaluateDoctorDialog();

                break;
        }
    }

    private void showCancelDialog() {
        UIUtil.showConfirm(this, "温馨提示", "是否取消本次预约", "立即取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAppointment4Net();

            }
        }, "暂不取消", null);
    }


    /**
     * 取消订单接口
     */
    private void cancelAppointment4Net() {

        mManager.cancelOrder(mEntity.orderId, new ResponseCallback<String>() {

            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(MyAppointmentDetalActivity.this);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                UIUtil.hideProgressBar(MyAppointmentDetalActivity.this);
                if (mSuccess) {
                    EventBus.getDefault().post(new MyAppointChangeEvent());
                }
            }
        });

    }


    private View evaluateView;
    private Button leftBtn, rightBtn;
    private HintEditTextView content;
    private Dialog evaluateDialog;

    private void showEvaluateDoctorDialog() {
        if (evaluateDialog == null) {

            if (evaluateView == null) {
                evaluateView = LayoutInflater.from(MyAppointmentDetalActivity.this).inflate(R.layout.dlg_evaluate_doctor, null);
                rightBtn = (Button) evaluateView.findViewById(R.id.btn_confirm_right);
                leftBtn = (Button) evaluateView.findViewById(R.id.btn_confirm_left);
                content = (HintEditTextView) evaluateView.findViewById(R.id.et_content);
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        evaluateDialog.dismiss();
                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        evaluateDoctor(content.getContent());

                    }
                });
            }
            evaluateDialog = UIUtil.showAlert(MyAppointmentDetalActivity.this, evaluateView);
        } else {
            content.setContent("");

            evaluateDialog.show();
        }
    }

    private void evaluateDoctor(String content) {

        if ("".equals(content.trim())) {
            UIUtil.toastShort(this, "请输入评价内容");
            return;
        }

        mManager.evaluateDoctor(mEntity.orderId, content, new ResponseCallback() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(MyAppointmentDetalActivity.this);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mSuccess) {
                    evaluateDialog.dismiss();
                    EventBus.getDefault().post(new MyAppointChangeEvent());
                }
                UIUtil.hideProgressBar(MyAppointmentDetalActivity.this);
            }
        });

    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    public void onEvent(MyAppointChangeEvent event) {
        loadData();
    }

}
