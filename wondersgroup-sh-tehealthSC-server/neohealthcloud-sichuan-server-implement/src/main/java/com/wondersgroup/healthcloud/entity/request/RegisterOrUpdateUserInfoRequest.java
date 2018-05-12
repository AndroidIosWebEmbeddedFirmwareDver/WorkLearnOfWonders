package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fulong on 2018/4/17.
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterOrUpdateUserInfoRequest extends BaseRequest {
    @XmlElement(name = "UserInfo")
    public UserInfo userInfo;

    @Data
    @XmlRootElement(name = "UserInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UserInfo {
        private String userCardId;//证件ID, 必填
        private String userCardType;//证件类型, 必填 01：居民身份证 02：居民户口簿 03：护照 04：军官证（士兵证）05：驾驶执照 06：港澳居民来往内地通行证 07：台湾居民来往内地通行证 99：其他
        private String userPhone;//电话号码
        private String userName;//用户姓名
        private String passWord;//登录密码
        private String identifyCode;//是否认证用户
        private String userSex;//性别, 0：未知的性别 1：男性 2：女性 5：女性改（变）为男性 6：男性改（变）为女性 9：未说明的性别
        private String userBD;//生日,yyyy-MM-dd
        private String userContAdd;//用户地址
        private String userEmail;//用户邮箱


    }
}

