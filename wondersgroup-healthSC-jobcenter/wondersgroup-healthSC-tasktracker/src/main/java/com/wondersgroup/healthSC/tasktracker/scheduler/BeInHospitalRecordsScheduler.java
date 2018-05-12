package com.wondersgroup.healthSC.tasktracker.scheduler;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import com.wondersgroup.healthSC.services.interfaces.BeInHospitalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by nick on 2016/11/7.
 * @author nick
 * 目前还不用这块的数据,所以先注释掉
 */
//@LTS
public class BeInHospitalRecordsScheduler {

    private static Logger logger = LoggerFactory.getLogger(BeInHospitalRecordsScheduler.class);

    @Autowired
    private BeInHospitalRecordsService beInHospitalRecordsService;

//    @JobRunnerItem(shardValue = "beInHospitalRecord")
    public Result runJob(Job job) throws Throwable {
        try {
            logger.info("开始执行获取住院信息任务开始：" + job);
            beInHospitalRecordsService.handleBeInHospitalRecords();
            BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("结束执行获取住院信息任务结束");
            logger.info("结束执行获取住院信息任务结束");
        } catch (Exception e) {
            logger.info("获取住院信息任务失败", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "获取住院信息任务成功");
    }
}
