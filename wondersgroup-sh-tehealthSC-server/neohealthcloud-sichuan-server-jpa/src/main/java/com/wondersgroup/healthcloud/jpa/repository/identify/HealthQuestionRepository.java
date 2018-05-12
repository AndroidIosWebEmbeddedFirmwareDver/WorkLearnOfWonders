package com.wondersgroup.healthcloud.jpa.repository.identify;


import com.wondersgroup.healthcloud.jpa.entity.identify.HealthQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author wang
 */
public interface HealthQuestionRepository extends JpaRepository<HealthQuestion, String> {
    @Query(value = "select hq from HealthQuestion hq where registerid=?1 and type = 1 order by testtime desc")
    List<HealthQuestion> findResultByRegisterId(String registerid);

    @Query(value = "select hq from HealthQuestion hq where registerid=?1 and type = ?2 order by testtime desc")
    List<HealthQuestion> findRecentResultByType(String registerid, String type);
}
