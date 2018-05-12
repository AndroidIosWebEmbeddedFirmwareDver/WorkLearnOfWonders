package com.wonders.health.venus.open.doctor.module.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.HospitalInfo;
import com.wonders.health.venus.open.doctor.logic.RegistrationManager;
import com.wonders.health.venus.open.doctor.module.registration.adapter.HospitalListAdapter;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Sunning
 * 2016/11/06
 * 医院列表
 */
public class HospitalListActivity extends UnifyFinishActivity {
    private static final int REQUEST_CODE = 0x1101;

    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleViewHospitals;

    private HospitalListAdapter mAdapter;
    private TitleBar.Action action;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private List<HospitalInfo.Hospital> list = new ArrayList<>();

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBar.setTitle("预约挂号");
        initLocalForTitleBar();
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hospitallist);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecycleViewHospitals = (BaseRecyclerView) findViewById(R.id.recycler_view_hospital);
        mRecycleViewHospitals.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                HospitalInfo.Hospital hospital = list.get(position);
                if (hospital != null) {
                    Intent intent = new Intent(HospitalListActivity.this, DepartmentSelectActivity.class);
                    intent.putExtra(DepartmentSelectActivity.HOS_ORG_CODE, hospital.hospitalCode);
                    intent.putExtra(DepartmentSelectActivity.HOS_ORG_ID, hospital.hospitalId+"");
                    startActivity(intent);
                } else {
//                    UIUtil.toastShort(HospitalListActivity.this, ErrorMessageSelector.getOne());
                }
            }
        });

        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_RELOAD);
            }
        });

        mPullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_NEXT);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getHospitalList(Constant.TYPE_INIT);
    }

    //获取医院列表
    private void getHospitalList(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mMoreParams;
        }
        RegistrationManager.getInstance().getHospitalList(params, new FinalResponseCallback<HospitalInfo>(this, type) {
            @Override
            public void onSuccess(HospitalInfo t) {
                super.onSuccess(t);
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (type != Constant.TYPE_NEXT) {
                    list.clear();
                }
                list.addAll(t.getList());
                setIsEmpty(list.isEmpty());
                bindView(type, t.getList().size());
            }

            @Override
            public void onReload() {
                super.onReload();
                getHospitalList(Constant.TYPE_INIT);
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
        if (mAdapter == null) {
            mAdapter = new HospitalListAdapter(this, list);
            mRecycleViewHospitals.setAdapter(mAdapter);
//            View headSearch = LayoutInflater.from(this).inflate(R.layout.hospital_title_search, null);
//            View search = headSearch.findViewById(R.id.hos_list_search);
//            search.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    startActivity(new Intent(HospitalListActivity.this, RegistrationHospitalSearchActivity.class));
////                }
//            });
//            mRecycleViewHospitals.addHeader(headSearch);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mAdapter.refreshList(list);
            } else {
                mAdapter.notifyItemRangeInserted(list.size() - itemCount, itemCount);
            }
        }
        mPullToRefreshView.setLoadMoreEnable(mIsMore);
    }

    private void initLocalForTitleBar() {
//        mTitleBar.removeAllActions();
//        if (action == null) {
//            action = new TitleBar.Action() {
//                @Override
//                public String getText() {
//                    return AreaManager.getInstance().getLocalPoint() == null ? AreaManager.AREA_NAME_CHENGDU : AreaManager.getInstance().getLocalPoint().name;
//                }
//
//                @Override
//                public int getDrawable() {
//                    return R.mipmap.ic_local_blue;
//                }
//
//                @Override
//                public void performAction(View view) {
//                    startActivityForResult(new Intent(HospitalListActivity.this, AreaSelectActivity.class), REQUEST_CODE);
//                }
//            };
//        }
//        TextView title = (TextView) mTitleBar.addAction(action);
//        title.setCompoundDrawablesWithIntrinsicBounds(action.getDrawable(), 0, 0, 0);
//        title.setCompoundDrawablePadding((int) getResources().getDimension(com.wondersgroup.hs.healthcloud.common.R.dimen.M));
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            AreaEntity entity = (AreaEntity) data.getSerializableExtra(AreaSelectActivity.EXTRA_AREA);
//            if (entity != null) {
//                AreaManager.getInstance().saveLocal(entity);
//            }
//            initLocalForTitleBar();
//            getHospitalList(Constant.TYPE_INIT);
//        }
    }
}