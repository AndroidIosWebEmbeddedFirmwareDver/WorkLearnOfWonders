package com.wondersgroup.healthcloud.entity;

import com.wondersgroup.healthcloud.entity.request.RequestMessageHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhaozhenxing on 2016/11/3.
 */
@Component
public class RequestMessageHeaderUtil {

    @Value("${area.platform.frontproviderId}")
    private String frontproviderId;
    @Value("${area.platform.inputCharset}")
    private String inputCharset;
    @Value("${area.platform.signType}")
    private String signType;

    public RequestMessageHeader generator() {
        RequestMessageHeader requestMessageHeader = new RequestMessageHeader(frontproviderId, inputCharset, signType, null);
        return requestMessageHeader;
    }
}
