package com.wondersgroup.healthcloud.jpa.repository.hospital;

import java.util.List;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    Hospital findByHospitalCode(String hospitalCode);

    List<Hospital> findByAppid(String appid);

    @Query(nativeQuery = true, value = "select * from tb_hospital_info where city_code in ?1 and status = '1' and del_flag = '0' and city_code<>'510122000000' limit ?2,?3")
    List<Hospital> queryByHospitalCode(List<String> cityCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select * from tb_hospital_info where city_code in ?1 and status = '1' and del_flag = '0' limit ?2,?3")
    List<Hospital> queryAreaHospital(List<String> cityCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select * from tb_hospital_info where hospital_name like ?1 and status = '1' and del_flag = '0' limit ?2,?3")
    List<Hospital> findByName(String hospitalName, int start, int end);

    @Query(nativeQuery = true, value = "select * from tb_hospital_info where city_code in ?1 and status = '1' and del_flag = '0' limit ?2,?3")
    List<Hospital> findByCity(List<String> area, int start, int end);

    @Query(nativeQuery = true, value = "select * from tb_hospital_info where " +
            " hosptial_longitude between ?1 and ?2 and hospital_latitude between ?3 and ?4 and status = '1' and del_flag = '0'")
    List<Hospital> findByArea(double longitude_start, double longitude_end, double latitude_start, double latitude_end);

    Hospital findByHospitalCodeAndDelFlag(String hospitalCode, String delFlag);

    @Query("select a from Hospital a where a.status = ?1 and a.delFlag = ?2 and a.hospitalName like ?3")
    Page<Hospital> findByStatusAndDelFlagAndHospitalNameLike(String status, String delFlag, String hospitalName, Pageable pageable);

    @Query(nativeQuery = true, value = "select hos.* from app_tb_favorite_hospital fav " +
            " left join tb_hospital_info hos on fav.hos_id=hos.id " +
            " where fav.user_id=?1 and hos.city_code=?2 and hos.id is not null order by fav.update_time desc limit ?3,?4")
    List<Hospital> getUserFavoriteHospitals(String uid, String cityCode, int pageNo, int pageSize);

    @Query(nativeQuery = true, value = "select hos.* from app_tb_favorite_hospital fav " +
            " left join tb_hospital_info hos on fav.hos_id=hos.id " +
            " where fav.user_id=?1 and hos.id is not null order by fav.update_time desc limit ?2,?3")
    List<Hospital> getUserFavoriteHospitals(String uid, int pageNo, int pageSize);
}
