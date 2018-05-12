package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 * 搜索页历史列表adapter
 * Created by songzhen on 2016/11/9.
 */
public class SearchHisAdapter extends BaseAdapter<String,SearchHisAdapter.HisAdapter> {
    //最大行数
    public static final int LINE_COUNT = 3;
    private boolean isShowAll ;
    private String key;

    public SearchHisAdapter(Context context, List<String> list) {
        super(context, list);
    }
    public SearchHisAdapter(Context context, List<String> list, String key) {
        super(context, list);
        this.key = key;
    }

    public SearchHisAdapter(Context context, List<String> list, Boolean isShowAll) {
        super(context, list);
        this.isShowAll = isShowAll;
    }

//    @Override
//    public int getItemCount() {
//        if (mItems != null) {
//            if (isShowAll){
//                return mItems.size();
//            }else {
//                return mItems.size()>LINE_COUNT?LINE_COUNT:mItems.size();
//            }
//        } else {
//            return 0;
//        }
//    }

    public void changeStatus(boolean isShowAll){
        this.isShowAll = isShowAll;
    }

    //刷新列表状态
    public void refresh(List<String> list, boolean isShowAll){
        mItems = list;
        changeStatus(isShowAll);
        notifyDataSetChanged();
    }

    @Override
    public HisAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search_history,parent,false);
        return new HisAdapter(view);
    }

    @Override
    public void onBindViewHolder(HisAdapter holder, int position) {
        String str = mItems.get(position);
        if (!TextUtils.isEmpty(key)){
            if (str.contains(key)){
                int start = str.indexOf(key);
                int end = start+key.length();
                SpannableStringBuilder builder = new SpannableStringBuilder(str);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
                builder.setSpan(blueSpan,start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv_history.setText(builder);
            }else {
                holder.tv_history.setText(str);
            }
        }else {
            holder.tv_history.setText(str);
        }

    }

    class HisAdapter extends RecyclerView.ViewHolder{
        private TextView tv_history;

        public HisAdapter(View itemView) {
            super(itemView);
            tv_history = (TextView) itemView.findViewById(R.id.tv_history);
        }
    }
}
