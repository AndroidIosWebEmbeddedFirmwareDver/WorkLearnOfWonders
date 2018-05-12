package com.wonders.health.venus.open.doctor.module.registration;
/*
 * Created by sunning on 2016/11/8.
 * 科室名称-按日期预约
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DoctorListVO;
import com.wonders.health.venus.open.doctor.logic.RegistrationManager;
import com.wonders.health.venus.open.doctor.module.registration.adapter.AdapterDoctorListBySpecialist;
import com.wonders.health.venus.open.doctor.module.registration.response.DoctorListResponse;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DoctorListBySpecialistFragment extends BaseFragment {

    public static final String ORDER_INFO = "order_info";

    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecyclerView;

    private AdapterDoctorListBySpecialist mDataAdapter;
    private List<DoctorListVO> mDatas;
    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private String hosOrgCode, hosDeptCode;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_doctors_list, null);
    }

    @Override
    protected void initViews() {
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
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
        mDatas = new ArrayList<>();
        loadParams();
        if (TextUtils.isEmpty(hosOrgCode) && TextUtils.isEmpty(hosDeptCode)) {
            return;
        }
        loadData(Constant.TYPE_INIT);
    }

    private void loadParams() {
        Bundle bundle = mBaseActivity.getIntent().getExtras();
        if (bundle != null) {
            hosOrgCode = bundle.getString(DepartmentSelectActivity.HOS_ORG_CODE);
            hosDeptCode = bundle.getString(DepartmentSelectActivity.HOS_DEPT_CODE);
        }
    }

    private void loadData(final int type) {
        if (type == Constant.TYPE_RELOAD || type == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                mPullToRefreshView.setLoadMoreEnable(mIsMore);
                return;
            }
        }
        RegistrationManager.getInstance().queryDoctorList(hosOrgCode, hosDeptCode, mMoreParams, new FinalResponseCallback<DoctorListResponse>(this,type) {
            @Override
            public void onSuccess(DoctorListResponse t) {
                super.onSuccess(t);
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (type != Constant.TYPE_NEXT) {
                    mDatas.clear();
                }
                mDatas.addAll(t.getList());
                setIsEmpty(mDatas.isEmpty());
                bindView(type, t.getList().size());
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
            }
        });
    }
    private void bindView(int type, int itemCount) {
        if (mDataAdapter == null) {
            mDataAdapter = new AdapterDoctorListBySpecialist(mBaseActivity, mDatas);
            mRecyclerView.setAdapter(mDataAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mDataAdapter.refreshList(mDatas);
            } else {
                mDataAdapter.notifyItemRangeInserted(mDatas.size() - itemCount, itemCount);
            }
        }
        mPullToRefreshView.setLoadMoreEnable(mIsMore);
    }
}