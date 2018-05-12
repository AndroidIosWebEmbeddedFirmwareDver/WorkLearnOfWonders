package com.wondersgroup.healthcloud.services.pay;

import com.wondersgroup.common.http.HttpRequestExecutorManager;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Created by dukuanxin on 05/22/2017.
 */
@Component
public class NewLianPayUtilSelector {

    @Value("${lianpay.url}")
    private String url;

    private Map<String, NewLianPayUtil> idMap = new HashedMap();

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private JedisPool pool;

    public NewLianPayUtil getNewLianPayUtil(String appId, String appSecret) {

        if (idMap.containsKey(appId)) {
            return idMap.get(appId);
        }

        NewLianPayKeyUtil newLianPayKeyUtil = new NewLianPayKeyUtil(url, appId, appSecret);
        newLianPayKeyUtil.setManager(manager);
        newLianPayKeyUtil.setPool(pool);

        NewLianPayUtil newLianPayUtil = new NewLianPayUtil(url, appId, appSecret);
        newLianPayUtil.setManager(manager);
        newLianPayUtil.setKeyUtil(newLianPayKeyUtil);
        idMap.put(appId, newLianPayUtil);
        return idMap.get(appId);
    }
}
