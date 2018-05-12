package com.wondersgroup.healthcloud.api.http.controllers;

import java.util.List;

import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;

public abstract class BaseController {

    protected <T> JsonListResponseEntity<T> buildListEntity(List<T> t, boolean hasNext, int pageNumber) {
        JsonListResponseEntity<T> responseEntity = new JsonListResponseEntity<T>();
        if (hasNext) {
            pageNumber += 1;
        }
        responseEntity.setContent(t, hasNext, null, pageNumber + "");
        return responseEntity;
    }

    protected <T> JsonResponseEntity<T> buildEntity(int code, String msg, T data) {
        return new JsonResponseEntity<>(code, msg, data);
    }

    protected <T> JsonResponseEntity<T> buildFailed(int code, String msg) {
        return buildEntity(code, msg, null);
    }

    protected <T> JsonResponseEntity<T> buildFailed(String msg) {
        return buildEntity(1000, msg, null);
    }

    protected <T> JsonResponseEntity<T> buildSuccessed(T data) {
        return buildEntity(0, null, data);
    }

    protected <T> JsonResponseEntity<T> buildSuccessed(String msg, T data) {
        return buildEntity(0, msg, data);
    }

}
