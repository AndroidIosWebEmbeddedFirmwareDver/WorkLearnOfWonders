package com.wondersgroup.healthcloud.services.doctor.entity;


import lombok.Data;

/**
 * Created by longshasha on 16/8/30.
 */
@Data
public class DoctorInfo {
    private String userID;//用户ID
    private String name;//姓名
    private String mobile;//手机号
    private String orgName;//所属医院
    private String deptName;//所属科室
    private String idCardNo;//登录名
    private String gender;//性别

    private String avatar;//头像
    private String nickname;//昵称
    private String talkid; //环信账号
    private String talkpwd;//环信密码

}
