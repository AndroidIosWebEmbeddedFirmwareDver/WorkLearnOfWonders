package com.wondersgroup.healthcloud.api.http.controllers.config;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.config.ServerConfig;
import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import com.wondersgroup.healthcloud.services.hospital.dto.DicCityInfoDTO;
import org.apache.commons.collections.map.HashedMap;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by dukuanxin on 2017/2/24.
 */
@RestController
@RequestMapping("/api/serverConfig")
public class ServerConfigController {

    @Autowired
    private ServerConfigService serverConfigServiceImpl;
    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/areaCode")
    public JsonResponseEntity areaCode(@RequestParam(required = false, defaultValue = "510100000000") String areaCode) {
        JsonResponseEntity response = new JsonResponseEntity();
        List<DicCityInfoDTO> dicCityInfoDTOs = hospitalService.getDicAreaByUpperCode("510000000000");
        Map<String, String> cityNames = new LinkedHashMap<>();
        for (DicCityInfoDTO infoDTO : dicCityInfoDTOs) {
            cityNames.put(infoDTO.getCityCode(), infoDTO.getCityName());
        }
        Map<String, List<DicCityInfoDTO>> subAreas = hospitalService.getDicAreaByUpperCodes(cityNames.keySet());

        List<DicCityInfoDTO> result = new ArrayList<>();
        for (List<DicCityInfoDTO> subAreaList : subAreas.values()) {
            for (DicCityInfoDTO cityInfoDTO : subAreaList) {
                String cityName = cityNames.containsKey(cityInfoDTO.getUpperCityCode()) ? cityNames.get(cityInfoDTO.getUpperCityCode()) + " - " : "";
                cityInfoDTO.setCityName(cityName + cityInfoDTO.getCityName());
                result.add(cityInfoDTO);
            }
        }
        response.setData(result);
        return response;
    }

    @GetMapping("/queryServerConfig")
    public JsonResponseEntity queryServerConfig(@RequestParam(required = false, defaultValue = "510000000000") String areaCode) {
        JsonResponseEntity response = new JsonResponseEntity();
        List<ServerConfig> serverConfigs = serverConfigServiceImpl.queryServerConfig(areaCode);
        response.setData(serverConfigs);
        return response;
    }

    @GetMapping("/queryArea")
    public JsonResponseEntity queryArea() {
        JsonResponseEntity response = new JsonResponseEntity();
        List<ServerConfig> serverConfigs = serverConfigServiceImpl.queryServerConfig();
        List<Map<String, Object>> areas = new ArrayList<>();
        for (ServerConfig config : serverConfigs) {
            Map<String, Object> data = new HashedMap();
            data.put("areaName", config.getAreaName());
            data.put("areaCode", config.getAreaCode());
            areas.add(data);
        }
        response.setData(areas);
        return response;
    }

    @PostMapping("/updateServerConfig")
    public JsonResponseEntity updateServerConfig(@RequestBody List<ServerConfig> serverConfigs) {
        JsonResponseEntity response = new JsonResponseEntity();

        for (ServerConfig config : serverConfigs) {
            if (config.getId() == null) {
                List<ServerConfig> list = serverConfigServiceImpl.queryServerConfig(config.getAreaCode());
                if (list.size() > 0) {
                    response.setCode(-1);
                    response.setMsg("已经添加");
                    return response;
                }
            }
        }

        serverConfigServiceImpl.updateServerConfig(serverConfigs);
        response.setMsg("添加成功");
        return response;
    }
}
