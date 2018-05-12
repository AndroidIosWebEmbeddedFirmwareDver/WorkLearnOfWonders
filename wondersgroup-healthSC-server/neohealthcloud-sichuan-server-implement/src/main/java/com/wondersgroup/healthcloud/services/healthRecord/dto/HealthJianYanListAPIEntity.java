package com.wondersgroup.healthcloud.services.healthRecord.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 实验室检验报告
 */
@Data
public class HealthJianYanListAPIEntity {

    private String id;
    private String date;//检验日期
    private String view_url;//详情
    private String hospital_name = "未知";//医院名称
    private String item_name; // 检验项目
    private String department_name;//科室
    private List<HealthJianYanDetailAPIEntity> list;//报告列表

    public String getDate() {
        return StringUtils.length(date) > 10 ? date.substring(0, 10) : date;
    }
}
