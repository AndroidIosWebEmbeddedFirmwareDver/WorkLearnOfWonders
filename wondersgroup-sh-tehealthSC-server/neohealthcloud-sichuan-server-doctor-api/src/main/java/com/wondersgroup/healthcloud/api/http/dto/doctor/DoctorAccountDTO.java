package com.wondersgroup.healthcloud.api.http.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by longshasha on 16/8/1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorAccountDTO {
    private String uid;
    private String mobile;
    private String name;
    private String nickname;//昵称

    @JsonProperty("login_name")
    private String loginName;//登录名

    private String idcard;//身份证号
    private String gender;//性别

    @JsonProperty("hospital_id")
    private String hospitalId;//所属医疗机构代码

    @JsonProperty("hospital_name")
    private String hospitalName;//所属医疗机构名称

    @JsonProperty("depart_name")
    private String departName;//所属标准科室

    @JsonProperty("duty_name")
    private String dutyName; //职称
    private String no;//工号
    private String expertin;//擅长
    private String introduction;//简介
    private String avatar;//头像

    private String talkid; //环信账号
    private String talkpwd;//环信密码
    private String talkgroupid;//环信群组Id

    private String actcode;//医生推广邀请码

    private Boolean hasQA;//是否有 我的问答

    @JsonProperty("attention_num")
    private Long attentionNum;//关注数

    @JsonProperty("fans_num")
    private Long fansNum;//粉丝数

    @JsonProperty("notecase_num")
    private Long notecaseNum;//病例数

    @JsonProperty("dynamic_num")
    private Long dynamicNum;//动态

    private Boolean verified = true;


    public DoctorAccountDTO(Map<String, Object> doctor) {
        if (doctor != null) {
            this.uid = doctor.get("id") == null ? "" : doctor.get("id").toString();
            this.mobile = doctor.get("mobile") == null ? "" : doctor.get("mobile").toString();
            this.name = doctor.get("name") == null ? "" : doctor.get("name").toString();
            this.nickname = doctor.get("nickname") == null ? "" : doctor.get("nickname").toString();
            this.loginName = doctor.get("loginName") == null ? "" : doctor.get("loginName").toString();

            this.idcard = doctor.get("idcard") == null ? "" : doctor.get("idcard").toString();
            this.gender = doctor.get("gender") == null ? "" : doctor.get("gender").toString();

            this.hospitalId = doctor.get("hospitalId") == null ? "" : doctor.get("hospitalId").toString();
            this.hospitalName = doctor.get("hospitalName") == null ? "" : doctor.get("hospitalName").toString();
            this.dutyName = doctor.get("dutyName") == null ? "" : doctor.get("dutyName").toString();
            this.departName = doctor.get("departName") == null ? "" : doctor.get("departName").toString();
            this.no = doctor.get("no") == null ? "" : doctor.get("no").toString();

            this.introduction = doctor.get("introduction") == null ? "" : doctor.get("introduction").toString();
            this.expertin = doctor.get("expertin") == null ? "" : doctor.get("expertin").toString();
            this.avatar = doctor.get("avatar") == null ? "" : doctor.get("avatar").toString();


            this.talkid = doctor.get("talkid") == null ? "" : doctor.get("talkid").toString();
            this.talkpwd = doctor.get("talkpwd") == null ? "" : doctor.get("talkpwd").toString();
            this.talkgroupid = doctor.get("talkgroupid") == null ? "" : doctor.get("talkgroupid").toString();

            this.actcode = doctor.get("actcode") == null ? "" : doctor.get("actcode").toString();
        }


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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getExpertin() {
        return expertin;
    }

    public void setExpertin(String expertin) {
        this.expertin = expertin;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTalkid() {
        return talkid;
    }

    public void setTalkid(String talkid) {
        this.talkid = talkid;
    }

    public String getTalkpwd() {
        return talkpwd;
    }

    public void setTalkpwd(String talkpwd) {
        this.talkpwd = talkpwd;
    }

    public String getTalkgroupid() {
        return talkgroupid;
    }

    public void setTalkgroupid(String talkgroupid) {
        this.talkgroupid = talkgroupid;
    }

    public String getActcode() {
        return actcode;
    }

    public void setActcode(String actcode) {
        this.actcode = actcode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public Boolean getHasQA() {
        return hasQA;
    }

    public void setHasQA(Boolean hasQA) {
        this.hasQA = hasQA;
    }

    public Long getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(Long attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Long getFansNum() {
        return fansNum;
    }

    public void setFansNum(Long fansNum) {
        this.fansNum = fansNum;
    }

    public Long getNotecaseNum() {
        return notecaseNum;
    }

    public void setNotecaseNum(Long notecaseNum) {
        this.notecaseNum = notecaseNum;
    }

    public Long getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(Long dynamicNum) {
        this.dynamicNum = dynamicNum;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
