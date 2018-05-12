package com.wondersgroup.healthcloud.api.http.controllers.admin.administrative;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.administrative.Area;
import com.wondersgroup.healthcloud.jpa.entity.administrative.City;
import com.wondersgroup.healthcloud.jpa.entity.administrative.Province;
import com.wondersgroup.healthcloud.jpa.repository.administrative.AreaRepository;
import com.wondersgroup.healthcloud.jpa.repository.administrative.CityRepository;
import com.wondersgroup.healthcloud.jpa.repository.administrative.ProvinceRepository;
import com.wondersgroup.healthcloud.services.administrative.dto.AreaDTO;
import com.wondersgroup.healthcloud.services.administrative.dto.CityDTO;
import com.wondersgroup.healthcloud.services.administrative.dto.ProvinceDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@RestController
@RequestMapping("/api/administrative")
@Admin
public class AdministrativeController {

    private static final Logger log = Logger.getLogger(AdministrativeController.class);

    @Autowired
    private ProvinceRepository provinceRepo;

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private AreaRepository areaRepo;

    @RequestMapping(value = "/findAllProvince", method = RequestMethod.GET)
    public JsonResponseEntity<List<ProvinceDTO>> findAllProvince() {
        JsonResponseEntity<List<ProvinceDTO>> result = new JsonResponseEntity<>();
        try {
            List<Province> provinceList = provinceRepo.findAllProvince();
            if (provinceList != null && provinceList.size() > 0) {
                List<ProvinceDTO> provinceDTOList = new ArrayList<>();
                for (Province province : provinceList) {
                    provinceDTOList.add(new ProvinceDTO(province));
                }
                result.setData(provinceDTOList);
            }
        } catch (Exception ex) {
            log.error("AdministrativeController.findAllProvince error --> " + ex.getLocalizedMessage());
            result.setCode(1000);
            result.setMsg("查询数据失败！");
        }
        return result;
    }

    @RequestMapping(value = "/findCityByProvinceId", method = RequestMethod.GET)
    public JsonResponseEntity<List<CityDTO>> findCityByProvinceId(@RequestParam String provinceId) {
        JsonResponseEntity<List<CityDTO>> result = new JsonResponseEntity<>();
        try {
            List<City> cityList = cityRepo.findCityByProvinceId(provinceId);
            if (cityList != null && cityList.size() > 0) {
                List<CityDTO> cityDTOList = new ArrayList<>();
                for (City city : cityList) {
                    cityDTOList.add(new CityDTO(city));
                }
                result.setData(cityDTOList);
            }
        } catch (Exception ex) {
            log.error("AdministrativeController.findCityByProvinceId error --> " + ex.getLocalizedMessage());
            result.setCode(1000);
            result.setMsg("查询数据失败！");
        }
        return result;
    }

    @RequestMapping(value = "/findAllCity", method = RequestMethod.GET)
    public JsonResponseEntity<List<CityDTO>> findAllCity() {
        JsonResponseEntity<List<CityDTO>> result = new JsonResponseEntity<>();
        try {
            List<City> cityList = cityRepo.findAllCity();
            if (cityList != null && cityList.size() > 0) {
                List<CityDTO> cityDTOList = new ArrayList<>();
                for (City city : cityList) {
                    cityDTOList.add(new CityDTO(city));
                }
                result.setData(cityDTOList);
            }
        } catch (Exception ex) {
            log.error("AdministrativeController.findAllCity error --> " + ex.getLocalizedMessage());
            result.setCode(1000);
            result.setMsg("查询数据失败！");
        }
        return result;
    }

    @RequestMapping(value = "/findAreaByCityId", method = RequestMethod.GET)
    public JsonResponseEntity<List<AreaDTO>> findAreaByCityId(@RequestParam String cityId) {
        JsonResponseEntity<List<AreaDTO>> result = new JsonResponseEntity<>();
        try {
            List<Area> areaList = areaRepo.findAreaByCityId(cityId);
            if (areaList != null && areaList.size() > 0) {
                List<AreaDTO> areaDTOList = new ArrayList<>();
                for (Area area : areaList) {
                    areaDTOList.add(new AreaDTO(area));
                }
                result.setData(areaDTOList);
            }
        } catch (Exception ex) {
            log.error("AdministrativeController.findAreaByCityId error --> " + ex.getLocalizedMessage());
            result.setCode(1000);
            result.setMsg("查询数据失败！");
        }
        return result;
    }
}
