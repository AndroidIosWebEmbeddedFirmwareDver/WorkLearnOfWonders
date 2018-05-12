package com.wonders.health.venus.open.user.module.home.elecprescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.BuildConfig;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.entity.PrescriptionItemEntity;
import com.wonders.health.venus.open.user.entity.PrescriptionResponse;
import com.wonders.health.venus.open.user.logic.PrescriptionManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * 电子处方列表
 * Created by wang on 2016/11/8.
 */

public class PrescriptionItemActivity extends BaseActivity {

    public static final String KEY_DAY = "day";
    public static final String KEY_HOSPITAL = "hospital";

    private PullToRefreshView pullToRefreshView;
    private BaseRecyclerView recyclerView;

    private PrescriptionItemAdapter adapter;
    List<PrescriptionItemEntity> list = new ArrayList<>();

    private PrescriptionManager manager;

    private String yljgdm;
    private String timeFlag;

    private ExtractHospitalEntity mHospitalEntity;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_prescription_item);
        mTitleBar.setTitle("电子处方");
        pullToRefreshView=(PullToRefreshView)findViewById(R.id.pullToRefreshView) ;
        recyclerView = (BaseRecyclerView) findViewById(R.id.recycle_prescription);
        recyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                PrescriptionItemEntity entity = list.get(position);
                if (entity != null&& !TextUtils.isEmpty(entity.url)) {
                    SchemeUtil.startActivity(PrescriptionItemActivity.this, entity.url);
                }

            }
        });

        pullToRefreshView.setLoadMoreEnable(false);
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData();
                pullToRefreshView.onHeaderRefreshComplete();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        manager = new PrescriptionManager();
        Intent intent = getIntent();
        mHospitalEntity = (ExtractHospitalEntity) intent.getSerializableExtra(KEY_HOSPITAL);
        timeFlag = intent.getStringExtra(KEY_DAY);
        yljgdm = mHospitalEntity != null ? mHospitalEntity.hospitalCode : "";
        loadData();

    }

    private void loadData() {
        manager.getPrescriptionList(yljgdm, timeFlag, new FinalResponseCallback<PrescriptionResponse>(this) {
            @Override
            public void onSuccess(PrescriptionResponse result) {
                super.onSuccess(result);
                if (result != null&&result.prescription!=null) {
                    list.clear();
                    list.addAll(result.prescription);
                    if (adapter == null) {
                        adapter = new PrescriptionItemAdapter(PrescriptionItemActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    setIsEmpty(true);
                }
            }

            @Override
            public void onReload() {
                super.onReload();
                loadData();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
