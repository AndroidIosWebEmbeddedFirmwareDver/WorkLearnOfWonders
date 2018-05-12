package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：11/10/16 4:15 PM
 */
public class ScheduleInfo implements Serializable{
    public static final String TIME_AM = "1";
    public static final String TIME_PM = "2";

    public DoctorInfoEntity doctorInfo;
    public List<ScheduleEntity> schedule;
    public String systemTime;
    public String week;

    public static class DoctorInfoEntity implements Serializable{
        public String hosOrgCode;
        public String hosName;
        public String hosDeptCode;
        public String deptName;
        public String hosDoctCode;
        public String doctorName;
        public String doctorTitle;
        public String headphoto;
        public String gender;
    }

    public static class ScheduleEntity implements Serializable {
        public String scheduleId;
        public String scheduleDate;
        public String weekDay;
        public String startTime;
        public String endTime;
        public String visitLevel;
        public String visitCost;
        public String timeRange; // 1 上午 2 下午 3 晚上
        public int isFull; //0-约满,1-可预约
        public int numSource;
    }
}
