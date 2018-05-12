package com.wondersgroup.healthcloud.services.beinhospital.impl;

import com.google.common.collect.Lists;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.healthcloud.constant.URLEnum;
import com.wondersgroup.healthcloud.entity.po.BeInHosPaymentRecord;
import com.wondersgroup.healthcloud.entity.request.BeInHosPayRecordRequest;
import com.wondersgroup.healthcloud.entity.response.BeInHosPayRecordDetailResponse;
import com.wondersgroup.healthcloud.jpa.entity.beinhospital.BeInHospitalRecord;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.beinhospital.BeInHospitalRepository;
import com.wondersgroup.healthcloud.services.beinhospital.BeInHospitalRecordService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nick on 2016/11/8.
 */
@Service
public class BeInHospitalRecordServiceImpl implements BeInHospitalRecordService {

    @Autowired
    private BeInHospitalRepository beInHospitalRepository;

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AreaResourceUrl areaResourceUrl;

    @Override
    public List<BeInHospitalRecord> getRecordsByIdCard(String idCard, String hospitalCode, int start, int end) {

        return beInHospitalRepository.findByIdCardAndHospitalCode(idCard, hospitalCode, start, end);
    }

    @Override
    public int countByIdCard(String idCard, String hospitalCode) {
        return beInHospitalRepository.countByIdCard(idCard, hospitalCode);
    }

    @Override
    public List<BeInHosPaymentRecord> queryBeInHosPrePaidRecord(String idCard, String hospitalCode, String beInHospitalCode) {
        BeInHosPayRecordRequest request = new BeInHosPayRecordRequest();
        request.setKlx("01");
        request.setKh(idCard);
        request.setYljgdm(hospitalCode);
        request.setZyh(beInHospitalCode);
        BeInHosPayRecordDetailResponse response = raisePrepaidPaymentRecord(request, getCityCode(hospitalCode));
        List<BeInHosPaymentRecord> paymentRecords = Lists.newArrayList();
        if (response != null && !CollectionUtils.isEmpty(response.getItem())) {
            for (BeInHosPayRecordDetailResponse.Item item : response.getItem()) {
                BeInHosPaymentRecord record = new BeInHosPaymentRecord(item, hospitalCode);
                paymentRecords.add(record);
            }
        }
        return paymentRecords;
    }

    private BeInHosPayRecordDetailResponse raisePrepaidPaymentRecord(BeInHosPayRecordRequest payRecordRequest, String cityCode) {
        String xmlParam = JaxbUtil.convertToXml(payRecordRequest);
        Request request = new RequestBuilder().post().url(areaResourceUrl.getUrl("2", cityCode) + URLEnum.PRE_PAID_RECORD_QUERY_PATH).
                body(xmlParam).build();
        StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
        String resultXml = wrapper.convertBody();
        BeInHosPayRecordDetailResponse response = JaxbUtil.convertToJavaBean(resultXml, BeInHosPayRecordDetailResponse.class);
        return response;
    }

    /**
     * 根据医院 查询所属区域
     */
    private String getCityCode(String hospitalCode) {
        Hospital hospital = hospitalService.findByHospitalCode(hospitalCode);
        if (hospital != null && StringUtils.isNotEmpty(hospital.getCityCode())) {
            return hospital.getCityCode();
        }
        return "510000000000";
    }
}
