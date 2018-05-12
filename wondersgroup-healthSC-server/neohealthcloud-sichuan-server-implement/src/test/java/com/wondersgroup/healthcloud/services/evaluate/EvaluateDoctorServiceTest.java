package com.wondersgroup.healthcloud.services.evaluate;

import com.wondersgroup.healthcloud.jpa.config.JPAConfig;
import com.wondersgroup.healthcloud.redis.config.RedisConfig;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;
import com.wondersgroup.healthcloud.services.evaluate.impl.EvaluateDoctorServiceImpl;
import com.wondersgroup.healthcloud.services.healthRecord.HealthRecordService;
import com.wondersgroup.healthcloud.services.healthRecord.impl.HealthRecordServiceImpl;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthJiuZhenListResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ys on 16-11-3.
 */
@ActiveProfiles("de")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {EvaluateDoctorServiceImpl.class})
@Import(value = {JPAConfig.class, RedisConfig.class})
public class EvaluateDoctorServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EvaluateDoctorService evaluateDoctorService;

    @Test
    public void testGetJiuzhenList() {
        try {
            List<AppEvaluateDoctorListDTO> list = evaluateDoctorService.findValidListByDoctorId(2, 1, 10);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
