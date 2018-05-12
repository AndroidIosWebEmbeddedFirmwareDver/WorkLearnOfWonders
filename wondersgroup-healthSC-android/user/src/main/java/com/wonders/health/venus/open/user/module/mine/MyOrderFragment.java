package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.OrderResponse;
import com.wonders.health.venus.open.user.entity.event.OrderPaySuccessEvent;
import com.wonders.health.venus.open.user.entity.event.OrderTimeOutEvent;
import com.wonders.health.venus.open.user.logic.OrderManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * 类描述：我的订单
 * 创建人：tanghaihua
 * 创建时间：2016/1/1 14:30
 */
public class MyOrderFragment extends BaseFragment {
    public static final String EXTRA_ORDER_STATUS = "order_status";
    private boolean isPaySuccess = false;
    private boolean isTimeOut = false;

    public enum ORDER_STATUS{
        ALL, NOTPAY, SUCCESS, REFUNDSUCCESS, EXPIRED;
    }

    private PullToRefreshView mPull_view;
    private BaseRecyclerView mRecycler_view;
    private OrderAdapter mOrderAdapter;
    private OrderResponse mOrderResponse;
    private String mOrderStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ORDER_STATUS orderStatus = (ORDER_STATUS) getArguments().getSerializable(EXTRA_ORDER_STATUS);
            if(orderStatus != null){
                mOrderStatus = orderStatus.toString();
            }
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myorder, null, false);
    }

    @Override
    protected void initViews() {
        mPull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        mPull_view.setRefreshEnable(true);
        mPull_view.setLoadMoreEnable(true);
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
        new OrderManager().getOrderList(mOrderStatus, UserManager.getInstance().getUser().uid, params,
                new FinalResponseCallback<OrderResponse>(mPull_view, type) {
            @Override
            public void onSuccess(OrderResponse t) {
                super.onSuccess(t);
                int count = mOrderResponse.refresh(type, t);
                if (mOrderResponse.isListEmpty()) {
                    setIsEmpty(true);
                } else {
                    if (mOrderAdapter == null) {
                        mOrderAdapter = new OrderAdapter(mBaseActivity, mOrderResponse.getList(), true);
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
