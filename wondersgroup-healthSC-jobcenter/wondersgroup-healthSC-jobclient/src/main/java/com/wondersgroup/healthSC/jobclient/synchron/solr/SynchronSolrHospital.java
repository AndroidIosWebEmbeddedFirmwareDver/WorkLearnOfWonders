package com.wondersgroup.healthSC.jobclient.synchron.solr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Component
public class SynchronSolrHospital implements CommandLineRunner {
    @Autowired
    private JobClient jobClient;

    @Autowired
    private Environment environment;

    @Override
    public void run(String... strings) throws Exception {

        Job job = new Job();
        job.setTaskId("healthSC_synchronSolrHospital");
        job.setParam("jobType","solrHospital");
        job.setCronExpression("0 0 12 * * ?");//每天12点
        job.setTaskTrackerNodeGroup(environment.getProperty("lts.jobclient.tasknodegroup"));
        jobClient.submitJob(job);

    }
}
