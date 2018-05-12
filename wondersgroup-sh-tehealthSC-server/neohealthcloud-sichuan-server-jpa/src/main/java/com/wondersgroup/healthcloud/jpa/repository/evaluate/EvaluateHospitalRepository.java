package com.wondersgroup.healthcloud.jpa.repository.evaluate;

import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by longshasha on 16/11/1.
 */
public interface EvaluateHospitalRepository extends JpaRepository<EvaluateHospital, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update EvaluateHospital set status = ?1 , isTop = 0 where id in ?2")
    void batchSetStatuByIds(Integer status, List<Integer> ids);


    @Transactional
    @Modifying
    @Query(value = "update EvaluateHospital set status = ?1 where id = ?2")
    void setStatuById(Integer status, Integer id);


    @Transactional
    @Modifying
    @Query(value = "update EvaluateHospital set isTop = ?1 where id = ?2")
    void setIsTopById(Integer isTop, Integer id);

    @Query(value = "select count(1) from  EvaluateHospital where isTop = 1")
    int countTopEvaluate();

}
