package com.wondersgroup.healthcloud.solr.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.List;

@Data
@SolrDocument(solrCoreName = "sc_heathcloud_hospital")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolrHospital {

    @Id
    @Field
    private Integer id;

    @Field
    private String hospitalCode;

    @Field
    private String hospitalName;

    @Field
    private String hospitalAddress;

    @Field
    private String hospitalRule;

    @Field
    private String hospitalDesc;

    @Field
    private String hospitalPhone;

    @Field
    private String hospitalGrade;

    @Field
    private String hospitalLatitude;

    @Field
    private String hosptialLongitude;

    @Field
    private String hosptialPhoto;

    @Field
    private String cityCode;

    @Field
    private String orderMode;

    @Field
    private String isOrderToday;

    @Field
    private String status;

    @Field
    private Integer receiveCount;

    @Field
    private String delFlag;

    @Field
    private Date createTime;

    @Field
    private Date updateTime;

    @Field
    private List<String> text;

    @Field
    private List<String> ikText;

    private String receiveThumb; //缩略图

    private Integer hospitalId; //医院ID

    public String getReceiveThumb() {
        return hosptialPhoto;
    }

    public Integer getHospitalId() {
        return id;
    }

}
