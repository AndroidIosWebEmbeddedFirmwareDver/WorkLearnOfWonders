package com.wondersgroup.healthcloud.api.http.controllers.prescription;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.api.http.controllers.BaseController;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.constant.CardType;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.prescription.PrescriptionService;
import com.wondersgroup.healthcloud.services.prescription.dto.PrescriptionDto;
import com.wondersgroup.healthcloud.services.prescription.dto.RequestDto;
import com.wondersgroup.healthcloud.services.prescription.dto.ResponseListDto;
import com.wondersgroup.healthcloud.utils.DateFormatter;

/**
 * 电子账单
 *
 * @author tanxueliang
 */
@RestController
@RequestMapping("/api/eprescription")
public class EPrescriptionController extends BaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    AppUrlH5Utils appUrlH5Utils;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @VersionRange
    public Object list(@RequestHeader(name = "city-code", defaultValue = "", required = false) String cityCode, String yljgdm, int timeFlag, String userId) {

        Account account = accountService.info(userId);
        String idCard = account.getIdcard();

        RequestDto request = new RequestDto();
        if (StringUtils.isNotEmpty(cityCode) && cityCode.equals("510122000000")) {
            setTimeRangeForSL(timeFlag, request);
        } else {
            setTimeRange(timeFlag, request);
        }
        request.setYljgdm(yljgdm);
        request.setKlx(CardType.ID_CARD);
        request.setKh(idCard);
        request.setJzlsh("");

        ResponseListDto response = prescriptionService.getPrescriptionList(request);
        setYljgmcForPrescription(userId, yljgdm, response);

        return buildSuccessed(response);
    }

    private void setTimeRangeForSL(int timeFlag, RequestDto request) {

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.add(Calendar.DATE, +1);

        Calendar startCalendar = Calendar.getInstance();
        switch (timeFlag) {
            case 1:
                startCalendar.add(Calendar.DAY_OF_YEAR, -2); // 最近1天 今天
                break;
            case 3:
                startCalendar.add(Calendar.DAY_OF_YEAR, -3); // 最近3天
                break;
            case 7:
                startCalendar.add(Calendar.DAY_OF_YEAR, -7); // 最近7天
                break;
            case 30:
                startCalendar.add(Calendar.MONTH, -1); // 最近一个月
                break;
            case 90:
                startCalendar.add(Calendar.MONTH, -3); // 最近3个月
                break;
            case 180:
                startCalendar.add(Calendar.MONTH, -6); // 最近6个月
                break;
            default:
                break;
        }
        request.setZzkfsj(DateFormatter.dateFormat(startCalendar.getTime()));
        request.setZwkfsj(DateFormatter.dateFormat(nowCalendar.getTime()));
    }

    private void setTimeRange(int timeFlag, RequestDto request) {

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.add(Calendar.DATE, +1);

        Calendar startCalendar = Calendar.getInstance();
        switch (timeFlag) {
            case 1:
                startCalendar.add(Calendar.DAY_OF_YEAR, -2); // 最近三天
                break;
            case 2:
                startCalendar.add(Calendar.DAY_OF_YEAR, -6); // 最近七天
                break;
            case 3:
                startCalendar.add(Calendar.MONTH, -1); // 最近一个月
            default:
                break;
        }
        request.setZzkfsj(DateFormatter.dateFormat(startCalendar.getTime()));
        request.setZwkfsj(DateFormatter.dateFormat(nowCalendar.getTime()));
    }

    private void setYljgmcForPrescription(String userId, String yljgdm, ResponseListDto response) {
        Hospital hospital = hospitalRepository.findByHospitalCode(yljgdm);

        String yljgmc = "";
        if (hospital != null) {
            yljgmc = hospital.getHospitalName();
        }

        List<PrescriptionDto> prescriptionList = response.getPrescription();
        if (prescriptionList != null && !prescriptionList.isEmpty()) {
            for (PrescriptionDto p : prescriptionList) {
                p.setYljgmc(yljgmc);
                p.setYljgdm(yljgdm);
                p.setUrl(appUrlH5Utils.buildBasicUrl(buildURL(yljgdm, userId, p)));
            }
        }
    }

    private String buildURL(String yljgdm, String userId, PrescriptionDto prescription) {
        String url = "/healthRecords/prescribingInfo?yljgdm=%s&cfhm=%s&userId=%s&name=%s&department=%s&amount=%s&date=%s&type=app";

        String name = urlEncode(prescription.getYljgmc());
        String department = urlEncode(prescription.getKfksmc());
        String amount = urlEncode(prescription.getCfje());
        String date = urlEncode(prescription.getKfsj());
        String cfhm = urlEncode(prescription.getCfhm());

        return String.format(url, yljgdm, cfhm, userId, name, department, amount, date);
    }

    public static String urlEncode(String content) {
        if (StringUtils.isEmpty(content)) {
            content = "";
        } else {
            try {
                content = URLEncoder.encode(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return content;
    }

}
