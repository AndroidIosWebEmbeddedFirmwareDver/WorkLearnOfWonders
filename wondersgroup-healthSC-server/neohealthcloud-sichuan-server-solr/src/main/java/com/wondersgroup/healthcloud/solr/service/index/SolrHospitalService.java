package com.wondersgroup.healthcloud.solr.service.index;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wondersgroup.healthcloud.solr.document.SolrHospital;
import com.wondersgroup.healthcloud.solr.repository.SolrHospitalRepository;
import com.wondersgroup.healthcloud.solr.service.redis.SolrRedisService;
import com.wondersgroup.healthcloud.solr.utils.Segmenter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SolrHospitalService {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    SolrHospitalRepository solrHospitalRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SolrRedisService solrRedisService;

    public void save(SolrHospital hospital) {
        solrHospitalRepository.save(hospital);
    }

    public void fullImport() {
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    solrHospitalRepository.deleteAll();
                    log.info("Step.1 清空医院信息成功！");

                    String sql = "select * from tb_hospital_info";
                    Object[] args = new Object[]{};
                    List<SolrHospital> resultList = jdbcTemplate.query(sql, args,
                            new BeanPropertyRowMapper<SolrHospital>(SolrHospital.class));

                    removeDirty(resultList);
                    if (resultList.size() > 0) {
                        solrHospitalRepository.save(resultList);
                    }

                    log.info("Step.2 导入医院信息结束，共导入 " + resultList.size() + " 条数据");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入医院信息失败，失败原因：" + e.getMessage());
        }

    }

    public void deltaImport() {
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    String timestr = solrRedisService.get(SolrRedisService.HOSPITAL_KEY);
                    long time = Long.valueOf(StringUtils.isEmpty(timestr) ? "0" : timestr);
                    Date date = new Date(time);
                    time = System.currentTimeMillis();
                    String sql = "select * from tb_hospital_info where update_time > ?";
                    Object[] args = new Object[]{date};
                    RowMapper<SolrHospital> rowMapper = new BeanPropertyRowMapper<SolrHospital>(SolrHospital.class);
                    List<SolrHospital> resultList = jdbcTemplate.query(sql, args, rowMapper);

                    removeDirty(resultList);
                    if (resultList.size() > 0) {
                        solrHospitalRepository.save(resultList);
                    }

                    log.info("导入医生信息结束，共导入 " + resultList.size() + " 条数据");

                    solrRedisService.set(SolrRedisService.HOSPITAL_KEY, time + "");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入医院信息失败，失败原因：" + e.getMessage());
        }
    }

    private void removeDirty(List<SolrHospital> list) {
        Iterator<SolrHospital> it = list.iterator();
        while (it.hasNext()) {
            SolrHospital solrHospital = it.next();
            if (StringUtils.isBlank(solrHospital.getHospitalCode())) {
                it.remove();
            } else {
                String name = solrHospital.getHospitalName();
                if (StringUtils.isNotBlank(name)) {
                    solrHospital.setText(Segmenter.getTerms(name));
                    solrHospital.setIkText(Segmenter.getWordByIK(name));
                }


            }
        }
    }

}
