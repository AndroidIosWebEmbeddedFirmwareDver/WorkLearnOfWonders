package com.wonders.health.venus.open.doctor.module.registration;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.RegisterHeadEntity;
import com.wonders.health.venus.open.doctor.entity.ScheduleInfo;
import com.wonders.health.venus.open.doctor.entity.ScheduleItem;
import com.wonders.health.venus.open.doctor.logic.RegistrationManager;
import com.wonders.health.venus.open.doctor.module.registration.adapter.ScheduleGridAdapter;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.DateUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.DividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：11/9/16 10:02 AM
 */
public class DoctorScheduleActivity extends UnifyFinishActivity implements View.OnClickListener {
    public final static String EXTRA_HOSPITAL_CODE = "hospitalCode";
    public final static String EXTRA_DEPT_CODE = "hosDeptCode";
    public final static String EXTRA_DOCTOR_CODE = "hosDoctCode";
    public final static String EXTRA_SCHEDULE_INFO = "schedule_info";
    private final static int SIZE = 7;

    private String mHospitalCode;
    private String mDeptCode;
    private String mDoctorCode;
    private BaseRecyclerView mRecycler_view;
    private ScheduleGridAdapter mScheduleGridAdapter;
    private ScheduleInfo mScheduleInfo;
    private List<ScheduleItem> mList = new ArrayList<>();
    private ScheduleItem[] mDateArray = new ScheduleItem[SIZE];
    private ScheduleItem[] mAMArray = new ScheduleItem[SIZE];
    private ScheduleItem[] mPMArray = new ScheduleItem[SIZE];

    private TextView mTv_hospital_name;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_doctor_schedule);
//        ((LinearLayout)findViewById(R.id.ll_root)).addView(CommonViews.getRuleView(this));
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) ;
        mRecycler_view.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
        mTv_hospital_name = (TextView) findViewById(R.id.tv_hospital_name);

    }

    private void addHeadView() {
        LinearLayout rootView = (LinearLayout) findViewById(R.id.doctor_schedule_hospital_head);
        if(rootView != null)
            rootView.addView(new RegistrationDoctorHead().getHeadView(this, new RegisterHeadEntity(mScheduleInfo.doctorInfo)), 0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHospitalCode = getIntent().getStringExtra(EXTRA_HOSPITAL_CODE);
        mDeptCode = getIntent().getStringExtra(EXTRA_DEPT_CODE);
        mDoctorCode = getIntent().getStringExtra(EXTRA_DOCTOR_CODE);
        mScheduleInfo = (ScheduleInfo) getIntent().getSerializableExtra(EXTRA_SCHEDULE_INFO);
        if (mScheduleInfo != null) {
            bindViews();
        } else {
            loadData(Constant.TYPE_INIT);
        }
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }

    private void loadData(final int type) {
        RegistrationManager.getInstance().getScheduleInfo(mHospitalCode, mDeptCode, mDoctorCode,new FinalResponseCallback<ScheduleInfo>(this,type){
            @Override
            public void onSuccess(ScheduleInfo t) {
                super.onSuccess(t);
                if (t != null) {
                    mScheduleInfo = t;
                    bindViews();
                } else {
                    finish();
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
            }

            @Override
            public boolean isShowNotice() {
                return true;
            }
        });
    }

    private void bindViews() {
        addHeadView();
        mTv_hospital_name.setText(mScheduleInfo.doctorInfo.deptName + " - " +mScheduleInfo.doctorInfo.hosName);
        convertData();
        if (mScheduleGridAdapter == null) {
            mScheduleGridAdapter = new ScheduleGridAdapter(DoctorScheduleActivity.this, mScheduleInfo.doctorInfo, mList);
            mRecycler_view.setAdapter(mScheduleGridAdapter);
        } else {
            mScheduleGridAdapter.refreshList(mList, mList.size());
        }
    }


    private void convertData() {
        List<ScheduleInfo.ScheduleEntity> schedules = mScheduleInfo.schedule;
        for (ScheduleInfo.ScheduleEntity t:schedules) {
            int index = DateUtil.getInterDay(mScheduleInfo.systemTime, t.scheduleDate) - 1;
            if (index < SIZE && index > -1) {
                mDateArray[index] = new ScheduleItem();
                mDateArray[index].title = t.weekDay;
                mDateArray[index].desc = t.scheduleDate.substring(5);
                mDateArray[index].status = ScheduleItem.STATUS_DISABLED;
                if (ScheduleInfo.TIME_AM.equals(t.timeRange)) {
                    mAMArray[index] = new ScheduleItem();
                    mAMArray[index].title = t.visitLevel;
                    mAMArray[index].desc = t.visitCost;
                    mAMArray[index].schedule = t;
                } else if (ScheduleInfo.TIME_PM.equals(t.timeRange)) {
                    mPMArray[index] = new ScheduleItem();
                    mPMArray[index].title = t.visitLevel;
                    mPMArray[index].desc = t.visitCost;
                    mPMArray[index].schedule = t;
                }
            }
        }
        for (int i=0; i<SIZE; i++) {
            if (mDateArray[i] == null) {
                Calendar cal = DateUtil.getInterLaterDay(mScheduleInfo.systemTime, i + 1);
                mDateArray[i] = new ScheduleItem();
                mDateArray[i].title = DateUtil.getWeekOfCalendar(cal);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                mDateArray[i].desc = dateFormat.format(cal.getTime()).substring(5);
                mDateArray[i].status = ScheduleItem.STATUS_DISABLED;
            }
            mList.add(mDateArray[i]);
            mList.add(mAMArray[i]);
            mList.add(mPMArray[i]);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    public static List<ScheduleItem> getNextSevenDays(String systemTime) {
        List<ScheduleItem> list = new ArrayList<>();
        ScheduleItem item;
        for (int i=0; i<SIZE; i++) {
            item = new ScheduleItem();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
            Calendar cal = DateUtil.getInterLaterDay(systemTime, i + 1);
            item.title = DateUtil.getWeekOfCalendar(cal);
            item.desc = dateFormat.format(cal.getTime()).substring(5);
            list.add(item);
        }
        return list;
    }


    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
