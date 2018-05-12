package com.wonders.health.venus.open.user.module.home;

import android.os.Bundle;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.OrderResponse;
import com.wonders.health.venus.open.user.entity.event.OrderPaySuccessEvent;
import com.wonders.health.venus.open.user.entity.event.OrderTimeOutEvent;
import com.wonders.health.venus.open.user.logic.OrderManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.mine.OrderAdapter;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.HashMap;

/**
 * 类描述：诊间支付列表
 * 创建人：tanghaihua
 * 创建时间：11/7/16 8:06 PM
 */
public class PayClinicActivity extends BaseActivity {
    public static final String EXTRA_HOSPITAL_CODE = "hospital_code";

    private PullToRefreshView mPull_view;
    private BaseRecyclerView mRecycler_view;
    private OrderResponse mOrderResponse;
    private String mHispitalCode;
    private OrderAdapter mOrderAdapter;
    private boolean isPaySuccess = false;
    private boolean isTimeOut = false;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_clinic);
        mPull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.recycler_view);

        mPull_view.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
        mPull_view.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("诊间支付");

        mHispitalCode = getIntent().getStringExtra(EXTRA_HOSPITAL_CODE);
        mOrderResponse = new OrderResponse();
        loadData(Constant.TYPE_INIT);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isPaySuccess){
            loadData(Constant.TYPE_RELOAD);
            isPaySuccess = false;
        }

        if(isTimeOut){
            loadData(Constant.TYPE_RELOAD);
            isTimeOut = false;
        }
    }

    private void loadData(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mOrderResponse.more_params;
        }
        new OrderManager().getClinicList(mHispitalCode, UserManager.getInstance().getUser().uid, params,
                new FinalResponseCallback<OrderResponse>(mPull_view, type) {
                    @Override
                    public void onSuccess(OrderResponse t) {
                        super.onSuccess(t);
                        int count = mOrderResponse.refresh(type, t);
                        if (mOrderResponse.isListEmpty()) {
                            setIsEmpty(true);
                        } else {
                            if (mOrderAdapter == null) {
                                mOrderAdapter = new OrderAdapter(PayClinicActivity.this, mOrderResponse.getList(), false);
                                mRecycler_view.setAdapter(mOrderAdapter);
                            } else {
                                mOrderAdapter.refreshList(mOrderResponse.getList(), count);
                            }
                        }
                    }

                    @Override
                    public void onReload() {
                        super.onReload();
                        loadData(Constant.TYPE_INIT);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (type == Constant.TYPE_NEXT) {
                            mPull_view.onFooterRefreshComplete();
                        } else {
                            mPull_view.onHeaderRefreshComplete();
                        }
                        mPull_view.setLoadMoreEnable(mOrderResponse.more);
                    }
                });
    }

    /**
     * 支付成功后刷新订单列表
     * @param event
     */
    public void onEvent(OrderPaySuccessEvent event) {
        isPaySuccess = true;
    }

    /**
     * 订单超时
     * @param event
     */
    public void onEvent(OrderTimeOutEvent event) {
        isTimeOut = true;
    }
}
