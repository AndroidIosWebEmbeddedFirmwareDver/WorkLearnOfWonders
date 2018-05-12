package com.wondersgroup.healthcloud.services.imagetext.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/9/1.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageTextPositionDTO {
    private String imgUrl;
    private String hoplink;
    private String position;

    public ImageTextPositionDTO(ImageText imageText) {
        this.imgUrl = imageText.getImgUrl();
        this.hoplink = imageText.getHoplink();
        this.position = imageText.getPosition();
    }
}