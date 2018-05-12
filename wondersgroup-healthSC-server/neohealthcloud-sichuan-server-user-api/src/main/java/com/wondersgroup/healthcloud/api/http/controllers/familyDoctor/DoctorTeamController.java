package com.wondersgroup.healthcloud.api.http.controllers.familyDoctor;

import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.familyDoctor.FamilyDoctorService;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ys on 17-6-6.
 * 家庭医生
 */
@RestController
@RequestMapping("/api/family/doctor/")
public class DoctorTeamController {

    @Autowired
    private FamilyDoctorService familyDoctorService;

    private final static double TEAM_QUERY_DISTANCE = 5.0;
    private final static int TEAM_LIST_PAGE_SIZE = 10;

    /**
     * 家庭医生团队列表
     */
    @RequestMapping(value = "teamList")
    @VersionRange
    @WithoutToken
    public JsonListResponseEntity<FamilyTeamListDTO> teamList(@RequestParam String lat, @RequestParam String lng,
                                                              @RequestParam(required = false, defaultValue = "1") Integer flag) {
        JsonListResponseEntity<FamilyTeamListDTO> entity = new JsonListResponseEntity<>();
        List<FamilyTeamListDTO> listDTOs = familyDoctorService.getFamilyDoctorList(lat, lng, TEAM_QUERY_DISTANCE, flag, TEAM_LIST_PAGE_SIZE);
        if (listDTOs.size() > TEAM_LIST_PAGE_SIZE) {
            listDTOs = listDTOs.subList(0, TEAM_LIST_PAGE_SIZE);
        }
        entity.setContent(listDTOs, listDTOs.size() > TEAM_LIST_PAGE_SIZE, null, String.valueOf(flag + 1));
        return entity;
    }

    /**
     * 家庭医生团队详情
     */
    @VersionRange
    @RequestMapping(value = "teamInfo")
    public JsonResponseEntity teamInfo(@RequestParam String teamId) {
        JsonResponseEntity<FamilyTeamInfoDTO> entity = new JsonResponseEntity();
        FamilyTeamInfoDTO familyTeamInfoDTO = familyDoctorService.getFamilyTeamInfo("", teamId);
        entity.setData(familyTeamInfoDTO);
        return entity;
    }

}
