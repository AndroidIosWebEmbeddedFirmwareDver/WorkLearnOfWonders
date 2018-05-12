package com.wonders.health.venus.open.doctor.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.wonders.health.venus.open.doctor.logic.SignRequest;
import com.wondersgroup.hs.healthcloud.common.db.annotation.Transient;
import com.wondersgroup.hs.healthcloud.common.http.RequestParams;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/3/1 13:31
 */
public class User {
    // user key
    public String uid;
    public String userID;
    public String token;
    public String key;

    public String mobile;
    public String orgName;
    public String orgCode;
    public String deptName;
    public String deptCode;
    public String idCardNo;
    public String talkid;
    public String talkpwd;


    @Transient
    public String verify_code;
    @Transient
    public String pwd;

    // userinfo
    public String avatar;

    public String gender; // 1 男 2 女

    public String name;
    public String idcard;
    public String age;
    public String birthday;
    public boolean verified;
    public String verificationStatus;//实名认证状态 0-未提交,1-认证失败,2-审核中,3-认证成功
    public String height;
    public String weight;
    public String waist;
    public String nickname;
    public String bind_personcard;
    public String specArea;//用户选择的区域
    @JSONField(name = "address_province")
    public String province;
    @JSONField(name = "address_city")
    public String city;
    @JSONField(name = "address_county")
    public String county;
    @JSONField(name = "address_town")
    public String town;
    @JSONField(name = "address_committee")
    public String committee;
    @JSONField(name = "address_other")
    public String other;
    @JSONField(name = "address_display")
    public String display;
    public boolean actived_invitation;
    public boolean password_complete; // 是否设置过密码
    public boolean first_login;  // 是否第一次使用

    // 本地字段 判断环信是否登录
    public boolean isEMLogin;

    public static class Address {
        public String province;
        public String city;
        public String county;
        public String town;
        public String committee;
        public String other;
        public String display;
    }

    /**
     * 构建注册参数
     *
     * @return
     */
//    public RequestParams buildRegistParams() {
//        SignRequest params = new SignRequest();
//        params.addBodyParameter("mobile", mobile);
//        params.addBodyParameter("verify_code", verify_code);
//        params.addBodyParameter("password", getPwd());
//        return params;
//    }

    /**
     * 构建注册参数
     *
     * @return
     */
//    public RequestParams buildLoginParams() {
//        SignRequest params = new SignRequest();
//        params.addQueryStringParameter("mobile", mobile);
//        params.addQueryStringParameter("password", getPwd());
//        return params;
//    }

    /**
     * 构建快速登录参数
     *
     * @return
     */
    public RequestParams buildFastLoginParams() {
        SignRequest params = new SignRequest();
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("code", verify_code);
        return params;
    }

//    public String getPwd() {
//        if (TextUtils.isEmpty(pwd)) {
//            return pwd;
//        }
//        try {
//            String key = AppConfigManager.getInstance().getAppConfig().publicKey;
//            String publicKey = RSAUtil.encryptByPublicKey(pwd, key);
//            return publicKey;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "***";
//    }
}
