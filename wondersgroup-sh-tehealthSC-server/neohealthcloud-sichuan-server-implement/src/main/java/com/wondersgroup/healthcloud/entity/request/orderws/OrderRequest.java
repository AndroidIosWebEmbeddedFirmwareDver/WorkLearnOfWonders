package com.wondersgroup.healthcloud.entity.request.orderws;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
@XmlRootElement(name = "request")
public class OrderRequest {

    @XmlElement(name = "msgHeader")
    public MsgHeader msgHeader;

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "workInfo")
    public List<WorkInfo> list;

    @XmlRootElement
    public static class MsgHeader {
        @XmlElement(required = true)
        private String token;
        @XmlElement(required = true)
        private String timeStamp;
        @XmlElement(required = true)
        private String sign;
    }

    @XmlRootElement
    public static class WorkInfo {
        @XmlElement(required = true)
        private String orderId;
        @XmlElement(required = true)
        private String hosOrgCode;
        @XmlElement(required = true)
        private String workFlowId;
        @XmlElement(required = true)
        private String currWorkDate;
        @XmlElement(required = true)
        private String desc;
        @XmlElement(required = true)
        private String orderStatus;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getHosOrgCode() {
            return hosOrgCode;
        }

        public void setHosOrgCode(String hosOrgCode) {
            this.hosOrgCode = hosOrgCode;
        }

        public String getWorkFlowId() {
            return workFlowId;
        }

        public void setWorkFlowId(String workFlowId) {
            this.workFlowId = workFlowId;
        }

        public String getCurrWorkDate() {
            return currWorkDate;
        }

        public void setCurrWorkDate(String currWorkDate) {
            this.currWorkDate = currWorkDate;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
