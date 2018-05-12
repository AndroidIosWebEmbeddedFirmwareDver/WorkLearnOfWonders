package com.wondersgroup.healthSC.tasktracker.scheduler.mail;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import com.wondersgroup.healthSC.services.impl.CustomEmailsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ys on 2017/04/25.
 *
 */
@LTS
public class CustomEmailSenderScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEmailSenderScheduler.class);

    @Autowired
    private CustomEmailsSender customEmailsSender;

    @JobRunnerItem(shardValue = "customEmailsSenderTask")
    public Result runJob(Job job) throws Throwable {
        try {
            LOGGER.info("开始执行发送医院订单流水数据：" + job);
            customEmailsSender.sendOrderReportingDataToHospital();
            BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("结束执行发送医院订单流水数据");
            LOGGER.info("结束执行发送医院订单流水数据");
        } catch (Exception e) {
            LOGGER.info("发送医院订单流水数据失败", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "发送医院订单流水数据成功");
    }
}
