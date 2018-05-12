package com.wonders.health.venus.open.user.module.home.registration;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wonders.health.venus.open.user.entity.PayInfo;
import com.wonders.health.venus.open.user.entity.PayOrder;
import com.wonders.health.venus.open.user.entity.PayResponseVO;
import com.wonders.health.venus.open.user.logic.RegistrationManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.view.MyPopuWindow;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import static com.wonders.health.venus.open.user.module.home.registration.PayDescViewHelper.EXTRA_PAY_FROM;
import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.EXTRA_PAY_ORDER;
import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.IS_APPOINTMENT;

import com.wonders.health.venus.open.user.R;
/**
 * Created by sunning on 16/1/4.
 * 预约信息
 */
public class OrderedInformationActivity extends UnifyFinishActivity  implements  View.OnClickListener {

    private DoctorListVO infoVO;
    private CircleImageView mIv_item_doctor_info_head_img;
    private TextView mTv_item_doctor_info_name;
    private TextView mTv_item_doctor_info_title;
    private TextView mTv_item_doctor_info_hospital;
    private TextView mTv_item_doctor_info_category;
    private TextView mTv_item_doctor_info_date;
    private TextView mTv_item_doctor_info_type;
    private TextView mTv_item_doctor_info_fee;
    private TextView mTv_medi_card_id;
    private TextView personName;
    private TextView mTv_time_range;
    private BitmapTools mBitmapTools;
    private LinearLayout mLl_card_type;
    private TextView mTv_card_type;
    private String[] mTv_state_value;
    private LinearLayout mediCard;

    @Override
    protected void initViews() {
        setContentView(R.layout.register_information_layout);
        mTitleBar.setTitle("预约信息");
        //去除温馨提示 2017/6/15
//        if (isFirstShow()) {
//            UIUtil.showWarningAlert(this, getString(R.string.dialog_text));
//            PrefUtil.putBoolean(this, Constant.KEY_FIRST_SHOW_DIALOG, false);
//        }
        ((FrameLayout) findViewById(R.id.ll_rule)).addView(CommonViews.getRuleView(this));
        mIv_item_doctor_info_head_img = (CircleImageView) findViewById(R.id.iv_item_doctor_info_head_img);
        mTv_item_doctor_info_name = (TextView) findViewById(R.id.tv_item_doctor_info_name);
        mTv_item_doctor_info_title = (TextView) findViewById(R.id.tv_item_doctor_info_title);
        mTv_item_doctor_info_hospital = (TextView) findViewById(R.id.tv_item_doctor_info_hospital);
        mTv_item_doctor_info_category = (TextView) findViewById(R.id.tv_item_doctor_info_category);
        mTv_item_doctor_info_date = (TextView) findViewById(R.id.tv_item_doctor_info_date);
        mTv_item_doctor_info_type = (TextView) findViewById(R.id.tv_item_doctor_info_type);
        mTv_item_doctor_info_fee = (TextView) findViewById(R.id.tv_item_doctor_info_fee);
        mLl_card_type = (LinearLayout) findViewById(R.id. ll_card_type);
        mTv_card_type = (TextView) findViewById(R.id.tv_card_type);
        mTv_medi_card_id = (TextView) findViewById(R.id.tv_medi_card_id);
        personName = (TextView) findViewById(R.id.register_doctor_person_name);
        mTv_time_range = (TextView) findViewById(R.id.tv_time_range);
        mLl_card_type.setOnClickListener(this);
        mediCard = (LinearLayout) findViewById(R.id.medi_card_root);
        mTv_medi_card_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mediCard.setBackgroundColor(Color.parseColor("#80ff665e"));
                } else {
                    mediCard.setBackgroundColor(getResources().getColor(R.color.bc1));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        View submitBtn = findViewById(R.id.order_submit_btn);
        if (submitBtn != null) {
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //就诊卡号非必填 2017/6/15
//                    if (TextUtils.isEmpty(mTv_medi_card_id.getText().toString())) {
//                        mediCard.setBackgroundColor(Color.parseColor("#80ff665e"));
//                        UIUtil.toastShort(OrderedInformationActivity.this, "请填写就诊卡卡号");
//                        return;
//                    }
                    PayInfo payInfo = new PayInfo(infoVO);
                   // payInfo.mediCardId = mTv_medi_card_id.getText().toString();
                    RegistrationManager.getInstance().submitOrder(payInfo, new DialogResponseCallback<PayResponseVO>(OrderedInformationActivity.this) {
                        @Override
                        public void onSuccess(PayResponseVO payResponse) {
                            super.onSuccess(payResponse);
                            PayOrder payOrder = new PayOrder();
                            payOrder.id = payResponse.payOrderId;
                            payOrder.orderId = payResponse.orderId;
                            payOrder.showOrderId = payResponse.showOrderId;
                            if (infoVO.schedule != null) {
                                payOrder.price = infoVO.schedule.visitCost;
                            }else{
                                payOrder.price = "未获取到支付金额!";
                            }
                            startActivity(new Intent(OrderedInformationActivity.this, PayTypeActivity.class)
                                    .putExtra(EXTRA_PAY_ORDER, payOrder)
                                    .putExtra(IS_APPOINTMENT, true)
                                    .putExtra(EXTRA_PAY_FROM, PayDescViewHelper.PayFrom.REGISTRATION_TIME));
                        }
                    });
                }
            });
        }

    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        mTv_state_value = new String[] { "身份证", "居民健康卡"};
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
        mTv_item_doctor_info_fee.setText(infoVO.schedule.visitCost + "元");
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_card_type:
                showListPopupWindow();
                break;
        }
    }

    private void showListPopupWindow() {
        // TODO Auto-generated method stub
        int[] location = new int[2];
        mTv_card_type.getLocationOnScreen(location);

        final MyPopuWindow popupWindow = new MyPopuWindow(
                this, mTv_state_value, UIUtil.dip2px(200),
                ViewGroup.LayoutParams.WRAP_CONTENT,0);//这里的最后一个参数是popupWindow的背景
        popupWindow.setOnMyItemClickListener(new MyPopuWindow.MyClickListener() {
            @Override
            public void ItemClickListener(int index, String str) {
                mTv_card_type.setText(str);
                mediCard.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                //处理popupWindow消失时处理的事情
            }
        });
        popupWindow.showAtLocation(mTv_card_type, Gravity.NO_GRAVITY,
                location[0], location[1] + mTv_card_type.getHeight());
    }


}
