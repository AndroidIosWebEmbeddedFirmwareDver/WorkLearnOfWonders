package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fulong on 2018/4/17.
 */
@Data
@XmlRootElement(name = "Response")
public class RegisterOrUpdateUserInfoResponse extends BaseResponse {

    @XmlElement(name = "Result")
    public Result result;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        private String platformUserId;//平台用户ID
        private String userCardId;//卡号码
        private String userPhone;//电话号码
        private String userPwd;//平台登录密码
    }
}
