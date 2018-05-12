package com.wondersgroup.healthSC.jobclient.synchron.pay;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.jobclient.JobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 09/11/2016.
 */
@Component
public class PayScheduleTimer {

    @Autowired
    private JobClient jobClient;

    @Autowired
    private Environment environment;

    private Logger logger = LoggerFactory.getLogger("EX");
    public void setTimer(String id, String status, Long time) {
        Job job = new Job();
        job.setTaskId("pt" + id);
        job.setTaskTrackerNodeGroup(environment.getProperty("lts.jobclient.tasknodegroup"));
        job.setReplaceOnExist(true);
        job.setParam("jobType", "paytimer");
        job.setParam("id", id);
        job.setParam("status", status);
        job.setMaxRetryTimes(3);
        Date executeTime = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("set timer for id " + id + "run time at " + format.format(executeTime));
        job.setTriggerDate(executeTime);
        jobClient.submitJob(job);
    }
}
