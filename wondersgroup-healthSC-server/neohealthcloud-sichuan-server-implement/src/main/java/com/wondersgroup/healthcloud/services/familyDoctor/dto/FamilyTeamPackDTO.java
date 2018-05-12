package com.wondersgroup.healthcloud.services.familyDoctor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamPackResponse;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 签约团队医生详情
 * Created by ys
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FamilyTeamPackDTO {

    private String packId;
    private String orgCode;
    private String packName;
    private String packDesc;
    private String packTerm;//条款
    private String packFee;//服务包费用
    private String packBrief;//简介
    private String packApply;//使用人群

    public FamilyTeamPackDTO() {
    }

    public FamilyTeamPackDTO(SignTeamPackResponse packResponse) {
        this.packId = packResponse.getPackId();
        this.packName = packResponse.getPackName();
        this.packDesc = packResponse.getPackDesc();
        this.orgCode = packResponse.getOrgCode();
        this.packApply = packResponse.getPackApply();
        this.packFee = packResponse.getPackFee();
        this.packBrief = packResponse.getPackBrief();
        this.packApply = packResponse.getPackApply();
    }
}
