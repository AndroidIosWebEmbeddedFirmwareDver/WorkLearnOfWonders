package com.wonders.health.venus.open.user.logic;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wonders.health.venus.open.user.BaseApp;

import de.greenrobot.event.EventBus;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2015/11/26 10:56
 */
public class LocationManager {
    private static LocationManager sInstance;
    private LocationClient mLocationClient = null;
    private BDLocation mBDLocation;
    private BDLocationListener mBDLocationListener;

    public static LocationManager getInstance() {
        if (sInstance == null) {
            synchronized (LocationManager.class) {
                if (sInstance == null) {
                    sInstance = new LocationManager();
                }
            }
        }
        return sInstance;
    }

    private LocationManager() {

    }

    private void initLocationClient() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(BaseApp.getApp());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(10000);// 设置发起定位请求的间隔时间为10000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
    }

    public BDLocation getLocation() {
//        if (mBDLocation == null) {
//            mBDLocation = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_BD_LOCATION, BDLocation.class);
//        }
        return mBDLocation;
    }

    /**
     * 开始定位
     */
    public void startLocation() {

        startLocation(null);
    }


    /**
     * 开始定位
     */
    public void startLocation(final CallBack callBack) {
        try {
            if (callBack != null) {
                callBack.onStart();
            }
            // 先取本地缓存的上次定位 信息
            mBDLocation = getLocation();
//            if (mBDLocation != null) {
//                EventBus.getDefault().post(mBDLocation);
//            }
            if (mLocationClient == null) {
                initLocationClient();
            }
            if (mBDLocationListener == null) {
                mBDLocationListener = new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        if (bdLocation != null && bdLocation.getLatitude() != 0 && bdLocation.getLongitude() != 0) {
                            mBDLocation = bdLocation;
                        }
                        if (callBack != null) {
                            callBack.onSuccess(bdLocation);
                        }
                        EventBus.getDefault().post(mBDLocation);
                    }
                };
            }
            mLocationClient.registerLocationListener(mBDLocationListener);// 注册监听
            if (!mLocationClient.isStarted()) {
                mLocationClient.start();
            }
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.requestLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError();
            }
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.unRegisterLocationListener(mBDLocationListener);// 取消监听
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
            mBDLocationListener=null;
        }
//        if (mBDLocation != null) {
//            PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_BD_LOCATION, BDLocation.class);
//            mBDLocation = null;
//        }
    }


    public interface CallBack {

        void onStart();

        void onSuccess(BDLocation bdLocation);

        void onError();
    }
}
