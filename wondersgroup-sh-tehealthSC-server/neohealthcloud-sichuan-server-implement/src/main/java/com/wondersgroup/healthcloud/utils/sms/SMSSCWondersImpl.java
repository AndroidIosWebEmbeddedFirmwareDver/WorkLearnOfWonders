package com.wondersgroup.healthcloud.utils.sms;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhaozhenxing on 2016/11/08.
 */
public class SMSSCWondersImpl implements SMS {

    private final String url;
    private final String corpID;
    private final String pwd;

    private HttpRequestExecutorManager manager;

    public void setHttpManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }

    public SMSSCWondersImpl(String url, String corpID, String pwd) {
        this.url = url;
        this.corpID = corpID;
        this.pwd = pwd;
    }

    /**
     * @param mobile
     * @param content
     */
    @Override
    public void send(String mobile, String content) {
        /*
         * CorpID    账号
         * Pwd       密码
         * Mobile    发送手机号码（多号码以","分隔，如:13812345678,13519876543，建议100个号码）
         * Content   发送内容（平台编码:GB2312）
         * Cell      扩展号（必须为数字或为空）
         * SendTime  定时发送时间（可为空，长度:14，如:20160912152435）
         */
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request request = new RequestBuilder().post().url(url).params(new String[]{"CorpID", corpID, "Pwd", pwd, "Mobile", mobile, "Content", content, "Cell", ""}).build();
        JsonNodeResponseWrapper result = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = result.convertBody();
        /*
            大于0的数字	提交成功
            -1    账号未注册
            -2    其他错误
            -3    帐号或密码错误
            -5    余额不足，请充值
            -6    定时发送时间不是有效的时间格式
            -7    提交信息末尾未签名，请添加中文的企业签名【】
            -8	  发送内容需在1到300字之间
            -9	  发送号码为空
            -10	  定时时间不能小于系统当前时间
            -100  IP黑名单
            -102  账号黑名单
            -103  IP未导白
        */
        if (!(Integer.parseInt(node.asText()) > 0)) {
            throw new SMSFailureException();
        }
    }
}
