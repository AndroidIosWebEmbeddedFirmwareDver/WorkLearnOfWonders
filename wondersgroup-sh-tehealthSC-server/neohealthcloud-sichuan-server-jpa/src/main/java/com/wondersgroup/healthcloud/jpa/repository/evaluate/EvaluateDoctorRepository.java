package com.wondersgroup.healthcloud.jpa.repository.evaluate;

import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by longshasha on 16/11/1.
 */
public interface EvaluateDoctorRepository extends JpaRepository<EvaluateDoctor, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update EvaluateDoctor set status = ?1 ,isTop = 0  where id in ?2")
    void batchSetStatuByIds(Integer status, List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "update EvaluateDoctor set status = ?1 where id = ?2")
    void setStatuById(Integer status, Integer id);


    @Transactional
    @Modifying
    @Query(value = "update EvaluateDoctor set isTop = ?1 where id = ?2")
    void setIsTopById(Integer isTop, Integer id);

    @Query(value = "select count(1) from EvaluateDoctor  where isTop = 1")
    int countTopEvaluate();

    @Query(value = "select a from EvaluateDoctor a where a.orderId = ?1")
    EvaluateDoctor findByOrderId(Integer orderId);

}
