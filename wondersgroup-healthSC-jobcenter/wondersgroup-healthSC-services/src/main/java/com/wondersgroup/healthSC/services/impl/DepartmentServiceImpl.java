package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.services.interfaces.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void synchronInfo() {
        String sql = "INSERT INTO tb_department_info(\n" +
                " hospital_code,dept_code,dept_name,upper_dept_code,dept_desc,is_special,del_flag)\n" +
                " SELECT ORGCODE,HOSDEPTCODE,DEPTNAME,PARENTDEPT,DEPT_DESC,ISSPECIAL,'0' \n" +
                " FROM orderweb_md_deptment" +
                " WHERE NOT EXISTS (SELECT id from tb_department_info where " +
                " IF(ORGCODE IS NULL,hospital_code is null,hospital_code = ORGCODE) and dept_code = HOSDEPTCODE)";
        jdbcTemplate.update(sql);

    }


}
