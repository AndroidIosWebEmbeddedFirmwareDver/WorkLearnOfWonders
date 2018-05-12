package com.wondersgroup.healthcloud.services.administrative.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.administrative.Area;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDTO {
    private String areaId;
    private String areaName;

    public AreaDTO() {
    }

    public AreaDTO(Area area) {
        this.areaId = area.getAreaId();
        this.areaName = area.getAreaName();
    }

}
