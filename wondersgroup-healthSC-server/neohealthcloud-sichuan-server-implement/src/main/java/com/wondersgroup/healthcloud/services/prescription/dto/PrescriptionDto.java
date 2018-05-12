package com.wondersgroup.healthcloud.services.prescription.dto;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by tanxueliang on 16/11/7.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionDto {

    private String cfhm; // 处方号码

    private String cflx; // 处方类型

    private String kfksdm; // 开方科室代码

    private String kfksmc; // 开方科室名称

    private String kfysbh; // 开方医生编号

    private String kfysxm; // 开方医生姓名

    private String kfsj; // 开方时间

    private String cfje; // 处方金额 单位:分

    private String zfzt; // 支付状态

    private String yljgdm; // 医疗机构代码

    private String yljgmc; // 医疗机构名称

    private String url;

    public String getKfsj() {
        if (StringUtils.isBlank(kfsj)) {
            return kfsj;
        }
        if (kfsj.length() < 10) {
            return kfsj;
        }
        return kfsj.substring(0, 10);
    }

    public String getCfje() {
        return PrescriptionDetailDto.fenToYuan(cfje);
    }


}
