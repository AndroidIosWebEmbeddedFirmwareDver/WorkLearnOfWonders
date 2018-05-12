package com.wondersgroup.healthcloud.services.evaluate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by longshasha on 16/11/2.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluateHospitalDTO {

    private Integer id;
    private String uid;
    private String mobile;
    private Integer hospitalId;
    private String hospitalName;
    private String content;
    private String status;
    private Integer isTop;
    private String createTime;

}
