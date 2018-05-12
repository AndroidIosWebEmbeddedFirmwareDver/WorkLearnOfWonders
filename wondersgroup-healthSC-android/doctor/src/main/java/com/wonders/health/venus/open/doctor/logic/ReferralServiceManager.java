package com.wonders.health.venus.open.doctor.logic;

import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

/**
 * Created by wang on 2017/6/7.
 */

public class ReferralServiceManager {

    private HttpTools mHttpTools;

    private static ReferralServiceManager sInstance;

    public static ReferralServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (RegistrationManager.class) {
                if (sInstance == null) {
                    sInstance = new ReferralServiceManager();
                }
            }
        }
        return sInstance;
    }
    public ReferralServiceManager(){
        mHttpTools=new HttpTools();
    }

    public void queryReferralServiceList(String uid, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.setPath("http://0b3c46bd.ngrok.io/data");
        mHttpTools.get(request, callback);
    }


    /**
     * 就诊服务全部信息
     */
    public void getAll(HashMap<String, String> mMoreParams, ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", getUser().uid);
//        if (mMoreParams != null) {
//            request.addQueryMapParameter(mMoreParams);
//        }

        // mHttpTools.get(UrlConst.GET_SYSTEM_MSG, signRequest, callback);
        //  mHttpTools.post(UrlConst.HOSPITAL_EVALUATE, request, callback);
    }




    /**
     *  就诊服务----接入转诊
     */
    public void  getinsert(HashMap<String, String> mMoreParams, ResponseCallback callback) {
        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", getUser().uid);
//        if (mMoreParams != null) {
//            request.addQueryMapParameter(mMoreParams);
//        }

        // mHttpTools.get(UrlConst.GET_SYSTEM_MSG, signRequest, callback);
        //  mHttpTools.post(UrlConst.HOSPITAL_EVALUATE, request, callback);
    }


}
