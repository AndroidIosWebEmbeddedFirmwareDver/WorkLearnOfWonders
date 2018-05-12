package com.wondersgroup.healthcloud.api.http.controllers.healthRecord;

import java.util.List;

import javax.annotation.Resource;

import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthOnlineListAPIEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.healthRecord.HealthOnlineService;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianChaListResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianYanListResponse;

/**
 * 提取报告
 * 检验/检查报告(实时查询his)
 * <p/>
 * Created by yanshuai on 16/11/08.
 */
@RestController
@RequestMapping(value = "/api/healthOnline")
public class HealthOnlineController {

    @Resource
    private HealthOnlineService healthOnlineService;

    @Value("${h5-web.connection.url}")
    private String h5ViewUrl = "";

    /**
     * 检验报告
     * //city-code:510122000000成都市区双流区域编码
     */
    @RequestMapping(value = "/jianyan/list", method = RequestMethod.GET)
    @ResponseBody
    @VersionRange
    public JsonListResponseEntity<HealthOnlineListAPIEntity> jianyan(
            @RequestHeader(name = "city-code", defaultValue = "", required = false) String cityCode,
            @RequestParam String uid,
            @RequestParam String medicalOrgId,
            @RequestParam(required = false, defaultValue = "0") Integer timeFlag) {

        JsonListResponseEntity<HealthOnlineListAPIEntity> entity = new JsonListResponseEntity<>();
        HealthOnlineJianYanListResponse jianYanListResponse = healthOnlineService.getJianYanList(uid, medicalOrgId, timeFlag);
        List<HealthOnlineListAPIEntity> apiEntities = jianYanListResponse.coverToApiEntity(h5ViewUrl + "/extractReport/inspectInfo?id=%s&hosName=%s");
        entity.setContent(apiEntities, false, null, "1");
        return entity;
    }

    /**
     * 检查报告
     */
    @RequestMapping(value = "/jiancha/list", method = RequestMethod.GET)
    @ResponseBody
    @VersionRange
    public JsonListResponseEntity<HealthOnlineListAPIEntity> jianChaList(
            @RequestHeader(name = "city-code", defaultValue = "", required = false) String cityCode,
            @RequestParam String uid,
            @RequestParam String medicalOrgId,
            @RequestParam(required = false, defaultValue = "0") Integer timeFlag) {

        JsonListResponseEntity<HealthOnlineListAPIEntity> entity = new JsonListResponseEntity<>();
        HealthOnlineJianChaListResponse jianChaListResponse = healthOnlineService.getJianChaList(uid, medicalOrgId, timeFlag);
        List<HealthOnlineListAPIEntity> apiEntities = jianChaListResponse.coverToApiEntity(h5ViewUrl + "/extractReport/checkupInfo?id=%s&hosName=%s");
        entity.setContent(apiEntities, false, null, "1");
        return entity;
    }

}
