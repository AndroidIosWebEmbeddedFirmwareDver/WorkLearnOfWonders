package com.wonders.health.venus.open.doctor.entity;

/**
 * 挂号列表
 * Bob
 */
public class RegistrationListVO {

    public String doctor_name;
    public String hospital_name;
    public String status; //预约状态 1:预约成功, 2:就诊成功, 3:爽约, 4:取消, 5:评价成功
    public String id;
    public String start_time;
    public String department_name;
}
