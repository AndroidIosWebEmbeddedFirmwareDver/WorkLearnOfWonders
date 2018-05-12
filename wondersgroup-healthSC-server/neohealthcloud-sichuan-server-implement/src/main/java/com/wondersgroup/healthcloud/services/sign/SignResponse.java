package com.wondersgroup.healthcloud.services.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * sign platform 接口返回JSON封装
 * Created by jialing.yao on 2017-6-6.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignResponse<T> {
    @JsonProperty("resultCode")
    private String resultCode; // 0-成功 1失败
    @JsonProperty("message")
    private String message;//返回消息
    @JsonProperty("data")
    private T data;//数据实体

    public boolean isSuccessFul() {
        if (resultCode.equals("0")) {
            return true;
        }
        return false;
    }

}
