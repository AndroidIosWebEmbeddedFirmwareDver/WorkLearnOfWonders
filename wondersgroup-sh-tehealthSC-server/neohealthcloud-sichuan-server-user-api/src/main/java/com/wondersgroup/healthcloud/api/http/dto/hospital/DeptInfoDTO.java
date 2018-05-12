package com.wondersgroup.healthcloud.api.http.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.entity.response.department.DepInfo;
import com.wondersgroup.healthcloud.entity.response.department.DepTwoInfo;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import lombok.Data;

import java.util.List;

/**
 * Created by dukuanxin on 2016/11/8.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeptInfoDTO {

    private String hosOrgCode;//医院代码

    private String hosDeptCode;//科室代码

    private String deptName;//科室名称

    private String deptAddr;//科室地址

    public DeptInfoDTO(DepInfo depInfo, String hosOrgCode) {
        this.hosOrgCode = hosOrgCode;
        this.hosDeptCode = depInfo.getHosDeptCode();
        this.deptName = depInfo.getDeptName();
    }

    public static List<DeptInfoDTO> infoDTO(List<DepInfo> list, String hosOrgCode) {
        List<DeptInfoDTO> infos = Lists.newArrayList();
        if (list != null) {
            for (DepInfo depInfo : list) {
                infos.add(new DeptInfoDTO(depInfo, hosOrgCode));
            }
        }
        return infos;
    }

    public DeptInfoDTO() {
    }

    public DeptInfoDTO(Department depInfo) {
        this.hosOrgCode = depInfo.getHospitalCode();
        this.hosDeptCode = depInfo.getDeptCode();
        this.deptName = depInfo.getDeptName();
        this.deptAddr = depInfo.getDeptAddr();
    }

    public static List<DeptInfoDTO> infoDTO(List<Department> list) {
        List<DeptInfoDTO> infos = Lists.newArrayList();
        if (list != null) {
            for (Department depInfo : list) {
                infos.add(new DeptInfoDTO(depInfo));
            }
        }
        return infos;
    }
}
