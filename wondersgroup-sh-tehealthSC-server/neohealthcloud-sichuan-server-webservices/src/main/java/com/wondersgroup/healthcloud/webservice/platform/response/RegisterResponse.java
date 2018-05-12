package com.wondersgroup.healthcloud.webservice.platform.response;

import com.wondersgroup.healthcloud.entity.response.BaseResponse;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 平台注册系统接口返回参数
 */
@Data
@XmlRootElement(name = "Response")
public class RegisterResponse extends BaseResponse {

    @XmlElement(name = "Result")
    public Result result;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        private String platformUserId;//平台注册ID
        private String userId;//微健康注册ID
        private String userPwd;//微健康登录密码

        public Result(){
        }
    }
}