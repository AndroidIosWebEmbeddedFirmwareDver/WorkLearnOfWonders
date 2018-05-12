package com.wonders.health.venus.open.user.module.referral;




import com.wonders.health.venus.open.user.R;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wonders.health.venus.open.user.BaseActivity;

import com.wonders.health.venus.open.user.logic.ReferralServiceManager;
import com.wonders.health.venus.open.user.logic.UserManager;

import com.wonders.health.venus.open.user.module.referral.adapter.TurnDiagnosisAdapter;
import com.wonders.health.venus.open.user.module.referral.bean.TurnDiagnosisRecordEntity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 转诊记录
 * Created by win10 on 2017/5/31.
 */

public class TurnDiagnosisRecordActivity extends BaseActivity  {

    private PullToRefreshView pull_view;
    private RecyclerView recycler_view;
   private TurnDiagnosisAdapter myAdapter;
    public List<TurnDiagnosisRecordEntity.Referral> recordList = new ArrayList<TurnDiagnosisRecordEntity.Referral>();
    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;

    @Override
    protected void initViews() {
        setContentView(R.layout.transfer_treatment_record_activity);
     pull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);


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

    private void loadData(final int refreshType) {
        if (refreshType == Constant.TYPE_RELOAD || refreshType == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                pull_view.setLoadMoreEnable(mIsMore);
                return;
            }
        }

      ReferralServiceManager.getInstance().getReferral(mMoreParams, new FinalResponseCallback<TurnDiagnosisRecordEntity>(pull_view, refreshType) {
            @Override
            public void onSuccess(TurnDiagnosisRecordEntity t) {
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





    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("转诊信息");
        loadData(Constant.TYPE_INIT);

    }

    /**
     * 服务端取得数据更新UI
     *
     * @param type
     * @param itemCount
     */
    private void bindView(int type, int itemCount) {
        if (myAdapter == null) {
            myAdapter = new TurnDiagnosisAdapter(TurnDiagnosisRecordActivity.this,recordList);
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
