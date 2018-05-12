package com.wondersgroup.hs.healthcloud.common.logic;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;
import com.wondersgroup.hs.healthcloud.common.entity.event.TokenEmptyEvent;
import com.wondersgroup.hs.healthcloud.common.entity.event.TokenExpiredEvent;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 15:35
 */
public abstract class BaseCallback<T> implements Callback<T> {
    public static final String ERR_MSG = "网络连接异常，请稍后再试";
    public static final boolean SHOW_RESULTS = true;


    public void parseData(String str, Handler handler) {
        if (SHOW_RESULTS) {
            Log.e("response", "response->" + str);
        }
        final BaseResponse response = JSON.parseObject(str, BaseResponse.class);
        if (response.isSuccess()) {
            Type genType = getClass().getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                genType = getClass().getSuperclass().getGenericSuperclass();
            }
            Class<T> type = null;
            if (genType instanceof ParameterizedType) {
                try {
                    type = ((Class<T>) (((ParameterizedType) (genType)).getActualTypeArguments()[0]));
                } catch (ClassCastException e) {
                }
                // 当responseCallback没有传泛型的时候，直接强转data为泛型类型
                if (type == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess((T) response.data);
                        }
                    });
                } else {
                    try {

                        if (response.data instanceof JSONObject) {
                            final T t = JSON.toJavaObject((JSONObject) response.data, type);
                            if (t == null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onFailure(new Exception(ERR_MSG));
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onSuccess(t);
                                    }
                                });
                            }
                        } else if (response.data instanceof JSONArray) {
                            final List<T> list = JSON.parseArray(response.data.toString(), type);
                            if (list == null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onFailure(new Exception(ERR_MSG));
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onSuccess(list);
                                    }
                                });
                            }
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onSuccess((T) response.data);
                                }
                            });
                        }
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(new Exception(ERR_MSG));
                            }
                        });
                    }
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess((T) response.data);
                    }
                });
            }
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (response.isTokenExpired()) {
                        EventBus.getDefault().post(new TokenExpiredEvent(response));
                    } else if (response.code == BaseResponse.CODE_TOKEN_EMPTY) {
                        EventBus.getDefault().post(new TokenEmptyEvent(response.data));
                    }
                    if (response.code == BaseResponse.CODE_WRONG_TIME) {
                        BaseResponse.sTimeDiff = response.time_diff;
                    }
                    onFailure(new HttpException(response.code, response.msg));
                }
            });
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(response);
            }
        });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

    public void onLoading(long total, long current, boolean isUploading) {
    }

    public void onResponse(BaseResponse response) {
        LogUtils.d("okhttp:response->" + response);
    }

    public void onSuccess(List<T> list) {

    }

    public void onCancelled() {
    }
}
