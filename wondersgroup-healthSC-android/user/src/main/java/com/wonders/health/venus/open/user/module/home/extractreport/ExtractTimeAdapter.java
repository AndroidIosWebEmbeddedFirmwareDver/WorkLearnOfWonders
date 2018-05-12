package com.wonders.health.venus.open.user.module.home.extractreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractTimeAdapter extends BaseAdapter<String, ExtractTimeAdapter.TimeViewHolder> implements View.OnClickListener {

    private int clickPos;

    public ExtractTimeAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_extract_time_layout, null);
        TimeViewHolder viewHolder = new TimeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TimeViewHolder holder, final int position) {
        final String content = mItems.get(position);
        holder.tvContent.setText(content);
    }

    @Override
    public void onClick(View v) {
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public RadioButton rbCheck;

        public TimeViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rb_check);
        }
    }
}
