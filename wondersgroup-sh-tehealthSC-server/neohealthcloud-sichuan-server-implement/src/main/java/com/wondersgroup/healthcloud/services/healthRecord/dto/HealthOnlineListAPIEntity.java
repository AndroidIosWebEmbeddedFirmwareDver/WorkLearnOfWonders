package com.wondersgroup.healthcloud.services.healthRecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 提取报告-检验检查列表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthOnlineListAPIEntity {

    private String id;
    private String date;//检验日期
    private String view_url;//详情
    private String hospital_name;//医院名称
    private String item_name; // 检验项目
    private String department_name;//科室

    public String getDate() {
        return StringUtils.length(date) > 10 ? date.substring(0, 10) : date;
    }
}
