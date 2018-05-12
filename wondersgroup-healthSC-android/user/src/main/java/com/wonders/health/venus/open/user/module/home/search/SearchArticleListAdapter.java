package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果--文章列表
 * Created by songzhen on 2016/11/10.
 */
public class SearchArticleListAdapter extends BaseAdapter {
    private static int MAX_SHOW_SIZE = 2;
    private BitmapTools mBitmaptool;
    private Context mContext;
    private List<ArticleItem> mItems = new ArrayList<>();
    private String keyword = "";

    public SearchArticleListAdapter(Context mContext,List<ArticleItem> mItems){
        new SearchArticleListAdapter(mContext,mItems,"");
    }

    public SearchArticleListAdapter(Context mContext,List<ArticleItem> mItems, String keyword){
        this.mContext = mContext;
        this.mItems = mItems;
        mBitmaptool = new BitmapTools(mContext);
        this.keyword = keyword;
    }
    @Override
    public int getCount() {
        if (mItems.size()>0){
            if (mItems.size()>MAX_SHOW_SIZE){
                return MAX_SHOW_SIZE;
            }else {
                return mItems.size();
            }
        }else {
            return 0;
        }
    }

    @Override
    public ArticleItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleSmallHolder smallHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_article_small,parent,false);
            smallHolder = new ArticleSmallHolder(convertView);
            convertView.setTag(smallHolder);
        }else {
            smallHolder = (ArticleSmallHolder) convertView.getTag();
        }
        ArticleItem info =  getItem(position);
        if (info != null) {
            mBitmaptool.display(smallHolder.item_iv, info.thumb, BitmapTools.SizeType.SMALL,R.mipmap.ic_default_article,null);
//            smallHolder.item_title.setText(info.title);
            if(!TextUtils.isEmpty(keyword)&&!TextUtils.isEmpty(info.title)){
                SpannableStringBuilder builder = new SpannableStringBuilder(info.title);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#2E7AF0"));
                int start = info.title.indexOf(keyword);
                int end = start + keyword.length();
                if (start!=-1){
                    builder.setSpan(blueSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                smallHolder.item_title.setText(builder);
            }else{
                smallHolder.item_title.setText(info.title+"");
            }
            smallHolder.item_pv.setVisibility(View.GONE);
//            smallHolder.item_desc.subTextView(false);
            smallHolder.item_desc.setText(info.desc);

        }
        return convertView;
    }

    public static class ArticleSmallHolder extends RecyclerView.ViewHolder {

        private final ImageView item_iv;
        private final TextView item_title;
        private final TextView item_desc;
        private final TextView item_pv;

        public ArticleSmallHolder(View view) {
            super(view);

            item_iv = (ImageView) view.findViewById(R.id.item_iv);
            item_title = (TextView) view.findViewById(R.id.item_title);
            item_desc = (TextView) view.findViewById(R.id.item_desc);
            item_pv = (TextView) view.findViewById(R.id.item_pv);
        }
    }
}
