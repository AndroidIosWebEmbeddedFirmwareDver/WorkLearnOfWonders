package com.wonders.health.venus.open.doctor.module.referral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.InsertReferralList;
import com.wonders.health.venus.open.doctor.logic.ReferralServiceManager;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 接入转诊---首页
 * Created by win10 on 2017/6/6.
 */

public class InsertReferralActivity  extends BaseActivity {

    private BaseRecyclerView recycler_view;
    private PullToRefreshView pull_view;
    private InsertReferralAdapter myAdapter;
    public List<InsertReferralList> recordList = new ArrayList<InsertReferralList>();
    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    @Override
    protected void initViews() {
        setContentView(R.layout.insert_referral_activity);
        pull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        recycler_view = (BaseRecyclerView)findViewById(R.id.recycler_view);
        pull_view.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
        pull_view.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("接入转诊");
        loadData(Constant.TYPE_INIT);
        recycler_view.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent  intent  =new Intent(InsertReferralActivity.this,InsertDetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
    private void loadData(final int refreshType) {
        if (refreshType == Constant.TYPE_RELOAD || refreshType == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                pull_view.setLoadMoreEnable(mIsMore);
                return;
            }
        }

       ReferralServiceManager.getInstance().getinsert(mMoreParams, new FinalResponseCallback<InsertReferralList>(pull_view, refreshType) {
            @Override
            public void onSuccess(InsertReferralList t) {
                super.onSuccess(t);
                if (t != null && null != t.getList()) {
                    setIsEmpty(t.getList().isEmpty());
                }
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (refreshType != Constant.TYPE_NEXT) {
                    recordList.clear();
                }
                recordList.addAll(t.getList());
                bindView(refreshType, t.getList().size());
            }

            @Override
            public void onReload() {
                super.onReload();
                loadData(Constant.TYPE_INIT);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (refreshType == Constant.TYPE_NEXT) {
                    pull_view.onFooterRefreshComplete();
                } else {
                    pull_view.onHeaderRefreshComplete();
                }
            }
        });
    }
    /**
     * 服务端取得数据更新UI
     *
     * @param type
     * @param itemCount
     */
    private void bindView(int type, int itemCount) {
        if (myAdapter == null) {
            myAdapter =new InsertReferralAdapter(InsertReferralActivity.this,recordList);
            recycler_view.setAdapter(myAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                myAdapter.refreshList(recordList);
            } else {
                myAdapter.notifyItemRangeInserted(recordList.size() - itemCount, itemCount);
            }
        }
        pull_view.setLoadMoreEnable(mIsMore);
    }


}
