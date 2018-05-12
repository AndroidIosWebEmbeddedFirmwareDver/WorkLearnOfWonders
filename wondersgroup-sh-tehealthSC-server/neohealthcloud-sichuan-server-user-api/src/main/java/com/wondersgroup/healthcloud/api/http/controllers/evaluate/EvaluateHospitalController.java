package com.wondersgroup.healthcloud.api.http.controllers.evaluate;

import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateHospital;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateHospitalService;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateHospitalListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价医院
 * add by ys 2016-11-05
 */
@RestController
@RequestMapping("/api/evaluate/hospital")
public class EvaluateHospitalController {

    private static final Integer PAGE_SIZE = 10;//每页个数10

    @Autowired
    private EvaluateHospitalService evaluateHospitalService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonListResponseEntity<AppEvaluateHospitalListDTO> list(@RequestParam Integer hospitalId,
                                                                   @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<AppEvaluateHospitalListDTO> entity = new JsonListResponseEntity<>();
        List<AppEvaluateHospitalListDTO> hospitalDTOs = evaluateHospitalService.findValidListByHospitalId(hospitalId, flag, PAGE_SIZE);

        Boolean hasMore = false;
        if (null != hospitalDTOs && hospitalDTOs.size() > PAGE_SIZE) {
            hospitalDTOs = hospitalDTOs.subList(0, PAGE_SIZE);
            hasMore = true;
        }
        entity.setContent(hospitalDTOs, hasMore, null, String.valueOf(flag + 1));
        return entity;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Map<String, Object>> publish(@RequestBody EvaluateHospital evaluateHospital) {
        JsonResponseEntity body = new JsonResponseEntity<>();
        evaluateHospitalService.publishEvaluateHospital(evaluateHospital);
        if (evaluateHospital.getId() == 0) {
            body.setCode(2021);
            body.setMsg("评论失败");
            return body;
        }
        Map<String, Object> info = new HashMap<>();
        info.put("id", evaluateHospital.getId());
        body.setData(info);
        body.setMsg("评价成功");
        return body;
    }

}
