package com.wonders.health.venus.open.doctor.module.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DoctorListVO;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;



/**
 * Created by sunning on 16/1/4.
 * 预约信息
 */
public class OrderedInformationActivity extends UnifyFinishActivity {

    private DoctorListVO infoVO;
    private CircleImageView mIv_item_doctor_info_head_img;
    private TextView mTv_item_doctor_info_name;
    private TextView mTv_item_doctor_info_title;
    private TextView mTv_item_doctor_info_hospital;
    private TextView mTv_item_doctor_info_category;
    private TextView mTv_item_doctor_info_date;
    private TextView mTv_item_doctor_info_type;
    private TextView personName;
    private TextView mTv_time_range;
    private BitmapTools mBitmapTools;


    @Override
    protected void initViews() {
        setContentView(R.layout.register_information_layout);
        mTitleBar.setTitle("预约信息");
//        ((FrameLayout) findViewById(R.id.ll_rule)).addView(CommonViews.getRuleView(this));
        mIv_item_doctor_info_head_img = (CircleImageView) findViewById(R.id.iv_item_doctor_info_head_img);
        mTv_item_doctor_info_name = (TextView) findViewById(R.id.tv_item_doctor_info_name);
        mTv_item_doctor_info_title = (TextView) findViewById(R.id.tv_item_doctor_info_title);
        mTv_item_doctor_info_hospital = (TextView) findViewById(R.id.tv_item_doctor_info_hospital);
        mTv_item_doctor_info_category = (TextView) findViewById(R.id.tv_item_doctor_info_category);
        mTv_item_doctor_info_date = (TextView) findViewById(R.id.tv_item_doctor_info_date);
        mTv_item_doctor_info_type = (TextView) findViewById(R.id.tv_item_doctor_info_type);
        personName = (TextView) findViewById(R.id.register_doctor_person_name);
        mTv_time_range = (TextView) findViewById(R.id.tv_time_range);
        View submitBtn = findViewById(R.id.order_submit_btn);
        if (submitBtn != null) {
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    PayInfo payInfo = new PayInfo(infoVO);
//                    payInfo.mediCardId = mTv_medi_card_id.getText().toString();
//                    RegistrationManager.getInstance().submitOrder(payInfo, new DialogResponseCallback<PayResponseVO>(OrderedInformationActivity.this) {
//                        @Override
//                        public void onSuccess(PayResponseVO payResponse) {
//                            super.onSuccess(payResponse);
//                            PayOrder payOrder = new PayOrder();
//                            payOrder.id = payResponse.payOrderId;
//                            payOrder.orderId = payResponse.orderId;
//                            payOrder.showOrderId = payResponse.showOrderId;
//                            if (infoVO.schedule != null) {
//                                payOrder.price = infoVO.schedule.visitCost;
//                            }else{
//                                payOrder.price = "未获取到支付金额!";
//                            }
//                            startActivity(new Intent(OrderedInformationActivity.this, PayTypeActivity.class)
//                                    .putExtra(EXTRA_PAY_ORDER, payOrder)
//                                    .putExtra(IS_APPOINTMENT, true)
//                                    .putExtra(EXTRA_PAY_FROM, PayDescViewHelper.PayFrom.REGISTRATION_TIME));
//                        }
//                    });
                }
            });
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(this);
        loadParams();
    }

    private void bindView() {
        String timeRange = "1".equals(infoVO.schedule.timeRange) ? "上午" : "下午";
        mTv_item_doctor_info_name.setText(infoVO.doctorName);
        mTv_item_doctor_info_title.setText(infoVO.doctorTitle);
        mTv_item_doctor_info_hospital.setText(infoVO.hosName);
        mTv_item_doctor_info_category.setText(infoVO.deptName);
        mTv_item_doctor_info_date.setText(infoVO.schedule.scheduleDate + " " + (TextUtils.isEmpty(infoVO.schedule.weekDay) ? "" : infoVO.schedule.weekDay) + " " + timeRange);
        mTv_item_doctor_info_type.setText(infoVO.schedule.visitLevel);
        personName.setText(UserManager.getInstance().getUser().name);
        String range = getTimeRange();
        mTv_time_range.setText(TextUtils.isEmpty(range) ? timeRange : range);
        mBitmapTools.display(mIv_item_doctor_info_head_img, infoVO.headphoto, RegistrationDoctorHead.getGenderHeadRes(infoVO.gender));
    }

    private String getTimeRange() {
        String timeRange = "";
        String startTime = infoVO.schedule.startTime;
        String endTime = infoVO.schedule.endTime;
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            int startIndex = startTime.lastIndexOf(":");
            int endIndex = endTime.lastIndexOf(":");
            if (startIndex != -1 && endIndex != -1) {
                timeRange = startTime.substring(0, startIndex) +" - "+ endTime.substring(0, endIndex);
            }
        }
        return timeRange;
    }

    private void loadParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            infoVO = (DoctorListVO) bundle.getSerializable(DoctorListBySpecialistFragment.ORDER_INFO);
            bindView();
        }
    }

    public boolean isFirstShow() {
        return PrefUtil.getBoolean(this, Constant.KEY_FIRST_SHOW_DIALOG, true);
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}

