package com.wondersgroup.healthcloud.services.wonderCloud;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by longshasha on 16/8/2.
 */
public class WondersUser {
    public String userId;
    public String username;
    public String email;
    public String type;
    public String mobile;
    public boolean isVerified;
    public String name;
    public String idCard;
    public String ssCard;

    public String tagid;
    public Integer channelType;

    public WondersUser() {
    }

    public WondersUser(JsonNode wu) {
        this.userId = wu.get("userid").asText();
        this.username = wu.get("username").isNull() ? null : wu.get("username").asText();
        this.email = wu.get("email").isNull() ? null : wu.get("email").asText();
        this.type = wu.get("type").isNull() ? null : wu.get("type").asText();
        this.mobile = wu.get("mobile").isNull() ? null : wu.get("mobile").asText();
        this.isVerified = !wu.get("realvalid").isNull() && wu.get("realvalid").asInt() == 1;
        this.name = wu.get("name").isNull() ? null : wu.get("name").asText();
        this.idCard = wu.get("idcard").isNull() ? null : wu.get("idcard").asText();
        this.ssCard = wu.get("sscard").isNull() ? null : wu.get("sscard").asText();
    }

    public WondersUser(JsonNode wu, int channelType) {
        this.userId = wu.get("userid").asText();
        this.username = wu.get("username").isNull() ? null : wu.get("username").asText();
        this.email = wu.get("email").isNull() ? null : wu.get("email").asText();
        this.type = wu.get("type").isNull() ? null : wu.get("type").asText();
        this.mobile = wu.get("mobile").isNull() ? null : wu.get("mobile").asText();
        this.isVerified = !wu.get("realvalid").isNull() && wu.get("realvalid").asInt() == 1;
        this.name = wu.get("name").isNull() ? null : wu.get("name").asText();
        this.idCard = wu.get("idcard").isNull() ? null : wu.get("idcard").asText();
        this.ssCard = wu.get("sscard").isNull() ? null : wu.get("sscard").asText();
        JsonNode tagNode = wu.get("tagid") == null ? null : wu.get("tagid");
        if (tagNode != null) {
            this.tagid = tagNode.asText();
        }
        this.channelType = channelType;
    }
}
