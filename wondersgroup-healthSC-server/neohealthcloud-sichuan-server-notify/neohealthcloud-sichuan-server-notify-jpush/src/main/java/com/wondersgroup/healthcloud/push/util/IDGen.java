package com.wondersgroup.healthcloud.push.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;


public class IDGen {

    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return StringUtils.remove(uuid, "-");
    }
}
