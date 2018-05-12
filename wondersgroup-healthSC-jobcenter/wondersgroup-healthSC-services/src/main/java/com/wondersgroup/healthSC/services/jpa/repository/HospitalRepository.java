package com.wondersgroup.healthSC.services.jpa.repository;

import com.wondersgroup.healthSC.services.jpa.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
public interface HospitalRepository extends JpaRepository<Hospital,Integer>{
    @Query(nativeQuery = true,value ="select a.* from tb_hospital_info a where a.hosptial_photo is null and " +
            " a.hospital_code in (select HOSORGCODE from orderweb_md_org where HOSPHOTO is not null )")
    List<Hospital> getNoPicHospital();

    @Query(nativeQuery = true, value = "select * from tb_hospital_info a where a.del_flag = '0' and a.status = '1'")
    List<Hospital> getAllHospitals();
}
