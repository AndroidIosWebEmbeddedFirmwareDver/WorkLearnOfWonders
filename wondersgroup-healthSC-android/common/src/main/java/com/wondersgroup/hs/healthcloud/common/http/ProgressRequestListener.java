package com.wondersgroup.hs.healthcloud.common.http;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/19 11:05
 */
public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}