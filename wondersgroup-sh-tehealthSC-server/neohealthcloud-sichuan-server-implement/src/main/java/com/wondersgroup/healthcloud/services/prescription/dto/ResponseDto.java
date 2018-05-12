package com.wondersgroup.healthcloud.services.prescription.dto;

import lombok.Data;

/**
 * Created by tanxueliang on 16/11/7.
 */
@Data
public class ResponseDto {

    private String resultcode; //处理结果代码:0-成功

    private String resultmessage; //处理结果描述

    private String yljgdm; //医疗机构代码

    private String yljgmc; //医疗机构名称

}
