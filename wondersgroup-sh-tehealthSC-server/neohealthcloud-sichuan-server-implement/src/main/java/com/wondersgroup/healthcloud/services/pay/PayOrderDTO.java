package com.wondersgroup.healthcloud.services.pay;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import lombok.Data;


/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 04/11/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PayOrderDTO {

    public String id;
    public Long amount;
    public String subject;
    public String body;
    public String description;
    public PayOrder.Status status;
    public String channel;
    @JsonProperty("time_left")
    public Integer timeLeft;
    @JsonProperty("hospital_code")
    public String hospitalCode;
    public String appid;
    public String submerno;//链支付子商户号

    public PayOrderDTO() {

    }

    public PayOrderDTO(PayOrder payOrder) {
        this.id = payOrder.getId();
        this.amount = payOrder.getAmount();
        this.subject = payOrder.getSubject();
        this.body = payOrder.getBody();
        this.description = payOrder.getDescription();
        this.status = payOrder.getStatus();
        this.channel = payOrder.getChannel();
        try {
            JsonNode node = JsonConverter.toJsonNode(payOrder.getBusiness());
            this.hospitalCode = node.get("hospitalCode").asText();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (payOrder.getDueTime() != null) {
            timeLeft = (int) (payOrder.getDueTime().getTime() - System.currentTimeMillis()) / 1000;
            if (timeLeft <= 0 && PayOrder.Status.NOTPAY.equals(this.status)) {
                this.status = PayOrder.Status.EXPIRED;
            }
        }
    }
}
