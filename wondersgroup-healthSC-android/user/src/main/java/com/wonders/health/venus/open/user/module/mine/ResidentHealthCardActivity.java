package com.wonders.health.venus.open.user.module.mine;
/*
 * Created by sunning on 2017/8/22.
 */

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;

import com.wonders.health.venus.open.user.logic.UserManager;

import com.wonders.health.venus.open.user.module.home.registration.HospitalListActivity;
import com.wonders.health.venus.open.user.util.Constant;

import com.wonders.health.venus.open.user.util.DialogUtils;

import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidentHealthCardActivity extends BaseActivity implements ResidentHealthCardAdapter.ViholderViewOnClickListener<UserCardsEntity> {


    public ResidentHealthCardAdapter residentHealthCardAdapter;
    private PullToRefreshView layout_resident_health_refresh_view;
    private BaseRecyclerView layout_resident_health_recycle_view;
    private List<UserCardsEntity> mCardTypeEntitys;

    @Override
    protected void initViews() {
        mTitleBar.setTitle("我的就诊卡");
        setContentView(R.layout.layout_resident_health_card);
        initHadcardsViews();
    }


    /**
     * 有卡
     */
    @SuppressLint("InflateParams")
    private void initHadcardsViews() {
        layout_resident_health_refresh_view = (PullToRefreshView) findViewById(R.id.layout_resident_health_refresh_view);
        if (layout_resident_health_refresh_view != null) {
            layout_resident_health_refresh_view.setLoadMoreEnable(false);
            layout_resident_health_refresh_view.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
                @Override
                public void onHeaderRefresh(PullToRefreshView view) {
                    loadData(Constant.TYPE_RELOAD);
                }
            });
        }

        layout_resident_health_recycle_view = (BaseRecyclerView) findViewById(R.id.layout_resident_health_recycle_view);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {

        loadData(Constant.TYPE_INIT);

    }

    /**
     * 下拉刷新数据
     */

    private void refreshData() {
        if (null == mCardTypeEntitys) {
            mCardTypeEntitys = new ArrayList<>();
        }
        mCardTypeEntitys.clear();
    }

    /**
     * 选出居民健康卡
     *
     * @param lists
     * @return
     */
    private UserCardsEntity generHeaderHealthCard(List<UserCardsEntity> lists) {
        UserCardsEntity headerHealthCard = null;
        if (null != lists && lists.size() > 0) {
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).card_type_code != null && lists.get(i).card_type_code.equals(CardTypesEntity.FOR_HEALTH_CARD)) {
                    headerHealthCard = lists.get(i);
                    headerHealthCard.index = i;
                    return headerHealthCard;
                }
            }
        }
        headerHealthCard = new UserCardsEntity();
        headerHealthCard.index = -1;
        return headerHealthCard;
    }

    /**
     * 处理数据刷新界面
     *
     * @param lists
     */
    private void refreshLoadData(List<UserCardsEntity> lists) {
        if (null != lists) {
            try {
                UserCardsEntity headerHealthCard = generHeaderHealthCard(lists);

                Log.e("aaaaaaaa", "" + headerHealthCard.index);
                //处理逻辑
                List<UserCardsEntity> listNew = new ArrayList<>(lists);
                if (headerHealthCard.index >= 0 && listNew.size() > headerHealthCard.index) {
                    listNew.remove(headerHealthCard.index);
                }
                headerHealthCard.showAddHospitalCardSize = listNew.size();
                headerHealthCard.showAddHospitalCard = headerHealthCard.showAddHospitalCardSize < 5;
                headerHealthCard.showNoticeHospitalCard = listNew.size() < 1;
                headerHealthCard.showAddHealthCard = headerHealthCard.index < 0;


                Log.e("aaaaaaaa", headerHealthCard.toString());

                mCardTypeEntitys.add(headerHealthCard);
                if (listNew.size() > 0)
                    mCardTypeEntitys.addAll(listNew);
            } catch (Exception e) {
                Log.e("aaaaaaaa", e.getMessage());
            }
            if (null == residentHealthCardAdapter) {
                residentHealthCardAdapter = new ResidentHealthCardAdapter(ResidentHealthCardActivity.this, mCardTypeEntitys, UserManager.getInstance().getUser(), this);
            } else {
                residentHealthCardAdapter.notifyDataSetChanged();
            }
            if (null != layout_resident_health_recycle_view) {
                layout_resident_health_refresh_view.setVisibility(View.VISIBLE);
                layout_resident_health_recycle_view.setAdapter(residentHealthCardAdapter);
            }
        }

    }

    /**
     * 从服务端取数据
     */
    private void loadData(final int refreshType) {
        refreshData();
        UserManager.getInstance().getCards(new ResponseCallback<UserCardsEntity>() {

            @Override
            public boolean isShowNotice() {
                return false;
            }

            @Override
            public void onSuccess(UserCardsEntity t) {
                super.onSuccess(t);
            }

            @Override
            public void onSuccess(List<UserCardsEntity> list) {
                super.onSuccess(list);
                if (null != list) {
                    refreshLoadData(list);
                }
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
                DialogUtils.showNormalMessage(ResidentHealthCardActivity.this, null != error && null != error.getMessage() ? error.getMessage() : "");

            }

            @Override
            public void onFinish() {
                super.onFinish();
                layout_resident_health_refresh_view.onHeaderRefreshComplete();
            }
        });
    }

    private void deleteACard(UserCardsEntity data) {
        if (null != data) {
            UserManager.getInstance().deleteACard(
                    data.id,
                    data.mediacl_card_no,
                    data.hospital_code,
                    data.card_type_code,
                    new ResponseCallback<UserCardsEntity>() {

                        @Override
                        public boolean isShowNotice() {
                            return true;
                        }

                        @Override
                        public void onSuccess(UserCardsEntity t) {
                            super.onSuccess(t);
                        }

                        @Override
                        public void onSuccess(List<UserCardsEntity> list) {
                            super.onSuccess(list);
                        }

                        @Override
                        public void onFailure(Exception error) {
                            super.onFailure(error);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            loadData(Constant.TYPE_RELOAD);
                        }
                    });
        }
    }


    @Override
    public void onClick(View var1, ResidentHealthCardAdapter.ViewHolder holder, final int position, UserCardsEntity data) {
        if (var1 != null) {
            Intent intent = null;
            switch (var1.getId()) {
                case R.id.item_resident_health_card_activity_ly_add_hospital_card://添加院内就诊卡
                    intent = new Intent(ResidentHealthCardActivity.this, AddCardTypeActivity.class);
                    startActivityForResult(intent, 1001);
                    break;
                case R.id.item_resident_health_card_activity_ly_add_health_card://无卡添加居民健康卡
                    intent = new Intent(ResidentHealthCardActivity.this, BindingResidentCard.class);
                    startActivityForResult(intent, 1002);
                    break;
                case R.id.item_resident_health_card_activity_ly_show_health_card://居民健康卡-挂号
                    intent = new Intent(ResidentHealthCardActivity.this, HospitalListActivity.class);
                    startActivityForResult(intent, 1003);
                    break;
                case R.id.item_resident_health_card_activity_btn_delete://院内就诊卡-解绑
                    Log.e("aaaaaaaa", "btn_delete->:" + position);
                    DialogUtils.showNormalChoiceMsessage(this, null, "确定要解除绑定吗？（解除定后不能再使用该卡在线挂号）"
                            , null, null, null,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (null != mCardTypeEntitys && mCardTypeEntitys.size() > 0 && mCardTypeEntitys.size() > position)
                                        deleteACard(mCardTypeEntitys.get(position));
                                }
                            }
                    );
                    break;
                case R.id.item_resident_health_card_activity_btn_gh://院内就诊卡-挂号
                    Log.e("aaaaaaaa", "btn_gh->:" + position);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (layout_resident_health_refresh_view != null) {
            loadData(Constant.TYPE_RELOAD);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
