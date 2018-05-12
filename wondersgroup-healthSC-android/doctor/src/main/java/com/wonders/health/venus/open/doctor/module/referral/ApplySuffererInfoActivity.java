package com.wonders.health.venus.open.doctor.module.referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;

/**
 * 申请 -门诊------患者信息
 * Created by win10 on 2017/6/7.
 */

public class ApplySuffererInfoActivity  extends BaseActivity  implements View.OnClickListener{

    private TextView sex;
    private ImageView right_sex;
    private EditText et_sufferer_age;
    private ImageView right_type;
    private TextView tv_card_type;
    private EditText et_sufferer_card;
    private EditText et_sufferer_phone;
    private EditText et_sufferer_address;
    private TextView tv_commonly;
    private TextView tv_urgent;
    private EditText et_first_visit;
    private TextView tv_max_first_visit;
    private EditText et_medical_history;
    private TextView tv_max_medical_history;
    private EditText et_past;
    private TextView tv_max_past;
    private EditText et_treatment;
    private TextView tv_max;
    private Button bt_next;

    @Override
    protected void initViews() {
            setContentView(R.layout.apply_sufferer_info_activity);
        sex = (TextView) findViewById(R.id.tv_sufferer_sex);

        right_sex = (ImageView) findViewById(R.id.right_sex);//性别
        right_type = (ImageView) findViewById(R.id.right_type);
        tv_card_type = (TextView) findViewById(R.id.tv_card_type);//就诊卡类型
        et_sufferer_age = (EditText) findViewById(R.id.et_sufferer_age);//年龄
        et_sufferer_card = (EditText) findViewById(R.id.et_sufferer_card);//就诊卡号码
        //手机号码
        et_sufferer_phone = (EditText) findViewById(R.id.et_sufferer_phone);

        et_sufferer_address = (EditText) findViewById(R.id.et_sufferer_address);//家庭住址
        tv_commonly = (TextView) findViewById(R.id.tv_commonly);//一般紧急程度
        //紧急程度
        tv_urgent = (TextView) findViewById(R.id.tv_urgent);

        et_first_visit = (EditText) findViewById(R.id.et_first_visit);//初诊
        tv_max_first_visit = (TextView) findViewById(R.id.tv_max_first_visit);//当前输入的字数

        et_medical_history = (EditText) findViewById(R.id.et_medical_history);//病史摘要
        //当前输入的字数
        tv_max_medical_history = (TextView) findViewById(R.id.tv_max_medical_history);
        //主要既往史
        et_past = (EditText) findViewById(R.id.et_past);
        //当前输入的字数
        tv_max_past = (TextView) findViewById(R.id.tv_max_past);
        //治疗情况
        et_treatment = (EditText) findViewById(R.id.et_treatment);
        //当前输入的字数
        tv_max = (TextView) findViewById(R.id.tv_max);
        bt_next = (Button) findViewById(R.id.bt_next);//下一步


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next:
                //预约挂号
                startActivity(new Intent(ApplySuffererInfoActivity.this,ReferralInformationActivity.class));
                break;
        }

    }
}
