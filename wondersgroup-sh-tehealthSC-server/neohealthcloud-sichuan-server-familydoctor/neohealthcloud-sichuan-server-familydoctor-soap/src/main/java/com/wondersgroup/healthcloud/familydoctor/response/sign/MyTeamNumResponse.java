package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 个人统计接口（团队签约数、我所在团队数）
 * Created by jialing.yao on 2017-6-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyTeamNumResponse {
    private String doctorIdCard;//医生身份证号
    private int myTeamNum;//我所在团队数
    private int signNum;//团队签约数

    @JsonProperty("idCardNo")
    public void setDoctorIdCard(String doctorIdCard) {
        this.doctorIdCard = doctorIdCard;
    }

    @JsonProperty("myTeamNum")
    public void setMyTeamNum(int myTeamNum) {
        this.myTeamNum = myTeamNum;
    }

    @JsonProperty("myTeamContractNum")
    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

}
