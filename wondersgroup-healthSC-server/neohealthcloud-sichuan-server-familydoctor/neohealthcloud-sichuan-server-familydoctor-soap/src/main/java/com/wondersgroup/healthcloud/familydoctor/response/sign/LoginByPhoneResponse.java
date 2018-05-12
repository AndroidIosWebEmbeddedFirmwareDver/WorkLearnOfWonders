package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by jialing.yao on 2017-6-26.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginByPhoneResponse {
    private String userId;
    private String name;
    private String idCardNo;
    private String orgCode;
    private String orgName;
    private String deptCode;
    private String deptName;
    private String titleCode;
    private String titleName;
    private String memberSex;
}
