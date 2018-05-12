package com.wondersgroup.healthcloud.services.healthRecord.dto;

import com.wondersgroup.healthcloud.services.prescription.dto.PrescriptionDetailDto;
import lombok.Data;


/**
 * 门诊处方信息
 * Created by ys on 2015/10/14.
 */
@Data
public class HealthYongYaoListAPIEntity {

    private String id;
    private String print_time = "-";//开方时间
    private String hospital_name = "未知";//医院名称
    private String department_name = "未知";//科室
    private String prescription_type = "未知";//处方类型
    private String prescription_amount = "-";//处方金额

    public String getPrescription_amount() {
        return PrescriptionDetailDto.fenToYuan(prescription_amount);
    }
}
