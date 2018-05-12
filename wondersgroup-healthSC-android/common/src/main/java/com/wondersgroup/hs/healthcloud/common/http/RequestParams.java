package com.wondersgroup.hs.healthcloud.common.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 14:54
 */
public class RequestParams {
    public static final String TYPE_URL_ENCODE = "application/x-www-form-urlencoded";
    public static final String TYPE_RAW_JSON = "application/json";
    public static final String TYPE_MULTI_PART = "multipart/form-data";

    protected String path;  //请求url路径
    protected String method = com.wondersgroup.hs.healthcloud.common.http.HttpMethod.GET;    // 请求谓词
    protected String bodyText;  // 请求body
    protected List<NameValuePair> queryStringParams; // 请求url后的参数
    protected List<NameValuePair> bodyParams;   // 请求body
    protected List<FileWrapper> fileParams;     // 上传文件
    protected String contentType = TYPE_RAW_JSON;   // request的内容格式
    protected String charset = "UTF-8"; // 请求的编码
    private boolean canCancel = true;   // 请求是否可以终止
    private boolean autoResume = true;  // 下载是否从上次点继续
    private String downloadPath;        // 下载文件路径

    private final Request.Builder builder;

    public RequestParams() {
        builder = new Request.Builder();
    }

    public RequestParams(String path) {
        builder = new Request.Builder();
        this.path = path;
    }

    public void setDefaultPostType(String contentType) {
        this.contentType = contentType;
    }

    public String getDefaultPostType() {
        return contentType;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    /***********************
     * 设置path
     *******************************************/
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isAutoResume() {
        return autoResume;
    }

    public void setAutoResume(boolean autoResume) {
        this.autoResume = autoResume;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    /***********************
     * 设置header
     ***************************************/
    public void addHeader(NameValuePair header) {
        if (header == null) {
            return;
        }
        addHeader(header.getName(), header.getValue());
    }

    public void addHeader(String name, String value) {
        if (value == null) {
            value = "";
        }
        builder.addHeader(name, value);
    }

    public void setHeader(String name, String value) {
        if (value == null) {
            value = "";
        }
        builder.header(name, value);
    }


    /***********************
     * 设置queryString
     ***************************************/
    public void addQueryStringParameter(NameValuePair nameValuePair) {
        if (nameValuePair == null || TextUtils.isEmpty(nameValuePair.getValue())) {
            return;
        }
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }
        queryStringParams.add(nameValuePair);
    }

    public void addQueryStringParameter(String name, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }
        queryStringParams.add(new BasicNameValuePair(name, value));
    }

    public void addQueryMapParameter(Map<String, String> map) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }

        if (map != null) {
            for (String key : map.keySet()) {
                addQueryStringParameter(key, map.get(key));
            }
        }
    }

    /***********************
     * 设置body
     ***************************************/
    public void addBodyParameter(String name, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (bodyParams == null) {
            bodyParams = new ArrayList<NameValuePair>();
        }
        bodyParams.add(new BasicNameValuePair(name, value));
    }

    public void addBodyParameter(NameValuePair nameValuePair) {
        if (nameValuePair == null || TextUtils.isEmpty(nameValuePair.getValue())) {
            return;
        }
        if (bodyParams == null) {
            bodyParams = new ArrayList<NameValuePair>();
        }
        bodyParams.add(nameValuePair);
    }

    public void addBodyParameter(String key, File file) {
        if (fileParams == null) {
            fileParams = new ArrayList<>();
        }
        fileParams.add(new FileWrapper(key, file));
    }

    public void addBodyParameter(String key, File file, String mimeType) {
        if (fileParams == null) {
            fileParams = new ArrayList<>();
        }
        fileParams.add(new FileWrapper(key, file, null, mimeType));
    }

    public Request toRequest() {
        // 设置url
        HttpUrl httpUrl = HttpUrl.parse(path);
        if (queryStringParams != null && !queryStringParams.isEmpty()) {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (NameValuePair pair : queryStringParams) {
                urlBuilder.addQueryParameter(pair.getName(), pair.getValue());
            }
            httpUrl = urlBuilder.build();
        }
        builder.url(httpUrl);
        if (autoResume && downloadPath != null) {
            File file = new File(downloadPath);
            if (file.exists()) {
                builder.header("RANGE", "bytes=" + file.length() + "-");
            }
        }
        builder.header("Content-Type", contentType + ";charset=" + charset);
        if (HttpMethod.requiresRequestBody(method)) {
            // 设置body
            buildBodyJsonText();
            if (TYPE_URL_ENCODE.equals(contentType)) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                if (bodyParams != null && !bodyParams.isEmpty()) {
                    for (NameValuePair pair : bodyParams) {
                        bodyBuilder.addEncoded(pair.getName(), pair.getValue());
                    }
                }
                builder.method(method, bodyBuilder.build());
            } else if (TYPE_RAW_JSON.equals(contentType)) {
                builder.method(method, RequestBody.create(MediaType.parse(contentType), bodyText));
            } else if (TYPE_MULTI_PART.equals(contentType)) {
                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
                if (bodyParams != null && !bodyParams.isEmpty()) {
                    for (NameValuePair pair : bodyParams) {
                        bodyBuilder.addFormDataPart(pair.getName(), pair.getValue());
                    }
                }
                if (fileParams != null && !fileParams.isEmpty()) {
                    for (FileWrapper pair : fileParams) {
                        bodyBuilder.addFormDataPart(pair.key, pair.getFileName()
                                , RequestBody.create(MediaType.parse(pair.getMediaType()), pair.file));
                    }
                }
                builder.method(method, bodyBuilder.build());
            }
        } else {
            builder.method(method, null);
        }
        return builder.build();
    }

    public String getQueryString(boolean sort) {
        StringBuilder sb = new StringBuilder();
        if (queryStringParams != null && !queryStringParams.isEmpty()) {
            if (sort) {
                Collections.sort(queryStringParams, new Comparator<NameValuePair>() {
                    @Override
                    public int compare(NameValuePair t0, NameValuePair t1) {
                        return t0.getName().compareTo(t1.getName());
                    }
                });
            }
            for (NameValuePair item : queryStringParams) {
                if (!TextUtils.isEmpty(item.getValue())) {
                    sb.append(item.getName()).append("=").append(item.getValue()).append("&");
                }
            }
            if (sb.toString().endsWith("&")) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    public void setJsonBody(Object object) {
        bodyText = JSON.toJSONString(object);
    }

    public void buildBodyJsonText() {
        if (TextUtils.isEmpty(bodyText) && bodyParams != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                for (NameValuePair pair : bodyParams) {
                    jsonObject.put(pair.getName(), pair.getValue());
                }
                bodyText = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        bodyText = (bodyText == null ? "" : bodyText);
    }

    private static class FileWrapper {
        public String key;
        public File file;
        private String fileName;
        private String mediaType;

        public FileWrapper(String key, File fie) {
            this(key, fie, null, null);
        }

        public FileWrapper(String key, File file, String fileName, String mediaType) {
            this.fileName = fileName;
            this.mediaType = mediaType;
            this.file = file;
            this.key = key;
        }

        public String getFileName() {
            if (fileName != null) {
                return fileName;
            } else {
                return file == null ? "" : file.getName();
            }
        }

        public String getMediaType() {
            if (TextUtils.isEmpty(mediaType)) {
                return "application/octet-stream";
            }
            return mediaType;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(method + "->");
        HttpUrl httpUrl = HttpUrl.parse(path);
        if (queryStringParams != null && !queryStringParams.isEmpty()) {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (NameValuePair pair : queryStringParams) {
                urlBuilder.addQueryParameter(pair.getName(), pair.getValue());
            }
            httpUrl = urlBuilder.build();
        }
        sb.append(httpUrl.toString());
        buildBodyJsonText();
        if (!TextUtils.isEmpty(bodyText)) {
            sb.append("\t[body:" + bodyText + "]");
        }
        return sb.toString();
    }
}
