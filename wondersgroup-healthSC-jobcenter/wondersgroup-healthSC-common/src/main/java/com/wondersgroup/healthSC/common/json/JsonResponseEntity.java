package com.wondersgroup.healthSC.common.json;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by zhangzhixiu on 15/6/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponseEntity<T> {

    private int code;
    private String msg;
    private T data;

    public JsonResponseEntity() {
        this.code = 0;
    }

    public JsonResponseEntity(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
