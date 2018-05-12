package com.wonders.health.venus.open.user.module.home.extractreport;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.entity.ExtractReportEntity;
import com.wonders.health.venus.open.user.entity.ExtractReportResponse;
import com.wonders.health.venus.open.user.logic.ExtractManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractReportFragment extends BaseFragment implements BaseRecyclerView.OnItemClickListener {

    private PullToRefreshView mPullView;
    private BaseRecyclerView recyclerView;
    private ExtractManager mExtractManager;
    private ExtractReportAdapter adapter;
    private ExtractReportResponse mReportResponse;

    private String day;
    private ExtractHospitalEntity mHospitalEntity;
    private int mExtractType;


    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extract_report, null);
    }

    @Override
    protected void initViews() {
        recyclerView = (BaseRecyclerView) findViewById(R.id.recycle_report);
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);


    }

    private void loadData(final int type) {

        if (mReportResponse == null) {
            mReportResponse = new ExtractReportResponse();
        }
        if (type != Constant.TYPE_NEXT) {
            mReportResponse.more_params = null;
        }


        mExtractManager.getExtractReport(mReportResponse.more_params, mExtractType,
                mHospitalEntity.hospitalCode, day, new FinalResponseCallback<ExtractReportResponse>(mPullView, type) {
                    @Override
                    public void onSuccess(ExtractReportResponse response) {
                        super.onSuccess(response);
                        mReportResponse.refresh(type, response);

                        int itemCount = response.getList().size();
                        if (adapter == null) {
                            adapter = new ExtractReportAdapter(mBaseActivity, mReportResponse.getList());
                            recyclerView.setAdapter(adapter);
                        } else {
                            if (type != Constant.TYPE_NEXT) {
                                adapter.refreshList(mReportResponse.getList());
                            } else {
                                adapter.notifyItemRangeInserted(mReportResponse.getList().size() - itemCount, itemCount);
                            }
                        }
                    }

                    @Override
                    public void onReload() {
                        super.onReload();
                        loadData(type);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mPullView.setLoadMoreEnable(mReportResponse != null ? mReportResponse.more : false);
                        if (type == Constant.TYPE_NEXT) {
                            mPullView.onFooterRefreshComplete();
                        } else {
                            mPullView.onHeaderRefreshComplete();
                        }
                    }
                });
//
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ExtractReportActivity.KEY_DAY)) {
                day = bundle.getString(ExtractReportActivity.KEY_DAY);
                bundle.remove(ExtractReportActivity.KEY_DAY);
            }
            if (bundle.containsKey(ExtractReportActivity.KEY_HOSPITAL)) {
                mHospitalEntity = (ExtractHospitalEntity) bundle.getSerializable(ExtractReportActivity.KEY_HOSPITAL);
                bundle.remove(ExtractReportActivity.KEY_HOSPITAL);
            }
            if (bundle.containsKey("type")) {
                mExtractType = bundle.getInt("type", 0);
                bundle.remove("type");
            }
        }


        mExtractManager = new ExtractManager();

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

        loadData(Constant.TYPE_INIT);
        recyclerView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        ExtractReportEntity entity = mReportResponse.getList().get(position);
        if (entity != null) {
            SchemeUtil.startActivity(mBaseActivity, entity.view_url);

//            if(0==mExtractType){//检验报告详情H5
//                SchemeUtil.startActivity(mBaseActivity,
//                        BuildConfig.H5_IP + "/healthSC-app-h5/extractReport/checkupInfo?id=" + entity.id);
//            }else{//检查报告详情H5
//                SchemeUtil.startActivity(mBaseActivity,
//                        BuildConfig.H5_IP + "/healthSC-app-h5/extractReport/inspectInfo?id=" + entity.id);
//            }

        }
    }
}
