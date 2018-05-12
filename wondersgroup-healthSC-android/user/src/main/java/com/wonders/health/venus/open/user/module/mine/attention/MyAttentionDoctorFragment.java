package com.wonders.health.venus.open.user.module.mine.attention;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorDetailInfo;
import com.wonders.health.venus.open.user.entity.DoctorInfoData;
import com.wonders.health.venus.open.user.entity.event.AttentDoctorUpdateEvent;
import com.wonders.health.venus.open.user.module.home.registration.RegistrationDoctorDetailActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by szy
 */
public class MyAttentionDoctorFragment extends BaseFragment {
    private BaseRecyclerView mRecycler_view;
    private PullToRefreshView mPull_view;

    private DoctorInfoData mListResponse;
    private MyAttentionDoctorAdapter mDoctorAdapter;
    private DoctorDetailInfo mTempDoctorItem;
    private AttentionManager mAttentionManager;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_attention_doctor, null, false);
    }

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
    protected void initViews() {
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.lv_mycollection);
        mPull_view = (PullToRefreshView) findViewById(R.id.rv_mycollection);

        mRecycler_view.setLayoutManager(new LinearLayoutManager(mBaseActivity));// 布局管理器。

        mPull_view.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);
            }
        });
        mPull_view.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_RELOAD);
            }
        });
        mRecycler_view.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                //跳转到医生详情
                if (!mListResponse.isListEmpty()) {
                    DoctorDetailInfo doctorInfo = mDoctorAdapter.getItem(position);
                    RegistrationDoctorDetailActivity.startDoctorDetail(mBaseActivity,doctorInfo.hosOrgCode,doctorInfo.hosDoctCode,doctorInfo.hosDeptCode);
                }

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mListResponse = new DoctorInfoData();
        mAttentionManager = new AttentionManager();
        loadData(Constant.TYPE_INIT);
    }

    private void loadData(final int type) {

        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mListResponse.more_params;
        }
        mAttentionManager.getAttentionDoctorList(params, new FinalResponseCallback<DoctorInfoData>(this, type) {
            @Override
            public void onSuccess(DoctorInfoData doctorInfoData) {
                super.onSuccess(doctorInfoData);
                int count = mListResponse.refresh(type, doctorInfoData);
                if (mListResponse.isListEmpty()) {
                    setIsEmpty(true);
                } else {
                    if (mDoctorAdapter == null) {
                        mDoctorAdapter = new MyAttentionDoctorAdapter(mBaseActivity, mListResponse.getList());
                        mRecycler_view.setAdapter(mDoctorAdapter);
                    } else {
                        mDoctorAdapter.refreshList(mListResponse.getList(), count);
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
                mPull_view.setLoadMoreEnable(mListResponse.more);
            }
        });
    }

    public void onEvent(AttentDoctorUpdateEvent event) {

        if (event.isAttented) {
            if (mTempDoctorItem != null && mTempDoctorItem.id.equals(event.doctorId)) {
                mListResponse.getList().add(0, mTempDoctorItem);
            }
        } else {
            for (DoctorDetailInfo detailInfo : mListResponse.getList()) {
                if (detailInfo.id.equals(event.doctorId)) {
                    mTempDoctorItem = detailInfo;
                    break;
                }
            }

            if (mTempDoctorItem != null) {
                mListResponse.getList().remove(mTempDoctorItem);
            }
        }

        if (mListResponse.getList() != null && mListResponse.getList().isEmpty()) {
            UIUtil.showEmptyView((ViewGroup) getRootView(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData(Constant.TYPE_INIT);
                }
            });
        } else {
            UIUtil.hideAllNoticeView((ViewGroup) getRootView());
        }
        mDoctorAdapter.refreshList(mListResponse.getList());

    }
}
