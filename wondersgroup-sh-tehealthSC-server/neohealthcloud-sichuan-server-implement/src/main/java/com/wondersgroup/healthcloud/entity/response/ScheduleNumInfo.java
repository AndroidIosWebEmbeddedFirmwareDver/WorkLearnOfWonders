package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/10/30.
 *
 * @author nick
 */
@Data
@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleNumInfo {

    /**
     * 排班ID
     */
    private String scheduleId;

    /**
     * 号源ID
     */
    private String numSourceId;

    /**
     * 排班日期
     */
    private String scheduleDate;

    /**
     * 医院代码
     */
    private String hosOrgCode;

    /**
     * 医院名称
     */
    private String hosName;

    /**
     * 科室代码
     */
    private String hosDeptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 医生代码
     */
    private String hosDoctCode;

    /**
     * 医生名称
     */
    private String doctName;

    /**
     * 出诊级别编码
     */
    private String visitLevelCode;

    /**
     * 出诊级别
     * <p>
     * 0:其他;1:住院医师;2:主治医生;3:副主任医师;4:主任医师;5:名老专家。
     * <p>
     * 由于各医院的出诊级别没有标准化，第三方接口直接用中文表示出诊级别。
     */
    private String visitLevel;

    /**
     * 出诊费用
     */
    private String visitCost;

    /**
     * 出诊时段
     */
    private String timeRange;

    /**
     * 开始时间
     * 格式 9:00
     */
    private String startTime;

    /**
     * 结束时间
     * 格式 9:30
     */
    private String endTime;

    /**
     * 已预约数
     */
    private int orderedNum;

    /**
     * 可预约总数
     * <p>
     * 可预约总数即放号数，包括已预约号源数。
     */
    private int reserveOrderNum;

    /**
     * 预约类型
     * <p>
     * 包括指定到科室，医生级别，医生（缺省到医生）
     * 1: 医生；
     * 2：医生级别；
     * 3：科室；
     */
    private String orderType;

    /**
     * 序号
     * <p>
     * 以|分割的数字串
     */
    private String visitNo;

}
