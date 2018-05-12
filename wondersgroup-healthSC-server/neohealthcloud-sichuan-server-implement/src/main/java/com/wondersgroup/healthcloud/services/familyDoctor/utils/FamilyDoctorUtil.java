package com.wondersgroup.healthcloud.services.familyDoctor.utils;

import com.wondersgroup.healthcloud.common.utils.JsonMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ys on 17-8-2.
 */
public class FamilyDoctorUtil {
    private static final Map<String, Object> requestHeader;
    private static final String requestHeaderJsonStr;

    static {
        requestHeader = new HashMap<>();
        requestHeader.put("zcjgdm", "注册厂商代码");
        requestHeader.put("zcjgmc", "注册厂商名称");
        requestHeader.put("bdjgdm", "510000002122");
        requestHeader.put("bdjgmc", "绑定机构名称");
        requestHeader.put("bdyydm", "绑定应用代码");
        requestHeader.put("bdyymc", "绑定应用名称");
        requestHeader.put("jkdm", "CDJTYSQY0002");
        requestHeader.put("jkmc", "接口名称");
        requestHeader.put("username", "admin");
        requestHeader.put("password", "111111");
        requestHeader.put("bdczxtdm", "绑定操作系统代码");
        requestHeader.put("bdczxtmc", "绑定操作系统名称");
        requestHeaderJsonStr = JsonMapper.nonDefaultMapper().toJson(requestHeader);
    }

    public static Map<String, Object> getRequestHeader() {
        return requestHeader;
    }

    public static String getRequestHeaderForJson() {
        return requestHeaderJsonStr;
    }

    public static Map<String, Object> getRequestHeader(Map<String, Object> header) {
        if (null == header || header.isEmpty()) {
            return requestHeader;
        }
        Map<String, Object> newHeader = new HashMap<>();
        newHeader.putAll(requestHeader);
        newHeader.putAll(header);
        return newHeader;
    }

    public static String getRequestHeaderForJson(Map<String, Object> header) {
        return JsonMapper.nonDefaultMapper().toJson(getRequestHeader(header));
    }
}
