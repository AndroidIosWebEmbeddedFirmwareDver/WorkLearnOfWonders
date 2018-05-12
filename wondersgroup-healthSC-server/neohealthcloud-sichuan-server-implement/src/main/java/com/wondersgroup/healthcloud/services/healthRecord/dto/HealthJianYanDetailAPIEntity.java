package com.wondersgroup.healthcloud.services.healthRecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 实验室检验报告
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthJianYanDetailAPIEntity {

    private String item_name;
    private String item_value;
    private Integer item_status = 0;//0：正常 1：偏高，2：偏低
    private String refer_value;//参考值

}
