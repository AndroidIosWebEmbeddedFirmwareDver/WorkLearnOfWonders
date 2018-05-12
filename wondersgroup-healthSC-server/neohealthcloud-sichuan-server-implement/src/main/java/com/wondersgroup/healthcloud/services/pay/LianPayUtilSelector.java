package com.wondersgroup.healthcloud.services.pay;

import com.google.common.collect.ImmutableMap;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
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
 * <p>
 * Created by zhangzhixiu on 13/12/2016.
 */
@Component
public class LianPayUtilSelector implements InitializingBean {

    @Value("${lianpay.url}")
    private String url;

    @Value("${lianpay.appId.sc}")
    private String appIdSC;
    @Value("${lianpay.appSecret.sc}")
    private String appSecretSC;

    @Value("${lianpay.appId.pj}")
    private String appIdPJ;
    @Value("${lianpay.appSecret.pj}")
    private String appSecretPJ;

    @Value("${lianpay.appId.sl}")
    private String appIdSL;
    @Value("${lianpay.appSecret.sl}")
    private String appSecretSL;

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private JedisPool pool;

    private Map<String, LianPayUtil> nameMap;
    private Map<String, LianPayUtil> idMap;


    @Override
    public void afterPropertiesSet() throws Exception {
        ImmutableMap.Builder<String, LianPayUtil> nameBuilder = ImmutableMap.builder();
//        ImmutableMap.Builder<String, LianPayUtil> idBuilder = ImmutableMap.builder();
        idMap = new HashMap<>();

        LianPayKeyUtil scKey = new LianPayKeyUtil("sc", url, appIdSC, appSecretSC);
        scKey.setManager(manager);
        scKey.setPool(pool);
        LianPayUtil scUtil = new LianPayUtil("sc", url, appIdSC, appSecretSC);
        scUtil.setManager(manager);
        scUtil.setKeyUtil(scKey);
        nameBuilder.put("sc", scUtil);
        idMap.put(appIdSC, scUtil);

        LianPayKeyUtil pjKey = new LianPayKeyUtil("pj", url, appIdPJ, appSecretPJ);
        pjKey.setManager(manager);
        pjKey.setPool(pool);
        LianPayUtil pjUtil = new LianPayUtil("pj", url, appIdPJ, appSecretPJ);
        pjUtil.setManager(manager);
        pjUtil.setKeyUtil(pjKey);
        nameBuilder.put("pj", pjUtil);
        idMap.put(appIdPJ, pjUtil);

        LianPayKeyUtil slKey = new LianPayKeyUtil("sl", url, appIdSL, appSecretSL);
        slKey.setManager(manager);
        slKey.setPool(pool);
        LianPayUtil slUtil = new LianPayUtil("sl", url, appIdSL, appSecretSL);
        slUtil.setManager(manager);
        slUtil.setKeyUtil(slKey);
        nameBuilder.put("sl", slUtil);
        idMap.put(appIdSL, slUtil);

        this.nameMap = nameBuilder.build();
//        this.idMap = idBuilder.build();
    }

    public LianPayUtil getByName(String key) {
        LianPayUtil result = nameMap.get(key);
        if (result == null) {
            return nameMap.get("sc");
        } else {
            return result;
        }
    }

    public LianPayUtil getById(String appId) {
        if (idMap.containsKey(appId)) {
            return idMap.get(appId);
        }
        LianPayKeyUtil scKey = new LianPayKeyUtil("sc", url, appId, appId);
        scKey.setManager(manager);
        scKey.setPool(pool);
        LianPayUtil scUtil = new LianPayUtil("sc", url, appId, appId);
        scUtil.setManager(manager);
        scUtil.setKeyUtil(scKey);
        idMap.put(appId, scUtil);

        return idMap.get(appId);
    }

}
