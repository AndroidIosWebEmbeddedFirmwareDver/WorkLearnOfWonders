package com.wondersgroup.healthcloud.jpa.repository.beinhospital;

import com.wondersgroup.healthcloud.jpa.entity.beinhospital.BeInHospitalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nick on 2016/11/8.
 *
 * @author nick
 */
public interface BeInHospitalRepository extends JpaRepository<BeInHospitalRecord, String> {

    @Query(nativeQuery = true, value = "select * from tb_be_in_hospital_record a where a.id_Card = ?1 and a.hospital_code = ?2 and del_flag = 0 order by a.update_time desc limit ?3, ?4")
    List<BeInHospitalRecord> findByIdCardAndHospitalCode(String idCard, String hospitalCode,
                                                         int start, int end);

    @Query(nativeQuery = true, value = "select count(*) from tb_be_in_hospital_record a where a.id_Card = ?1 and a.hospital_code = ?2 and a.del_flag = 0 order by a.update_time desc")
    int countByIdCard(String idCard, String hospitalCode);
}
