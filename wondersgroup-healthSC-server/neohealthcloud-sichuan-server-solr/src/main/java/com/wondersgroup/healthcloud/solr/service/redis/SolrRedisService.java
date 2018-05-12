package com.wondersgroup.healthcloud.solr.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Service
@Transactional
public class SolrRedisService {

    public final static String DOCTOR_KEY = "schealthcloud:solr:lastupdatetime:doctor";
    public final static String HOSPITAL_KEY = "schealthcloud:solr:lastupdatetime:hospital";
    public final static String NEWS_ARTICLE_KEY = "schealthcloud:solr:lastupdatetime:article";

    @Autowired
    JedisPool jedisPool;

    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        } catch (Exception e) {
            log.error("Redis缓存数据失败！");
        }
    }

    public String get(String key) {
        String value = null;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("Redis缓存数据失败！");
        }
        return value;
    }

}
