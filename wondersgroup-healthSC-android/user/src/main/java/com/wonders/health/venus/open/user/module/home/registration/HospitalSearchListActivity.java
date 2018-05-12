package com.wonders.health.venus.open.user.module.home.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.module.home.registration.adapter.HospitalListAdapter;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.HashMap;

/**
 * 类描述：医院查询Activity
 * 创建人：hhw
 * 创建时间：2015/7/13 9:55
 * <p/>
 * Sunning
 * 2016/01/04
 */
public class HospitalSearchListActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_SEARCH_KEY = "search_key";

    private ImageView mIv_back;
    private TextView mTv_back;
    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleViewHospitals;
    private HospitalListAdapter mAdapter;

    private String mSearchKey;
    private HospitalInfo mHospitalInfo;

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hospital_search_list);

        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mTv_back = (TextView) findViewById(R.id.tv_back);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecycleViewHospitals = (BaseRecyclerView) findViewById(R.id.recycler_view_hospital);
        mRecycleViewHospitals.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                HospitalInfo.Hospital hospital = mHospitalInfo.getList().get(position);
                Intent intent = new Intent(HospitalSearchListActivity.this, DepartmentSelectActivity.class);
                intent.putExtra(DepartmentSelectActivity.HOS_ORG_CODE, hospital.hospitalId);
//                intent.putExtra(DepartmentSelectActivity.EXTRA_HOSPITAL_NAME, hospital.name);
                startActivity(intent);
            }
        });

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

        mIv_back.setOnClickListener(this);
        mTv_back.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSearchKey = getIntent().getStringExtra(EXTRA_SEARCH_KEY);
        mHospitalInfo = new HospitalInfo();
        if (TextUtils.isEmpty(mSearchKey)) {
            finish();
            return;
        }
        mTv_back.setText(mSearchKey);
        loadData(Constant.TYPE_INIT);
    }

    //获取医院列表
    private void loadData(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mHospitalInfo.more_params;
        }

//        RegistrationManager.getInstance().getHospitalList(mSearchKey, params, new FinalResponseCallback<HospitalInfo>(mPullToRefreshView, type) {
//            @Override
//            public void onSuccess(HospitalInfo t) {
//                super.onSuccess(t);
//                int size = mHospitalInfo.refresh(type, t);
//                // 设置页面有没有数据，没有数据会自动显示暂无数据界面
//                if (mHospitalInfo.isListEmpty()) {
//                    setIsEmpty(true);
//                } else {
//                    bindView(size);
//                }
//            }
//
//            // 设置加载失败后点击的回调事件
//            @Override
//            public void onReload() {
//                super.onReload();
//                loadData(Constant.TYPE_INIT);
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                if (type == Constant.TYPE_NEXT) {
//                    mPullToRefreshView.onFooterRefreshComplete();
//                } else {
//                    mPullToRefreshView.onHeaderRefreshComplete();
//                }
//            }
//        });
    }

    // 数据加载完成，绑定到UI
    private void bindView(int itemCount) {
        if (mAdapter == null) {
            mAdapter = new HospitalListAdapter(this, mHospitalInfo.getList());
            mRecycleViewHospitals.setAdapter(mAdapter);
        } else {
            mAdapter.refreshList(mHospitalInfo.getList(), itemCount);
        }

        // 如果没有下一页，关掉上拉加载更多
        mPullToRefreshView.setLoadMoreEnable(mHospitalInfo.more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

}




