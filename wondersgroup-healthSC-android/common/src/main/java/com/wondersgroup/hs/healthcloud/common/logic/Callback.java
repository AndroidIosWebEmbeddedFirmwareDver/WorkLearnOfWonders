package com.wondersgroup.hs.healthcloud.common.logic;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 13:35
 */
public interface Callback<T> {
    void onStart();
    void onSuccess(T t);
    void onFailure(Exception e);
    void onFinish();
}
