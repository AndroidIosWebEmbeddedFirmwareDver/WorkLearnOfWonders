package com.wondersgroup.healthcloud.services.healthRecord;

import com.wondersgroup.healthcloud.jpa.config.JPAConfig;
import com.wondersgroup.healthcloud.redis.config.RedisConfig;
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

/**
 * Created by ys on 16-11-3.
 */
@ActiveProfiles("de")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HealthRecordServiceImpl.class})
@Import(value = {JPAConfig.class, RedisConfig.class})
public class HealthRecordServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private HealthRecordService healthRecordService;

    @Test
    public void testGetJiuzhenList() {
        try {
            HealthJiuZhenListResponse response = healthRecordService.getJiuZhenList("513024195101259705", 1, 20, "510000000000");
            System.out.println(response.getResultCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetZhuYuanList() {
        try {
            healthRecordService.getZhuYuanList("450820268100001393461", 1, 20, "510000000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
