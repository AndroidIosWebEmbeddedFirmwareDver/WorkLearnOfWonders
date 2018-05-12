package com.wondersgroup.healthcloud.im.sdk.easemob;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jialing.yao on 2017-5-26.
 */
public class Test {
    @org.junit.Test
    public void test() {
        ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();
        Object token = tokenCache.get("access_token");
        System.out.println(token == null ? "" : token.toString());
    }
}
