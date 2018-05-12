package com.wondersgroup.healthSC.services.jpa.repository;

import com.wondersgroup.healthSC.services.jpa.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Integer>{
    @Query(nativeQuery = true ,value = "select a.* from tb_doctor_info a where a.headphoto is null and \n" +
            " EXISTS (SELECT HOSEMPLCODE from orderweb_md_employee \n" +
            " where DOCTPHOTO IS not null AND HOSEMPLCODE = doctor_code  AND HOSDEPTCODE= dept_code AND HOSORGCODE = hospital_code)\n")
    List<Doctor> getNoPicDoctor();
}
