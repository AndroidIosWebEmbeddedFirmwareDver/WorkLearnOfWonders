package com.wonders.health.venus.open.doctor.logic;
/*
 * Created by sunning on 2017/6/14.
 */

import com.wonders.health.venus.open.doctor.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

public class PersonManger {

    private static PersonManger sInstance;

    private HttpTools mHttpTools;

    public static PersonManger getInstance() {
        if (sInstance == null) {
            synchronized (PersonManger.class) {
                if (sInstance == null) {
                    sInstance = new PersonManger();
                }
            }
        }
        return sInstance;
    }

    private PersonManger() {
        mHttpTools = new HttpTools();
    }

    public void getTeamList(HashMap<String, String> moreParams, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        request.addQueryMapParameter(moreParams);
        request.setPath(UrlConst.TEAM_LIST);
        mHttpTools.get(request, callback);
    }

    public void myCount(ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("orgCode", UserManager.getInstance().getUser().orgCode);
        request.addQueryStringParameter("doctorIdCard", UserManager.getInstance().getUser().idcard);
        mHttpTools.get(UrlConst.MY_COUNT, request, callback);
    }

    public void feedback(String content, String mobile, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("content", content);
        request.addQueryStringParameter("mobile", mobile);
        mHttpTools.get(UrlConst.FEEDBACK, request, callback);
    }
}
