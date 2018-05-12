package com.wondersgroup.healthcloud.api.http.controllers.favorite;

import com.wondersgroup.healthcloud.api.http.dto.hospital.HospitalDTO;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.favorite.FavoriteHospital;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.favorite.FavoriteService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by ys on 2016/11/3.
 * 医院 医生收藏
 */
@RestController
@RequestMapping("/api/favorite/hospital")
public class HospitalFavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    @VersionRange
    public JsonListResponseEntity<HospitalDTO> hospitalList(@RequestParam String uid,
                                                            @RequestParam(required = false, defaultValue = "1") Integer flag) {
        JsonListResponseEntity<HospitalDTO> response = new JsonListResponseEntity<>();
        List<Hospital> hospitals = favoriteService.getFavoriteHospitals(uid, flag, 10);
        List<HospitalDTO> hospitalApis = getHospitals(hospitals);
        Boolean hasMore = false;
        if (hospitals != null && hospitals.size() > 10) {
            hospitalApis = hospitalApis.subList(0, 10);
            hasMore = true;
        }
        response.setContent(hospitalApis, hasMore, null, String.valueOf(flag + 1));
        return response;
    }

    @Transactional
    @VersionRange
    @RequestMapping(value = "/addDel", method = RequestMethod.POST)
    public JsonResponseEntity<String> addArticleFavorite(@RequestBody String request) {

        JsonResponseEntity<String> body = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer hospitalId = reader.readInteger("hospitalId", false);
        String uid = reader.readString("uid", false);
        FavoriteHospital favoriteHospital = favoriteService.getFavorHospital(uid, hospitalId);
        if (null != favoriteHospital) {
            favoriteService.getFavoriteHospitalRep().delete(favoriteHospital);
            body.setMsg("取消关注成功");
        } else {
            Hospital hospital = hospitalService.queryById(hospitalId);
            if (null == hospital) {
                throw new CommonException(2021, "医院无效");
            }
            favoriteHospital = new FavoriteHospital();
            favoriteHospital.setHosId(hospitalId);
            favoriteHospital.setUserId(uid);
            favoriteHospital.setUpdateTime(new Date());
            favoriteService.getFavoriteHospitalRep().save(favoriteHospital);
            body.setMsg("添加关注成功");
        }

        return body;
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
