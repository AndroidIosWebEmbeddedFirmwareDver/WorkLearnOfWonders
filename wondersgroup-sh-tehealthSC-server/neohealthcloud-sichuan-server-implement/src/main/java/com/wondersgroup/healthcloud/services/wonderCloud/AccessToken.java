package com.wondersgroup.healthcloud.services.wonderCloud;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by longshasha on 16/5/11.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessToken {
    private AccessTokenStatus status;
    private String uid;
    private String token;
    private String key;

    private Boolean isValid;//患者是否是实名认证的

    @JsonProperty("user_type")
    private String userType;// "0" for doctor, "1" for patient

    public AccessToken() {

    }

    public AccessToken(AccessTokenStatus status, String uid, String token, String key, String userType) {
        this.status = status;
        this.uid = uid;
        this.token = token;
        this.key = key;
        this.userType = userType;
    }

    public AccessToken(String session, JsonNode jsonNode) {
        this.token = session;
        this.status = AccessTokenStatus.TOKEN_OK;
        this.uid = jsonNode.has("userid") ? jsonNode.get("userid").asText() : null;
        this.isValid = "true".equals(jsonNode.get("isValid").asText());
        this.key = jsonNode.has("key") ? jsonNode.get("key").asText() : "";
        this.userType = jsonNode.has("type") ? jsonNode.get("type").asText() : "";
    }

    public AccessTokenStatus getStatus() {
        return status;
    }

    public void setStatus(AccessTokenStatus status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
