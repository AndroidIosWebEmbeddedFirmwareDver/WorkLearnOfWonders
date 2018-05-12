package com.wonders.health.venus.open.doctor.module.referral;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;

import butterknife.BindView;

/**
 * 住院————患者信息
 * Created by win10 on 2017/6/8.
 */

public class HospitalizationPatientInfoActivity extends BaseActivity {

    @BindView(R.id.bt_next)
    Button bt_next;//下一步
     @BindView(R.id.et_patient_treatment)
     EditText et_patient_treatment;//治疗情况
    @BindView(R.id.tv_max)
    TextView tv_max;
    @BindView(R.id.et_patient_past)
    EditText et_patient_past;//主要既往史
    @BindView(R.id.tv_max_past)
    TextView tv_max_past;


    @BindView(R.id.et_patient_medical_history)
    EditText et_patient_medical_history;//病史摘要
    @BindView(R.id.tv_max_medical_history)
    TextView tv_max_medical_history;


    @BindView(R.id.et_patient_first_visit)
    EditText et_patient_first_visit;//初步诊断
    @BindView(R.id.tv_max_first_visit)
    TextView tv_max_first_visit;

    @BindView(R.id.tv_patient_urgent)
    TextView tv_patient_urgent;//紧急
    @BindView(R.id.tv_patient_commonly)
    TextView tv_patient_commonly;//一般
    @BindView(R.id.et_patient_address)
    EditText et_patient_address;//家庭住址

    @BindView(R.id.et_patient_phone)
    EditText et_patient_phone;//手机号

    @BindView(R.id.et_patient_age)
    EditText et_patient_age;//年龄

    @BindView(R.id.tv_patient_sex)
    TextView tv_patient_sex;//设置性别

    @BindView(R.id.iv_patient_right)
    ImageView iv_patient_right;//选择性别按钮


    @Override
    protected void initViews() {
        setContentView(R.layout.hospitalization_patient_info_activity);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
