package com.wondersgroup.healthcloud.services.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;
import lombok.Data;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by dukuanxin on 2016/11/5.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorInfoDTO {

    private Integer id;
    private String hosOrgCode; // 医院代码
    private String hosName;//医院名称
    private String hosDeptCode;// 科室代码
    private String deptName;//科室名称
    private String hosDoctCode;// 医生代码
    private String doctorName;// 医生名称
    private String gender;// 医生性别
    private String doctorTitle;// 医生职称
    private String doctorDesc;// 医生简介
    private String expertin;// 特长
    private String level;// 预约级别：1表示专家，2表示主任医师，3表示副主任医师，4表示主治医师
    private int orderCount; //接诊量
    private String headphoto;// 头像
    private int isFull;//0-约满，1-可预约
    private Integer evaluateCount;//评价量
    private Schedule schedule;//排班
    private Integer concern;//是否关注0-未关注，1-已关注
    List<AppEvaluateDoctorListDTO> evaluList;//医生评价

    public DoctorInfoDTO() {
    }
    public DoctorInfoDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.hosOrgCode = doctor.getHospitalCode();
        this.hosDeptCode = doctor.getDeptCode();
        this.hosDoctCode = doctor.getDoctorCode();
        this.doctorName = doctor.getDoctorName();
        this.doctorTitle = doctor.getDoctorTitle();
        this.expertin = doctor.getExpertin();
        this.orderCount = doctor.getOrderCount();
        this.headphoto = doctor.getHeadphoto();
        this.gender = doctor.getGender();
        this.isFull = doctor.getIsFull();
        this.concern = 0;
    }

    @Data
    public static class Schedule {
        public String scheduleId;//排班id
        public String scheduleDate;//排班日期
        public String weekDay;
        public int numSource;//剩余号源数
        public String visitCost;//诊费
        public String visitLevel;//出诊级别
        public String timeRange;//出诊时间段1:上午2:下午3:晚上
        public String startTime;//开始时间
        public String endTime;//结束时间

    }
}
