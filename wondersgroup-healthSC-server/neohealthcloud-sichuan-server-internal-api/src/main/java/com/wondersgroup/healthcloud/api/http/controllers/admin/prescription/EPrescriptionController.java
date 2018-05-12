package com.wondersgroup.healthcloud.api.http.controllers.admin.prescription;

import java.util.ArrayList;
import java.util.List;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.constant.CardType;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthYongYaoMedicineInfoAPIEntity;
import com.wondersgroup.healthcloud.services.prescription.PrescriptionService;
import com.wondersgroup.healthcloud.services.prescription.dto.PrescriptionDetailDto;
import com.wondersgroup.healthcloud.services.prescription.dto.RequestDto;
import com.wondersgroup.healthcloud.services.prescription.dto.ResponseItemDto;

/**
 * 电子账单
 *
 * @author tanxueliang
 */
@RestController
@RequestMapping("/api/eprescription")
@Admin
public class EPrescriptionController {

    @Autowired
    AccountService accountService;

    @Autowired
    PrescriptionService prescriptionService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public Object details(String yljgdm, String cfhm, String userId) {

        JsonResponseEntity<Object> responseEntity = new JsonResponseEntity<>();

        Account account = accountService.info(userId);

        String idCard = account.getIdcard();

        RequestDto request = new RequestDto();
        request.setYljgdm(yljgdm);
        request.setKlx(CardType.ID_CARD);
        request.setKh(idCard);
        request.setCfhm(cfhm);
        request.setJzlsh("");
        ResponseItemDto response = prescriptionService.getPrescriptionDetail(request);
        responseEntity.setData(buildListEntity(response.getItem()));
        return responseEntity;
    }

    private List<HealthYongYaoMedicineInfoAPIEntity> buildListEntity(List<PrescriptionDetailDto> list) {
        List<HealthYongYaoMedicineInfoAPIEntity> resultEntity = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return resultEntity;
        }
        for (PrescriptionDetailDto dto : list) {
            resultEntity.add(buildEntity(dto));
        }
        return resultEntity;
    }

    private HealthYongYaoMedicineInfoAPIEntity buildEntity(PrescriptionDetailDto dto) {
        HealthYongYaoMedicineInfoAPIEntity entity = new HealthYongYaoMedicineInfoAPIEntity();
        entity.setDay_use_num(dto.getXmpl());
        entity.setMedicine_name(dto.getXmmc());
        entity.setMedicine_num(dto.getXmsl() + dto.getXmdw());
        entity.setPer_use_num("");
        entity.setSpecification(dto.getXmgg());
        entity.setUse_type(dto.getXmyf());
        return entity;
    }

}
