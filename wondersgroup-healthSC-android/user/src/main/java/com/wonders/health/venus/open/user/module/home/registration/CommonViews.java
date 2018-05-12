package com.wonders.health.venus.open.user.module.home.registration;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AppConfig;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：11/11/16 5:11 PM
 */
public class CommonViews {

    static View getRuleView(final Context mContext) {
        View container = LayoutInflater.from(mContext).inflate(R.layout.view_appoint_tips, null);
        TextView tv_tip = (TextView) container.findViewById(R.id.tv_tip);
        TextView tv_rule = (TextView) container.findViewById(R.id.tv_rule);
        String text = tv_tip.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.tc1)), text.indexOf("，"), text.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_tip.setText(spannableString);
        tv_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.Common appConfig = AppConfigManager.getInstance().getAppConfig();
                if (appConfig != null && !TextUtils.isEmpty(appConfig.appointmentRule)) {
                    SchemeUtil.startActivity(mContext, appConfig.appointmentRule);
                }
            }
        });
        return container;
    }

}
