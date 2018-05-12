package com.wondersgroup.healthcloud.services.healthRecord;

import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianChaDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianChaListResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianYanDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianYanListResponse;

/**
 * Created by ys on 2016/11/01.
 * 提取报告的检验检查
 */
public interface HealthOnlineService {

    /**
     * 检验报告
     */
    HealthOnlineJianYanListResponse getJianYanList(String uid, String medicalOrgId, Integer day);

    HealthOnlineJianYanDetailResponse getJianYanDetail(String id);

    /**
     * 检查报告
     */
    HealthOnlineJianChaListResponse getJianChaList(String uid, String medicalOrgId, Integer day);

    HealthOnlineJianChaDetailResponse getJianChaDetail(String id);
}
