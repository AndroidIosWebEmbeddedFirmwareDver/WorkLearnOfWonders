package com.wondersgroup.healthcloud.jpa.repository.user;

import com.wondersgroup.healthcloud.jpa.entity.user.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by longshasha on 16/11/4.
 */
public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    @Query(nativeQuery = true, value = "select * from tb_user_verification a where a.uid = ?1 order by a.create_time desc LIMIT 1")
    Verification findLatestVerificationByUid(String uid);


    /**
     * 根据身份证号查询审核中和审核成功的记录
     *
     * @param idCard
     * @return
     */
    @Query("select a from Verification a where a.idcard = ?1 and a.verificationLevel >= 0 ")
    List<Verification> findVerificationsByVerificationLevel(String idCard);

}
