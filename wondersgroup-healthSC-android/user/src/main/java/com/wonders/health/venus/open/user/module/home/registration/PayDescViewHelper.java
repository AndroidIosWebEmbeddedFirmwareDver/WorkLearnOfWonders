package com.wonders.health.venus.open.user.module.home.registration;
/*
 * Created by sunning on 2016/11/18.
 */

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.Order;
import com.wonders.health.venus.open.user.entity.PayOrder;
import com.wonders.health.venus.open.user.entity.PayResult;
import com.wonders.health.venus.open.user.entity.event.OrderTimeOutEvent;

import de.greenrobot.event.EventBus;

import static com.wonders.health.venus.open.user.entity.PayResult.CODE_TIMEOUT;
import static com.wonders.health.venus.open.user.module.home.registration.PayResultActivity.EXTRA_RESULT;
import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.IS_APPOINTMENT;
import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.TIMEOUT_MSG;


public abstract class PayDescViewHelper {

    public static final String EXTRA_PAY_FROM = "pay_from";

    protected Context mContext;

    protected View view;

    private PayDescViewHelper(Context mContext) {
        this.mContext = mContext;
        init();
    }

    static View inject(Context context, Order order) {
        PayDescViewHelper viewHelper;
        if(order instanceof PayOrder){
            viewHelper = new PayForRegister(context);
        }else{
            viewHelper = new PayForDetail(context);
        }
        return viewHelper.injectView(order);
    }

    public abstract View injectView(Order payOrder);

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(getLayoutId(), null);
    }

    public abstract int getLayoutId();

    private static class PayForDetail extends PayDescViewHelper {

        private PayForDetail(Context mContext) {
            super(mContext);
        }

        @Override
        public View injectView(Order order) {
            Order.Business business = order.business;
            ((TextView) view.findViewById(R.id.tv_hospital_name)).setText(business.hospitalName);
            TextView feeTV = ((TextView) view.findViewById(R.id.tv_order_fee));
            String fee = "支付金额:   " + order.price + "元";
            SpannableString spannableString = new SpannableString(fee);
            try {
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.tc5)), fee.indexOf(":") + 1, fee.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            feeTV.setText(spannableString);
            ((TextView) view.findViewById(R.id.tv_order_time)).setText("开方时间：" + business.time);
            ((TextView) view.findViewById(R.id.tv_prescribe_no)).setText("处方号码：" + business.prescriptionNum);
            ((TextView) view.findViewById(R.id.tv_order_no)).setText("订单号：" + order.orderId);
            return view;
        }

        @Override
        public int getLayoutId() {
            return R.layout.pay_detail_doctor_time;
        }
    }

    private static class PayForRegister extends PayDescViewHelper {

        private PayForRegister(Context mContext) {
            super(mContext);
        }

        @Override
        public View injectView(final Order order) {
            PayOrder payOrder = (PayOrder) order;
            ((TextView) view.findViewById(R.id.tv_order_no)).setText("订单号: " + payOrder.showOrderId);
            ((TextView) view.findViewById(R.id.tv_money)).setText(payOrder.price + " 元");
            final TextView minTV = (TextView) view.findViewById(R.id.count_down_min);
            final TextView secTV = (TextView) view.findViewById(R.id.count_down_second);
            CountDownToPay.getInstance().start(payOrder.time_left, new CountDownToPay.CountdownCallBack() {
                @Override
                public void getTime(String min, String sec) {
                    minTV.setText(min);
                    secTV.setText(sec);
                }

                @Override
                public void onFinish() {
                    timeout(mContext, TIMEOUT_MSG);
                }
            });
            return view;
        }

        @Override
        public int getLayoutId() {
            return R.layout.pay_detail_register;
        }
    }

    public enum PayFrom {
        DOCTOR_TIME, REGISTRATION_TIME
    }

    static void timeout(Context context, String msg) {
        EventBus.getDefault().post(new OrderTimeOutEvent());
        PayTypeActivity payTypeAct = (PayTypeActivity) context;
        payTypeAct.startActivity(new Intent(context, PayResultActivity.class)
                .putExtra(EXTRA_RESULT, new PayResult(CODE_TIMEOUT, msg, "0"))
                .putExtra(IS_APPOINTMENT, payTypeAct.isAppointment));
        payTypeAct.finish();
    }
}
