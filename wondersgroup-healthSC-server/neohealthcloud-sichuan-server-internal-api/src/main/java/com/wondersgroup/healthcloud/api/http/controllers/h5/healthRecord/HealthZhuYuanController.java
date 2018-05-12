package com.wondersgroup.healthcloud.api.http.controllers.h5.healthRecord;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthZhuYuanDetailAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthZhuYuanListAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthZhuYuanDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthZhuYuanListResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 健康数据
 * 住院信息
 * <p/>
 * Created by yanshuai on 15/7/13.
 */
@RestController("H5HealthZhuYuanController")
@RequestMapping(value = "/h5/api/healthRecord/zhuyuan")
public class HealthZhuYuanController {

    @Resource
    private HealthRecordService healthRecordService;

    private final static int pageSize = 20;

    /**
     * 住院列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonListResponseEntity<HealthZhuYuanListAPIEntity> zhuyuan(@RequestParam String uid,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<HealthZhuYuanListAPIEntity> entity = new JsonListResponseEntity<>();

        HealthZhuYuanListResponse response = healthRecordService.getZhuYuanList(uid, flag, pageSize, "510000000000");
        List<HealthZhuYuanListAPIEntity> apiEntities = response.coverToZhuYuanListEntity();
        Boolean has_more = false;
        if (null != apiEntities) {
            if (response.getTotalPage() > flag) {
                has_more = true;
            }
        }
        if (has_more) {
            entity.setContent(apiEntities, true, "", String.valueOf(flag + 1));
        } else {
            entity.setContent(apiEntities);
        }
        return entity;
    }

    /**
     * 住院列表
     * id详见HealthZhuYuanListResponse
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonResponseEntity<HealthZhuYuanDetailAPIEntity> detail(@RequestParam String id, @RequestParam String uid) {

        String[] parms = id.split("-\\|-");
        if (parms.length != 2) {
            throw new CommonException(2001, "id格式无效");
        }
        JsonResponseEntity<HealthZhuYuanDetailAPIEntity> entity = new JsonResponseEntity<>();
        HealthZhuYuanDetailResponse detailResponse = healthRecordService.getZhuYuanDetail(parms[0], parms[1], uid, "510000000000");
        HealthZhuYuanDetailAPIEntity detailAPIEntity = detailResponse.coverToZhuYuanDetailEntity();
        entity.setData(detailAPIEntity);
        return entity;
    }


}
