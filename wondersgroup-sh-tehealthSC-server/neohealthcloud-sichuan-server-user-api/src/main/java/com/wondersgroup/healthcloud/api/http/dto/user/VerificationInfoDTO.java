package com.wondersgroup.healthcloud.api.http.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.wondersgroup.healthcloud.jpa.entity.user.Verification;
import com.wondersgroup.healthcloud.utils.IdcardUtils;

/**
 * Created by longshasha on 16/5/13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationInfoDTO {

    public static final String[] statusArray = {"未提交", "认证失败", "审核中", "认证成功"};

    private String uid;
    private Boolean success;
    @JsonProperty("can_submit")
    private Boolean canSubmit;
    private Integer status;
    private String statusSpec;
    private String msg;
    private String name;
    private String idcard;

    public VerificationInfoDTO() {

    }

    /**
     * erificationLevel -2,-1,0,1 对应 status 0,1,2,3
     * <p>
     * status 0-未提交,1-认证失败,2-审核中,3-认证成功
     *
     * @param uid
     * @param info
     */
    public VerificationInfoDTO(String uid, Verification info) {
        this.uid = uid;
        if (info == null) {
            this.status = 0;
            this.canSubmit = true;
            this.statusSpec = statusArray[0];
        } else {
            int status = info.getVerificationLevel();
            this.status = info.getVerificationLevel() + 2;
            this.statusSpec = statusArray[this.status];
            this.success = status == 1;
            this.canSubmit = status == -1;
            this.name = IdcardUtils.maskName(info.getName());
            this.idcard = IdcardUtils.maskIdcard(info.getIdcard());
            this.msg = info.getRefusal_reason();
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getCanSubmit() {
        return canSubmit;
    }

    public void setCanSubmit(Boolean canSubmit) {
        this.canSubmit = canSubmit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusSpec() {
        return statusSpec;
    }

    public void setStatusSpec(String statusSpec) {
        this.statusSpec = statusSpec;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
