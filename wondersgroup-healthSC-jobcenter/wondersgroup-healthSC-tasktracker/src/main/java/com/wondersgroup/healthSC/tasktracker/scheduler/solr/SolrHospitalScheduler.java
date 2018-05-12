package com.wondersgroup.healthSC.tasktracker.scheduler.solr;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tanxueliang on 16/11/5.
 */
@LTS
@Slf4j
public class SolrHospitalScheduler extends SolrBaseScheduler {

    @JobRunnerItem(shardValue = "solrHospital")
    public Result runJob(Job job) throws Throwable {
        try {
            curl("/api/solr/hospital/deltaImport");
        } catch (Exception e) {
            log.info("同步SOLR医院信息信息失败", e);
            return new Result(Action.EXECUTE_LATER, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "同步SOLR医院信息成功");
    }
}
