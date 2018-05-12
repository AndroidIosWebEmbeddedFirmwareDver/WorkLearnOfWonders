package com.wondersgroup.healthcloud.api.http.controllers.hospital;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.api.http.dto.hospital.HospitalDTO;
import com.wondersgroup.healthcloud.api.http.dto.hospital.HospitalNear;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.hospital.dto.DicCityInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 附近就医
 * Created by zhuchunliu on 2016/11/7.
 */
@RestController
@RequestMapping("/api/hospital/near")
public class HospitalNearController {

    @Autowired
    private HospitalService hospitalService;

    private static double EARTH_RADIUS = 6378.137;

    public static void main(String[] args) {
        String city = "上海市a";
        System.out.println(StringUtils.stripEnd(city, "市"));
    }

    @GetMapping("/city")
    @VersionRange
    @WithoutToken
    public JsonResponseEntity city() {
        List<DicCityInfoDTO> list = hospitalService.getDicAreaByUpperCode("510000000000");
        for (DicCityInfoDTO infoDTO : list) {
            if (StringUtils.isNotEmpty(infoDTO.getCityName())) {
                infoDTO.setCityName(StringUtils.stripEnd(infoDTO.getCityName(), "市"));
            }
        }
        return new JsonResponseEntity(0, null, list);
    }

    /**
     * @param longitude    经度
     * @param latitude     纬度
     * @param hospitalName 医院名称
     * @param flag         页码
     * @return
     */
    @GetMapping("/list")
    @VersionRange
    @WithoutToken
    public JsonListResponseEntity list(
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) String hospitalName,
            @RequestParam(required = false, defaultValue = "510100000000") String cityCode,
            @RequestParam(required = false, defaultValue = "1") String flag) {
        JsonListResponseEntity response = new JsonListResponseEntity();
        int pageNo = Integer.valueOf(flag);
        int pageSize = 3;
        List<Hospital> resource = hospitalService.queryNearHospital(cityCode, longitude, latitude, hospitalName, 10, pageNo, pageSize);
        if (null == resource || 0 == resource.size()) {
            response.setContent(null, false, null, flag);
            return response;
        }

        List<HospitalNear> list = Lists.newArrayList();
        for (Hospital hospital : resource) {
            list.add(new HospitalNear(hospital));
        }
        if (null != longitude && null != latitude) {
            for (HospitalNear hospitalNear : list) {
                hospitalNear.setRange(this.getDistance(hospitalNear.getHospitalLatitude(), hospitalNear.getHospitalLongitude(), latitude, longitude));
            }
            Collections.sort(list, new Comparator<HospitalNear>() {
                @Override
                public int compare(HospitalNear o1, HospitalNear o2) {
                    if (o1.getRange() > o2.getRange()) return 1;
                    if (o1.getRange() < o2.getRange()) return -1;
                    return 0;
                }
            });
        }

        Boolean hasMore = false;
        if (null != longitude && null != longitude) {
            if (pageNo * pageSize < list.size()) {
                hasMore = true;
                flag = String.valueOf(pageNo + 1);
            }
            list = list.subList((pageNo - 1) * pageSize, pageNo * pageSize < list.size() ? pageNo * pageSize : list.size());

        } else {
            if (resource.size() > pageSize) {
                list = list.subList(0, pageSize);
                hasMore = true;
                flag = String.valueOf(pageNo + 1);
            }
        }

        response.setContent(list, hasMore, null, flag);
        return response;
    }


    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private double getDistance(double lat1, double lng1, double lat2,
                               double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

}
