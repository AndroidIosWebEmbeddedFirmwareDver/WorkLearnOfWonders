package com.wondersgroup.healthSC.jobclient.synchron.mail;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.wondersgroup.healthSC.services.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by ys on 2017/04/25.
 * 发送订单报表信息to定制的医院邮箱
 */
@Component
public class SendReportingMailsToHospital implements CommandLineRunner {
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
        job.setTaskId("customEmailsSenderTask");
        job.setParam("jobType","customEmailsSenderTask");
        job.setCronExpression("0 30 0 * * ?");//每天凌晨发送
        job.setTaskTrackerNodeGroup(environment.getProperty("lts.jobclient.tasknodegroup"));
        jobClient.submitJob(job);
    }
}
