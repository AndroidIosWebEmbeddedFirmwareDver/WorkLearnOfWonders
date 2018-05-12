package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.util.UrlConst;
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

//    public void queryReferralServiceList(String uid, ResponseCallback callback){
//        SignRequest request = new SignRequest();
//        request.setPath("http://0b3c46bd.ngrok.io/data");
//        mHttpTools.get(request, callback);
//    }
    /**
     * 转诊记录列表
     */
    public void getReferral(HashMap<String, String> mMoreParams, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid","");
        if(mMoreParams != null){
            request.addQueryMapParameter(mMoreParams);
        }

        // mHttpTools.get(UrlConst.GET_SYSTEM_MSG, signRequest, callback);
        mHttpTools.post(UrlConst.HOSPITAL_EVALUATE, request, callback);
    }



}
