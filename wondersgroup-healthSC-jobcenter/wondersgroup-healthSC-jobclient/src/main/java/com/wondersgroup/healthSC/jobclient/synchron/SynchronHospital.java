package com.wondersgroup.healthSC.jobclient.synchron;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.wondersgroup.healthSC.services.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
//@Component
public class SynchronHospital implements CommandLineRunner {
    @Autowired
    private JobClient jobClient;

    @Autowired
    private Environment environment;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public void run(String... strings) throws Exception {
        hospitalService.synchronInfo(); //初始化同步一次数据

        Job job = new Job();
        job.setTaskId("healthSC_synchronHospital");
        job.setParam("jobType","hospital");
        job.setCronExpression("59 59 23 ? * 1");//每周末晚上23:59::59执行一次
        job.setTaskTrackerNodeGroup(environment.getProperty("lts.jobclient.tasknodegroup"));
        jobClient.submitJob(job);
    }
}
