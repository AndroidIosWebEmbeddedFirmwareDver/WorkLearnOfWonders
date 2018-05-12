package com.wondersgroup.healthcloud.services.hospital;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorBaseInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/11/7.
 */
public interface DoctorService {

    List<Doctor> queryByHospitalCode(String hospitalCode, String deptCode, int pageNo, int pageSize);

    Doctor queryByDoctorCode(String hospitalCode, String deptCode, String doctorCode);

    Doctor getInfoById(Integer doctorId);

    /**
     * 根据医生ID更新医生接诊量
     *
     * @param doctorId
     */
    void updateOrderCount(int doctorId);

    /**
     * 根据医院科室医生更新医生接诊量
     *
     * @param hospitalCode
     * @param deptCode
     * @param doctorCode
     */
    void updateOrderCount(String hospitalCode, String deptCode, String doctorCode);

    /**
     * 查看指定医院科室下面是否存在该医生信息，如果没有，则到其他科室下面找，找到则复制到指定科室下
     * 找不到则返回为null
     *
     * @param hospitalCode
     * @param deptCode
     * @param doctorCode
     */
    Doctor synchronDoctor(String hospitalCode, String deptCode, String doctorCode);

    /**
     * param支持
     * {
     * "doctorName":"",
     * "hospitalCode":""
     * "delFlag":""
     * }
     */
    List<DoctorBaseInfoDTO> findList(Map<String, Object> param, int page, int pageSize);

    /**
     * param支持
     * {
     * "doctorName":"",
     * "hospitalCode":""
     * "delFlag":""
     * }
     */
    int countByMap(Map<String, Object> param);

    void saveDoctor(Doctor doctor) throws CommonException;

    void delete(Integer hosId, Integer depId, Integer docId);

    boolean checkFavoritedDoctor(String uid, int doctorId);
}
