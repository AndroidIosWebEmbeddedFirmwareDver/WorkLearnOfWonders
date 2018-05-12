package com.wondersgroup.healthcloud.services.hospital.impl;

import com.wondersgroup.healthcloud.config.client.GetTopDeptInfoClient;
import com.wondersgroup.healthcloud.config.client.GetTwoDepInfoClient;
import com.wondersgroup.healthcloud.config.client.OrderInfoNumClient;
import com.wondersgroup.healthcloud.entity.RequestMessageHeaderUtil;
import com.wondersgroup.healthcloud.entity.request.depaetment.DepRequest;
import com.wondersgroup.healthcloud.entity.request.depaetment.HosInfo;
import com.wondersgroup.healthcloud.entity.response.department.DepInfo;
import com.wondersgroup.healthcloud.entity.response.department.DepInfoResponse;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.utils.SignatureGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dukuanxin on 2017/3/1.
 */
@Component
public class SchedulCommon {

    @Autowired
    public GetTopDeptInfoClient getTopDeptInfoClient;

    @Autowired
    public GetTwoDepInfoClient getTwoDepInfoClient;

    @Autowired
    public OrderInfoNumClient orderInfoNumClient;

    @Autowired
    public SignatureGenerator signatureGenerator;

    @Autowired
    public RequestMessageHeaderUtil reqMesHeaderUtil;
    @Autowired
    public DoctorService doctorService;
    @Autowired
    public ServerConfigService serverConfigServiceImpl;

    @Autowired
    public HospitalService hospitalService;

    public String getHostialCityCode(String hospitalCode) {

        Hospital hospital = hospitalService.findByHospitalCode(hospitalCode);

        return hospital.getCityCode();
    }

    public List<DepInfo> getTopDept(String hospitalCode, String area) {

        if (area == null) area = "510000000000";

        DepRequest depRequest = new DepRequest();

        DepRequest topDepRequest;

        DepInfoResponse depInfoResponse;
        switch (area) {
            case "510122000000"://双流
                depRequest.requestMessageHeader = reqMesHeaderUtil.generator();
                topDepRequest = getTopDepRequest(depRequest, hospitalCode);
                break;
            default:
                depRequest.requestMessageHeader = reqMesHeaderUtil.generator();
                topDepRequest = getTopDepRequest(depRequest, hospitalCode);
                break;
        }
        String url = serverConfigServiceImpl.queryApiUrl(area, "1");
        getTopDeptInfoClient.setDefaultUri(url);
        depInfoResponse = getTopDeptInfoClient.get(JaxbUtil.convertToXml(topDepRequest, "GBK"));

        return depInfoResponse.getDepInfos();
    }

    DepRequest getTopDepRequest(DepRequest depRequest, String hospitalCode) {
        HosInfo hosInfo = new HosInfo();
        hosInfo.setHosOrgCode(hospitalCode);
        depRequest.hosInfo = hosInfo;
        String sign = signatureGenerator.generateSignature(depRequest);
        depRequest.requestMessageHeader.setSign(sign);
        return depRequest;
    }

}
