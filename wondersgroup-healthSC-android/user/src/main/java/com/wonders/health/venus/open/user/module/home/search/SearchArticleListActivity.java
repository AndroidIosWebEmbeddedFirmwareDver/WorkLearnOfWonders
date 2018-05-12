package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wonders.health.venus.open.user.entity.SearchArticleData;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页搜索-医生列表--加载更多
 * Created by songzhen on 2016/11/10.
 */
public class SearchArticleListActivity extends BaseActivity {

    public static String EXTRA_KEY = "key";
    private LinearLayout layout_search;
    private BaseRecyclerView mRecyclerView;
    private PullToRefreshView mPullView;
    private ArticleAdapter mAdapter;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private List<ArticleItem> mItems = new ArrayList<>();

    private SearchManager mManager;
    private String key;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_homesearchresult_list);
        layout_search = (LinearLayout) findViewById(R.id.layout_search);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);
        mTitleBar.setTitle("全部文章");
        layout_search.setVisibility(View.GONE);
        addListener();
    }

    private void addListener() {
        mPullView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getArticleList(Constant.TYPE_RELOAD);
            }
        });
        mPullView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getArticleList(Constant.TYPE_NEXT);
            }
        });
        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                SchemeUtil.startActivity(SearchArticleListActivity.this,mItems.get(position).url);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager = new SearchManager();
        key = getIntent().getStringExtra(EXTRA_KEY);
        getArticleList(Constant.TYPE_INIT);
    }

    //获取医院列表
    private void getArticleList(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mMoreParams;
        }
        mManager.SearchArticleList(params, key, new FinalResponseCallback<SearchArticleData>(this, type) {
            @Override
            public void onSuccess(SearchArticleData t) {
                super.onSuccess(t);
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (type != Constant.TYPE_NEXT) {
                    mItems.clear();
                }
                mItems.addAll(t.getList());
                setIsEmpty(mItems.isEmpty());
                bindView(type, t.getList().size());
            }

            @Override
            public void onReload() {
                super.onReload();
                getArticleList(Constant.TYPE_INIT);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (type == Constant.TYPE_NEXT) {
                    mPullView.onFooterRefreshComplete();
                } else {
                    mPullView.onHeaderRefreshComplete();
                }
            }
        });
    }


    private void bindView(int type, int itemCount) {
        if (mAdapter == null) {
            mAdapter = new ArticleAdapter(this, mItems);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mAdapter.refreshList(mItems);
            } else {
                mAdapter.notifyItemRangeInserted(mItems.size() - itemCount, itemCount);
            }
        }
        mPullView.setLoadMoreEnable(mIsMore);
    }



    class ArticleAdapter extends BaseAdapter<ArticleItem,ArticleAdapter.ArticleListHolder>{

        private BitmapTools mBitmaptool;
        public ArticleAdapter(Context context, List<ArticleItem> list) {
            super(context, list);
            mBitmaptool = new BitmapTools(context);
        }

        @Override
        public ArticleListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_article_small,parent,false);
            return new ArticleListHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleListHolder holder, int position) {
            ArticleItem info =  getItem(position);
            if (info != null) {
                mBitmaptool.display(holder.item_iv, info.thumb, BitmapTools.SizeType.SMALL,R.mipmap.ic_default_article,null);
                holder.item_title.setText(info.title);
                holder.item_pv.setVisibility(View.GONE);
//                holder.item_desc.subTextView(false);
                holder.item_desc.setText(info.desc);

            }
        }

        class ArticleListHolder extends RecyclerView.ViewHolder {
            private final ImageView item_iv;
            private final TextView item_title;
            private final TextView item_desc;
            private final TextView item_pv;

            public ArticleListHolder(View view) {
                super(view);

                item_iv = (ImageView) view.findViewById(R.id.item_iv);
                item_title = (TextView) view.findViewById(R.id.item_title);
                item_desc = (TextView) view.findViewById(R.id.item_desc);
                item_pv = (TextView) view.findViewById(R.id.item_pv);
            }
        }
    }


}
