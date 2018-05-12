package com.wondersgroup.hs.healthcloud.common.util;
/*
 * Created by sunning on 2017/6/14.
 */

public abstract class Singletion<T> {

    private T mInstance;

    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
        }
        return mInstance;
    }
}
