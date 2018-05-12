package com.wondersgroup.healthSC.tasktracker.scheduler;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import com.wondersgroup.healthSC.services.interfaces.ClinicPayService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by nick on 2016/11/17.
 */
@LTS
public class ClinicPayScheduler {

    @Autowired
    private ClinicPayService clinicPayService;

    private Logger logger = LoggerFactory.getLogger(ClinicPayScheduler.class);

    @JobRunnerItem(shardValue = "ClinicOrderScanJob")
    public Result runJob(Job job) throws Throwable {
        try {
            logger.info("开始处理未支付诊间支付订单：" + job);
            clinicPayService.handlerNotPayClinicOrder();
            BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("结束处理未支付诊间支付订单：");
            logger.info("结束处理未支付诊间支付订单");
        } catch (Exception e) {
            logger.info("处理未支付诊间支付订单任务失败", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "处理未支付诊间支付订单任务成功");
    }
}
