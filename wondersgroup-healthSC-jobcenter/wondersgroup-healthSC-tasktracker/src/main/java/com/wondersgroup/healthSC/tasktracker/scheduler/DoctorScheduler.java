package com.wondersgroup.healthSC.tasktracker.scheduler;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import com.wondersgroup.healthSC.services.interfaces.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@LTS
public class DoctorScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorScheduler.class);

    @Autowired
    private DoctorService doctorService;


    @JobRunnerItem(shardValue = "doctor")
    public Result runJob(Job job) throws Throwable {
        try {
            LOGGER.info("开始执行同步医生任务：" + job);
            doctorService.synchronInfo();
            BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("结束执行同步医生任务");
            LOGGER.info("结束执行同步医生任务");
        } catch (Exception e) {
            LOGGER.info("同步医生信息失败", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "同步医生信息成功");
    }
}
