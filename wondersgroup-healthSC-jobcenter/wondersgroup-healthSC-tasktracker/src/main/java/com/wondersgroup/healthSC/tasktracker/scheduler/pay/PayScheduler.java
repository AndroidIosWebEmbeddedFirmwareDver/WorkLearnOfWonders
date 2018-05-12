package com.wondersgroup.healthSC.tasktracker.scheduler.pay;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.LtsLoggerFactory;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.healthSC.common.exceptions.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
@LTS
public class PayScheduler {

    private static Logger logger = LoggerFactory.getLogger(PayScheduler.class);

    @Autowired
    private HttpRequestExecutorManager manager;

    @Value("${internal.api.url}")
    private String url;

    @JobRunnerItem(shardValue = "paytimer")
    public Result runJob(Job job) throws Throwable {
        try {
            String id = job.getParam("id");
            String status = job.getParam("status");

            Request request = new RequestBuilder().post().url(url + "/api/pay/timer").param("id", id).param("status", status).build();
            logger.info("call /api/pay/timer for " + id);
            StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
            String result = wrapper.convertBody();
            BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
            bizLogger.info(id + " " + result);
            if ("success".equals(result)) {
                return new Result(Action.EXECUTE_SUCCESS, "订单定时器执行成功");
            } else {
                return new Result(Action.EXECUTE_LATER, "订单定时器执行失败");
            }
        } catch (Exception e) {
            logger.error(Exceptions.getStackTraceAsString(e));
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
    }
}
