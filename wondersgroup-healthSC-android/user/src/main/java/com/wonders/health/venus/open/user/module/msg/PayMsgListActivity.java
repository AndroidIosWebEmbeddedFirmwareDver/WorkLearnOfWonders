package com.wonders.health.venus.open.user.module.msg;

import android.os.Bundle;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.MsgEntity;
import com.wonders.health.venus.open.user.entity.event.MessageEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 支付消息列表
 * Created by zjy on 2016/11/8.
 */

public class PayMsgListActivity extends BaseActivity {
    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleView;
    public PayMsgAdapter mMsgAdapter;
    public List<MsgEntity.message> mMsgList = new ArrayList<MsgEntity.message>();
    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_msg_list);
        mTitleBar.setTitle("支付通知");
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

       UserManager.getInstance().getPayMsg(mMoreParams, new FinalResponseCallback<MsgEntity>(mPullToRefreshView, refreshType) {
            @Override
            public void onSuccess(MsgEntity t) {
                super.onSuccess(t);
                if (t != null && null != t.getList()) {
                    setIsEmpty(t.getList().isEmpty(),"暂无通知");
                }
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (refreshType != Constant.TYPE_NEXT) {
                    mMsgList.clear();
                }
                mMsgList.addAll(t.getList());
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
        if (mMsgAdapter == null) {
            mMsgAdapter = new PayMsgAdapter(PayMsgListActivity.this, mMsgList);
            mRecycleView.setAdapter(mMsgAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mMsgAdapter.refreshList(mMsgList);
            } else {
                mMsgAdapter.notifyItemRangeInserted(mMsgList.size() - itemCount, itemCount);
            }
        }
        mPullToRefreshView.setLoadMoreEnable(mIsMore);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new MessageEvent(true));
    }
}
