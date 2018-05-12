package com.wondersgroup.healthSC.services.jpa.repository;

import com.wondersgroup.healthSC.services.jpa.entity.BeInHospitalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nick on 2016/11/7.
 */
public interface BeInHospitalRecordRepository extends JpaRepository<BeInHospitalRecord, String>{

    BeInHospitalRecord getByIdCard(String idCard);
}
