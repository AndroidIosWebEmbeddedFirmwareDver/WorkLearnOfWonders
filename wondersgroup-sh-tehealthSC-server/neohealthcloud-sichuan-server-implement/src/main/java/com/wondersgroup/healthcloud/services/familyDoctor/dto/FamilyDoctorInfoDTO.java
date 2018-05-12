package com.wondersgroup.healthcloud.services.familyDoctor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamDoctorResponse;
import lombok.Data;

/**
 * Created by ys on 17-6-6.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDoctorInfoDTO {
    private String avator;//头像
    private String doctorId;
    private String doctorName;
    private String teamId;
    private String teamName;
    private Integer isLeader;//是否为组长1:是0:不是
    private String orgName;//所属机构名称
    private String orgCode;//所属机构code
    private String dept;//所属科室
    private String title;//职称
    private String mainJob;//主要工作
    private String address;//地址

    public FamilyDoctorInfoDTO() {
    }

    public FamilyDoctorInfoDTO(SignTeamDoctorResponse doctorResponse) {
        this.doctorId = doctorResponse.getMemberId();
        this.doctorName = doctorResponse.getMemberName();
        this.teamName = doctorResponse.getMemberId();
        this.orgCode = doctorResponse.getMemberOrg();
        this.orgName = doctorResponse.getMemberOrg();
        this.dept = doctorResponse.getMemberDept();
        this.title = doctorResponse.getMemberTitle();
        this.mainJob = doctorResponse.getMemberMainJob();
        this.address = doctorResponse.getAddress();
    }

}
