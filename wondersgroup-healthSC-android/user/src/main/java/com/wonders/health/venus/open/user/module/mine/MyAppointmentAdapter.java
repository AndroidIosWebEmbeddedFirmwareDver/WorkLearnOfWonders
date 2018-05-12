package com.wonders.health.venus.open.user.module.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.Order;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;

import java.util.List;

/**
 * class MyAppointmentAdapter
 * auth carrey
 * 16-11-7.
 */

public class MyAppointmentAdapter extends BaseAdapter<Order, MyAppointmentAdapter.ViewHolder> {

    private onAppointmentBtnClickListener mBtnClickListener;

    public void setBtnClickListener(onAppointmentBtnClickListener btnClickListener) {
        mBtnClickListener = btnClickListener;
    }

    public MyAppointmentAdapter(Context context, List<Order> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_appointment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mItems.get(position));
    }


    public class ViewHolder extends BaseRecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_appointment_state;
        public TextView tv_hospital_name;
        public TextView tv_name_department;
        public TextView tv_patient;
        public TextView tv_price;
        public TextView tv_date;
        public TextView tv_order_number;
        public TextView btn_has_evaluate;
        public TextView btn_evaluate;
        public TextView btn_cancel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_appointment_state = (TextView) itemView.findViewById(R.id.tv_appointment_state);
            this.btn_has_evaluate = (TextView) itemView.findViewById(R.id.btn_has_evaluate);
            this.btn_evaluate = (TextView) itemView.findViewById(R.id.btn_evaluate);
            this.btn_cancel = (TextView) itemView.findViewById(R.id.btn_cancel);
            this.tv_hospital_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
            this.tv_name_department = (TextView) itemView.findViewById(R.id.tv_name_department);
            this.tv_patient = (TextView) itemView.findViewById(R.id.tv_patient);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_order_number = (TextView) itemView.findViewById(R.id.tv_order_number);
        }

        public void bindData(Order order) {

            if (order.business != null) {


                btn_cancel.setOnClickListener(this);
                btn_evaluate.setOnClickListener(this);
                btn_cancel.setTag(order);
                btn_evaluate.setTag(order);

                tv_appointment_state.setTextColor(mContext.getResources().getColor(order.getStatusColor()));
                tv_appointment_state.setText(order.getStatusString());

                tv_hospital_name.setText(order.business.hospitalName);

                tv_name_department.setText(order.business.deptName + " "
                        + order.business.doctorName + " " + order.business.outDoctorLevel);
                tv_patient.setText("就诊人："+order.business.patientName);
                tv_price.setText("挂号金额： " + order.price+ "元");
                tv_date.setText(order.business.time);
                tv_order_number.setText("预约单号： " + order.showOrderId);

                btn_has_evaluate.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
                if (order.canCancel()) {
                    btn_cancel.setVisibility(View.VISIBLE);
                } else if (order.business.isCanEvaluate(order.isEvaluated)) {
                    btn_evaluate.setVisibility(View.VISIBLE);
                }else if (order.isEvaluated(order.isEvaluated)){
                    btn_has_evaluate.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    if (mBtnClickListener != null) {
                        mBtnClickListener.cancelAppointment((Order) v.getTag());
                    }
                    break;
                case R.id.btn_evaluate:
                    if (mBtnClickListener != null) {
                        mBtnClickListener.evaluateDoctor((Order) v.getTag());
                    }
                    break;
            }
        }
    }

    public interface onAppointmentBtnClickListener {

        void cancelAppointment(Order entity);

        void evaluateDoctor(Order entity);

    }
}
