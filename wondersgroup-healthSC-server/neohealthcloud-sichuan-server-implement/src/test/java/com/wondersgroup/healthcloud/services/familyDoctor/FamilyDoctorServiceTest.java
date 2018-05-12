package com.wondersgroup.healthcloud.services.familyDoctor;

/**
 * Created by ys on 17-8-3.
 * 家庭医生 测试
 */

import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyDoctorInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamInfoDTO;
import com.wondersgroup.healthcloud.services.familyDoctor.dto.FamilyTeamListDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ActiveProfiles("te")
@RunWith(SpringRunner.class)
@SpringBootApplication(scanBasePackages = "com.wondersgroup.healthcloud")
@SpringBootTest
public class FamilyDoctorServiceTest {

    private final Logger logger = LoggerFactory.getLogger(FamilyDoctorServiceTest.class);

    @Autowired
    private FamilyDoctorService familyDoctorService;

    /**
     * 附近的团队列表
     */
    @Test
    public void testGetNearbyTeamList() {
        List<FamilyTeamListDTO> doctorList = familyDoctorService.getFamilyDoctorList("30.680045", "104.06786", 3.3, 1, 10);
        logger.warn(JsonMapper.nonDefaultMapper().toJson(doctorList));
    }

    /**
     * 团队详情
     */
    @Test
    public void testGetFamilyTeamInfo() {
        FamilyTeamInfoDTO familyTeamInfoDTO = familyDoctorService.getFamilyTeamInfo("1", "1000000001");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(familyTeamInfoDTO));
    }

    /**
     * 医生详情
     */
    @Test
    public void testGetFamilyDoctorInfo() {
        FamilyDoctorInfoDTO doctorInfoDTO = familyDoctorService.getFamilyDoctorInfo("", "123", "");
        logger.warn(JsonMapper.nonDefaultMapper().toJson(doctorInfoDTO));
    }
}
