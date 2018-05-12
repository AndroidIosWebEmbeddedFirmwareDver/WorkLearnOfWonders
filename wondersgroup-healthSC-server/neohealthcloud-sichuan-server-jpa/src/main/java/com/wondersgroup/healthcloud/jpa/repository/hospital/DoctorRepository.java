package com.wondersgroup.healthcloud.jpa.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query(nativeQuery = true, value = "select * from tb_doctor_info where hospital_code=?1 and dept_code=?2 and del_flag='0' limit ?3,?4")
    List<Doctor> queryByHospitalCode(String hospitalCode, String deptCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select * from tb_doctor_info  where hospital_code=?1 and dept_code=?2 and doctor_code=?3 and del_flag='0'")
    Doctor queryByDoctorCode(String hospitalCode, String deptCode, String doctorCode);

    @Query(nativeQuery = true, value = "select * from tb_doctor_info  where hospital_code=?1 and doctor_code=?2 and del_flag='0'")
    List<Doctor> queryByDoctorCode(String hospitalCode, String doctorCode);

    @Query(nativeQuery = true, value = "select doc.* from app_tb_favorite_doctor fav " +
            " left join tb_doctor_info doc on fav.doc_id=doc.id " +
            " where fav.user_id=?1 and doc.id is not null order by fav.update_time desc limit ?2,?3")
    List<Doctor> getUserFavoriteDoctors(String uid, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select doc.* from app_tb_favorite_doctor fav " +
            " left join tb_doctor_info doc on fav.doc_id=doc.id " +
            " left join tb_hospital_info hos on doc.hospital_code = hos.hospital_code" +
            " where fav.user_id=?1 and hos.city_code = ?2 and doc.id is not null order by fav.update_time desc limit ?3,?4")
    List<Doctor> getUserFavoriteDoctors(String uid, String cityCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select * from tb_doctor_info  where hospital_code=?1 and dept_code=?2 and doctor_code=?3 ")
    Doctor findDoctorInfo(String hospitalCode, String deptCode, String doctorCode);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM app_tb_favorite_doctor WHERE user_id = ?1 AND doc_id = ?2")
    int checkFavoritedDoctor(String uid, int doctorId);
}
