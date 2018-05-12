package com.wonders.health.venus.open.doctor.entity;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：11/9/16 8:27 PM
 */
public class ScheduleItem {
    public static final int STATUS_ENABLED = 0;
    public static final int STATUS_DISABLED = 1;
    public String title;
    public String desc;
    public int status = STATUS_ENABLED;
    public ScheduleInfo.ScheduleEntity schedule;

}
