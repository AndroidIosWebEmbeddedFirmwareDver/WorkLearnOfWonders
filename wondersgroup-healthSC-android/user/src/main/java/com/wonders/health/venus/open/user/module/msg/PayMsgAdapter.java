package com.wonders.health.venus.open.user.module.msg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.MsgEntity;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

import java.util.List;

/**
 * 类描述：消息adapter
 * 创建人：zhangjingyang
 * 创建时间：2016/11/8 15:21
 */
public class PayMsgAdapter extends BaseAdapter<MsgEntity.message, PayMsgAdapter.ViewHolder> {

	private Context mContext;
    public List<MsgEntity.message> getItems(){
		return mItems;
	}

	public PayMsgAdapter(Context ctx, List<MsgEntity.message> list) {
		super(ctx, list);
		mContext = ctx;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_msg_pay, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final MsgEntity.message info = getItem(position);

		holder.createDate.setText(info.createDate);
		holder.payTypeName.setText(info.payTypeName);
		holder.orderId.setText(info.orderId);
		if("1".equals(info.payStatus)){
			holder.payStatus.setText("支付成功");
		}else{
			holder.payStatus.setText("支付失败");
		}

		if("2".equals(info.payType)) {//挂号费支付
			holder.ll_diagnosing_pay.setVisibility(View.GONE);
			holder.ll_register_pay.setVisibility(View.VISIBLE);

			holder.hospitalName1.setText(info.hospitalName);
			holder.dpt_dct_clncTp.setText(info.department+"	 "+info.doctorName+"  "+info.clinicType);
			holder.patientName.setText(info.patientName);
			holder.orderTime.setText(info.orderTime);
			holder.price1.setText(info.price+"元");
		}else if("1".equals(info.payType)){//诊间支付
			holder.ll_diagnosing_pay.setVisibility(View.VISIBLE);
			holder.ll_register_pay.setVisibility(View.GONE);

			holder.hospitalName.setText(info.hospitalName);
			holder.price.setText(info.price+"元");
			holder.prescriptionTime.setText(info.prescriptionTime);
			holder.prescriptionCode.setText(info.prescriptionCode);
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		LinearLayout ll_msg,ll_register_pay,ll_diagnosing_pay;
		TextView orderId;// "1",//订单id
		TextView hospitalName;// "1",//医院名称
		TextView hospitalName1;// "1",//医院名称
		TextView patientName;// "1",//患者姓名
		TextView dpt_dct_clncTp;// "1",//患者姓名
/*		TextView department;// "1",//科室
		TextView doctorName;// "1",//医生姓名
		TextView clinicType;// "专家门诊",//门诊类型*/
		TextView price;// 0,//价格
		TextView price1;// 0,//价格
		TextView payStatus;// 1,//0 支付失败， 1 支付成功
		TextView payType;// 1,//1 诊间支付，2挂号费支付
		TextView payTypeName;// "诊间支付",//支付类型名称

		TextView prescriptionCode;// "123123123",//处方号码
		TextView prescriptionTime;// "2016-11-07 16:39"null"",//开方时间
		TextView orderTime;// "2016-11-07 周一 上午",//订单时间
		TextView createDate;// "2016-11-07 16:39:19.0",
		TextView registerId;// "1",//用户id
		TextView messageId;// "asdkjhkjakshd",
		TextView state;// 1  //状态

		public ViewHolder(View itemView) {
			super(itemView);
			ll_msg = (LinearLayout)itemView.findViewById(R.id.ll_msg);
			ll_register_pay = (LinearLayout)itemView.findViewById(R.id.ll_register_pay);
			ll_diagnosing_pay = (LinearLayout)itemView.findViewById(R.id.ll_diagnosing_pay);

			createDate=	(TextView) itemView.findViewById(R.id.txt_create_date);
			payTypeName=	(TextView) itemView.findViewById(R.id.txt_pay_type_name);
			payStatus	=(TextView) itemView.findViewById(R.id.txt_pay_status);
			hospitalName	=(TextView) itemView.findViewById(R.id.txt_hospital);
			hospitalName1	=(TextView) itemView.findViewById(R.id.txt_hospital1);
			dpt_dct_clncTp	=(TextView) itemView.findViewById(R.id.txt_dpt_dct_clncTp);
			patientName=(TextView) itemView.findViewById(R.id.txt_patient_name);
			price	=(TextView) itemView.findViewById(R.id.txt_price);
			price1	=(TextView) itemView.findViewById(R.id.txt_price1);
			orderTime	=(TextView) itemView.findViewById(R.id.txt_order_time);
			prescriptionTime=(TextView) itemView.findViewById(R.id.txt_prescription_time);
			prescriptionCode	=(TextView) itemView.findViewById(R.id.txt_prescription_code);
			orderId	=(TextView) itemView.findViewById(R.id.txt_order_id);
		}
	}
}
