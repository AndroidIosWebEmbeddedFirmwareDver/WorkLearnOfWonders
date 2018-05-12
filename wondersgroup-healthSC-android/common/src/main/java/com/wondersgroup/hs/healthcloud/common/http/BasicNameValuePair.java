package com.wondersgroup.hs.healthcloud.common.http;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 15:02
 */
public class BasicNameValuePair implements NameValuePair {
    private String name;
    private String value;

    public BasicNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
