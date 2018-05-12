package com.wondersgroup.hs.healthcloud.common.util;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;
import com.wondersgroup.hs.healthcloud.common.http.CallProxy;
import com.wondersgroup.hs.healthcloud.common.http.HttpLoggingInterceptor;
import com.wondersgroup.hs.healthcloud.common.http.HttpMethod;
import com.wondersgroup.hs.healthcloud.common.http.RequestParams;
import com.wondersgroup.hs.healthcloud.common.logic.BaseCallback;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 10:58
 */
public class HttpTools {

    private static OkHttpClient sOkHttp;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ArrayList<WeakReference<CallProxy>> mCalls = new ArrayList<>();

    public HttpTools() {
        if (sOkHttp == null) {
            sOkHttp = initOkHttpClient();
        }
    }

    private OkHttpClient initOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES))
//                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
    }

    public void configSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        sOkHttp = sOkHttp.newBuilder().sslSocketFactory(sslSocketFactory).build();
    }

    @Deprecated
    public <T> CallProxy get(String url, RequestParams params, BaseCallback<T> callBack) {
        params.setPath(url);
        return get(params, callBack);
    }

    public <T> CallProxy get(RequestParams params, BaseCallback<T> callBack) {
        params.setMethod(HttpMethod.GET);
        return send(params, callBack);
    }

    @Deprecated
    public <T> CallProxy post(String url, RequestParams params, BaseCallback<T> callBack) {
        params.setPath(url);
        return post(params, callBack);
    }

    public <T> CallProxy post(RequestParams params, BaseCallback<T> callBack) {
        params.setMethod(HttpMethod.POST);
        return send(params, callBack);
    }

    public <T> CallProxy delete(String url, RequestParams params, BaseCallback<T> callBack) {
        params.setPath(url);
        return delete(params, callBack);
    }

    public <T> CallProxy delete(RequestParams params, BaseCallback<T> callBack) {
        params.setMethod(HttpMethod.DELETE);
        return send(params, callBack);
    }

    public <T> CallProxy upload(String url, RequestParams params, BaseCallback<T> callBack) {
        params.setPath(url);
        return upload(params, callBack);
    }

    public <T> CallProxy upload(RequestParams params, BaseCallback<T> callBack) {
        params.setMethod(HttpMethod.POST);
        params.setDefaultPostType(RequestParams.TYPE_MULTI_PART);
        return post(params, callBack);
    }

    public CallProxy download(String path, String target, RequestParams params, BaseCallback<File> callback) {
        params.setPath(path);
        params.setDownloadPath(target);
        return download(params, callback);
    }

    public CallProxy download(@NonNull final RequestParams params, final BaseCallback<File> callBack) {
        params.setMethod(HttpMethod.GET);
        final File downloadFile = new File(params.getDownloadPath());
        if (!downloadFile.getParentFile().exists()) {
            downloadFile.getParentFile().mkdirs();
        }
        if (!downloadFile.exists()) {
            try {
                downloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return enqueue(sOkHttp, params, new CallAdapter<File>(callBack, mHandler) {

            @Override
            public void onResult(Call call, Response response) throws Exception {
                if (response.code() == 416) {
                    if (callBack != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(downloadFile);
                            }
                        });
                    }
                } else {
                    params.setAutoResume(params.isAutoResume() && isSupportRange(response));
                    final long contentLen = response.body().contentLength() + downloadFile.length();
                    FileUtil.streamToFile(response.body().byteStream(), params.getDownloadPath(),
                            params.isAutoResume(), new FileUtil.OnProgressListener() {
                                @Override
                                public void onProgress(final long len) {
                                    if (callBack != null) {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                callBack.onLoading(contentLen, len, false);
                                            }
                                        });
                                    }
                                }
                            });
                    if (callBack != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(downloadFile);
                            }
                        });
                    }
                }
            }
        });
    }

    public <T> CallProxy send(@NonNull RequestParams params, final BaseCallback<T> callBack) {
        return enqueue(sOkHttp, params, new CallAdapter<T>(callBack, mHandler) {

            @Override
            public void onResult(Call call, Response response) throws Exception {
                if (callBack != null) {
                    callBack.parseData(response.body().string(), mHandler);
                }
            }
        });
    }

    private <T> CallProxy enqueue(@NonNull OkHttpClient client, @NonNull RequestParams params, final CallAdapter<T> adapter) {
        LogUtils.d("okhttp:" + params.toString());
        Call call = client.newCall(params.toRequest());
        call.enqueue(adapter);
        CallProxy callProxy = new CallProxy(call);
        mCalls.add(new WeakReference<>(callProxy));
        return callProxy;
    }

    public <T> T sendSync(RequestParams params, Class<T> clazz) throws IOException {
        Response stream = sendSync(params);
        try {
            if (stream != null && stream.isSuccessful()) {
                String result = stream.body().string();
                BaseResponse response = JSON.parseObject(result, BaseResponse.class);
                if (response.isSuccess()) {
                    T t = JSON.parseObject(response.data.toString(), clazz);
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> sendArraySync(RequestParams params, Class<T> clazz) throws IOException {
        Response stream = sendSync(params);
        try {
            if (stream != null && stream.isSuccessful()) {
                String result = stream.body().string();
                BaseResponse response = JSON.parseObject(result, BaseResponse.class);
                if (response.isSuccess()) {
                    List<T> t = JSON.parseArray(response.data.toString(), clazz);
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response sendSync(RequestParams params) throws IOException {
        return sOkHttp.newCall(params.toRequest()).execute();
    }

    public void cancel() {
        sOkHttp.dispatcher().cancelAll();
    }

    public void cancel(@NonNull Object tag) {
        if (tag == null) {
            return;
        }
        for (WeakReference<CallProxy> callProxyWeakReference : mCalls) {
            CallProxy callProxy = callProxyWeakReference.get();
            if (callProxy != null && tag.equals(callProxy.getTag())) {
                callProxy.cancel();
            }
        }
    }

    static abstract class CallAdapter<T> implements Callback {
        private BaseCallback callBack;
        private Handler mHandler;

        public CallAdapter(final BaseCallback<T> callBack, Handler handler) {
            this.callBack = callBack;
            mHandler = handler;
            if (callBack != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onStart();
                    }
                });
            }
        }

        @Override
        public void onFailure(final Call call, final IOException e) {
            LogUtils.e("okhttp:response->" + e.toString());
            if (callBack != null) {
                if (call.isCanceled()) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onCancelled();
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailure(e);
                            callBack.onFinish();
                        }
                    });
                }

            }
        }

        @Override
        public void onResponse(Call call, Response response) {
            try {
                onResult(call, response);
            } catch (final Exception e) {
                if (callBack != null) {
                    LogUtils.e("okhttp:response->" + response.toString());
                    if (call.isCanceled()) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onCancelled();
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFailure(e);
                            }
                        });
                    }
                }
            } finally {
                if (response.body() != null) {
                    response.close();
                }
                if (callBack != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFinish();
                        }
                    });
                }
            }
        }

        public abstract void onResult(Call call, Response response) throws Exception;
    }

    private static boolean isSupportRange(final Response response) {
        if (response == null) return false;
        String header = response.header("Accept-Ranges");
        if ("bytes".equals(header)) {
            return true;
        }
        header = response.header("Content-Range");
        if (header != null && header.startsWith("bytes")) {
            return true;
        }
        return false;
    }
}
