package com.wondersgroup.healthcloud.jpa.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wondersgroup.healthcloud.jpa.entity.pay.SLHospitalCCBInfo;

public interface SLHospitalCCBInfoRepository extends JpaRepository<SLHospitalCCBInfo, String> {

    SLHospitalCCBInfo findByHospitalId(String hospitalId);

}
