package com.wonders.health.venus.open.user.module.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.logic.LocationManager;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * 医院位置--地图
 * Created by songzhen on 2016/11/8.
 */
public class HospitalPointActivity extends BaseActivity {
    private static final float DEFAULT_MAP_ZOOM_LEVEL = 16f;//地图的默认缩放级别
    private static final String EXTRA_LATITUDE = "latitude";
    private static final String EXTRA_LONGITUDE = "longitude";
    private static final String EXTRA_ADDRESS = "address";
    private static final String EXTRA_FROM = "from";
    private static final String EXTRA_TITLE = "title";

    private MapView mMapView;
    private BaiduMap mBDMap;
    private LocationManager mLocationManager;
    private BitmapDescriptor mMeasurePointBD = BitmapDescriptorFactory
            .fromResource(R.mipmap.mapred);
    private String latitude;
    private String longitude;
    private String address;
    private String from;
    private String title;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hospital_point);
        mMapView = (MapView) findViewById(R.id.mapView);
        mBDMap = mMapView.getMap();
        UIUtil.showProgressBar(this,"地图加载中");
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            title = extras.getString(EXTRA_TITLE);
            latitude = extras.getString(EXTRA_LATITUDE);
            longitude = extras.getString(EXTRA_LONGITUDE);
            address = extras.getString(EXTRA_ADDRESS);
            from = extras.getString(EXTRA_FROM);
            if (!TextUtils.isEmpty(latitude) &&
                    !TextUtils.isEmpty(longitude) &&
                    StringUtil.isFloathString(latitude) &&
                    StringUtil.isFloathString(longitude) &&
                    Float.valueOf(latitude) != 0 &&
                    Float.valueOf(longitude) != 0) {
                addMeasurePointMarker(latitude, longitude, address == null ? "" : address);
            }
        }
        addListener();
    }

    private void addListener() {
        //地图加载完回调
        mBDMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                UIUtil.hideProgressBar(HospitalPointActivity.this);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (HospitalHomeActivity.class.toString().equals(from)) {
            mTitleBar.setTitle(title);
        } else {
            mTitleBar.setTitle(address);
        }
        if (!isOpenGPS(this)){
            UIUtil.toastShort(this,"请打开GPS定位");
        }
        mBDMap.setMyLocationEnabled(true);
        mLocationManager = LocationManager.getInstance();
        mLocationManager.startLocation();
    }

    /**
     * 判断GPS是否开启
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isOpenGPS(final Context context) {
        android.location.LocationManager locationManager
                = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    private void addMeasurePointMarker(String latitude, String longitude, String windowInfo){
        LatLng latLng = new LatLng(Float.valueOf(latitude),Float.valueOf(longitude));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(mMeasurePointBD).zIndex(9).draggable(false);
        mBDMap.addOverlay(markerOptions);

        LinearLayout llLayout = (LinearLayout) View.inflate(this, R.layout.ppw_free_device_location, null);
        TextView tv = (TextView) llLayout.findViewById(R.id.tv_device_address);
        tv.setText(windowInfo);


        final InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(llLayout)
                ,latLng
                ,(-UIUtil.dip2px(25))
                ,new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                mBDMap.hideInfoWindow();
            }
        });
        mBDMap.showInfoWindow(infoWindow);

        mBDMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mBDMap.showInfoWindow(infoWindow);
                return true;
            }
        });

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latLng,DEFAULT_MAP_ZOOM_LEVEL);
        mBDMap.animateMapStatus(msu);

    }


    public static void actionActivity(Activity activity, String name, String latitude, String longitude, String address, String from) {
        Intent intent = new Intent(activity, HospitalPointActivity.class);
        intent.putExtra(EXTRA_TITLE, name);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        intent.putExtra(EXTRA_ADDRESS, address);
        intent.putExtra(EXTRA_FROM, from);
        activity.startActivity(intent);
    }

    public void onEvent(BDLocation bdLocation) {
        if (bdLocation==null||bdLocation.getLatitude()==0||bdLocation.getLongitude()==0){
            UIUtil.toastShort(this,"定位失败");
        }else {
            mLocationManager.stopLocation();
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDirection()>0?bdLocation.getDirection():0)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBDMap.setMyLocationData(myLocationData);
            MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
            mBDMap.setMyLocationConfigeration(myLocationConfiguration);
            if (TextUtils.isEmpty(latitude) ||
                    TextUtils.isEmpty(longitude) ||
                    !StringUtil.isFloathString(latitude) ||
                    !StringUtil.isFloathString(longitude) ||
                    Float.valueOf(latitude) == 0 ||
                    Float.valueOf(longitude) == 0) {
                LatLng locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(locationLatLng, DEFAULT_MAP_ZOOM_LEVEL);
                mBDMap.animateMapStatus(msu);
            }
        }
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        if (mBDMap != null) {
            // 关闭定位图层
            mBDMap.setMyLocationEnabled(false);
        }
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
        if (mLocationManager != null) {
            mLocationManager.stopLocation();
        }
        mMeasurePointBD.recycle();
        super.onDestroy();
    }
}
