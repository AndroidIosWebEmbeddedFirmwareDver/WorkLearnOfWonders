package com.wondersgroup.healthcloud.services.account;

import com.google.common.collect.ImmutableMap;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

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
 * Created by zhangzhixiu on 15/12/17.
 */
@Component("sessionUtil")
public class SessionUtil {

    private static final String sessionPrefix = "sc:session:";
    private static final String userSessionPrefix = "sc:session:user:";
    private static final String adminSessionPrefix = "sc:admin:session:";
    private static final String adminUserSessionPrefix = "sc:admin:session:user";
    private static final int sessionExpireSecond = 10 * 24 * 60 * 60;
    private static final int sessionHistorySize = 2;

    private JedisPool jedisPool;

    private String generateKeyBy(String accessToken, Boolean admin) {
        return (admin ? adminSessionPrefix : sessionPrefix) + accessToken;
    }

    private String generateUserKeyBy(String userId, Boolean admin) {
        return (admin ? adminUserSessionPrefix : userSessionPrefix) + userId;
    }

    public Session get(String accessToken, Boolean admin) {
        Map<String, String> objectMap = getSessionFromRedis(generateKeyBy(accessToken, admin));
        if (objectMap != null && objectMap.size() > 0) {
            return new Session(accessToken, objectMap);
        } else {
            return null;
        }
    }


    private Map<String, String> getSessionFromRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> result = jedis.hgetAll(key);

            if (result != null) {
                jedis.expire(key, sessionExpireSecond);
            }

            return result;
        }
    }

    public Session createUser(String userId) {
        return create(userId, 0);
    }

    public Session createDoctor(String doctorId) {
        return create(doctorId, 1);
    }

    public Session createAdmin(String adminId) {
        return create(adminId, 2);
    }

    private Session create(String id, int role) {
        String accessToken = accessToken();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        if (id != null) {
            builder.put(Session.Key.id.name(), id);
        }
        builder.put(Session.Key.secret.name(), IdGen.uuid());
        builder.put(Session.Key.valid.name(), "1");
        if (role == 1) {
            builder.put(Session.Key.doctor.name(), "1");
        } else if (role == 2) {
            builder.put(Session.Key.admin.name(), "1");
        }
        Map<String, String> sessionObjectMap = builder.build();
        createSessionInRedis(generateKeyBy(accessToken, role == 2), sessionObjectMap);
        if (id != null) {
            modifyUserSession(id, accessToken, role == 2);
        }
        return new Session(accessToken, sessionObjectMap);
    }

    private void modifyUserSession(String userId, String newToken, Boolean admin) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userSessionKey = generateUserKeyBy(userId, admin);
            int sessionSize = jedis.llen(userSessionKey).intValue();
            if (sessionSize >= sessionHistorySize) {
                String toDeleteSession = jedis.rpop(userSessionKey);
                delSessionInRedis(sessionPrefix + toDeleteSession);
            }
            String todo = jedis.lindex(userSessionKey, 0);
            if (todo != null) {
                jedis.hset(sessionPrefix + todo, Session.Key.valid.name(), "0");
            }
            jedis.lpush(userSessionKey, newToken);
        }
    }

    public Session createGuest() {
        return createUser(null);
    }

    public void inactiveSession(String token, Boolean admin) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(generateKeyBy(token, admin), Session.Key.valid.name(), "0");
        }
    }

    private Map<String, String> createSessionInRedis(String token, Map<String, String> values) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hmset(token, values);
            jedis.expire(token, sessionExpireSecond);
        }
        return values;
    }

    private void delSessionInRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    private String accessToken() {
        return IdGen.uuid();
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
