package com.wondersgroup.healthcloud.api.http.controllers.h5.healthRecord;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJiuZhenListAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthJiuZhenListResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 健康数据
 * 就诊记录
 * <p/>
 * Created by yanshuai on 15/7/13.
 */
@RestController("H5HealthJiuZhenController")
@RequestMapping(value = "/h5/api/healthRecord/jiuzhen")
public class HealthJiuZhenController {

    @Resource
    private HealthRecordService healthRecordService;

    private final static int pageSize = 20;

    /**
     * 就诊记录
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonListResponseEntity<HealthJiuZhenListAPIEntity> jiuzhen(@RequestParam String uid,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<HealthJiuZhenListAPIEntity> entity = new JsonListResponseEntity<>();

        HealthJiuZhenListResponse jiuZhenListResponse = healthRecordService.getJiuZhenList(uid, flag, pageSize, "510000000000");
        Boolean has_more = false;
        List<HealthJiuZhenListAPIEntity> apiEntities = jiuZhenListResponse.coverToJiuzhenListEntity();
        if (null != apiEntities) {
            if (jiuZhenListResponse.getTotalPage() > flag) {
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

}
