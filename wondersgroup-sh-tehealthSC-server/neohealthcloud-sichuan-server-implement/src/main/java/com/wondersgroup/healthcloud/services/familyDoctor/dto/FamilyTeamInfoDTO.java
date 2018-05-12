package com.wondersgroup.healthcloud.services.familyDoctor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamDoctorResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamInfoResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamPackResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ys on 17-6-6.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyTeamInfoDTO {
    private String thumb;//团队缩略图
    private String teamId;
    private String teamName;//团队名称
    private String leader;//组长
    private String orgName;//所属机构名称
    private String orgCode;//所属机构code
    private String signedCount;//签约人数
    private Integer isOnline;//是否在线
    private String skilled;//擅长
    private String address;//地址
    private String telephone;//电话

    private List<FamilyDoctorInfoDTO> memberList;//成员列表

    private List<FamilyTeamPackDTO> packList;//成员列表

    public FamilyTeamInfoDTO() {
    }

    public FamilyTeamInfoDTO(SignTeamInfoResponse signTeamInfoResponse) {
        this.teamId = signTeamInfoResponse.getTeamId();
        this.teamName = signTeamInfoResponse.getTeamName();
        this.orgCode = signTeamInfoResponse.getOrgCode();
        this.orgName = signTeamInfoResponse.getOrgName();
        this.signedCount = signTeamInfoResponse.getContractNum();
        this.skilled = signTeamInfoResponse.getSkilled();
        this.address = signTeamInfoResponse.getAddress();
        this.telephone = signTeamInfoResponse.getPhone();
        this.mergeMemberList(signTeamInfoResponse.getMemberList());
        this.mergePackList(signTeamInfoResponse.getPackList());
    }

    public void mergeMemberList(List<SignTeamDoctorResponse> doctorResponses) {
        if (null == doctorResponses || doctorResponses.isEmpty()) {
            return;
        }
        if (this.memberList == null) {
            this.memberList = new ArrayList<>();
        }
        for (SignTeamDoctorResponse doctorResponse : doctorResponses) {
            this.memberList.add(new FamilyDoctorInfoDTO(doctorResponse));
        }
    }

    public void mergePackList(List<SignTeamPackResponse> packResponses) {
        if (null == packResponses || packResponses.isEmpty()) {
            return;
        }
        if (this.packList == null) {
            this.packList = new ArrayList<>();
        }
        for (SignTeamPackResponse packResponse : packResponses) {
            this.packList.add(new FamilyTeamPackDTO(packResponse));
        }
    }

}
