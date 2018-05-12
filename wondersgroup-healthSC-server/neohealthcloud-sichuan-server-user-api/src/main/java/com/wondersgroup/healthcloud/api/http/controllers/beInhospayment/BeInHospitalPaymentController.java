package com.wondersgroup.healthcloud.api.http.controllers.beInhospayment;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.entity.po.BeInHosPaymentRecord;
import com.wondersgroup.healthcloud.jpa.entity.beinhospital.BeInHospitalRecord;
import com.wondersgroup.healthcloud.services.beinhospital.BeInHospitalRecordService;
import com.wondersgroup.healthcloud.solr.document.SolrHospital;
import com.wondersgroup.healthcloud.solr.repository.SolrHospitalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by nick on 2016/11/8.
 *
 * @author nick
 * 院内支付, 诊间支付API
 */
@RestController
@RequestMapping("/api/beinhospital")
public class BeInHospitalPaymentController {

    @Autowired
    private SolrHospitalRepository solrHospitalRepository;

    @Autowired
    private BeInHospitalRecordService beInHospitalRecordService;

    private static Integer pageSize = 10;


    @RequestMapping(value = "/searchHospital", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<SolrHospital> searchHospital(@RequestParam String hospitalName) {

        JsonListResponseEntity<SolrHospital> responseEntity = new JsonListResponseEntity<>();
        List<SolrHospital> hospitalList = solrHospitalRepository.findByHospitalNameAndDelFlag(hospitalName, "0");
        responseEntity.setContent(hospitalList);
        return responseEntity;
    }

    /**
     * 获取用户住院记录
     * 身份证号
     * 医院组织机构编码
     *
     * @param idCard
     * @param hospitalCode
     * @param flag
     * @return
     */
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<BeInHospitalRecord> getBeInHospitalRecords(@RequestParam String idCard,
                                                                             @RequestParam String hospitalCode,
                                                                             @RequestParam(required = false) String flag) {
        JsonListResponseEntity<BeInHospitalRecord> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (StringUtils.isNotEmpty(flag))
            start = Integer.valueOf(flag);
        int end = start + pageSize;
        int total = beInHospitalRecordService.countByIdCard(idCard, hospitalCode);
        if (total == 0) {
            List<BeInHospitalRecord> records = Lists.newArrayList();
            responseEntity.setContent(records);
        } else {
            boolean hasMore = false;
            if (end > total)
                end = total;
            else
                hasMore = true;

            List<BeInHospitalRecord> records = beInHospitalRecordService.getRecordsByIdCard(idCard, hospitalCode, start, end);
            responseEntity.setContent(records, hasMore, "", String.valueOf(end));
        }
        return responseEntity;
    }

    /**
     * 获取住院号对应的缴费记录
     * 医院组织机构代码
     * 身份证号
     * 住院号
     *
     * @param hospitalCode
     * @param idCard
     * @param beInHospitalCode
     * @return
     */
    @RequestMapping(value = "payRecords", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<BeInHosPaymentRecord> getPrepaidPaymentRecords(@RequestParam String hospitalCode,
                                                                                 @RequestParam String idCard,
                                                                                 @RequestParam String beInHospitalCode) {
        JsonListResponseEntity<BeInHosPaymentRecord> responseEntity = new JsonListResponseEntity<>();
        List<BeInHosPaymentRecord> records = beInHospitalRecordService.queryBeInHosPrePaidRecord(idCard, hospitalCode, beInHospitalCode);
        responseEntity.setContent(records);
        return responseEntity;
    }
}
