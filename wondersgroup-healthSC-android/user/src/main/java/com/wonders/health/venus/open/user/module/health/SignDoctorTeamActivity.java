package com.wonders.health.venus.open.user.module.health;

/*
 * Created by sunning on 2017/6/6.
 */

import android.os.Bundle;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorItemListEntity;
import com.wonders.health.venus.open.user.logic.SignFamilyDoctorManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.adapter.CommonAdapter;
import com.wondersgroup.hs.healthcloud.common.adapter.base.ViewHolder;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SignDoctorTeamActivity extends BaseActivity {

    @BindView(R.id.pull_view)
    PullToRefreshView refreshView;

    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;

    private List<DoctorItemListEntity.DoctorItem> mDatas;

    private CommonAdapter adapter;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;


    @Override
    protected void initViews() {
        setContentView(R.layout.layout_sign_doctor_list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadData();
        initAdapter();
    }

    private void loadData() {
        mDatas = new ArrayList<>();
        getDoctorSignList(Constant.TYPE_INIT);
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonAdapter<DoctorItemListEntity.DoctorItem>(this, R.layout.item_sign_doctor_team, mDatas) {
                @Override
                protected void convert(ViewHolder holder, DoctorItemListEntity.DoctorItem s, int position) {
                }
            };
        }
        recyclerView.setAdapter(adapter);
    }

    public void getDoctorSignList(int type) {
        if (type == Constant.TYPE_RELOAD || type == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                refreshView.setLoadMoreEnable(mIsMore);
                return;
            }
        }
        SignFamilyDoctorManager.getInstance().getTeamList(mMoreParams, new FinalResponseCallback<DoctorItemListEntity>(this) {
            @Override
            public void onSuccess(DoctorItemListEntity doctorItemListEntity) {
                super.onSuccess(doctorItemListEntity);
                mIsMore = doctorItemListEntity.more;
                mMoreParams = doctorItemListEntity.more_params;
                mDatas.addAll(doctorItemListEntity.getList());
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
            }
        });
    }
}
