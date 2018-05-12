package com.wondersgroup.healthcloud.jpa.repository.pay;

import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 04/11/2016.
 */
public interface PayOrderRepository extends JpaRepository<PayOrder, String> {

    @Query("select po from PayOrder po where po.subjectId=?1 and po.subjectType=?2")
    List<PayOrder> getBySubjectIdAndType(String subjectId, SubjectType type);

    @Query("select po from PayOrder po where po.uid=?1")
    List<PayOrder> getByUid(String uid);

    PayOrder getBySubjectId(String subjectId);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 and a.status = ?2 order by a.update_time desc limit ?3, ?4")
    List<PayOrder> findByUidAndStatus(String uid, String status, int start, int end);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 and a.status = ?2 and a.city_code = ?5 order by a.update_time desc limit ?3, ?4")
    List<PayOrder> findByUidAndStatus(String uid, String status, int start, int end, String cityCode);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 order by a.update_time desc limit ?2, ?3")
    List<PayOrder> findByUid(String uid, int start, int end);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 and a.city_code = ?4 order by a.update_time desc limit ?2, ?3")
    List<PayOrder> findByUid(String uid, int start, int end, String cityCode);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 and a.subject_type = ?2 order by a.update_time desc limit ?3, ?4")
    List<PayOrder> findByUidAndSubjectType(String uid, String subjectType, int start, int end);

    @Query(nativeQuery = true, value = "select * from tb_pay_order a where a.uid = ?1 and a.subject_type = ?2 and a.city_code = ?5 order by a.update_time desc limit ?3, ?4")
    List<PayOrder> findByUidAndSubjectType(String uid, String subjectType, int start, int end, String cityCode);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 and a.status = ?2 order by a.update_time desc")
    int countByUidAndStatus(String uid, String status);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 and a.status = ?2 and a.city_code = ?3 order by a.update_time desc")
    int countByUidAndStatus(String uid, String status, String cityCode);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 order by a.update_time desc")
    int countByUid(String uid);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 and a.city_code = ?2 order by a.update_time desc")
    int countByUid(String uid, String cityCode);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 and a.subject_type = ?2 order by a.update_time desc")
    int countByUidAndSubjectType(String uid, String subjectType);

    @Query(nativeQuery = true, value = "select count(*) from tb_pay_order a where a.uid = ?1 and a.subject_type = ?2 and a.city_code = ?3 order by a.update_time desc")
    int countByUidAndSubjectType(String uid, String subjectType, String cityCode);

    @Query(nativeQuery = true, value = "select * from tb_pay_order where business like %?1%")
    List<PayOrder> findPayOrdersByPrescriptionNum(String prescriptionNum);

    @Query(nativeQuery = true, value = "select * from tb_pay_order where business like %?1% and city_code = ?2")
    List<PayOrder> findPayOrdersByPrescriptionNum(String prescriptionNum, String cityCode);

}
