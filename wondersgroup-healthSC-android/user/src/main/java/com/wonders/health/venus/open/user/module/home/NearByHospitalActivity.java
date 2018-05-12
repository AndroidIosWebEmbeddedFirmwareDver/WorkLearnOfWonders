package com.wonders.health.venus.open.user.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.logic.AreaManager;
import com.wonders.health.venus.open.user.logic.LocationManager;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
import com.wonders.health.venus.open.user.module.home.registration.adapter.HospitalListAdapter;
import com.wonders.health.venus.open.user.module.home.search.NearbyHospitalSearchActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.NetworkUtil;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 附近就医页面
 * Created by songzhen on 2016/11/8.
 */
public class NearByHospitalActivity extends BaseActivity {
    private static final float DEFAULT_MAP_ZOOM_LEVEL = 16f;//地图的默认缩放级别
    private static final float DEFAULT_CITY_MAP_ZOOM_LEVEL = 13f;//地图的默认缩放级别
    private static final String EXTRA_LATITUDE = "latitude";
    private static final String EXTRA_LONGITUDE = "longitude";
//    private static final String EXTRA_ADDRESS = "address";
//    private static final String EXTRA_FROM = "from";
//    private static final String EXTRA_TITLE = "title";
    private static int REQUEST_CODE = 1001;

    private String latitude;
    private String longitude;
//    private String address;
//    private String from;
//    private String title;

    private MapView mMap;
    private BaiduMap mBDMap;
    private BaseRecyclerView mRecyclerView;
    private PullToRefreshView mPullView;
    private HospitalListAdapter mAdapter;
    private RelativeLayout rl_search;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private List<HospitalInfo.Hospital> mItems = new ArrayList<>();

    private LocationManager mLocationManager;
    private SearchManager mManager;
    private BitmapDescriptor mMeasurePointBD = BitmapDescriptorFactory
            .fromResource(R.mipmap.mapred);
//    private BitmapDescriptor mMeasurePointBDBlue = BitmapDescriptorFactory
//            .fromResource(R.mipmap.mapblue);
    private TextView rightText;
    private String cityCode = AreaManager.AREA_CODE_CHENGDU;
    private String areaName = AreaManager.AREA_NAME_CHENGDU;
//    private GeoCoder mSearch = GeoCoder.newInstance();

    private DistrictSearch mDistrictSearch = DistrictSearch.newInstance();
    private ImageView rightImage;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_nearby_hospital);
        mMap = (MapView) findViewById(R.id.map);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);
        mBDMap = mMap.getMap();

        mBDMap.getUiSettings().setZoomGesturesEnabled(true);  //设置是否允许缩放手势
        mBDMap.getUiSettings().setRotateGesturesEnabled(false); //设置是否允许旋转手势
        mBDMap.getUiSettings().setCompassEnabled(false);  //设置是否允许指南针
        mMap.showZoomControls(true);

        UIUtil.showProgressBar(this, "地图加载中");
        mTitleBar.setTitle("附近医院");
        rightImage = (ImageView) mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.icon_location) {
            @Override
            public void performAction(View view) {

            }
        });
        rightImage.setPadding(8, 0, 10, 2);
       rightText = (TextView) mTitleBar.addAction(new TitleBar.TextAction("成都市") {
            @Override
            public void performAction(View view) {
            }
       });
        rightText.setPadding(0, 0, 2, 0);
        addListener();
    }

    private boolean isActionUp = false;
    private void addListener() {
        mBDMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                UIUtil.hideProgressBar(NearByHospitalActivity.this);
            }
        });

        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                HospitalInfo.Hospital hospital = mItems.get(position);
                if (hospital != null) {
                    if (TextUtils.isEmpty(hospital.hospitalId+"")){
                        UIUtil.toastShort(NearByHospitalActivity.this,"hospitalId不能为空");
                    }else {
                        Intent intent = new Intent(NearByHospitalActivity.this, HospitalHomeActivity.class);
                        intent.putExtra("hospitalId", hospital.hospitalId+"");
                        startActivity(intent);
                    }
                }
            }
        });

        mPullView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_RELOAD);
            }
        });

        mPullView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_NEXT);
            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearByHospitalActivity.this, NearbyHospitalSearchActivity.class);
                startActivity(intent);
            }
        });

        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearByHospitalActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mBDMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

                LatLng mCenterLatLng = mapStatus.target;
                longitude = mCenterLatLng.longitude + "";
                latitude = mCenterLatLng.latitude + "";
                if (isActionUp){
                    getHospitalList(Constant.TYPE_INIT);
                    isActionUp = false;
                }
            }
        });


        mBDMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    isActionUp = true;
                }

            }
        });


        //在地图上显示
//        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            //正向地理编码指的是由地址信息转换为坐标点的过程
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult result) {
//                if (result == null || result.error != SearchResult.ERRORNO.KEY_ERROR) {
//                    //没有检索到结果
//                    UIUtil.toastShort(NearByHospitalActivity.this,"正向地理编码 null");
//                } else {
//                    //获得地理编码结果
//                    LatLng location = result.getLocation();
//                    UIUtil.toastShort(NearByHospitalActivity.this,"正向地理编码 longitude:"+location.longitude+" :latitude"+location.latitude);
//                    //在地图上标注出来
//                    locate(location);
//
//                }
//
//            }
//
//            //反向地理编码指的是由坐标点转换为地址信息的过程
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                    //没有找到检索结果
//                }
//                //获取反向地理编码结果
//            }
//        });

        mDistrictSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {
            @Override
            public void onGetDistrictResult(DistrictResult result) {
                LatLng location = result.getCenterPt();
                if (location!=null){
                    locate(location);
                    longitude = location.longitude + "";
                    latitude = location.latitude + "";
                }
                getHospitalList(Constant.TYPE_INIT);
            }
        });
    }

    //经纬度为中心点显示在地图上
    private void locate(LatLng location){
        MapStatus mapStatus = new MapStatus.Builder().target(location).zoom(DEFAULT_CITY_MAP_ZOOM_LEVEL).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBDMap.setMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK&&requestCode==REQUEST_CODE){
            AreaEntity info = (AreaEntity) data.getSerializableExtra(AreaSelectActivity.EXTRA_AREA);
            rightText.setText(formatLocationName(info.name));
            cityCode = info.area_id;
//            longitude = "";
//            latitude = "";
            //获得城市中心点的经纬度
            mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(info.name));
//            mSearch.geocode(new GeoCodeOption().city(info.name).address(""));
        }
    }

    private String formatLocationName(String name){
        if (!TextUtils.isEmpty(name)&&name.length()>3){
            return name.substring(0,3)+"...";
        }
        return name;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (!isOpenGPS(this)) {
//            UIUtil.toastShort(this, "请打开GPS定位");
//        }
        mManager = new SearchManager();
        mLocationManager = LocationManager.getInstance();
        prepare();
    }

    private void prepare(){
        if (NetworkUtil.isNetworkAvailable(this)){
            //开启定位图层
            mBDMap.setMyLocationEnabled(true);
            //开始定位
            mLocationManager.startLocation(new LocationManager.CallBack() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(BDLocation bdLocation) {
                    if (bdLocation==null||bdLocation.getLatitude()==0||bdLocation.getLongitude()==0){
                        UIUtil.toastShort(NearByHospitalActivity.this,"定位失败,请手动选择地址定位");
                    }else {
                        if (bdLocation.getProvince().contains("四川")) { //在四川省
                            String district_name = bdLocation.getCity();
                            areaName = district_name;
                            cityCode = AreaManager.getInstance().getAreaCodeByName(areaName);
                        }
                        rightText.setText(areaName);
                        mLocationManager.stopLocation();
                        MyLocationData myLocationData = new MyLocationData.Builder()
                                .accuracy(bdLocation.getRadius())
                                .direction(bdLocation.getDirection()>0?bdLocation.getDirection():0)
                                .latitude(bdLocation.getLatitude())
                                .longitude(bdLocation.getLongitude()).build();
                        mBDMap.setMyLocationData(myLocationData);
                        //false 是否允许显示方向信息
                        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,false,null);
                        mBDMap.setMyLocationConfigeration(myLocationConfiguration);
                        if (TextUtils.isEmpty(latitude) ||
                                TextUtils.isEmpty(longitude) ||
                                !StringUtil.isFloathString(latitude) ||
                                !StringUtil.isFloathString(longitude) ||
                                Float.valueOf(latitude) == 0 ||
                                Float.valueOf(longitude) == 0) {
                            latitude = bdLocation.getLatitude()+"";
                            longitude = bdLocation.getLongitude()+"";
                            LatLng locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(locationLatLng, DEFAULT_CITY_MAP_ZOOM_LEVEL);
                            mBDMap.animateMapStatus(msu);
                        }
                        getHospitalList(Constant.TYPE_INIT);
                    }
                }

                @Override
                public void onError() {

                }
            });

            rightImage.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.VISIBLE);
            mMap.setVisibility(View.VISIBLE);
            rl_search.setVisibility(View.VISIBLE);
        }else {
            rightImage.setVisibility(View.GONE);
            rightText.setVisibility(View.GONE);
            mMap.setVisibility(View.GONE);
            rl_search.setVisibility(View.GONE);
            getHospitalList(Constant.TYPE_INIT);
            UIUtil.hideProgressBar(NearByHospitalActivity.this);
        }
    }


    //获取医院列表
    private void getHospitalList(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mMoreParams;
        }
        mManager.nearbyHospital(params, longitude, latitude, "", cityCode, new FinalResponseCallback<HospitalInfo>(mPullView, type) {
            @Override
            public void onSuccess(HospitalInfo t) {
                super.onSuccess(t);
                mIsMore = t.more;
                mMoreParams = t.more_params;
                if (type != Constant.TYPE_NEXT) {
                    mItems.clear();
                }
                mItems.addAll(t.getList());
                setIsEmpty(mItems.isEmpty(), "您附近无就医资源", R.mipmap.nearby_nodata);
                addMeasurePointMarker(mItems);
                bindView(type, t.getList().size());
            }

            @Override
            public void onReload() {
                super.onReload();
                prepare();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (type == Constant.TYPE_NEXT) {
                    mPullView.onFooterRefreshComplete();
                } else {
                    mPullView.onHeaderRefreshComplete();
                }
            }
        });
    }


    private void bindView(int type, int itemCount) {
        if (mAdapter == null) {
            mAdapter = new HospitalListAdapter(this, mItems);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mAdapter.refreshList(mItems);
            } else {
                mAdapter.notifyItemRangeInserted(mItems.size() - itemCount, itemCount);
            }
        }
        mPullView.setLoadMoreEnable(mIsMore);
    }

    private void addMeasurePointMarker(List<HospitalInfo.Hospital> list){
        mBDMap.clear();
        for(HospitalInfo.Hospital hospital:list){
            if (!TextUtils.isEmpty(hospital.hospitalLatitude)&&!TextUtils.isEmpty(hospital.hospitalLongitude)){
                LatLng point = new LatLng(Double.parseDouble(hospital.hospitalLatitude),
                        Double.parseDouble(hospital.hospitalLongitude));
                MarkerOptions options = new MarkerOptions().position(point).icon(mMeasurePointBD);
                mBDMap.addOverlay(options);
            }
        }
    }

//    private void addMeasurePointMarker(String latitude, String longitude, String windowInfo){
//
//        LatLng latLng = new LatLng(Float.valueOf(latitude),Float.valueOf(longitude));
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(mMeasurePointBD).zIndex(9).draggable(false);
//        mBDMap.addOverlay(markerOptions);
//
//        LinearLayout llLayout = (LinearLayout) View.inflate(this, R.layout.ppw_free_device_location, null);
//        TextView tv = (TextView) llLayout.findViewById(R.id.tv_device_address);
//        tv.setText(windowInfo);
//
//
//        final InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(llLayout)
//                ,latLng
//                ,(-UIUtil.dip2px(25))
//                ,new InfoWindow.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick() {
//                mBDMap.hideInfoWindow();
//            }
//        });
//        mBDMap.showInfoWindow(infoWindow);
//
//        mBDMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
////                marker.setIcon(mMeasurePointBDBlue);
//                mBDMap.showInfoWindow(infoWindow);
//                return true;
//            }
//        });
//
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latLng,DEFAULT_CITY_MAP_ZOOM_LEVEL);
//        mBDMap.animateMapStatus(msu);
//
//    }

//    public void onEvent(BDLocation bdLocation) {
//        if (bdLocation==null||bdLocation.getLatitude()==0||bdLocation.getLongitude()==0){
//            UIUtil.toastShort(this,"定位失败,请手动选择地址定位");
//        }else {
//            if (bdLocation.getProvince().contains("四川")) { //在四川省
//                String district_name = bdLocation.getCity();
//                areaName = district_name;
//                cityCode = AreaManager.getInstance().getAreaCodeByName(areaName);
//            }
////            UIUtil.toastShort(this,"定位成功，位置："+bdLocation.getCity());
//            rightText.setText(areaName);
//            mLocationManager.stopLocation();
//            MyLocationData myLocationData = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    .direction(bdLocation.getDirection()>0?bdLocation.getDirection():0)
//                    .latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude()).build();
//            mBDMap.setMyLocationData(myLocationData);
//            //false 是否允许显示方向信息
//            MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,false,null);
//            mBDMap.setMyLocationConfigeration(myLocationConfiguration);
//            if (TextUtils.isEmpty(latitude) ||
//                    TextUtils.isEmpty(longitude) ||
//                    !StringUtil.isFloathString(latitude) ||
//                    !StringUtil.isFloathString(longitude) ||
//                    Float.valueOf(latitude) == 0 ||
//                    Float.valueOf(longitude) == 0) {
//                latitude = bdLocation.getLatitude()+"";
//                longitude = bdLocation.getLongitude()+"";
//                LatLng locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(locationLatLng, DEFAULT_CITY_MAP_ZOOM_LEVEL);
//                mBDMap.animateMapStatus(msu);
//            }
//            getHospitalList(Constant.TYPE_INIT);
//
//        }
//    }


    /**
     * 判断GPS是否开启
     * @param context
     * @return true 表示开启
     */
    public static boolean isOpenGPS(final Context context) {
        android.location.LocationManager locationManager
                = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    @Override
    protected PermissionType[] applyPermission() {
        return new PermissionType[]{PermissionType.ACCESS_FINE_LOCATION};
    }

    @Override
    protected void onResume() {
        if(mMap!=null){
            mMap.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(mMap!=null){
            mMap.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mBDMap!=null){
            //关闭定位图层
            mBDMap.setMyLocationEnabled(false);
        }
        if (mMap!=null){
            mMap.onDestroy();
            mMap = null;
        }
        if (mLocationManager!=null){
            mLocationManager.stopLocation();
        }
        mMeasurePointBD.recycle();
        //释放地理编码检索实例
        super.onDestroy();
    }

}
