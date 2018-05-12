package com.wonders.health.venus.open.user.module.home.registration;
/*
 * Created by sunning on 2016/11/9.
 * 支付方式
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.pay.PayOrderInfo;
import com.wonders.health.venus.open.user.component.pay.PayUtil;
import com.wonders.health.venus.open.user.entity.Order;
import com.wonders.health.venus.open.user.entity.PayOrder;
import com.wonders.health.venus.open.user.entity.PayResult;
import com.wonders.health.venus.open.user.entity.event.OrderPaySuccessEvent;
import com.wonders.health.venus.open.user.entity.event.RegistrationEvent;
import com.wonders.health.venus.open.user.logic.OrderManager;
import com.wonders.health.venus.open.user.module.MainActivity;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.Callback;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.Map;

import cn.wd.checkout.api.WDReqParams;
import de.greenrobot.event.EventBus;

import static com.wonders.health.venus.open.user.module.home.registration.PayDescViewHelper.PayFrom.DOCTOR_TIME;
import static com.wonders.health.venus.open.user.module.home.registration.PayDescViewHelper.timeout;

public class PayTypeActivity extends UnifyFinishActivity implements View.OnClickListener {

    public static final String PAYED_MSG = "订单已支付";
    public static final String TIMEOUT_MSG = "订单已超时，请重新预约";

    private static final String ORDER_NOT_PAY = "NOTPAY";
    private static final String ORDER_SUCCESS = "SUCCESS";

    public static final String EXTRA_PAY_ORDER = "pay_order";
    public static final String EXTRA_PAY_ORDER_BUSINESS = "pay_order_business";
    public static final String IS_APPOINTMENT = "is_appointment";
    private ImageView mIvWeixinPay;
    private LinearLayout mLlWeixinPay;
    private ImageView mIvAliPay;
    private LinearLayout mLlAliPay;
    private Button mBtnPay;
    private PayOrder mPayOrder;
    private Order.Business business;
    private PayDescViewHelper.PayFrom from;
    public boolean isAppointment;
    private Order order;

    @Override
    protected void initViews() {
        setContentView(R.layout.pay_type_activity);
        loadParams();
        mTitleBar.setLeftImageResource(R.mipmap.ic_close);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backWarning();
            }
        });
        if(from == DOCTOR_TIME){
            mTitleBar.setTitle("支付详情");
        }else{
            mTitleBar.setTitle("支付方式");
        }
        mIvWeixinPay = (ImageView) findViewById(R.id.iv_weixin_pay);
        mLlWeixinPay = (LinearLayout) findViewById(R.id.ll_weixin_pay);
        mIvAliPay = (ImageView) findViewById(R.id.iv_ali_pay);
        mLlAliPay = (LinearLayout) findViewById(R.id.ll_ali_pay);
        mBtnPay = (Button) findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
        mLlWeixinPay.setOnClickListener(this);
        mLlAliPay.setOnClickListener(this);
        mIvWeixinPay.setSelected(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    public void onBackPressed() {
        backWarning();
    }

    private void backWarning(){
        UIUtil.showConfirm(PayTypeActivity.this, null, "是否离开支付界面", "继续支付", null, "确定离开", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAppointment) {
                    toHomeActivity();
                } else {
                    EventBus.getDefault().post(new RegistrationEvent());
                }
            }
        });
    }

    private void loadParams() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isAppointment = bundle.getBoolean(IS_APPOINTMENT, false);
            from = (PayDescViewHelper.PayFrom) bundle.getSerializable(PayDescViewHelper.EXTRA_PAY_FROM);
            if(from != null){
                switch (from){
                    case DOCTOR_TIME:
                        order = (Order) bundle.getSerializable(EXTRA_PAY_ORDER_BUSINESS);
                        if(order != null){
                            business = order.business;
                            mPayOrder = order.pay_order;
                            addDescView(order);
                        }
                        break;
                    case REGISTRATION_TIME:
                        mPayOrder = (PayOrder) bundle.getSerializable(EXTRA_PAY_ORDER);
                        if (mPayOrder == null || mPayOrder.time_left < 0) {
                            timeout(this, TIMEOUT_MSG);
                            return;
                        }
                        getOrderInfo();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountDownTimer timer = CountDownToPay.getInstance().getTimer();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                pay();
                break;
            case R.id.ll_weixin_pay:
                mIvAliPay.setSelected(false);
                mIvWeixinPay.setSelected(true);
                break;
            case R.id.ll_ali_pay:
                mIvAliPay.setSelected(true);
                mIvWeixinPay.setSelected(false);
                break;
            default:
                break;
        }
    }

    private void getOrderInfo() {
        new OrderManager().getOrder(mPayOrder.id, new FinalResponseCallback<PayOrder>((ViewGroup) findViewById(R.id.pay_type_root)) {
            @Override
            public void onSuccess(PayOrder order) {
                super.onSuccess(order);
                String showOrderId = mPayOrder.showOrderId;
                String orderId = mPayOrder.orderId;
                String price = mPayOrder.price;
                mPayOrder = order;
                mPayOrder.showOrderId = showOrderId;
                mPayOrder.orderId = orderId;
                mPayOrder.price = price;
                checkStatus();
            }

            @Override
            public void onReload() {
                getOrderInfo();
            }
        });
    }

    private void checkStatus() {
        if (mPayOrder.status != null) {
            switch (mPayOrder.status) {
                case ORDER_NOT_PAY:
                    addDescView(mPayOrder);
                    break;
                case ORDER_SUCCESS:
                    timeout(this, PAYED_MSG);
                    break;
                default:
                    timeout(this, TIMEOUT_MSG);
            }
        }
    }


    private void addDescView(Order order) {
        ViewGroup descView = (ViewGroup) findViewById(R.id.pay_detail);
        if (descView != null) {
            if (descView.getChildCount() != 0) {
                descView.removeAllViews();
            }
            descView.addView(PayDescViewHelper.inject(this, order));
        }
    }


    private void pay() {
        String channel = mIvWeixinPay.isSelected() ? WDReqParams.WDChannelTypes.wepay.toString()
                : WDReqParams.WDChannelTypes.alipay.toString();
        new OrderManager().payOrder(mPayOrder.id, channel, new DialogResponseCallback<Map<String, String>>(PayTypeActivity.this) {
            @Override
            public void onSuccess(Map<String, String> stringStringHashMap) {
                super.onSuccess(stringStringHashMap);
                final PayOrderInfo payInfo = mPayOrder.getPayInfo();
                payInfo.appKey = stringStringHashMap.get("key");
                if(business != null){
                    payInfo.goodsDesc = mPayOrder.description;
                    payInfo.goodsTitle = mPayOrder.subject;
                }
                if (mIvWeixinPay.isSelected()) {
                    PayUtil.payByWeixin(PayTypeActivity.this, payInfo, new PayCallback());
                } else {
                    PayUtil.payByAli(PayTypeActivity.this, payInfo, new PayCallback());
                }
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
                if (error instanceof HttpException) {
                    int code = ((HttpException) error).getExceptionCode();
                    // 9000过期，9001已支付
                    if (code == 9000) {
                        timeout(PayTypeActivity.this, TIMEOUT_MSG);
                        return;
                    } else if (code == 9001) {
                        timeout(PayTypeActivity.this, PAYED_MSG);
                        return;
                    }
                }
                PayResult result = new PayResult();
                result.code = PayResult.CODE_FAILED;
                if (error instanceof HttpException) {
                    result.msg = error.getMessage();
                } else {
                    result.msg = ERR_MSG;
                }
                startActivity(new Intent(PayTypeActivity.this, PayResultActivity.class)
                        .putExtra(PayResultActivity.EXTRA_RESULT, result));
            }

            @Override
            public boolean isShowNotice() {
                return mSuccess;
            }
        });
    }

    class PayCallback implements Callback<String> {
        @Override
        public void onStart() {
            UIUtil.showProgressBar(PayTypeActivity.this);
        }

        @Override
        public void onSuccess(String s) {
            LogUtils.d("bacy" + s);
            PayResult result = new PayResult();
            result.code = PayResult.CODE_SUCCESS;
            if(order != null){
                result.price = order.price ;
            }else{
                result.price = mPayOrder.price ;
            }
            result.orderId = mPayOrder.orderId;
            startActivity(new Intent(PayTypeActivity.this, PayResultActivity.class)
                    .putExtra(IS_APPOINTMENT, isAppointment)
                    .putExtra(PayResultActivity.EXTRA_RESULT, result));
            EventBus.getDefault().post(new OrderPaySuccessEvent(mPayOrder.id));
        }

        @Override
        public void onFailure(Exception e) {
            LogUtils.d("bacy" + e.toString());
            PayResult result = new PayResult();
            result.code = PayResult.CODE_FAILED;
            result.msg = e.getMessage();
            startActivity(new Intent(PayTypeActivity.this, PayResultActivity.class)
                    .putExtra(IS_APPOINTMENT, isAppointment)
                    .putExtra(PayResultActivity.EXTRA_RESULT, result));
        }

        @Override
        public void onFinish() {
            UIUtil.hideProgressBar(PayTypeActivity.this);
        }
    }

    private void toHomeActivity() {
        Intent intent = new Intent();
        intent.setClass(PayTypeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PayTypeActivity.this.startActivity(intent);
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

}
