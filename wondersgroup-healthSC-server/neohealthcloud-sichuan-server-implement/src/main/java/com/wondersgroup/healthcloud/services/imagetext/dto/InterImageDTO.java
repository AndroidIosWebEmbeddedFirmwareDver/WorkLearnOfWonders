package com.wondersgroup.healthcloud.services.imagetext.dto;

import com.wondersgroup.healthcloud.common.appenum.ImageTextEnum;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import lombok.Data;

@Data
public class InterImageDTO {

    public String description;
    public int location;// 广告对应的位置：1-个人诊所, 2-医学圈, 3-医生详情, 4-问答详情
    public String img_url;
    public String url;
    public boolean is_visable;
    public int create_time;
    public int update_time;
    public String update_by;

    public InterImageDTO(ImageText imageText) {
        this.description = imageText.getMainTitle();
        this.img_url = imageText.getImgUrl();
        this.url = imageText.getHoplink();
        this.is_visable = imageText.getDelFlag() == 0 ? true : false;
        if (ImageTextEnum.AD_HOME.getType().equals(imageText.getAdcode())) {
            this.location = 1;
        } else if (ImageTextEnum.AD_CIRCLE.getType().equals(imageText.getAdcode())) {
            this.location = 2;
        } else if (ImageTextEnum.AD_DOCTOR_DETAIL.getType().equals(imageText.getAdcode())) {
            this.location = 3;
        } else if (ImageTextEnum.AD_QA_DETAIL.getType().equals(imageText.getAdcode())) {
            this.location = 4;
        } else {
            this.is_visable = false;
        }
        this.update_by = "";
    }

}
