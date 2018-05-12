package com.wondersgroup.healthcloud.api.http.controllers.doctor;

import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DepartmentRepository;
import com.wondersgroup.healthcloud.services.hospital.DepartmentService;
import com.wondersgroup.healthcloud.services.hospital.dto.DeptsMenuDTO;
import com.wondersgroup.healthcloud.utils.ExcelParse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 科室
 * Created by ys on 2017-04-13
 */
@RestController
@RequestMapping(path = "/api/department")
public class DepartmentController {

    private static String[] xlsFieldNames = {"hospitalCode", "deptCode", "deptName", "upperDeptCode", "deptDesc", "deptAddr", "isSpecial"};

    private static String[] xlsFieldNamesNoEmpty = {"hospitalCode", "deptCode", "deptName", "upperDeptCode"};

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/list")
    public Pager list(@RequestBody Pager pager) {
        int pageSize = pager.getSize();
        Map parms = pager.getParameter();
        if (!parms.containsKey("hospitalCode") || StringUtils.isEmpty(parms.get("hospitalCode").toString())) {
            throw new CommonException(2001, "医院code不能为空");
        }
        String hospitalCode = parms.get("hospitalCode").toString();
        pager.setData(departmentService.getDepartmentList(parms, pager.getNumber(), pager.getSize()));
        int total = departmentService.countDepartment(hospitalCode);
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pager.setTotalElements(total);
        pager.setTotalPages(totalPage);
        return pager;
    }

    @GetMapping("/menuList")
    public JsonResponseEntity menuList(@RequestParam String hospitalCode) {
        JsonResponseEntity entity = new JsonResponseEntity();
        DeptsMenuDTO menuDTO = departmentService.getDeptsMenu(hospitalCode);
        entity.setData(menuDTO);
        return entity;
    }

    @GetMapping("/topDepts")
    public JsonResponseEntity topDepts(@RequestParam String hospitalCode) {
        JsonResponseEntity entity = new JsonResponseEntity();
        List<Department> departments = departmentService.getTopDepts(hospitalCode);
        entity.setData(departments);
        return entity;
    }

    @GetMapping("/secondDepts")
    public JsonResponseEntity getSecondDepts(@RequestParam String hospitalCode) {
        JsonResponseEntity entity = new JsonResponseEntity();
        List<Department> departments = departmentService.getSecondDepts(hospitalCode);
        entity.setData(departments);
        return entity;
    }

    @GetMapping("/view")
    public JsonResponseEntity<Department> view(Integer id) {
        JsonResponseEntity entity = new JsonResponseEntity<>();
        Department department = departmentRepository.findOne(id);
        entity.setData(department);
        return entity;
    }

    @PostMapping("/save")
    public JsonResponseEntity<Department> save(@RequestBody Department department) {
        JsonResponseEntity<Department> entity = new JsonResponseEntity<>();
        department.setUpdateTime(new Date());
        if (department.getUpperDeptCode().equals(department.getDeptCode())) {
            throw new CommonException(2011, "上级科室无效");
        }
        departmentService.saveDepartment(department);
        entity.setData(department);
        entity.setMsg("保存成功!");
        return entity;
    }

    @PostMapping("/del")
    public JsonResponseEntity<Department> del(@RequestParam String hospitalCode, @RequestParam Integer id) {
        JsonResponseEntity<Department> entity = new JsonResponseEntity<>();
        departmentService.delDeptsByIds(hospitalCode, new Integer[]{id});
        entity.setMsg("删除成功!");
        return entity;
    }

    @PostMapping(path = "upload")
    public JsonResponseEntity<Map<String, Object>> upload(MultipartFile file) {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<>();
        Map<String, Object> info = new HashMap<>();
        List<Map<String, Object>> tables = ExcelParse.parseExcelFile(file, xlsFieldNames, xlsFieldNamesNoEmpty);
        List<String> errorMsgs = new ArrayList<>();
        for (Map<String, Object> data : tables) {
            Department department = new Department();
            String rowNum = data.get("rowNum").toString();
            try {
                BeanUtils.populate(department, data);
                departmentService.saveDepartment(department);
            } catch (CommonException ce) {
                data.put("error", ce.msg());
                errorMsgs.add("第" + rowNum + "行 : " + ce.msg());
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("第" + rowNum + "行 : " + e.getMessage());
            }
            data.put("dealResult", department.getId());
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
}
