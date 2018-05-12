package com.wondersgroup.healthcloud.api.http.controllers.doctor;

import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.utils.ExcelParse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 医院
 * Created by ys on 2017-04-13
 */
@RestController
@RequestMapping(path = "/api/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    private static String[] xlsFieldNames = {"hospitalCode", "hospitalName", "hospitalAddress", "hospitalDesc", "hospitalPhone",
            "hospitalGrade", "hospitalLatitude", "hosptialLongitude", "hosptialPhoto", "cityCode", "submerno"};

    private static String[] xlsFieldNamesNoEmpty = {"hospitalCode", "hospitalName", "hospitalAddress", "hospitalDesc", "hospitalPhone",
            "hospitalGrade", "cityCode"};


    @PostMapping("/list")
    public Pager list(@RequestBody Pager pager) {
        int pageSize = pager.getSize();
        List<Hospital> hospitalList = hospitalService.findList(pager.getParameter(), pager.getNumber(), pager.getSize());
        pager.setData(hospitalList);
        int total = hospitalService.countByMap(pager.getParameter());
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pager.setTotalElements(total);
        pager.setTotalPages(totalPage);
        return pager;
    }

    @GetMapping("/view")
    public JsonResponseEntity<Hospital> view(Integer id) {
        JsonResponseEntity<Hospital> entity = new JsonResponseEntity<>();
        Hospital hospital = hospitalService.findByHospitalId(id);
        if (null != hospital.getCustomEmails()) {
            hospital.setCustomEmails(hospital.getCustomEmails().replace(",", "\n"));
        }
        entity.setData(hospital);
        return entity;
    }

    @PostMapping("/save")
    public JsonResponseEntity<Hospital> save(@RequestBody Hospital hospital) {
        JsonResponseEntity<Hospital> entity = new JsonResponseEntity<>();
        hospitalService.saveHospital(hospital);
        entity.setData(hospital);
        entity.setMsg("保存成功!");
        return entity;
    }

    @PostMapping(path = "upload")
    @VersionRange
    public JsonResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<>();
        Map<String, Object> info = new HashMap<>();
        List<Map<String, Object>> tables = ExcelParse.parseExcelFile(file, xlsFieldNames, xlsFieldNamesNoEmpty);
        List<String> errorMsgs = new ArrayList<>();
        for (Map<String, Object> data : tables) {
            Hospital hospital = new Hospital();
            String rowNum = data.get("rowNum").toString();
            try {
                BeanUtils.populate(hospital, data);
                hospital.setStatus("0");//上传的医生默认都是未启用的
                hospitalService.saveHospital(hospital);
            } catch (CommonException ce) {
                data.put("error", ce.msg());
                errorMsgs.add("第" + rowNum + "行 : " + ce.msg());
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("第" + rowNum + "行 : " + e.getMessage());
            }
            data.put("dealResult", hospital.getId());
        }
        int errorCount = errorMsgs.size();
        info.put("tables", tables);
        int okCount = tables.size() - errorCount;
        StringBuilder sb = new StringBuilder("本次上传共处理(" + tables.size() + ")条数据! 成功(" + okCount + ")条， 失败(" + errorCount + ")条");
        if (errorCount > 0) {
            sb.append("<br>失败原因:");
            for (String errorMsg : errorMsgs) {
                sb.append("<br>" + errorMsg);
            }
        }
        response.setMsg(sb.toString());
        response.setData(info);
        return response;
    }

    @GetMapping("/upHospitalCityCode")
    public String upHospitalCityCode(@RequestParam Integer id, @RequestParam String cityCode) {
        hospitalService.upHsopitalCiyCode(id, cityCode);
        return "success";
    }

}
