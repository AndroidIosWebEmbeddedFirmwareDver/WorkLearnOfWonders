package com.wondersgroup.jobtracker.beinhospital.test;

import com.wondersgroup.healthSC.services.interfaces.BeInHospitalRecordsService;
import com.wondersgroup.healthSC.tasktracker.startup.TaskTrackerStartUp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by nick on 2016/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TaskTrackerStartUp.class)
@ActiveProfiles("de")
public class BeInHospitalRecordsTest {

    @Autowired
    private BeInHospitalRecordsService beInHospitalRecordsService;

    @Test
    public void fetchBeInHospitalRecords(){
        beInHospitalRecordsService.handleBeInHospitalRecords();
    }
}
