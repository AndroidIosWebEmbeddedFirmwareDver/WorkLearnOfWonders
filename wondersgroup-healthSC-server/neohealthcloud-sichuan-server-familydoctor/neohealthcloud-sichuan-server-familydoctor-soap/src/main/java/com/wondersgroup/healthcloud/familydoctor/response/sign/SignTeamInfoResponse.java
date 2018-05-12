package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 签约团队详情
 * Created by ys
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignTeamInfoResponse {

    private String teamId;
    private String teamName;
    private String orgCode;
    private String orgName;
    private String contractNum;//签约人数
    private String address;
    private String skilled;
    private String phone;

    private List<SignTeamPackResponse> packList;

    private List<SignTeamDoctorResponse> memberList;

}
