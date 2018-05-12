package com.wonders.health.venus.open.user.logic;
/*
 * Created by sunning on 2017/6/14.
 */

import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

public class SignFamilyDoctorManager {

    private static SignFamilyDoctorManager sInstance;

    private HttpTools mHttpTools;

    public static SignFamilyDoctorManager getInstance() {
        if (sInstance == null) {
            synchronized (SignFamilyDoctorManager.class) {
                if (sInstance == null) {
                    sInstance = new SignFamilyDoctorManager();
                }
            }
        }
        return sInstance;
    }

    private SignFamilyDoctorManager() {
        mHttpTools = new HttpTools();
    }

    public void getTeamList(HashMap<String, String> moreParams, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        request.addQueryMapParameter(moreParams);
        mHttpTools.get(UrlConst.TEAM_LIST, request, callback);
    }
}
