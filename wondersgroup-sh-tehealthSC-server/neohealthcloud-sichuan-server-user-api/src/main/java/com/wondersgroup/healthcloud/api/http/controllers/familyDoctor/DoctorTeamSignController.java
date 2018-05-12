package com.wondersgroup.healthcloud.api.http.controllers.familyDoctor;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.familyDoctor.FamilyDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ys on 17-6-6.
 * 家庭医生团队签约
 */
@RestController
@RequestMapping("/api/family/doctor/")
public class DoctorTeamSignController {

    @Autowired
    private FamilyDoctorService familyDoctorService;

    /**
     * 家庭医生团队签约
     */
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Map<String, Object>> sign(@RequestBody String request) {
        JsonResponseEntity<Map<String, Object>> entity = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        String uid = reader.readString("uid", false);
        String teamId = reader.readString("teamId", false);
        familyDoctorService.signFamilyTeam(uid, teamId);
        entity.setMsg("签约成功!");
        return entity;
    }


}
