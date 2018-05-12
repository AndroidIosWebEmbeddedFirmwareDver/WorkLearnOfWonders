package com.wondersgroup.healthcloud.services.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by longshasha on 16/11/4.
 * 实名认证信息
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationForm {

    private Integer id;
    private String uid;
    private String mobile;
    private String name;
    private String idcard;
    private String gender = "0";
    private String photo;
    private Integer verificationLevel;// 实名认证级别 -1 拒绝，0未认证，1已认证
    private String refusalReason;
    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getVerificationLevel() {
        return verificationLevel;
    }

    public void setVerificationLevel(Integer verificationLevel) {
        this.verificationLevel = verificationLevel;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }
}
