package com.wondersgroup.healthcloud.utils.sms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

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
 * Created by zhangzhixiu on 16/3/6.
 */
@Component
public class VerifyCodeChecker {

    private static final String prefix = "sc:sms:";
    private static final String sendPrefix = "sc:sms:sent:";
    private static final int interval = 60;
    private static final int expire = 60 * 15;

    private JedisPool jedisPool;
    private VerificationFrequencyChecker frequencyChecker;
    private SMS sms;
    private SMS smssl;

    private static Random random = new Random();

    private static String randomSixDigit() {
        int i = random.nextInt(1000000) + 1000000;
        return String.valueOf(i).substring(1);
    }

    private void save(String mobile, String code) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = prefix + mobile;
            jedis.setex(key, expire, code);
            jedis.setex(sendPrefix + mobile, interval, "");
        }
    }

    private Boolean check(String mobile, String code, Boolean remove) {
        try (Jedis jedis = jedisPool.getResource()) {
            frequencyChecker.smsCheckBeforeVerification(mobile);
            String key = prefix + mobile;
            String value = jedis.get(key);
            Boolean result = value != null && value.equals(code);
            if (result && remove) {
                jedis.del(key);
            }
            if (!result) {
                frequencyChecker.smsMarkAfterFailure(mobile);
            }
            return result;
        }
    }

    private Boolean checkSendFrequency(String mobile) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = sendPrefix + mobile;
            return jedis.exists(key);
        }
    }

    public void sendVerifyCode(String mobile, String content, String cityCode) {
        if (checkSendFrequency(mobile)) {
            throw new SMSTooManyRequestException();
        }
        String code = randomSixDigit();
        save(mobile, code);
        if (StringUtils.equals(cityCode, "510122000000")) {
            smssl.send(mobile, String.format(content, code));
        } else {
            sms.send(mobile, String.format(content, code));
        }
    }

    public boolean checkVerifyCode(String mobile, String code, Boolean remove) {
        return check(mobile, code, remove);
    }


    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Autowired
    public void setFrequencyChecker(VerificationFrequencyChecker frequencyChecker) {
        this.frequencyChecker = frequencyChecker;
    }

    @Autowired
    public void setSms(SMS sms) {
        this.sms = sms;
    }

    @Autowired
    public void setSmssl(SMS smssl) {
        this.smssl = smssl;
    }
}
