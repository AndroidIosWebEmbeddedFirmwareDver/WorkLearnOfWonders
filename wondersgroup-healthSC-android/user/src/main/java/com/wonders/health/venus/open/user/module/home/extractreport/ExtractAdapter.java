package com.wonders.health.venus.open.user.module.home.extractreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.entity.ExtractTimeEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractAdapter extends BaseAdapter<ExtractHospitalEntity, ExtractAdapter.ExtractHolder> {
    private String key4Color;

    public ExtractAdapter(Context context, List<ExtractHospitalEntity> list, String key4Color) {
        super(context, list);
        this.key4Color = key4Color;
    }

    @Override
    public ExtractHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExtractHolder(mInflater.inflate(R.layout.item_extract_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ExtractHolder holder, int position) {
        ExtractHospitalEntity hospital = mItems.get(position);
        Pattern pattern = Pattern.compile(StringUtil.escapeExprSpecialWord(key4Color));
        Matcher matcher = pattern.matcher(hospital.hospitalName);
        holder.name.setText(Html.fromHtml(matcher.replaceAll("<font color=\"blue\" >" + key4Color + "</font>")));
    }


    public void changeItemColor(String key) {
        this.key4Color = key;
        notifyDataSetChanged();

    }


    public static class ExtractHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ExtractHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
