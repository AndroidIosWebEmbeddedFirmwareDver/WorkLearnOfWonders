package com.wondersgroup.healthcloud.services.imagetext.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/6/13.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicImageTextDTO {
    private String imgUrl;
    private String hoplink;

    public BasicImageTextDTO(ImageText imageText) {
        this.imgUrl = imageText.getImgUrl();
        this.hoplink = imageText.getHoplink();
    }
}
