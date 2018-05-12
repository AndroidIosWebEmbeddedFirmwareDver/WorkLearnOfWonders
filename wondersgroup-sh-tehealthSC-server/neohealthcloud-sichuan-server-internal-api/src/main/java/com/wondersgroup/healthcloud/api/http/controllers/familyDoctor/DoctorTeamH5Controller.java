package com.wondersgroup.healthcloud.api.http.controllers.familyDoctor;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.services.familyDoctor.FamilyDoctorService;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyDoctorInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ys on 17-6-27.
 * 家庭医生
 */
@RestController
@RequestMapping("/api/family/doctor/")
public class DoctorTeamH5Controller {


    @Autowired
    private FamilyDoctorService familyDoctorService;

    /**
     * 家庭医生团队详情
     */
    @RequestMapping(value = "teamInfo")
    public JsonResponseEntity teamInfo(@RequestParam String uid, @RequestParam String teamId) {
        JsonResponseEntity<FamilyTeamInfoDTO> entity = new JsonResponseEntity();
        FamilyTeamInfoDTO familyTeamInfoDTO = familyDoctorService.getFamilyTeamInfo(uid, teamId);
        entity.setData(familyTeamInfoDTO);
        return entity;
    }

    /**
     * 家庭医生团队详情
     */
    @RequestMapping(value = "doctorInfo")
    public JsonResponseEntity<FamilyDoctorInfoDTO> doctorInfo(@RequestParam String teamId, @RequestParam String doctorId) {
        JsonResponseEntity<FamilyDoctorInfoDTO> entity = new JsonResponseEntity();
        FamilyDoctorInfoDTO familyDoctorInfo = familyDoctorService.getFamilyDoctorInfo(teamId, doctorId, "");
        entity.setData(familyDoctorInfo);
        return entity;
    }

}
