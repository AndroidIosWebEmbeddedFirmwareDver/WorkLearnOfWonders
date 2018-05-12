package com.wondersgroup.healthcloud.services.hospital;

import com.wondersgroup.healthcloud.entity.response.ScheduleNumInfo;
import com.wondersgroup.healthcloud.entity.response.department.DepInfo;
import com.wondersgroup.healthcloud.entity.response.department.DepTwoInfo;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.services.hospital.dto.DepartmentInfoDTO;
import com.wondersgroup.healthcloud.services.hospital.dto.DeptsMenuDTO;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/11/3.
 */
public interface DepartmentService {

    /**
     * 科室查询
     *
     * @param hospitalCode
     * @return
     */
    List<Department> queryDept(String hospitalCode, String topHosDeptCode);

    List<Department> querySpecialDept(String hospitalCode);

    List<ScheduleNumInfo> queryScheduleNumInfo(String time, String hosOrgCode, String hosDeptCode, String hosDoctCode, String area);

    List<ScheduleNumInfo> queryScheduleNumInfo(String time, String hosOrgCode, String hosDeptCode, String area);

    List<DoctorInfoDTO> getScheduleByTime(List<ScheduleNumInfo> scheduleNumInfos);

    List<DoctorInfoDTO> getDoctorInfo(List<Doctor> doctors, String area);

    /**
     * 专家预约列表查询
     *
     * @param hospitalCode
     * @param hosDeptCode
     * @return
     */
    List<DoctorInfoDTO> queryScheduleList(String hospitalCode, String hosDeptCode, String area);

    void saveDepartment(Department department);

    void delDeptsByIds(String hospitalCode, Integer[] ids);

    List<DepartmentInfoDTO> getAllDepartmentList(String hospitalCode, int page, int pageSize);

    List<DepartmentInfoDTO> getDepartmentList(Map parms, int page, int pageSize);

    DeptsMenuDTO getDeptsMenu(String hospitalCode);

    List<Department> getTopDepts(String hospitalCode);

    public List<Department> getSecondDepts(String hospitalCode);

    int countDepartment(String hospitalCode);

    int countDepartment(String hospitalCode, String upperDeptCode);

    List<DepartmentInfoDTO> getDepartmentList(String hospitalCode, String upperDeptCode, int page, int pageSize);

}
