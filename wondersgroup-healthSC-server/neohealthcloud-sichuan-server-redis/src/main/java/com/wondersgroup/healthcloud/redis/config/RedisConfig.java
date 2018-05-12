package com.wondersgroup.healthcloud.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

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
 * <p/>
 * Created by zhangzhixiu on 15/12/16.
 */
@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean
    public JedisPool redisConnectionFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1000);
        config.setMaxIdle(1000);
        config.setMinIdle(1);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        config.setTestOnReturn(false);
        config.setBlockWhenExhausted(true);
        config.setJmxEnabled(true);
        config.setJmxNamePrefix("jedis-pool");
        config.setNumTestsPerEvictionRun(100);
        config.setTimeBetweenEvictionRunsMillis(60000);
        config.setMinEvictableIdleTimeMillis(300000);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        config.setTimeBetweenEvictionRunsMillis(600 * 1000);

        String host = env.getProperty("redis.connection.master.url");
        Integer port = Integer.valueOf(env.getProperty("redis.connection.master.port"));

        String password = env.getProperty("redis.connection.master.password");

        if (password == null) {
            return new JedisPool(config, host, port);
        } else {
            return new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, password);
        }
    }
}
