package com.wondersgroup.healthcloud.api.http.controllers.evaluate;

import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateDoctor;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateDoctorService;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价医生
 * add by ys 2016-11-05
 */
@RestController
@RequestMapping("/api/evaluate/doctor")
public class EvaluateDoctorController {

    private static final Integer PAGE_SIZE = 10;//每页个数10

    @Autowired
    private EvaluateDoctorService evaluateDoctorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonListResponseEntity<AppEvaluateDoctorListDTO> list(@RequestParam Integer doctorId,
                                                                 @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<AppEvaluateDoctorListDTO> entity = new JsonListResponseEntity<>();
        List<AppEvaluateDoctorListDTO> doctorDTOs = evaluateDoctorService.findValidListByDoctorId(doctorId, flag, PAGE_SIZE);

        Boolean hasMore = false;
        if (null != doctorDTOs && doctorDTOs.size() > PAGE_SIZE) {
            doctorDTOs = doctorDTOs.subList(0, PAGE_SIZE);
            hasMore = true;
        }
        entity.setContent(doctorDTOs, hasMore, null, String.valueOf(flag + 1));
        return entity;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Map<String, Object>> publish(@RequestBody EvaluateDoctor evaluateDoctor) {
        JsonResponseEntity body = new JsonResponseEntity<>();
        evaluateDoctorService.publishEvaluateDoctor(evaluateDoctor);
        if (evaluateDoctor.getId() == 0) {
            body.setCode(2021);
            body.setMsg("评论失败");
            return body;
        }
        Map<String, Object> info = new HashMap<>();
        info.put("id", evaluateDoctor.getId());
        body.setData(info);
        body.setMsg("评价成功");
        return body;
    }

}
