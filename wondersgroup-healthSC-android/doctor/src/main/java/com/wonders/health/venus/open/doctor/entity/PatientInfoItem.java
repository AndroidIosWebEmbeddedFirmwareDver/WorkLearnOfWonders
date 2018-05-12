package com.wonders.health.venus.open.doctor.entity;


import java.io.Serializable;

/**
 * Created by wang on 2017/6/5.
 */

public class PatientInfoItem implements Serializable{

    public String patientId;
    public String name;
    public String avatar;
    public String gender;
    public String age;
    public String address;
    public String telphone;
    public String patientTag;//患者标签（0重点、1贫困，一个患者会存在多个标签）

}
