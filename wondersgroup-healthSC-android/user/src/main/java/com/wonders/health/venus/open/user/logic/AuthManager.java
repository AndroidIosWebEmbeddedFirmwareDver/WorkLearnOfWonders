package com.wonders.health.venus.open.user.logic;


import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

/**
 * Created by wang on 2016/8/15 18:51.
 * Class Name :AuthManager
 */
public class AuthManager {

    private HttpTools mHttpTools;

    public AuthManager() {
        mHttpTools = new HttpTools();
    }

    public void getAuthStatus(ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        request.setPath(UrlConst.AUTH_QUERY);
        mHttpTools.get(request, callback);
    }
}
