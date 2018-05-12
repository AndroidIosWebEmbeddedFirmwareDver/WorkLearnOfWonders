package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

/**
 * 电子处方manager
 * Created by wang on 2016/11/9.
 */

public class PrescriptionManager {
   private HttpTools mHttpTools;

    public PrescriptionManager() {
        mHttpTools=new HttpTools();
    }

    /**
     * 电子处方列表
     * @param yljgdm
     * @param time
     * @param callback
     */
    public void getPrescriptionList(String yljgdm,String time,ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("yljgdm",yljgdm);
        request.addQueryStringParameter("timeFlag",time);
        request.addQueryStringParameter("userId",UserManager.getInstance().getUser().uid);
        request.setPath(UrlConst.PRESCRIPTION_LIST);
        mHttpTools.get(request,callback);
    }
}
