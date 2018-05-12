package com.wondersgroup.healthcloud.utils;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dukuanxin on 2016/9/5.
 */
public class BackCall implements Callback {

    private static Logger logger = LoggerFactory.getLogger(BackCall.class);

    @Override
    public void onFailure(Request request, IOException e) {
        logger.debug("Timed task call failed,Because the connection failed");
    }

    @Override
    public void onResponse(Response response) throws IOException {
        logger.debug("Timed task call success.");
    }
}
