package com.wondersgroup.healthcloud.push.JPush;

import lombok.Data;
import org.omg.CORBA.Object;

import java.util.Map;

/**
 * Created by jialing.yao on 2017-5-11.
 */
@Data
public class JPushLog {
    private String appKey;//APP 唯一的JPUSH key
    private Map<String, Object> request;
    private Map<String, Object> response;
    private String createTime;//系统时间
    private Boolean isSuccessful;

}
