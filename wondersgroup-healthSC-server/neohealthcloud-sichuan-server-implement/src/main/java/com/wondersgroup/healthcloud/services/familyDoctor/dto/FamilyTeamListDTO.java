package com.wondersgroup.healthcloud.services.familyDoctor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamListResponse;
import lombok.Data;

/**
 * Created by ys on 17-6-6.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyTeamListDTO {
    private String thumb;//团队缩略图
    private String teamId;
    private String teamName;//团队名称
    private String leader;//组长
    private String orgName;//所属机构名称
    private String orgCode;//所属机构code
    private String signedCount;//签约人数
    private Integer isOnline = 0;//是否在线

    public FamilyTeamListDTO() {
    }

    public FamilyTeamListDTO(SignTeamListResponse.TeamListEntity teamListEntity) {
        this.teamId = teamListEntity.getTeamId();
        this.teamName = teamListEntity.getTeamName();
        this.leader = teamListEntity.getLeader();
        this.orgCode = teamListEntity.getOrgCode();
        this.orgName = teamListEntity.getOrgName();
        this.signedCount = teamListEntity.getMemberNum();
    }
}
