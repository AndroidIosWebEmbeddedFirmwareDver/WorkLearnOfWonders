package com.wonders.health.venus.open.user.module.health;

import android.os.Bundle;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.EvaluateEntity;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zjy on 2016/11/8.
 */

public class EvaluateListActivity extends BaseActivity {
    public static final String EVALUATE_TYPE="evaluateType";
    public static final String EVALUATE_COUNT="evaluateCount";
    public static final String EVALUATE_TYPE_HOSPITAL="0";
    public static final String EVALUATE_TYPE_DOCTOR="1";
    public static final String EVALUATE_TARGET_ID="evaluateTargetId";

    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleView;
    public EvaluateAdapter mEvaluateAdapter;
    public List<EvaluateEntity.Evaluate> mEvaluateList = new ArrayList<EvaluateEntity.Evaluate>();
    private HashMap<String, String> mMoreParams;
    private String evaluateId,evaluateType,evaluateCount;
    private boolean mIsMore;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_evaluate_list);

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.refresh_view);
        mRecycleView = (BaseRecyclerView) findViewById(R.id.recycle_view);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
        mPullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        evaluateId = getIntent().getStringExtra(EVALUATE_TARGET_ID);
        evaluateType = getIntent().getStringExtra(EVALUATE_TYPE);
        evaluateCount = getIntent().getStringExtra(EVALUATE_COUNT);
        if(EVALUATE_TYPE_HOSPITAL.equals(evaluateType)) {
            mTitleBar.setTitle("医院评价("+evaluateCount+")");
        }else if(EVALUATE_TYPE_DOCTOR.equals(evaluateType)){
            mTitleBar.setTitle("患者评价("+evaluateCount+")");
        }
        loadData(Constant.TYPE_INIT);
    }


    /**
     * 从服务端取数据
     */
    private void loadData(final int refreshType) {
        if (refreshType == Constant.TYPE_RELOAD || refreshType == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                mPullToRefreshView.setLoadMoreEnable(mIsMore);
                return;
            }
        }

        UserManager.getInstance().getEvaluateList(evaluateType,evaluateId,mMoreParams, new FinalResponseCallback<EvaluateEntity>(mPullToRefreshView, refreshType) {
            @Override
            public void onSuccess(EvaluateEntity t) {
                super.onSuccess(t);
                if (t != null && null != t.getList()) {
                    setIsEmpty(t.getList().isEmpty());
                }
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (refreshType != Constant.TYPE_NEXT) {
                    mEvaluateList.clear();
                }
                mEvaluateList.addAll(t.getList());
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
                    mPullToRefreshView.onFooterRefreshComplete();
                } else {
                    mPullToRefreshView.onHeaderRefreshComplete();
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
        if (mEvaluateAdapter == null) {
            mEvaluateAdapter = new EvaluateAdapter(EvaluateListActivity.this, mEvaluateList);
            mRecycleView.setAdapter(mEvaluateAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mEvaluateAdapter.refreshList(mEvaluateList);
            } else {
                mEvaluateAdapter.notifyItemRangeInserted(mEvaluateList.size() - itemCount, itemCount);
            }
        }
        mPullToRefreshView.setLoadMoreEnable(mIsMore);
    }

}
