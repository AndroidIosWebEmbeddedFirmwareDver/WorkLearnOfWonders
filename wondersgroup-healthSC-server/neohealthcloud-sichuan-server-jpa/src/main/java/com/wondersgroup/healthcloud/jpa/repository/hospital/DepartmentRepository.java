package com.wondersgroup.healthcloud.jpa.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 " +
            " order by del_flag asc, id desc limit ?2,?3")
    List<Department> queryByHospitalCode(String hospitalCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and dept_code in ?2")
    List<Department> selectByHospitalCodeAndDeptCodes(String hospitalCode, Iterable<String> deptCodes);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1")
    List<Department> selectByHospitalCode(String hospitalCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and dept_code=?2")
    Department selectByHospitalCodeAndDeptCode(String hospitalCode, String deptCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and upper_dept_code= '-1' order by del_flag asc")
    List<Department> queryAllTopDeps(String hospitalCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and upper_dept_code!= '-1' and del_flag!= '1' order by dept_name")
    List<Department> queryAllSecondDepts(String hospitalCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 " +
            " and upper_dept_code<>'-1' and is_special='1' and del_flag = '0'")
    List<Department> querySpecialDeps(String hospitalCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and upper_dept_code=?2 and del_flag = '0'")
    List<Department> queryDepsByUpperDeptCode(String hospitalCode, String upperDeptCode);

    @Query(nativeQuery = true, value = "select * from tb_department_info where hospital_code=?1 and upper_dept_code= '-1' and del_flag = '0'")
    List<Department> queryTopDeps(String hospitalCode);

    @Transactional
    @Modifying
    @Query("update Department set delFlag='1' where id in ?1")
    void deleteByIds(Iterable<Integer> ids);
}
