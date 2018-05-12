package com.wondersgroup.healthcloud.services.healthRecord.dto;

import lombok.Data;

/**
 * 住院小结
 */
@Data
public class HealthZhuYuanDetailAPIEntity {

    private String name;
    private String age;
    private String hospitalDay;//住院天数
    private String hospital_name = "-";//医院名称
    private String hospitalized_time;//入院时间 2016-10-11到2016-12-12
    private String departmentName; // 科室名称
    private String admissionDiagnosis = "无";//入院诊断
    private String dischargeDiagnosis = "无";//出院诊断
    private String dischargeOrder = "无";//出院医嘱
    private String treatmentOutcome = "无";//治疗结果
}
