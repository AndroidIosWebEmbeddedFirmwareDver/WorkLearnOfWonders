package com.wonders.health.venus.open.user.module.home.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.RegistrationListVO;
import com.wonders.health.venus.open.user.entity.event.RegistrationEvent;
import com.wonders.health.venus.open.user.module.home.registration.adapter.AdapterForRegistrationList;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sunning on 16/1/4.
 * 挂号列表
 */
public class RegistrationListActivity extends BaseActivity {

    private BaseRecyclerView recyclerView;
    private PullToRefreshView refreshView;

    private AdapterForRegistrationList mAdapter;

    private List<RegistrationListVO> mRegistrationList;
    private HashMap<String, String> mMoreParams;
    private boolean mHasMore;

    @Override
    protected void initViews() {
        setContentView(R.layout.registration_list_layout);
        recyclerView = (BaseRecyclerView) findViewById(R.id.registration_recycler_view);
        refreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        mTitleBar.setTitle("我的预约");
        recyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                String regId = mRegistrationList.get(position).id;
                startActivity(new Intent(RegistrationListActivity.this, AppointmentDetailActivity.class)
                        .putExtra(AppointmentDetailActivity.EXTRA_ID, regId));
            }
        });
        refreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
        refreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
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

    private void loadData(final int type) {
        HashMap<String, String> moreParams = null;
        if (type == Constant.TYPE_NEXT) {
            moreParams = mMoreParams;
        }
//        RegistrationManager.getInstance().getMyAppointments(moreParams,
//                new FinalResponseCallback<RegistrationListResponse>(this, type) {
//                    @Override
//                    public void onSuccess(RegistrationListResponse t) {
//                        super.onSuccess(t);
//
//                        if (t != null) {
//                            mMoreParams = t.more_params;
//                            mHasMore = t.more;
//                            List<RegistrationListVO> content = t.getList();
//                            if (content != null) {
//                                if (mRegistrationList == null) {
//                                    mRegistrationList = new ArrayList<RegistrationListVO>();
//                                }
//                                if (type == Constant.TYPE_NEXT) {
//                                    mRegistrationList.addAll(content);
//                                } else {
//                                    mRegistrationList = content;
//                                }
//                            }
//
//                            setIsEmpty(mRegistrationList.isEmpty());
//                            if (mAdapter == null) {
//                                mAdapter = new AdapterForRegistrationList(RegistrationListActivity.this, mRegistrationList);
//                                recyclerView.setAdapter(mAdapter);
//                            } else {
//                                mAdapter.refreshList(mRegistrationList);
//                            }
//                        } else {
//                            setIsEmpty(true);
//                        }
//                    }
//
//                    @Override
//                    public void onReload() {
//                        super.onReload();
//                        loadData(Constant.TYPE_INIT);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        if (type == Constant.TYPE_NEXT) {
//                            refreshView.onFooterRefreshComplete();
//                        } else {
//                            refreshView.onHeaderRefreshComplete();
//                        }
//                        refreshView.setLoadMoreEnable(mHasMore);
//                    }
//                });

    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    public void onEvent(RegistrationEvent event) {
        if (event.isCancel) {
            if (mRegistrationList != null) {
                // 收到取消预约事件，刷新下该item的状态
                for (RegistrationListVO registrationListVO : mRegistrationList) {
                    if (registrationListVO.id.equals(event.regId)) {
                        registrationListVO.status = "4";
                        if (mAdapter == null) {
                            mAdapter = new AdapterForRegistrationList(RegistrationListActivity.this, mRegistrationList);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.refreshList(mRegistrationList);
                        }
                        break;
                    }
                }
            }
        }
    }
}
