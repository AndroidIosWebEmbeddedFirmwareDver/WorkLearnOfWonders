package com.wondersgroup.healthcloud.services.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ys on 2017/04/15.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeptsMenuDTO implements Serializable {

    private List<DeptCodeName> firstDepts = new ArrayList<>();

    private Map<String, List<DeptCodeName>> secondDepts = new HashMap<>();

    @Data
    public static class DeptCodeName {
        private Integer id;
        //科室代码
        private String deptCode;
        //科室名称
        private String deptName;
        private String delFlag = "0";

        public DeptCodeName(String deptCode, String deptName) {
            this.deptCode = deptCode;
            this.deptName = deptName;
        }

        public DeptCodeName(Department department) {
            this.id = department.getId();
            this.deptCode = department.getDeptCode();
            this.deptName = department.getDeptName();
            this.delFlag = department.getDelFlag();
        }
    }


}
