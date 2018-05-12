package com.wondersgroup.healthcloud.services.prescription.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by tanxueliang on 16/11/7.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionDetailDto {

    private String cfmxbh; // 处方明细编号
    private String xmmc; // 项目名称
    private String xmfl; // 项目分类
    private String xmgg; // 项目规格
    private String xmdw; // 项目单位
    private String xmsl; // 项目数量
    private String xmyf; // 项目用法
    private String xmpl; // 项目频率
    private String xmdj; // 项目单价 单位:分
    private String xmje; // 项目金额 单位:分

    public String getXmdj() {
        return fenToYuan(xmdj);
    }

    public String getXmje() {
        return fenToYuan(xmje);
    }

    public String getXmsl() {
        try {
            DecimalFormat format = new DecimalFormat("#.00");
            xmsl = format.format(Double.valueOf(xmsl));
        } catch (Exception e) {
        }
        return xmsl;
    }

    public static String fenToYuan(String fen) {
        if (StringUtils.isBlank(fen)) {
            return "0";
        }

        BigDecimal var = new BigDecimal(fen);
        BigDecimal var1 = new BigDecimal(100);
        return var.divide(var1, 2, RoundingMode.CEILING).toString();
    }


}
