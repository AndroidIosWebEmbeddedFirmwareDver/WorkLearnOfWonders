package com.wondersgroup.healthcloud.services.healthRecord;

import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianChaListResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianYanDetailResponse;
import com.wondersgroup.healthcloud.services.healthRecord.responseDto.HealthOnlineJianYanListResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ys on 17-8-16.
 * 提取报告测试
 * uid 可以 为 "0-****"  ***位身份证
 */
@ActiveProfiles("te")
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = "com.wondersgroup.healthcloud")
public class HealthOnlineServiceTest {

    @Autowired
    private HealthOnlineService healthOnlineService;

    @Test
    public void testGetJianChaList() {
        String uid = "";
        String hosCode = "";
        HealthOnlineJianChaListResponse listResponse = healthOnlineService.getJianChaList(uid, hosCode, 1);
    }

    @Test
    public void testGetJianYanList() {
        String uid = "1";
        String hosCode = "450813973";
        HealthOnlineJianYanListResponse listResponse = healthOnlineService.getJianYanList(uid, hosCode, 2);
    }

    @Test
    public void testGetJianYanDetail() {
        String id = "451209209-|-317081014919-|-4722141-|-2017-08-10";
        HealthOnlineJianYanDetailResponse listResponse = healthOnlineService.getJianYanDetail(id);
    }


}
