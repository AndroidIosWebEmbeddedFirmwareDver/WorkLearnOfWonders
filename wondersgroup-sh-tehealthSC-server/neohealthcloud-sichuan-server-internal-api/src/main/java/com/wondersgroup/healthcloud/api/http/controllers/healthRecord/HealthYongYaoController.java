package com.wondersgroup.healthcloud.api.http.controllers.healthRecord;

import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthYongYaoListAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthYongYaoMedicineInfoAPIEntity;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthYongYaoDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthYongYaoListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 健康数据
 * 用药
 * <p/>
 * Created by yanshuai on 15/7/13.
 */
@RestController
@RequestMapping(value = "/api/healthRecord/yongyao")
public class HealthYongYaoController {

    @Resource
    private HealthRecordService healthRecordService;

    private final static int pageSize = 20;

    /**
     * 用药列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonListResponseEntity<HealthYongYaoListAPIEntity> list(@RequestParam String uid,
                                                                   @RequestParam(required = false, defaultValue = "1") Integer flag) {

        JsonListResponseEntity<HealthYongYaoListAPIEntity> entity = new JsonListResponseEntity<>();

        HealthYongYaoListResponse response = healthRecordService.getYongYaoList(uid, flag, pageSize, "510000000000");
        List<HealthYongYaoListAPIEntity> apiEntities = response.coverToApiEntity();
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
     * 用药详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonResponseEntity<List<HealthYongYaoMedicineInfoAPIEntity>> yongyaoDetail(@RequestParam String id, @RequestParam String uid) {

        JsonResponseEntity<List<HealthYongYaoMedicineInfoAPIEntity>> entity = new JsonResponseEntity<>();
        HealthYongYaoDetailResponse yongYaoDetailResponse = healthRecordService.getYongYaoDetail(id, uid, "510000000000");
        List<HealthYongYaoMedicineInfoAPIEntity> apiEntities = yongYaoDetailResponse.coverToMedicineListApiEntity();
        entity.setData(apiEntities);
        return entity;
    }
}
