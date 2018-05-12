package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 签约团队医生详情
 * Created by ys
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignTeamDoctorResponse {

    private String memberId;
    private String memberIdCardNo;
    private String memberNo;
    private String memberName;
    private String memberMainJob;
    private String memberOrg;
    private String memberDept;
    private String memberTitle;
    private String address;

}
