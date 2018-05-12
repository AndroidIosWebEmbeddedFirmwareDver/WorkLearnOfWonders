package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;

/*
 * Created by sunning on 16/1/5.
 */
public class DoctorListVO implements Serializable{
    /**
         {
             "hosOrgCode": "450753341",//医院代码
             "hosDeptCode": "1007",//科室代码
             "hosDoctCode": "266",//医生代码
             "headphoto":"",//医生头像
             "doctorName": "王小剑",//医生姓名
             "doctorTitle": "",医生职称
             "expertin": "",//特长
             "orderCount": 0,//接诊量
             "isFull": 0//0-约满,1-可预约
         }
     */

    public String numSourceId;

    public String hosOrgCode;
    public String hosDeptCode;
    public String hosDoctCode;
    public String headphoto;
    public String doctorName;
    public String doctorTitle;
    public String expertin;
    public int orderCount;
    public int isFull;

    public int id;
    public String hosName;
    public String deptName;
    public String gender;
    /**
     * scheduleId : 274||1619
     * scheduleDate : 2016-11-14
     * numSource : 44
     * visitCost : 8
     * visitLevel : 医师
     * timeRange : 1
     */

    public ScheduleInfo.ScheduleEntity schedule;

//    public static class ScheduleEntity implements Serializable{
//        public String scheduleId;
//        public String scheduleDate;
//        public int numSource;
//        public String visitCost;
//        public String visitLevel;
//        public String timeRange;
//    }
}
