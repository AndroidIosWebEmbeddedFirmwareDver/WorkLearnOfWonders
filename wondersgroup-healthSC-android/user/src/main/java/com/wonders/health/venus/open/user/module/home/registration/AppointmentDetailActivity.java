package com.wonders.health.venus.open.user.module.home.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AppointmentDetail;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

/**
 * 类描述：预约详情
 * 创建人：Bob
 * 创建时间：2016/1/5 20:08
 */
public class AppointmentDetailActivity extends UnifyFinishActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "id";

    private PullToRefreshView mPullViewAppointment;
    private TextView mTvAppointmentState;
    private TextView mTvAppointmentHospital;
    private TextView mTvAppointmentKeshi;
    private TextView mTvAppointmentDoctor;
    private TextView mTvAppointmentType;
    private TextView mTvAppointmentFee;
    private TextView mTvAppointmentFeeType;
    private TextView mTvLookTime;
    private TextView mTvGetNumberAddr;
    private TextView mTvLookAddr;
    private TextView mTvUserName;
    private TextView mTvUserCard;
    private TextView mTvUserMobile;
    private TextView mTvLookType;
    private TextView mTvBookTime;
    private Button mBtnCancel;

    private String mId;


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_appointment_detail);

        mPullViewAppointment = (PullToRefreshView) findViewById(R.id.pull_view_appointment);
        mTvAppointmentState = (TextView) findViewById(R.id.tv_appointment_state);
        mTvAppointmentHospital = (TextView) findViewById(R.id.tv_appointment_hospital);
        mTvAppointmentKeshi = (TextView) findViewById(R.id.tv_appointment_keshi);
        mTvAppointmentDoctor = (TextView) findViewById(R.id.tv_appointment_doctor);
        mTvAppointmentType = (TextView) findViewById(R.id.tv_appointment_type);
        mTvAppointmentFee = (TextView) findViewById(R.id.tv_appointment_fee);
        mTvAppointmentFeeType = (TextView) findViewById(R.id.tv_appointment_fee_type);
        mTvLookTime = (TextView) findViewById(R.id.tv_look_time);
        mTvGetNumberAddr = (TextView) findViewById(R.id.tv_get_number_addr);
        mTvLookAddr = (TextView) findViewById(R.id.tv_look_addr);
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        mTvUserCard = (TextView) findViewById(R.id.tv_user_card);
        mTvUserMobile = (TextView) findViewById(R.id.tv_user_mobile);
        mTvLookType = (TextView) findViewById(R.id.tv_look_type);
        mTvBookTime = (TextView) findViewById(R.id.tv_book_time);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);

        mBtnCancel.setOnClickListener(this);
        UIUtil.setTouchEffect(mBtnCancel);

        mTitleBar.setTitle("预约详情");
        mBtnCancel.setOnClickListener(this);

        mPullViewAppointment.setLoadMoreEnable(false);
        mPullViewAppointment.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra(EXTRA_ID);
        if (TextUtils.isEmpty(mId)) {
            finish();
            return;
        }
        loadData(Constant.TYPE_INIT);
    }

    private void loadData(final int type) {
        /*RegistrationManager.getInstance().getAppointmentDetail(mId,
                new FinalResponseCallback<AppointmentDetail>(mPullViewAppointment, type) {
                    @Override
                    public void onSuccess(AppointmentDetail appointmentDetail) {
                        super.onSuccess(appointmentDetail);
                        bindView(appointmentDetail);
                    }

                    @Override
                    public void onReload() {
                        super.onReload();
                        loadData(Constant.TYPE_INIT);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (type == Constant.TYPE_RELOAD) {
                            mPullViewAppointment.onHeaderRefreshComplete();
                        }
                    }
                })*/;
    }

    @Override
    public void onClick(View v) {
        if ( v == mBtnCancel ) {
            UIUtil.showConfirm(this, "您确定要取消预约吗？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelRegestration();
                }
            });
        }
    }

    private void cancelRegestration() {
        /*RegistrationManager.getInstance().cancelRegistration(mId, new ResponseCallback<String>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(AppointmentDetailActivity.this);
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                EventBus.getDefault().post(new RegistrationEvent(true, mId));
                finish();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(!isFinishing()){
                    UIUtil.hideProgressBar(AppointmentDetailActivity.this);
                }
            }
        })*/;
    }

    private void bindView(AppointmentDetail appointmentDetail) {
        mTvAppointmentState.setText(appointmentDetail.getStatusStr());
        mTvAppointmentHospital.setText(appointmentDetail.hospital_name);
        mTvAppointmentKeshi.setText(appointmentDetail.department_name);
        mTvAppointmentDoctor.setText(appointmentDetail.doctor_name);
        mTvAppointmentType.setText(appointmentDetail.clinic_type);
        mTvAppointmentFee.setText(appointmentDetail.fee);
        mTvAppointmentFeeType.setText(appointmentDetail.pay_type);
        mTvLookTime.setText(appointmentDetail.start_time);
        mTvGetNumberAddr.setText(appointmentDetail.fetch_number_location);
        mTvLookAddr.setText(appointmentDetail.location);
        mTvUserName.setText(appointmentDetail.patient_name);
        mTvUserCard.setText(appointmentDetail.patient_idcard);
        mTvUserMobile.setText(appointmentDetail.patient_mobile);
        mTvLookType.setText(appointmentDetail.patient_type);
        mTvBookTime.setText("预约申请时间:\t" + appointmentDetail.order_time);

        mBtnCancel.setVisibility(appointmentDetail.can_cancel ? View.VISIBLE : View.GONE);
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
