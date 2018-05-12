package com.wondersgroup.healthcloud.push.JPush;

import java.util.Map;

/**
 * Created by jialing.yao on 2017-5-9.
 */
public interface JPushService {
    Boolean sendAll(String content, Map<String, String> extras);

    Boolean sendAlias(String alias, String content, Map<String, String> extras);
}
