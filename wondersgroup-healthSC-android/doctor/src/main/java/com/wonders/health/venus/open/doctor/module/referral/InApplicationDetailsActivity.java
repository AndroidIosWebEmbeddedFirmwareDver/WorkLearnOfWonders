package com.wonders.health.venus.open.doctor.module.referral;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;
import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;

/**
 * 申请中点击后跳转到------------------------详情界面
 * Created by win10 on 2017/6/8.
 */

public class InApplicationDetailsActivity extends BaseActivity {
    private int mDesHeight;
    private LinearLayout.LayoutParams mParams;
    private LinearLayout llDesRoot;//隐藏详细信息
    private TextView rlDesRoot;//点击事件处理
    private TextView sex;
    private TextView age;
    private TextView patient_id_card;
    private TextView id_card;
    private TextView phone;
    private TextView address;
    private TextView tv_diagnosis_up;
    private TextView degree;
    private TextView state;
    private TextView time;
    private TextView hospital_in;
    private TextView hospital_out;
    private TextView diagnosis_info;
    private TextView diagnosis_des1;
    private TextView diagnosis_des2;
    private TextView diagnosis_des3;
    private TextView diagnosis_des4;
    private TextView diagnosis_des5;
    private Button bt_cancel_app;

    @Override
    protected void initViews() {
        setContentView(R.layout.in_app_details_activity);
        mTitleBar.setTitle("患者姓名");
        sex = (TextView) findViewById(R.id.tv_diagnosis_sex);//性别
        age = (TextView) findViewById(R.id.tv_diagnosis_age);//年龄
        //年龄
        patient_id_card = (TextView) findViewById(R.id.tv_diagnosis_patientidcard);//就诊卡
        //身份证
        id_card = (TextView) findViewById(R.id.tv_diagnosis_idcard);
        //手机号
        phone = (TextView) findViewById(R.id.tv_diagnosis_phone);
        address = (TextView) findViewById(R.id.tv_diagnosis_address);//住址
        //住址
        tv_diagnosis_up = (TextView) findViewById(R.id.tv_diagnosis_up);//上转  下转
        //紧急程度
        degree = (TextView) findViewById(R.id.tv_diagnosis_degree);
        state = (TextView) findViewById(R.id.tv_diagnosis_state);//转诊状态
        //时间
        time = (TextView) findViewById(R.id.tv_diagnosis_time);
        //转入医院名
        hospital_in = (TextView) findViewById(R.id.tv_diagnosis_hospital_in);
        ////转出医院名
        hospital_out = (TextView) findViewById(R.id.tv_diagnosis_hospital_out);
        //预约信息
        diagnosis_info = (TextView) findViewById(R.id.tv_diagnosis_info);
        diagnosis_des1 = (TextView) findViewById(R.id.tv_diagnosis_des1);//转诊原因
        diagnosis_des2 = (TextView) findViewById(R.id.tv_diagnosis_des2); //初步诊断
        //病史摘要
        diagnosis_des3 = (TextView) findViewById(R.id.tv_diagnosis_des3);
        //主要既往史
        diagnosis_des4 = (TextView) findViewById(R.id.tv_diagnosis_des4);
        //治疗情况
        diagnosis_des5 = (TextView) findViewById(R.id.tv_diagnosis_des5);
        llDesRoot = (LinearLayout) findViewById(R.id.ll_des_root);
        rlDesRoot = (TextView) findViewById(R.id.tv_des_root);//查看详情
        //取消申请
        bt_cancel_app = (Button) findViewById(R.id.bt_cancel_app);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        diagnosis_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   startActivity(new Intent(this, 挂号界面));
            }
        });

        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        // 获取安全描述的完整高度
        llDesRoot.measure(0, 0);
        mDesHeight = llDesRoot.getMeasuredHeight();

        System.out.println("安全描述高度:" + mDesHeight);

        // 修改安全描述布局高度为0,达到隐藏效果
        mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);
    }

    private boolean isOpen=  false;
    private void toggle() {
        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;
            // 属性动画
            animator = ValueAnimator.ofInt(mDesHeight, 0);// 从某个值变化到某个值
        } else {
            // 开启
            isOpen = true;
            // 属性动画
            animator = ValueAnimator.ofInt(0, mDesHeight);
        }

        // 动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 启动动画之后, 会不断回调此方法来获取最新的值
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获取最新的高度值
                Integer height = (Integer) animator.getAnimatedValue();

                System.out.println("最新高度:" + height);

                // 重新修改布局高度
                mParams.height = height;
                llDesRoot.setLayoutParams(mParams);
            }
        });
        animator.setDuration(200);// 动画时间
        animator.start();// 启动动画


    }
}
