package com.wonders.health.venus.open.doctor.module.mine;
/*
 * Created by sunning on 2017/6/6.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.event.DoctorItemListEntity;
import com.wonders.health.venus.open.doctor.logic.PersonManger;
import com.wonders.health.venus.open.doctor.module.mine.h5.DoctorTeamDetailActivity;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.adapter.CommonAdapter;
import com.wondersgroup.hs.healthcloud.common.adapter.base.ViewHolder;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MyTeamActivity extends BaseActivity {

    @BindView(R.id.pull_view)
    PullToRefreshView mRefreshView;

    @BindView(R.id.recycler_view)
    BaseRecyclerView mRecyclerView;

    private List<DoctorItemListEntity.DoctorItem> mDatas;

    private CommonAdapter mAdapter;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;


    @Override
    protected void initViews() {
        setContentView(com.wondersgroup.hs.healthcloud.base.R.layout.pull_refresh_layout);
        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent intent = new DoctorTeamDetailActivity.Builder(MyTeamActivity.this).setHasTitle(false).setUrl("http://10.1.64.194/healthSC-app-h5/familyDoctor/teamDetail").getIntent();
                intent.setClass(MyTeamActivity.this,DoctorTeamDetailActivity.class);
                MyTeamActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(new DoctorItemListEntity.DoctorItem());
        }
//        loadData(Constant.TYPE_INIT);
        initAdapter();
    }

    private void loadData(int type) {
        if (type == Constant.TYPE_RELOAD || type == Constant.TYPE_INIT) {
            mMoreParams = null;
        } else {
            if (!mIsMore) {
                mRefreshView.setLoadMoreEnable(mIsMore);
                return;
            }
        }
        PersonManger.getInstance().getTeamList(mMoreParams, new FinalResponseCallback<DoctorItemListEntity>(this) {
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

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new CommonAdapter<DoctorItemListEntity.DoctorItem>(this, R.layout.item_my_team, mDatas) {
                @Override
                protected void convert(ViewHolder holder, DoctorItemListEntity.DoctorItem s, int position) {
                    holder.setText(R.id.team_name, s.teamName);
                    holder.setText(R.id.team_address, s.teamAddress);
                    holder.setBackgroundRes(R.id.iv_item_team_img, R.mipmap.ic_doctor_default_man);
                }
            };
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}
