package com.wondersgroup.healthcloud.services.healthRecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 门诊就诊记录
 * Created by ys on 2015/10/14.
 */
@Data
public class HealthJiuZhenListAPIEntity {

    private String date;//就诊日期
    private String hospital_name = "未知";//医院名称
    private String office_name = "未知"; //jzksmc 就诊科室名称 eq:急诊中心,神内科
    private String office_type;// 就诊类型名称 eq:急诊 普通门诊
    private String diagnose_result;//门诊诊断结果 eq:急性上呼吸道感染

    public String getHospital_name() {
        return StringUtils.isEmpty(hospital_name) ? "未知" : hospital_name;
    }
}
