package com.wondersgroup.healthcloud.services.healthRecord.dto;

import lombok.Data;

/**
 * 住院手术史
 */
@Data
public class HealthZhuYuanListAPIEntity {

    private String id;
    private String hospital_name = "-";//医院名称
    private String hospitalized_time;//入院时间 2016-10-11到2016-12-12
    private String office_name; // 科室名称
    private Integer office_type;// 就诊类型名称jzlx
    private String diagnose_result = "-";//出院诊断结果cyzd
    private String doctor_suggest = "-";//出院医嘱cyyz

}
