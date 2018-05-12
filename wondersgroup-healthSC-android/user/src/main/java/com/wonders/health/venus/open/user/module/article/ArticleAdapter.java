package com.wonders.health.venus.open.user.module.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.List;

/**
 * 类描述：文章列表适配器
 * 创建人：tanghaihua
 * 创建时间：2015/7/1 14:40
 */
public class ArticleAdapter extends BaseAdapter<ArticleItem, RecyclerView.ViewHolder> {
    private BitmapTools mBitmapTools;
    private boolean isCollection;
    private boolean isSearchResult;


    public ArticleAdapter(Context context, List<ArticleItem> list) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);
    }

    public ArticleAdapter(Context context, List<ArticleItem> list,boolean isCollection) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);
        this.isCollection = isCollection;
    }

    public ArticleAdapter(boolean isSearchResult,Context context, List<ArticleItem> list) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);
        this.isSearchResult = isSearchResult;
    }
    @Override
    public int getItemViewType(int position) {
        return getItem(position).img_type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ArticleItem.TYPE_SMALL_IMG:
                return new ArticleSmallHolder(mInflater.inflate(R.layout.item_article_small, null));
            case ArticleItem.TYPE_LARGE_IMG:
                return new ArticleLargeHolder(mInflater.inflate(R.layout.item_article_large, null));
            default:
                return new ArticleSmallHolder(mInflater.inflate(R.layout.item_article_small, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ArticleItem info = mItems.get(position);
        if (info != null) {
            switch (info.img_type) {
                case ArticleItem.TYPE_LARGE_IMG:
                    ArticleLargeHolder largeHolder = (ArticleLargeHolder) holder;

                    mBitmapTools.display(largeHolder.item_iv, info.thumb, BitmapTools.SizeType.LARGE);
                    largeHolder.item_desc.setText(info.desc);
                    break;
                case ArticleItem.TYPE_SMALL_IMG:
                default:
                    ArticleSmallHolder smallHolder = (ArticleSmallHolder) holder;

                    mBitmapTools.display(smallHolder.item_iv, info.thumb, BitmapTools.SizeType.SMALL, R.mipmap.ic_default_article, null);
                    smallHolder.item_title.setText(info.title);
                    smallHolder.item_desc.setText(info.desc);
                    smallHolder.item_pv.setVisibility(View.GONE);
//                    if (isCollection){
//                        if(!TextUtils.isEmpty(info.date)){
//                            smallHolder.item_pv.setText(info.date+"");
//                            smallHolder.item_pv.setVisibility(View.VISIBLE);
//                            smallHolder.item_desc.subTextView(true);
//                        }else{
//                            smallHolder.item_pv.setVisibility(View.GONE);
//                            smallHolder.item_desc.subTextView(false);
//                        }
//                        smallHolder.item_desc.setText(info.desc);
//                    }else if (isSearchResult){
//                        smallHolder.item_pv.setVisibility(View.GONE);
//                        smallHolder.item_desc.subTextView(false);
//                        smallHolder.item_desc.setText(info.desc);
//                    }else {
//                        if(!TextUtils.isEmpty(info.pv) && !"0".equals(info.pv)){
//                            smallHolder.item_pv.setText(info.pv + "阅");
//                            smallHolder.item_pv.setVisibility(View.VISIBLE);
//                            smallHolder.item_desc.subTextView(true);
//                        }else{
//                            smallHolder.item_pv.setVisibility(View.GONE);
//                            smallHolder.item_desc.subTextView(false);
//                        }
//                        smallHolder.item_desc.setText(info.desc);
//                    }
                    break;
            }
        }
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

    public static class ArticleLargeHolder extends RecyclerView.ViewHolder {

        private final ImageView item_iv;
        private final TextView item_desc;

        public ArticleLargeHolder(View view) {
            super(view);

            item_iv = (ImageView) view.findViewById(R.id.item_iv);
            item_desc = (TextView) view.findViewById(R.id.item_desc);

        }
    }
}
