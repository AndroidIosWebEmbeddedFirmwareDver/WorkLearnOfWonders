package com.wondersgroup.healthcloud.services.favorite;

import com.wondersgroup.healthcloud.jpa.entity.favorite.FavoriteDoctor;
import com.wondersgroup.healthcloud.jpa.entity.favorite.FavoriteHospital;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.favorite.FavoriteDoctorRep;
import com.wondersgroup.healthcloud.jpa.repository.favorite.FavoriteHospitalRep;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DoctorRepository;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dukuanxin on 2016/11/10.
 */
@Component
public class FavoriteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FavoriteDoctorRep favoriteDoctorRep;
    @Autowired
    private FavoriteHospitalRep favoriteHospitalRep;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getFavoriteHospitals(String uid, int page, int pageSize) {
        return hospitalRepository.getUserFavoriteHospitals(uid, (page - 1) * pageSize, pageSize + 1);
    }

    public List<Hospital> getFavoriteHospitals(String uid, int page, int pageSize, String cityCode) {
        return hospitalRepository.getUserFavoriteHospitals(uid, cityCode, (page - 1) * pageSize, pageSize + 1);
    }

    public List<Doctor> getFavoriteDoctors(String uid, int page, int pageSize) {
        return doctorRepository.getUserFavoriteDoctors(uid, (page - 1) * pageSize, pageSize + 1);
    }

    public List<Doctor> getFavoriteDoctors(String uid, int page, int pageSize, String cityCode) {
        return doctorRepository.getUserFavoriteDoctors(uid, cityCode, (page - 1) * pageSize, pageSize + 1);
    }

    public FavoriteDoctor getFavorDoctor(String userId, int docId) {
        return favoriteDoctorRep.queryFavor(docId, userId);
    }

    public FavoriteDoctorRep getFavoriteDoctorRep() {
        return favoriteDoctorRep;
    }

    public FavoriteHospitalRep getFavoriteHospitalRep() {
        return favoriteHospitalRep;
    }


    public FavoriteHospital getFavorHospital(String userId, int hosId) {
        return favoriteHospitalRep.queryFavor(hosId, userId);
    }


    /**
     * 检查是否关注过医生
     *
     * @param docId
     * @param userId
     * @return
     */
    public boolean isFavorDoctor(String userId, int docId) {
        FavoriteDoctor favoriteDoctor = favoriteDoctorRep.queryFavor(docId, userId);
        return null != favoriteDoctor;
    }

    /**
     * 检查是否收藏过医院
     *
     * @param hosId
     * @param userId
     * @return
     */
    public boolean isFavorHospital(String userId, int hosId) {
        FavoriteHospital favoriteHospital = favoriteHospitalRep.queryFavor(hosId, userId);
        return null != favoriteHospital;
    }

}
