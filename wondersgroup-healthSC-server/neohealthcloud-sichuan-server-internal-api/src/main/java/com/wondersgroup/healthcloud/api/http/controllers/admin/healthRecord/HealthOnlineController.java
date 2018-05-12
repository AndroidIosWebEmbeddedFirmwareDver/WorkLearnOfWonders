package com.wondersgroup.healthcloud.api.http.controllers.admin.healthRecord;

import com.wondersgroup.healthcloud.api.utils.BeanMapUtils;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.services.healthRecord.HealthOnlineService;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提取报告
 * 检验/检查报告(实时查询his)
 * <p/>
 * Created by yanshuai on 16/11/08.
 */
@RestController
@RequestMapping(value = "/api/healthOnline")
@Admin
public class HealthOnlineController {

    @Resource
    private HealthOnlineService healthOnlineService;

    /**
     * 检验详情
     */
    @RequestMapping(value = "/jianyan/detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonResponseEntity jianYanDetail(@RequestParam String id) {

        JsonResponseEntity entity = new JsonResponseEntity<>();
        HealthOnlineJianYanDetailResponse detailResponse = healthOnlineService.getJianYanDetail(id);
        List<HealthOnlineJianYanDetailResponse.LabDetail> labDetails = detailResponse.getLabDetails();
        Map<String, Object> body = new HashMap<>();
        if (null != labDetails && !labDetails.isEmpty()) {
            Map<String, Object> info = BeanMapUtils.beanToMap(labDetails.get(0));
            if (info.containsKey("inspection")) {
                info.remove("inspection");
            }
            body.put("info", info);
            body.put("list", labDetails);
        }
        entity.setData(body);
        return entity;
    }

    /**
     * 检查详情
     */
    @RequestMapping(value = "/jiancha/detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonResponseEntity jianChaDetail(@RequestParam String id) {

        JsonResponseEntity entity = new JsonResponseEntity<>();
        HealthOnlineJianChaDetailResponse detailResponse = healthOnlineService.getJianChaDetail(id);
        if (null != detailResponse) {
            List<HealthOnlineJianChaDetailResponse.ExamDetail> examDetails = detailResponse.getExamDetail();
            if (null != examDetails && !examDetails.isEmpty()) {
                entity.setData(examDetails.get(0));
            }
        }
        return entity;
    }

}
