package com.wondersgroup.healthcloud.api.http.controllers.hospital;

import com.wondersgroup.healthcloud.api.http.dto.hospital.HospitalDTO;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dukuanxin on 2017/3/6.
 */
@RestController
@RequestMapping("/api/area")
public class SLHospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/queryHospitls")
    @VersionRange
    public JsonListResponseEntity<HospitalDTO> queryHospitls(@RequestParam String cityCode, @RequestParam(required = false, defaultValue = "0") String flag) {
        JsonListResponseEntity<HospitalDTO> response = new JsonListResponseEntity<>();
        int pageNo = Integer.valueOf(flag);
        int pageSize = 10;
        List<Hospital> hospitals = hospitalService.queryAreaHospital(cityCode, pageNo * pageSize, pageSize + 1);
        List<HospitalDTO> hospitalApis = getHospitals(hospitals);
        Boolean hasMore = false;
        if (hospitals != null && hospitals.size() > 10) {
            hospitalApis = hospitalApis.subList(0, 10);
            hasMore = true;
        } else {
            flag = null;
        }
        if (hasMore) {
            flag = String.valueOf(pageNo + 1);
        }
        response.setContent(hospitalApis, hasMore, null, flag);
        return response;
    }


    List<HospitalDTO> getHospitals(List<Hospital> hospitals) {
        if (null == hospitals || hospitals.size() == 0) {
            return null;
        }
        List<HospitalDTO> hospitalDTOs = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            hospitalDTOs.add(new HospitalDTO(hospital));
        }
        return hospitalDTOs;
    }


}
