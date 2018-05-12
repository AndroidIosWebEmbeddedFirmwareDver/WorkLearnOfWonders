package com.wondersgroup.healthcloud.services.imagetext.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/8/22.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadingImageDTO {

    private String imgUrl;
    private String hoplink;
    private int duration;
    private Boolean isSkip;
    private Boolean isShow;

    public LoadingImageDTO() {
    }

    public LoadingImageDTO(ImageText imageText) {
        this.imgUrl = imageText.getImgUrl();
        this.hoplink = imageText.getHoplink();
        this.duration = imageText.getDurations();
        this.isSkip = imageText.getAllowClose() == 1 ? true : false;
        this.isShow = imageText.getDelFlag() == 0 ? true : false;
    }
}
