package com.wondersgroup.healthSC.jobclient.synchron.hospitalPay;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 
 * Created by nick on 2016/11/7.
 * @author nick
 * 医院住院记录查询接口
 *
 */
@Component
public class ClinicNotPayOrderProcessJob implements InitializingBean{

    @Autowired
    private JobClient jobClient;

    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        Job job = new Job();
        job.setTaskId("ClinicOrderScanJob");
        job.setTaskTrackerNodeGroup(environment.getProperty("lts.jobclient.tasknodegroup"));
        job.setReplaceOnExist(true);
        job.setParam("jobType","clinicOrderScan");
        job.setCronExpression("0 0 0 * * ?");
        jobClient.submitJob(job);
    }
}
