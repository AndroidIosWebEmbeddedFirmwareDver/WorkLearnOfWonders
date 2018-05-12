package com.wonders.health.venus.open.doctor.module.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.DepartmentEntity;
import com.wonders.health.venus.open.doctor.logic.RegistrationManager;
import com.wonders.health.venus.open.doctor.module.registration.adapter.DepartmentAdapter;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunning
 * 2016-11-04
 * 选择科室
 */
public class DepartmentSelectActivity extends UnifyFinishActivity {
    public static final int FIRST_CLASS_DEPARTMENT = 1;
    public static final int SECOND_CLASS_DEPARTMENT = 2;

    private BaseRecyclerView mRcView_firstClass_depat;
    private BaseRecyclerView mRcView_secondClass_depat;
    private DepartmentAdapter mFirstClass_dept_adapter;
    private DepartmentAdapter mSecondClass_dept_adapter;
    private LinearLayout rootView;

    private List<DepartmentEntity> firstClass_depat_data;
    private List<DepartmentEntity> secondClass_depat_data;
    private String hospitalId,org_id;

    public static final String HOS_ORG_CODE = "hos_org_code";//医院代码
    public static final String HOS_ORG_ID = "hos_org_id";//医院代码
    public static final String HOS_DEPT_CODE = "hos_dept_code";//科室代码
    public static final String HOS_DEPT_NAME = "hos_dept_name";//科室代码

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_deptselect);
        rootView = (LinearLayout) findViewById(R.id.department_select_rootView);
        mRcView_firstClass_depat = (BaseRecyclerView) findViewById(R.id.lv_firstclass_dept);
        mRcView_secondClass_depat = (BaseRecyclerView) findViewById(R.id.lv_secondclass_dept);
        onClickListener();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadParams();
        mTitleBar.setTitle("选择科室");
        if (TextUtils.isEmpty(hospitalId)) {
            UIUtil.toastShort(this, "不存在该医院！");
            finish();
            return;
        }
//        if(!getIntent().getBooleanExtra(HospitalHomeActivity.FROM_HOSPITAL_HOME,false)) {
//            mTitleBar.addAction(new TitleBar.TextAction("医院微站") {
//                @Override
//                public void performAction(View view) {
//                    startActivity(new Intent(DepartmentSelectActivity.this, HospitalHomeActivity.class).putExtra("hospitalId", org_id));
//                }
//            });
//        }
        firstClass_depat_data = new ArrayList<>();
        secondClass_depat_data = new ArrayList<>();
        mFirstClass_dept_adapter = new DepartmentAdapter(this, firstClass_depat_data, FIRST_CLASS_DEPARTMENT);
        mSecondClass_dept_adapter = new DepartmentAdapter(this, secondClass_depat_data, SECOND_CLASS_DEPARTMENT);
        mFirstClass_dept_adapter.setSelectedPosition(0);
        mRcView_firstClass_depat.setAdapter(mFirstClass_dept_adapter);
        mRcView_secondClass_depat.setAdapter(mSecondClass_dept_adapter);
        loadFirstLevelData();
    }

    private void loadParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            hospitalId = bundle.getString(HOS_ORG_CODE);
            org_id = bundle.getString(HOS_ORG_ID);
        }
    }

    private void loadFirstLevelData() {
        RegistrationManager.getInstance().queryFirstDepartment(hospitalId, new FinalResponseCallback<DepartmentEntity>(this) {
            @Override
            public void onSuccess(List<DepartmentEntity> responses) {
                super.onSuccess(responses);
                if (mSuccess && responses != null) {
                    if (responses.size() == 0) {
                        UIUtil.showEmptyView(rootView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadFirstLevelData();
                            }
                        });
                    } else {
                        firstClass_depat_data.clear();
                        firstClass_depat_data.addAll(responses);
                        mSecondClass_dept_adapter.setSelectedPosition(0);
                        if (!firstClass_depat_data.isEmpty()) {
                            loadSecondLevelData(firstClass_depat_data.get(0).hosDeptCode);
                        }
                    }
                }
            }

            @Override
            public void onReload() {
                super.onReload();
                loadFirstLevelData();
            }
        });
    }

    private void loadSecondLevelData(String id) {
        UIUtil.showProgressBar(DepartmentSelectActivity.this);
        RegistrationManager.getInstance().querySecondDepartment(hospitalId, id, new ResponseCallback<DepartmentEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(DepartmentSelectActivity.this);
            }

            @Override
            public void onSuccess(List<DepartmentEntity> responses) {
                super.onSuccess(responses);
                secondClass_depat_data.clear();
                secondClass_depat_data.addAll(responses);
                mSecondClass_dept_adapter.notifyDataSetChanged();
            }


            @Override
            public void onFinish() {
                super.onFinish();
                UIUtil.hideProgressBar(DepartmentSelectActivity.this);
            }
        });
    }

    private void onClickListener() {
        mRcView_firstClass_depat.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                mFirstClass_dept_adapter.setSelectedPosition(position);
                mFirstClass_dept_adapter.notifyDataSetChanged();
                loadSecondLevelData(firstClass_depat_data.get(position).hosDeptCode);
            }
        });
        mRcView_secondClass_depat.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                mSecondClass_dept_adapter.setSelectedPosition(position);
                Intent intent = new Intent(DepartmentSelectActivity.this, RegistrationDoctorListActivity.class);
                intent.putExtra(HOS_ORG_CODE, secondClass_depat_data.get(position).hosOrgCode);
                intent.putExtra(HOS_DEPT_CODE, secondClass_depat_data.get(position).hosDeptCode);
                intent.putExtra(HOS_DEPT_NAME, secondClass_depat_data.get(position).deptName);
                startActivity(intent);
            }
        });
    }


    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
