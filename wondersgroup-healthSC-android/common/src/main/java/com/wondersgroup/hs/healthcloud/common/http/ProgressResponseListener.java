package com.wondersgroup.hs.healthcloud.common.http;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/19 11:03
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
