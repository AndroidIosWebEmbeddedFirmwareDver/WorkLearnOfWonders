package com.wondersgroup.healthcloud.services.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ys on 2017/04/15.
 * 城市字典表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DicCityInfoDTO implements Serializable {

    private String cityCode;
    private String cityName;
    private Integer level;
    private String upperCityCode;
}
