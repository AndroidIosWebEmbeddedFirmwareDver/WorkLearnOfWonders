package com.wondersgroup.healthcloud.services.healthRecord;

import com.wondersgroup.healthcloud.services.healthRecord.responseDto.*;

/**
 * Created by ys on 2016/11/01.
 * 健康档案
 */
public interface HealthRecordService {

    /**
     * 检验报告
     */
    HealthJianYanListResponse getJianYanList(String uid, int page, int pageSize, String cityCode);

    HealthJianYanDetailResponse getJianYanDetail(String rid, String rNo, String date, String uid, String cityCode);

    /**
     * 住院报告
     */
    HealthZhuYuanListResponse getZhuYuanList(String uid, int page, int pageSize, String cityCode);

    /**
     * 住院小结
     *
     * @param medicalOrgId 医疗机构代码
     * @param serialNum    就诊流水号
     * @return
     */
    HealthZhuYuanDetailResponse getZhuYuanDetail(String medicalOrgId, String serialNum, String uid, String cityCode);

    /**
     * 就诊列表
     */
    HealthJiuZhenListResponse getJiuZhenList(String uid, int page, int pageSize, String cityCode);

    /**
     * 处方列表
     */
    HealthYongYaoListResponse getYongYaoList(String uid, int page, int pageSize, String cityCode);

    /**
     * 处方清单
     */
    HealthYongYaoDetailResponse getYongYaoDetail(String prescriptionId, String uid, String cityCode);


}
