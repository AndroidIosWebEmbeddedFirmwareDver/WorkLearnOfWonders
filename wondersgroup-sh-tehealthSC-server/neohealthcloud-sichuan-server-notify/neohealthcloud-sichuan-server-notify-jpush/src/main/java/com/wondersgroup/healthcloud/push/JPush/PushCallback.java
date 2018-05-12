package com.wondersgroup.healthcloud.push.JPush;

import com.squareup.okhttp.Response;

/**
 * Created by jialing.yao on 2017-5-9.
 */
public interface PushCallback {

    void onSuccess(Response response);

    void onFailure(Response response);
}
