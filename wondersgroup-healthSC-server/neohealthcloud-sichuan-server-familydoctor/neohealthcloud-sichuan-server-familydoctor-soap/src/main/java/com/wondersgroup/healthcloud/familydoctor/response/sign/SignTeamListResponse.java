package com.wondersgroup.healthcloud.familydoctor.response.sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 签约团队列表
 * Created by ys
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignTeamListResponse {

    private Integer firstResult;
    private Integer totalCount;
    private Integer pageNo;
    private Integer pageSize;
    private Integer maxResults;
    private List<TeamListEntity> list;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TeamListEntity {
        private String teamName;
        private String orgCode;
        private String orgName;
        private String leader;
        private String teamAddress;
        private String memberNum;
        private String teamId;
        private String leaderName;
    }

/*    @JsonProperty("idCardNo")
    public void setDoctorIdCard(String doctorIdCard) {
        this.doctorIdCard = doctorIdCard;
    }*/


}
