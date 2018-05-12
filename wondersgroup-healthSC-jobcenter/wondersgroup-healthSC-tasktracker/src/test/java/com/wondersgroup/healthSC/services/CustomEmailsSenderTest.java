package com.wondersgroup.healthSC.services;

import com.wondersgroup.healthSC.services.impl.CustomEmailsSender;
import com.wondersgroup.healthSC.services.interfaces.BeInHospitalRecordsService;
import com.wondersgroup.healthSC.tasktracker.startup.TaskTrackerStartUp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ys on 2017/04/25
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TaskTrackerStartUp.class)
@ActiveProfiles("de")
public class CustomEmailsSenderTest {

    @Autowired
    private CustomEmailsSender customEmailsSender;

    @Test
    public void sendOrderReportingDataToHospitalTest(){
        customEmailsSender.sendOrderReportingDataToHospital();
    }
}
