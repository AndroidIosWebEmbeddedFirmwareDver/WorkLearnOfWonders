package com.wondersgroup.healthcloud.utils.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
 * Created by zhangzhixiu on 16/4/20.
 */
@Component
public class VerificationFrequencyChecker {

    private static final String smsPrefix = "sc:freq:sms:";
    private static final int smsTimeout = 1 * 60;
    private static final String passwordPrefix = "sc:freq:pwd:";
    private static final int passwordTimeout = 10 * 60;
    private static final int maxErrorCount = 5;

    private JedisPool jedisPool;

    public void smsCheckBeforeVerification(String mobile) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = smsKey(mobile);
            String count = jedis.get(key);
            if (count != null && Integer.valueOf(count) >= maxErrorCount) {
                jedis.expire(key, smsTimeout);//update ttl in redis
                throw new VerificationTooManyRequestException();
            }
        }
    }

    public void passwordCheckBeforeVerification(String account) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = passwordKey(account);
            String count = jedis.get(key);
            if (count != null && Integer.valueOf(count) >= maxErrorCount) {
                jedis.expire(key, passwordTimeout);//update ttl in redis
                throw new VerificationTooManyRequestException();
            }
        }
    }

    public void smsMarkAfterFailure(String mobile) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = smsKey(mobile);
            Long value = jedis.incr(key);
            if (value.intValue() <= 1) {
                jedis.expire(key, smsTimeout);
            }
        }
    }

    public void passwordMarkAfterFailure(String account) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = passwordKey(account);
            Long value = jedis.incr(key);
            if (value.intValue() <= 1) {
                jedis.expire(key, smsTimeout);
            }
        }
    }

    private String smsKey(String mobile) {
        return smsPrefix + mobile;
    }

    private String passwordKey(String account) {
        return passwordPrefix + account;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
