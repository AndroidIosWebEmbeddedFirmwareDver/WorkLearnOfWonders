package com.wonders.health.venus.open.doctor.module.registration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;


/**
 * 类描述：支付结果页
 * 创建人：Bob
 * 创建时间：2016/11/18 16:15
 */

public class PayResultActivity extends BaseActivity {
    public static final String EXTRA_RESULT = "result";
    private TextView mTv_pay_result;
    private TextView mTv_money;
    private TextView mTv_msg;
    private Button mBtn_pay;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_pay_result);
        mTv_pay_result = (TextView) findViewById(R.id.tv_pay_result);
        mTv_money = (TextView) findViewById(R.id.tv_money);
        mTv_msg = (TextView) findViewById(R.id.tv_msg);
        mBtn_pay = (Button) findViewById(R.id.btn_pay);

        mTitleBar.setTitle("支付详情");
        mTitleBar.setLeftVisible(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        final PayResult result = (PayResult) getIntent().getSerializableExtra(EXTRA_RESULT);
//        if (result == null) {
//            finish();
//            return;
//        }
//        if (result.code == PayResult.CODE_SUCCESS) {
//            mBtn_pay.setVisibility(View.VISIBLE);
//            mTv_money.setVisibility(View.VISIBLE);
//            mTv_msg.setVisibility(View.GONE);
//            mTv_pay_result.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_pay_success, 0, 0);
//            mTv_pay_result.setText("支付成功");
//            mTv_money.setText(result.price + "元");
//            mBtn_pay.setBackgroundResource(R.drawable.btn_selector);
//            mBtn_pay.setText("完成");
//        } else {
//            mTv_money.setVisibility(View.GONE);
//            mTv_msg.setVisibility(View.VISIBLE);
//            mTv_pay_result.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_pay_failed, 0, 0);
//            mTv_pay_result.setText("支付失败");
//            mTv_msg.setText(result.msg);
//            mTitleBar.addAction(new TitleBar.TextAction("完成") {
//                @Override
//                public void performAction(View view) {
//                    if (getIntent().getBooleanExtra(IS_APPOINTMENT, false)) {
//                        Intent intent = new Intent();
//                        intent.setClass(PayResultActivity.this, MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        PayResultActivity.this.startActivity(intent);
//                    } else {
//                        EventBus.getDefault().post(new RegistrationEvent());
//                        finish();
//                    }
//                }
//            });
//            if (result.code == PayResult.CODE_FAILED) {
//                mBtn_pay.setVisibility(View.VISIBLE);
//                mBtn_pay.setBackgroundResource(R.drawable.btn_submit_selector);
//                mBtn_pay.setText("重新支付");
//            } else if (result.code == PayResult.CODE_TIMEOUT) {
//                mBtn_pay.setVisibility(View.GONE);
//            }
//        }
//        mBtn_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (result.code == PayResult.CODE_SUCCESS) {
//                    if (getIntent().getBooleanExtra(IS_APPOINTMENT, false)) {
//                        Intent intent = new Intent();
//                        intent.setClass(PayResultActivity.this, MyAppointmentDetalActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.putExtra(KEY_ORDERID, result.orderId);
//                        intent.putExtra(KEY_PAY_FROM, true);
//                        PayResultActivity.this.startActivity(intent);
//                    }
//                    EventBus.getDefault().post(new RegistrationEvent());
//                }
//                finish();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
