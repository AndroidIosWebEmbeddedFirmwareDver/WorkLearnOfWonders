package com.wondersgroup.healthcloud.services.administrative.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.administrative.City;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO {
    private String cityId;
    private String cityName;

    public CityDTO() {
    }

    public CityDTO(City city) {
        this.cityId = city.getCityId();
        this.cityName = city.getCityName();
    }
}
