package com.wonders.health.venus.open.doctor.logic;
/*
 * Created by sunning on 2017/6/16.
 */

import com.wonders.health.venus.open.doctor.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

public class HomeManager {
    private static HomeManager sInstance;

    private HttpTools mHttpTools;

    public static HomeManager getInstance() {
        if (sInstance == null) {
            synchronized (HomeManager.class) {
                if (sInstance == null) {
                    sInstance = new HomeManager();
                }
            }
        }
        return sInstance;
    }

    private HomeManager() {
        mHttpTools = new HttpTools();
    }


    public void prompt(ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("orgCode", UserManager.getInstance().getUser().orgCode);
        mHttpTools.get(UrlConst.PROMPT, request, callback);
    }

}
