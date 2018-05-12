package com.wondersgroup.healthcloud.services.prescription;

import java.util.Arrays;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.services.prescription.dto.RequestDto;
import com.wondersgroup.healthcloud.services.prescription.dto.ResponseDto;
import com.wondersgroup.healthcloud.services.prescription.dto.ResponseItemDto;
import com.wondersgroup.healthcloud.services.prescription.dto.ResponseListDto;

/**
 * Created by tanxueliang on 16/11/7.
 */
@Service
public class PrescriptionService {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private ServerConfigService serverConfigService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AreaResourceUrl areaResourceUrl;

    RestTemplate restTemplate = new RestTemplate();

    public ResponseListDto getPrescriptionList(RequestDto request) {
        return httpPost(buildURL("payment.getPrescriptionRecord", request.getYljgdm()), request, ResponseListDto.class);
    }

    public ResponseItemDto getPrescriptionDetail(RequestDto request) {
        return httpPost(buildURL("payment.getPrescriptionDetail", request.getYljgdm()), request, ResponseItemDto.class);
    }

    private HttpEntity<Object> buildHttpEntity(Object request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        return entity;
    }

    private String buildURL(String contentURL, String hospitalCode) {
        return areaResourceUrl.getUrl("2", getCityCode(hospitalCode)) + contentURL;
    }

    private <T extends ResponseDto> T httpPost(String url, RequestDto request, Class<T> classz) {
        HttpEntity<Object> httpEntity = buildHttpEntity(request);
        T response = restTemplate.postForObject(url, httpEntity, classz);

        String resultCode = response.getResultcode();
        if (!"0".equals(resultCode)) {
            String message = response.getResultmessage();
            if (StringUtils.isEmpty(message)) {
                message = "区县接口查询失败";
            }
            throw new CommonException(1000, message);
        }
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
