package com.wondersgroup.healthcloud.services.healthRecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 门诊处方信息
 * Created by ys on 2015/12/07.
 */
@Data
public class HealthYongYaoMedicineInfoAPIEntity {

    private String medicine_name; // 药品名称 xmmc
    private String medicine_num; //ypdw + ypdw 发药数量+发药数量单位
    private String use_type;//用法 yytj
    private String day_use_num;//每天用量 sypc 每天2次
    //每次用量 jl + jldw 每次使用剂量+每次使用剂量单位
    //或者 sl + sldw 每次使用数量+每次使用数量单位
    private String per_use_num;
    private String specification;//药品规格 ypgg

}
