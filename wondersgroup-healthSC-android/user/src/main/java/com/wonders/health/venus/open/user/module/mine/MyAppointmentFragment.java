package com.wonders.health.venus.open.user.module.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.Order;
import com.wonders.health.venus.open.user.entity.OrderResponse;
import com.wonders.health.venus.open.user.entity.event.MyAppointChangeEvent;
import com.wonders.health.venus.open.user.logic.MyAppointmentManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.view.HintEditTextView;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import de.greenrobot.event.EventBus;

/**
 * class MyAppointmentFragment
 * auth carrey
 * 16-11-7.
 */

public class MyAppointmentFragment extends BaseFragment implements BaseRecyclerView.OnItemClickListener, MyAppointmentAdapter.onAppointmentBtnClickListener {

    private PullToRefreshView mPullView;
    private BaseRecyclerView mOrderList;

    private MyAppointmentManager mManager;
    private MyAppointmentAdapter mAdapter;
    private OrderResponse mResponse;

    private int status;//0: //全部 2待就诊  3已取消  4已就诊


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, null);
    }

    @Override
    protected void initViews() {
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);
        mOrderList = (BaseRecyclerView) findViewById(R.id.brv_order_list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status", 0);

        }
        mManager = new MyAppointmentManager();
        mPullView.setLoadMoreEnable(false);
        mPullView.setRefreshEnable(true);
        mPullView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });

        mPullView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);

            }
        });
        mOrderList.setOnItemClickListener(this);
        loadData(Constant.TYPE_INIT);
    }

    private void loadData(final int type) {
        if (type != Constant.TYPE_NEXT) {
            if (mResponse == null) {
                mResponse = new OrderResponse();
            }
            mResponse.more_params = null;
        }

        mManager.getAppointmentList(mResponse.more_params, status, new FinalResponseCallback<OrderResponse>(mPullView, type) {

            @Override
            public void onSuccess(OrderResponse response) {
                super.onSuccess(response);
                mResponse.refresh(type, response);
                setIsEmpty(mResponse.isListEmpty());

                bindData(type, response.getList().size());
            }

            @Override
            public void onReload() {
                super.onReload();
                loadData(type);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mPullView.setLoadMoreEnable(mResponse != null ? mResponse.more : false);
                if (type == Constant.TYPE_NEXT) {
                    mPullView.onFooterRefreshComplete();
                } else {
                    mPullView.onHeaderRefreshComplete();
                }
            }
        });


    }

    private void bindData(int type, int itemCount) {
        if (mAdapter == null) {
            mAdapter = new MyAppointmentAdapter(mBaseActivity, mResponse.getList());
            mOrderList.setAdapter(mAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mAdapter.refreshList(mResponse.getList());
            } else {
                mAdapter.notifyItemRangeInserted(mResponse.getList().size() - itemCount, itemCount);
            }
        }
        mAdapter.setBtnClickListener(this);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        startActivity(new Intent(mBaseActivity, MyAppointmentDetalActivity.class)
                .putExtra(MyAppointmentDetalActivity.KEY_ORDERID, mAdapter.getItem(position).orderId));
    }


    private void showCancelDialog(final Order entity) {
        UIUtil.showConfirm(mBaseActivity, "温馨提示", "是否取消本次预约", "立即取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAppointment4Net(entity);

            }
        }, "暂不取消", null);
    }

    /**
     * 取消订单接口
     */
    private void cancelAppointment4Net(Order entity) {

        mManager.cancelOrder(entity.orderId, new ResponseCallback<String>() {

            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(mBaseActivity);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mSuccess) {
                    EventBus.getDefault().post(new MyAppointChangeEvent());
                }
                UIUtil.hideProgressBar(mBaseActivity);
            }
        });

    }


    private View evaluateView;
    private Button leftBtn, rightBtn;
    private HintEditTextView content;
    private Dialog evaluateDialog;

    private void showEvaluateDoctorDialog(final Order entity) {
        if (evaluateDialog == null) {

            if (evaluateView == null) {
                evaluateView = LayoutInflater.from(mBaseActivity).inflate(R.layout.dlg_evaluate_doctor, null);
                rightBtn = (Button) evaluateView.findViewById(R.id.btn_confirm_right);
                leftBtn = (Button) evaluateView.findViewById(R.id.btn_confirm_left);
                content = (HintEditTextView) evaluateView.findViewById(R.id.et_content);
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        evaluateDialog.dismiss();
                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        evaluateDoctor(entity.orderId, content.getContent());

                    }
                });
            }
            evaluateDialog = UIUtil.showAlert(mBaseActivity, evaluateView);
        } else {
            content.setContent("");

            evaluateDialog.show();
        }
    }

    private void evaluateDoctor(String orderId, String content) {

        if ("".equals(content.trim())) {
            UIUtil.toastShort(mBaseActivity, "请输入评价内容");
            return;
        }

        mManager.evaluateDoctor(orderId, content, new ResponseCallback() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(mBaseActivity);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mSuccess) {
                    evaluateDialog.dismiss();
                    EventBus.getDefault().post(new MyAppointChangeEvent());
                }
                UIUtil.hideProgressBar(mBaseActivity);
            }
        });

    }

    /**
     * 取消预约
     */
    @Override
    public void cancelAppointment(Order entity) {
        showCancelDialog(entity);
    }

    /**
     * 医生评价
     */
    @Override
    public void evaluateDoctor(Order entity) {

        showEvaluateDoctorDialog(entity);

    }

    public void onEvent(MyAppointChangeEvent event) {
        loadData(Constant.TYPE_RELOAD);
    }

}
