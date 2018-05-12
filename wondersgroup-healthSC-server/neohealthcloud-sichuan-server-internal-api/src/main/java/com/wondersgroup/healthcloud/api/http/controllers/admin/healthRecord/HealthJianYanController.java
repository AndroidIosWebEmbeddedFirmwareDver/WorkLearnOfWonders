package com.wondersgroup.healthcloud.api.http.controllers.admin.healthRecord;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJianYanDetailAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJianYanListAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthJianYanDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthJianYanListResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 健康数据
 * 检验报告
 * <p/>
 * Created by yanshuai on 16/11/07.
 */
@RestController
@RequestMapping(value = "/api/healthRecord/jianyan")
@Admin
public class HealthJianYanController {

    @Resource
    private HealthRecordService healthRecordService;

    private final static int pageSize = 20;

    /**
     * 检验报告
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonListResponseEntity<HealthJianYanListAPIEntity> jianyan(@RequestHeader(required = false, defaultValue = "510000000000") String cityCode,
                                                                      @RequestParam String uid,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<HealthJianYanListAPIEntity> entity = new JsonListResponseEntity<>();

        HealthJianYanListResponse jianYanListResponse = healthRecordService.getJianYanList(uid, flag, pageSize, cityCode);
        List<HealthJianYanListAPIEntity> apiEntities = jianYanListResponse.coverToApiEntity();
        Boolean has_more = false;
        if (apiEntities != null && jianYanListResponse.getTotalPage() > flag) {
            has_more = true;
        }
        if (has_more) {
            entity.setContent(apiEntities, true, "", String.valueOf(flag + 1));
        } else {
            entity.setContent(apiEntities);
        }
        return entity;
    }

    /**
     * 检验详情
     * id详见HealthZhuYuanListResponse
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonResponseEntity<List<HealthJianYanDetailAPIEntity>> detail(@RequestHeader(required = false, defaultValue = "510000000000") String cityCode,
                                                                         @RequestParam String id,
                                                                         @RequestParam String uid) {

        String[] parms = id.split("-\\|-");
        if (parms.length != 3) {
            throw new CommonException(2001, "id格式无效");
        }
        JsonResponseEntity<List<HealthJianYanDetailAPIEntity>> entity = new JsonResponseEntity<>();
        HealthJianYanDetailResponse detailResponse = healthRecordService.getJianYanDetail(parms[0], parms[1], parms[2], uid, cityCode);
        List<HealthJianYanDetailAPIEntity> apiEntities = detailResponse.coverToApiEntity();
        entity.setData(apiEntities);
        return entity;
    }

}
