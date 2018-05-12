package com.wondersgroup.healthcloud.api.http.controllers.doctor;

import com.wondersgroup.healthcloud.api.utils.BeanMapUtils;
import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DepartmentRepository;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.utils.ExcelParse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生
 * Created by ys on 2017-04-13
 */
@RestController
@RequestMapping(path = "/api/doctor")
public class DoctorController {

    private static String[] xlsFieldNames = {"doctorCode", "hospitalCode", "deptCode", "doctorName", "gender",
            "doctorTitle", "doctorDesc", "expertin", "headphoto"};

    private static String[] xlsFieldNamesNoEmpty = {"doctorCode", "hospitalCode", "deptCode", "doctorName", "gender",
            "doctorTitle", "doctorDesc", "expertin"};

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/list")
    public Pager list(@RequestBody Pager pager) {
        int pageSize = pager.getSize();
        pager.setData(doctorService.findList(pager.getParameter(), pager.getNumber(), pager.getSize()));
        int total = doctorService.countByMap(pager.getParameter());
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pager.setTotalElements(total);
        pager.setTotalPages(totalPage);
        return pager;
    }

    @GetMapping("/view")
    public JsonResponseEntity<Map<String, Object>> view(Integer id) {
        JsonResponseEntity<Map<String, Object>> entity = new JsonResponseEntity<>();
        Doctor doctor = doctorService.getInfoById(id);
        if (null != doctor) {
            Map<String, Object> info = BeanMapUtils.beanToMap(doctor);
            Department department = departmentRepository.selectByHospitalCodeAndDeptCode(doctor.getHospitalCode(), doctor.getDeptCode());
            info.put("topDeptCode", department != null ? department.getUpperDeptCode() : "1");
            entity.setData(info);
        }
        return entity;
    }

    @PostMapping("/save")
    public JsonResponseEntity<Doctor> save(@RequestBody Doctor doctor) {
        JsonResponseEntity<Doctor> entity = new JsonResponseEntity<>();
        doctorService.saveDoctor(doctor);
        entity.setData(doctor);
        entity.setMsg("保存成功!");
        return entity;
    }

    @PostMapping(path = "upload")
    public JsonResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<>();
        Map<String, Object> info = new HashMap<>();
        List<Map<String, Object>> tables = ExcelParse.parseExcelFile(file, xlsFieldNames, xlsFieldNamesNoEmpty);
        List<String> errorMsgs = new ArrayList<>();
        for (Map<String, Object> data : tables) {
            Doctor doctor = new Doctor();
            String rowNum = data.get("rowNum").toString();
            try {
                BeanUtils.populate(doctor, data);
                doctor.setDelFlag("0");
                doctorService.saveDoctor(doctor);
            } catch (CommonException ce) {
                data.put("error", ce.msg());
                errorMsgs.add("第" + rowNum + "行 : " + ce.msg());
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("第" + rowNum + "行 : " + e.getMessage());
            }
            data.put("dealResult", doctor.getId());
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

    @GetMapping("/delete")
    public String delete(@RequestParam(required = false) Integer hosId, @RequestParam(required = false) Integer depId, @RequestParam(required = false) Integer docId) {
        doctorService.delete(hosId, depId, docId);
        return "success";
    }

}
