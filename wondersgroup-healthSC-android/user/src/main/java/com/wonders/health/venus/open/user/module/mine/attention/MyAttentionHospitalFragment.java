package com.wonders.health.venus.open.user.module.mine.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalDetailInfo;
import com.wonders.health.venus.open.user.entity.HospitalInfoData;
import com.wonders.health.venus.open.user.entity.event.AttentHospitalUpdateEvent;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
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
public class MyAttentionHospitalFragment extends BaseFragment {
    private BaseRecyclerView mBaseRecyclerView;
    private PullToRefreshView mPullToRefreshView;
    private HospitalInfoData mListResponse;
    private MyAttentionHospitalAdapter mHospitalAdapter;
    private HospitalDetailInfo mTempHospitalItem;
    private AttentionManager mAttentionManager;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_attention_hospital, null, false);
    }

    @Override
    protected void initViews() {
        mBaseRecyclerView = (BaseRecyclerView) findViewById(R.id.baseRecyclerView);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pullToRefreshView);

        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));// 布局管理器。

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

        mBaseRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (!mListResponse.isListEmpty()) {
                    HospitalDetailInfo hospitalInfo = mHospitalAdapter.getItem(position);
                    startActivity(new Intent(mBaseActivity, HospitalHomeActivity.class).putExtra("hospitalId",hospitalInfo.hospitalId));
                }
            }
        });
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
    protected void initData(Bundle savedInstanceState) {
        mListResponse = new HospitalInfoData();
        mAttentionManager = new AttentionManager();
        loadData(Constant.TYPE_INIT);
    }

    private void loadData(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mListResponse.more_params;
        }

        mAttentionManager.getAttentionHospitalList(params,
                new FinalResponseCallback<HospitalInfoData>(this, type) {

                    @Override
                    public void onSuccess(HospitalInfoData t) {
                        super.onSuccess(t);
                        int count = mListResponse.refresh(type, t);
                        if (mListResponse.isListEmpty()) {
                            setIsEmpty(true);
                        } else {
                            if (mHospitalAdapter == null) {
                                mHospitalAdapter = new MyAttentionHospitalAdapter(mBaseActivity, mListResponse.getList());
                                mBaseRecyclerView.setAdapter(mHospitalAdapter);
                            } else {
                                mHospitalAdapter.refreshList(mListResponse.getList(), count);
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
                            mPullToRefreshView.onFooterRefreshComplete();
                        } else {
                            mPullToRefreshView.onHeaderRefreshComplete();
                        }
                        mPullToRefreshView.setLoadMoreEnable(mListResponse.more);

                    }
                });
    }

    public void onEvent(AttentHospitalUpdateEvent event) {

        if (event.isAttented) {
            if (mTempHospitalItem != null && mTempHospitalItem.hospitalId.equals(event.hospitalId)) {
                mListResponse.getList().add(0, mTempHospitalItem);
            }
        } else {
            for (HospitalDetailInfo detailInfo : mListResponse.getList()) {
                if (detailInfo.hospitalId.equals(event.hospitalId)) {
                    mTempHospitalItem = detailInfo;
                    break;
                }
            }

            if (mTempHospitalItem != null) {
                mListResponse.getList().remove(mTempHospitalItem);
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
        mHospitalAdapter.refreshList(mListResponse.getList());

    }
}
