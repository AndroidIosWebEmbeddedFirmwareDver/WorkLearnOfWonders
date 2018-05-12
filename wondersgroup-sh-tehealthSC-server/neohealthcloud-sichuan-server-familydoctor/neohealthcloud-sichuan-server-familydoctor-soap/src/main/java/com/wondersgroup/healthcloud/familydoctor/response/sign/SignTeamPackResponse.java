package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 签约团队医生详情
 * Created by ys
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignTeamPackResponse {

    private String packId;
    private String orgCode;
    private String packName;
    private String packDesc;
    private String packTerm;//条款
    private String packFee;//服务包费用
    private String packBrief;//简介
    private String packApply;//使用人群

}
