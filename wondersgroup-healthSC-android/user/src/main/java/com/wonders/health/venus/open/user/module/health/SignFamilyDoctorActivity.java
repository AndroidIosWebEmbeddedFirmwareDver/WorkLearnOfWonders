package com.wonders.health.venus.open.user.module.health;

/*
 * Created by sunning on 2017/6/5.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SignFamilyDoctorActivity extends BaseActivity {

    @BindView(R.id.sign_tv_desc1) TextView signDoctorNum;
    @BindView(R.id.sign_tv_desc2) TextView signServiceNum;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_sign);
        mTitleBar.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setSignDoctorNum("9");
        setDoctorServiceNum("6666");
    }

    @OnClick(R.id.sign_btn)
    public void sign() {
        startActivity(new Intent(this, SignDoctorTeamActivity.class));
    }

    private void setSignDoctorNum(String signNum) {
        setTextFormat(signDoctorNum, 2, "已有" + signNum + "个家庭医生团队入驻该平台", signNum.length());
    }

    private void setDoctorServiceNum(String serviceNum) {
        setTextFormat(signServiceNum, 1, "为" + serviceNum + "个家庭提供家庭医生服务", serviceNum.length());
    }

    private void setTextFormat(TextView tv, int prefixIndex, String originStr, int valueLength) {
        int endLength = prefixIndex + valueLength;
        SpannableString spannableString = new SpannableString(originStr);
        RelativeSizeSpan scaleSpan = new RelativeSizeSpan(2.f);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpan_B, prefixIndex, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, prefixIndex, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(scaleSpan, prefixIndex, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, prefixIndex, endLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

}
