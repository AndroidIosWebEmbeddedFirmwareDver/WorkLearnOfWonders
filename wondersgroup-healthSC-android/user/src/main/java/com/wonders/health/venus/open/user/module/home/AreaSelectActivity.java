package com.wonders.health.venus.open.user.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.logic.AreaManager;
import com.wonders.health.venus.open.user.logic.LocationManager;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：地区选择Activity
 * 创建人：hhw
 * 创建时间：2016/8/25 13:41
 */
public class AreaSelectActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_AREA = "extra_area";

    private BaseRecyclerView mRecycler_view;
    private LinearLayout mLl_location_select;
    private TextView mTv_location;

    private String district_name = AreaManager.AREA_NAME_CHENGDU;
    private List<AreaEntity> mArea_list = new ArrayList<>();
    private AreaManager mAreaManager;
    private boolean isLoading;
    private String latitude;
    private String longitude;

    @Override
    protected void initViews() {
        mTitleBar.setTitle("选择您的城市");

        setContentView(R.layout.activity_area_select);
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mLl_location_select = (LinearLayout) findViewById(R.id.ll_location);
        mTv_location = (TextView) findViewById(R.id.tv_location);

//        mTv_location.setOnClickListener(this);
        mLl_location_select.setOnClickListener(this);
        mRecycler_view.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
//                mAreaManager.updateRecentSelect(AreaSelectActivity.this, mArea_list.get(position));
                select(mArea_list.get(position).name, mArea_list.get(position).area_id);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAreaManager = AreaManager.getInstance();
        mArea_list = mAreaManager.getAreas();
        mRecycler_view.setAdapter(new AreaAdapter(this, mArea_list));
        startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocationManager.getInstance().stopLocation();

    }

    /**
     * 开始定位
     */
    private void startLocation() {
        LocationManager.getInstance().startLocation(new LocationManager.CallBack() {
            @Override
            public void onStart() {
                isLoading = true;
            }

            @Override
            public void onSuccess(BDLocation bdLocation) {
                isLoading = false;
                if (bdLocation != null) {
                    LocationManager.getInstance().stopLocation();
                    String province = bdLocation.getProvince();
                    if (TextUtils.isEmpty(province)) {
                        UIUtil.toastShort(AreaSelectActivity.this, "抱歉，定位失败，请手动选择地区");
                    } else {
                        mLl_location_select.setVisibility(View.VISIBLE);
                        latitude = String.valueOf(bdLocation.getLatitude());
                        longitude = String.valueOf(bdLocation.getLongitude());
                        if (province.contains("四川")) { //在四川省
                            district_name = bdLocation.getCity();
                            mTv_location.setText(district_name);
                        }
                    }
                } else {
                    UIUtil.toastShort(AreaSelectActivity.this, "抱歉，定位失败，请手动选择地区");
                }

            }

            @Override
            public void onError() {
                UIUtil.toastShort(AreaSelectActivity.this, "抱歉，定位失败，请手动选择地区");
                isLoading = false;
            }
        });
    }

    private void select(String area_name, String area_code) {
//        AreaManager.getInstance().setSpecArea(area_code);
        Intent intent = new Intent();
        AreaEntity area = new AreaEntity(area_code, area_name, latitude, longitude);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_AREA, area);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected PermissionType[] applyPermission() {
        return new PermissionType[]{PermissionType.ACCESS_FINE_LOCATION};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_location:
                if (isLoading) {
                    UIUtil.toastShort(AreaSelectActivity.this, "正在定位中，请稍后");
                    return;
                }
                if (!TextUtils.isEmpty(district_name)) {
                    String area_code = mAreaManager.getAreaCodeByName(district_name);
                    select(district_name, area_code);
                }
                break;
        }
    }
}
