package com.wondersgroup.healthcloud.services.administrative.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.administrative.Province;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceDTO {
    private String provinceId;
    private String provinceName;

    public ProvinceDTO() {
    }

    public ProvinceDTO(Province province) {
        this.provinceId = province.getProvinceId();
        this.provinceName = province.getProvinceName();
    }
}
