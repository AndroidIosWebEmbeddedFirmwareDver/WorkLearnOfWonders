package com.wondersgroup.healthcloud.api.http.controllers.favorite;

import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.favorite.FavoriteDoctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.services.favorite.FavoriteService;
import com.wondersgroup.healthcloud.services.hospital.DepartmentService;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ys on 2016/11/3.
 * 医生收藏
 */
@RestController
@RequestMapping("/api/favorite/doctor")
public class DoctorFavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    @VersionRange
    public JsonListResponseEntity<DoctorInfoDTO> list(@RequestParam String uid, @RequestParam(required = false) String area,
                                                      @RequestParam(required = false, defaultValue = "1") Integer flag) {
        JsonListResponseEntity<DoctorInfoDTO> response = new JsonListResponseEntity<>();
        List<Doctor> doctors = favoriteService.getFavoriteDoctors(uid, flag, 10);
        List<DoctorInfoDTO> doctorInfo = departmentService.getDoctorInfo(doctors, area);
        Boolean hasMore = false;
        if (doctorInfo != null && doctorInfo.size() > 10) {
            doctorInfo = doctorInfo.subList(0, 10);
            hasMore = true;
        }
        response.setContent(doctorInfo, hasMore, null, String.valueOf(flag + 1));
        return response;
    }

    @Transactional
    @VersionRange
    @RequestMapping(value = "/addDel", method = RequestMethod.POST)
    public JsonResponseEntity<String> addDel(@RequestBody String request) {

        JsonResponseEntity<String> body = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer doctorId = reader.readInteger("doctorId", false);
        String uid = reader.readString("uid", false);

        FavoriteDoctor favoriteDoctor = favoriteService.getFavorDoctor(uid, doctorId);
        if (null != favoriteDoctor) {
            favoriteService.getFavoriteDoctorRep().delete(favoriteDoctor);
            body.setMsg("取消关注成功");
        } else {
            Doctor doctor = doctorService.getInfoById(doctorId);
            if (null == doctor) {
                throw new CommonException(2021, "医生无效");
            }
            favoriteDoctor = new FavoriteDoctor();
            favoriteDoctor.setDocId(doctorId);
            favoriteDoctor.setUserId(uid);
            favoriteDoctor.setUpdateTime(new Date());
            favoriteService.getFavoriteDoctorRep().save(favoriteDoctor);
            body.setMsg("添加关注成功");
        }

        return body;
    }
}
