package com.wonders.health.venus.open.user.module.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.Order;
import com.wonders.health.venus.open.user.logic.OrderManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.home.registration.PayDescViewHelper;
import com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.EXTRA_PAY_ORDER;
import static com.wonders.health.venus.open.user.module.home.registration.PayTypeActivity.EXTRA_PAY_ORDER_BUSINESS;

/**
 * 类描述：订单
 * 创建人：tanghaihua
 * 创建时间：2015/7/1 14:40
 */
public class OrderAdapter extends BaseAdapter<Order, RecyclerView.ViewHolder> {

    private boolean isShowOrderType;

    public OrderAdapter(Context context, List<Order> list, boolean isShowOrderType) {
        super(context, list);
        this.isShowOrderType = isShowOrderType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(mInflater.inflate(R.layout.item_order, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Order order = mItems.get(position);
        if (order != null) {
            OrderHolder orderHolder = (OrderHolder) holder;
            bindData(order, orderHolder);
        }
    }

    private void bindData(final Order order, OrderHolder orderHolder) {
        orderHolder.tv_ordertype.setVisibility(isShowOrderType ? View.VISIBLE : View.GONE);
        orderHolder.tv_paystatus.setText(order.getPayStatusText());
        if("NOTPAY".equals(order.payStatus) || "FAILURE".equals(order.payStatus)){
            if(!TextUtils.isEmpty(order.orderId)){
                orderHolder.tv_ordernumber.setText("订单号：" + order.orderId);
            }else{
                orderHolder.tv_ordernumber.setText("");
            }

            orderHolder.ll_bottom.setVisibility(View.VISIBLE);
            orderHolder.btn_pay.setVisibility(View.VISIBLE);
            orderHolder.btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toPay(order);
                }
            });
        }else{
            if(!TextUtils.isEmpty(order.orderId)){
                orderHolder.tv_ordernumber.setText("订单号：" + order.orderId);
                orderHolder.ll_bottom.setVisibility(View.VISIBLE);
            }else{
                orderHolder.tv_ordernumber.setText("");
                orderHolder.ll_bottom.setVisibility(View.GONE);
            }

            orderHolder.btn_pay.setVisibility(View.GONE);
        }
        if("SUCCESS".equals(order.payStatus)){
            orderHolder.tv_paystatus.setTextColor(mContext.getResources().getColor(R.color.stc4));
        }else{
            orderHolder.tv_paystatus.setTextColor(mContext.getResources().getColor(R.color.tc2));
        }

        if("APPOINTMENT".equals(order.orderType)){//订单类型:CLINIC是诊间支付 APPOINTMENT是预约挂号
            orderHolder.tv_ordertype.setText("挂号费支付");
            orderHolder.tv_appointment_price.setText("挂号金额：" + order.price + "元");
            if(order.business != null){
                orderHolder.tv_hospitalName.setText(order.business.hospitalName);
                orderHolder.tv_deptName.setText(order.business.deptName + "  "
                        + order.business.doctorName + "  " + order.business.outDoctorLevel);
                orderHolder.tv_appointment_name.setText("就诊人：" + order.business.patientName);
                orderHolder.tv_appointment_time.setText(order.business.time);
            }

            orderHolder.ll_appointment.setVisibility(View.VISIBLE);
            orderHolder.ll_clinic.setVisibility(View.GONE);
        }else if("CLINIC".equals(order.orderType)){
            orderHolder.tv_ordertype.setText("诊间支付");
            orderHolder.tv_clinic_price.setText("支付金额：" + order.price + "元");
            if(order.business != null){
                orderHolder.tv_hospitalName.setText(order.business.hospitalName);
                orderHolder.tv_deptName.setText("科        室：" + order.business.deptName);
                orderHolder.tv_clinic_time.setText("开方时间：" + order.business.time);
                orderHolder.tv_clinic_number.setText("处方号码：" + order.business.prescriptionNum);
            }

            orderHolder.ll_appointment.setVisibility(View.GONE);
            orderHolder.ll_clinic.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 去支付
     * @param order
     */
    private void toPay(Order order) {
        if("CLINIC".equals(order.orderType) && order.pay_order == null) {//诊间支付，pay_order=null，重新提取
            generateClinicOrder(order);
        }else if("CLINIC".equals(order.orderType) && "FAILURE".equals(order.payStatus)){//诊间支付，且支付状态为FAILURE，重新提取
            generateClinicOrder(order);
        }else{
            order.pay_order.showOrderId = order.orderId;
            order.pay_order.price = order.price;
            Intent intent = new Intent(mContext, PayTypeActivity.class);
            if(("APPOINTMENT").equals(order.orderType)){
                intent.putExtra(PayDescViewHelper.EXTRA_PAY_FROM,PayDescViewHelper.PayFrom.REGISTRATION_TIME);
                intent.putExtra(EXTRA_PAY_ORDER, order.pay_order);
            }else{
                intent.putExtra(PayDescViewHelper.EXTRA_PAY_FROM,PayDescViewHelper.PayFrom.DOCTOR_TIME);
                intent.putExtra(EXTRA_PAY_ORDER_BUSINESS, order);
            }
            mContext.startActivity(intent);
        }
    }


    /**
     * 生成诊间支付订单
     * @param order
     */
    private void generateClinicOrder(Order order) {
        new OrderManager().generateClinicOrder(order.business, UserManager.getInstance().getUser().uid, order.price,
                new ResponseCallback<Order>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar((BaseActivity)mContext);
            }

            @Override
            public void onSuccess(Order newOrder) {
                super.onSuccess(newOrder);
                if(newOrder != null){
                    newOrder.pay_order.showOrderId = newOrder.orderId;
                    Intent intent = new Intent(mContext, PayTypeActivity.class);
                    intent.putExtra(PayDescViewHelper.EXTRA_PAY_FROM,PayDescViewHelper.PayFrom.DOCTOR_TIME);
                    intent.putExtra(EXTRA_PAY_ORDER_BUSINESS, newOrder);
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (!((BaseActivity)mContext).isFinishing()) {
                    UIUtil.hideProgressBar((BaseActivity)mContext);
                }
            }
        });
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {
        private TextView tv_ordertype;
        private TextView tv_paystatus;
        private TextView tv_hospitalName;
        private TextView tv_deptName;
        private LinearLayout ll_appointment;
        private TextView tv_appointment_name;
        private TextView tv_appointment_price;
        private TextView tv_appointment_time;
        private LinearLayout ll_clinic;
        private TextView tv_clinic_price;
        private TextView tv_clinic_time;
        private TextView tv_clinic_number;
        private TextView tv_ordernumber;
        private TextView btn_pay;
        private LinearLayout ll_bottom;

        public OrderHolder(View view) {
            super(view);

            tv_ordertype = (TextView) view.findViewById(R.id.tv_ordertype);
            tv_paystatus = (TextView) view.findViewById(R.id.tv_paystatus);
            tv_hospitalName = (TextView) view.findViewById(R.id.tv_hospitalName);
            tv_deptName = (TextView) view.findViewById(R.id.tv_deptName);
            ll_appointment = (LinearLayout) view.findViewById(R.id.ll_appointment);
            tv_appointment_name = (TextView) view.findViewById(R.id.tv_appointment_name);
            tv_appointment_price = (TextView) view.findViewById(R.id.tv_appointment_price);
            tv_appointment_time = (TextView) view.findViewById(R.id.tv_appointment_time);
            ll_clinic = (LinearLayout) view.findViewById(R.id.ll_clinic);
            tv_clinic_price = (TextView) view.findViewById(R.id.tv_clinic_price);
            tv_clinic_time = (TextView) view.findViewById(R.id.tv_clinic_time);
            tv_clinic_number = (TextView) view.findViewById(R.id.tv_clinic_number);
            tv_ordernumber = (TextView) view.findViewById(R.id.tv_ordernumber);
            btn_pay = (TextView) view.findViewById(R.id.btn_pay);
            ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        }
    }
}
