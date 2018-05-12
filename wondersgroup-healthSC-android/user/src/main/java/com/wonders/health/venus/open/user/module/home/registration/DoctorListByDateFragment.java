package com.wonders.health.venus.open.user.module.home.registration;
/*
 * Created by sunning on 2016/11/8.
 * 医生列表-按日期预约
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wonders.health.venus.open.user.entity.RegisterDateItem;
import com.wonders.health.venus.open.user.entity.ScheduleItem;
import com.wonders.health.venus.open.user.logic.RegistrationManager;
import com.wonders.health.venus.open.user.module.home.registration.adapter.AdapterDoctorListByDate;
import com.wonders.health.venus.open.user.module.home.registration.adapter.RegisterByDateAdapter;
import com.wonders.health.venus.open.user.module.home.registration.response.DoctorListResponse;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DoctorListByDateFragment extends BaseFragment {

    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecyclerView, mDateRecyclerView;

    private AdapterDoctorListByDate mDataAdapter;
    private List<DoctorListVO> mDatas;
    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private String hosOrgCode, hosDeptCode;
    private String year;
    private List<ScheduleItem> nextSevenDays;
    private String currentDate;
    private RegisterByDateAdapter dateAdapter;
    private boolean isRequesting;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_doctors_by_date_list, null);
    }

    @Override
    protected void initViews() {
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mDateRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view_date);
        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                DoctorListVO doctorListVO = mDatas.get(position);
                if(doctorListVO.isFull != 0){
                    if (RegistrationDoctorListActivity.verification(mBaseActivity, null)) {
                        Intent intent = new Intent();
                        intent.setClass(mBaseActivity, OrderedInformationActivity.class);
                        doctorListVO.numSourceId = String.valueOf(doctorListVO.schedule.numSource);
                        intent.putExtra(DoctorListBySpecialistFragment.ORDER_INFO, doctorListVO);
                        mBaseActivity.startActivity(intent);
                    }
                }
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
    }

    private void loadData(final int type) {
        isRequesting = true;
        RegistrationManager.getInstance().getDoctorByDate(hosOrgCode, hosDeptCode, currentDate, mMoreParams, new FinalResponseCallback<DoctorListResponse>(mPullToRefreshView, type) {
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
                loadData(Constant.TYPE_RELOAD);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (type == Constant.TYPE_NEXT) {
                    mPullToRefreshView.onFooterRefreshComplete();
                } else {
                    mPullToRefreshView.onHeaderRefreshComplete();
                }
                isRequesting = false;
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
        RegistrationManager.getInstance().getSystemTime(new ResponseCallback<RegisterDateItem>() {
            @Override
            public void onSuccess(RegisterDateItem item) {
                super.onSuccess(item);
                currentDate = item.date;
                getNextWeek();
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                DoctorListByDateFragment.this.currentDate = dateFormat.format(new Date(System.currentTimeMillis()));
                getNextWeek();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (nextSevenDays != null) {
                    dateAdapter = new RegisterByDateAdapter(mBaseActivity, nextSevenDays);
                    mDateRecyclerView.setAdapter(dateAdapter);
                    loadData(Constant.TYPE_INIT);
                    dateAdapter.setSelectedPosition(0);
                }
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    private void getNextWeek() {
        if (currentDate == null)
            return;
        String[] dateSplit = currentDate.split("-");
        if (dateSplit.length == 3) {
            year = dateSplit[0];
        }
        nextSevenDays = DoctorScheduleActivity.getNextSevenDays(currentDate);
        if(nextSevenDays != null && nextSevenDays.size() > 0){
            currentDate = year + "-" + nextSevenDays.get(0).desc;
        }
        if (nextSevenDays != null && nextSevenDays.size() > 0) {
            mDateRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position, long id) {
                    if (dateAdapter != null) {
                        if (dateAdapter.getSelectedPosition() == position || isRequesting) {
                            return;
                        }
                        dateAdapter.setSelectedPosition(position);
                    }
                    TextView dateView = (TextView) view.findViewById(R.id.register_by_date);
                    currentDate = year + "-" + dateView.getText().toString();
                    dateAdapter.notifyDataSetChanged();
                    loadData(Constant.TYPE_INIT);
                }
            });
        }
    }

    private void loadParams() {
        Bundle bundle = mBaseActivity.getIntent().getExtras();
        if (bundle != null) {
            hosOrgCode = bundle.getString(DepartmentSelectActivity.HOS_ORG_CODE);
            hosDeptCode = bundle.getString(DepartmentSelectActivity.HOS_DEPT_CODE);
        }
    }

    private void bindView(int type, int itemCount) {
        if (mDataAdapter == null) {
            mDataAdapter = new AdapterDoctorListByDate(mBaseActivity, mDatas);
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
