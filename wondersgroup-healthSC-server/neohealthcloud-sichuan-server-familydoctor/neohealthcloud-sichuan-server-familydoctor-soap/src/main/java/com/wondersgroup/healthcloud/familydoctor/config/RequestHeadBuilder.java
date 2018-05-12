package com.wondersgroup.healthcloud.familydoctor.config;

import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by jialing.yao on 2017-8-2.
 */
public class RequestHeadBuilder {
    public RequestHead requestHead;

    public RequestHeadBuilder(RequestHead requestHead) {
        this.requestHead = requestHead;
    }

    public String build() {
        return JsonMapper.nonDefaultMapper().toJson(requestHead);
    }

    public String build(JsonMapper jsonMapper) {
        if (jsonMapper == null) {
            jsonMapper = JsonMapper.nonDefaultMapper();
        }
        return jsonMapper.toJson(requestHead);
    }

    @Data
    static class RequestHead {
        @Value("${familydoctor.wsdl.requesthead.zcjgdm:zcjgdm}")
        private String zcjgdm;//注册厂商代码

        @Value("${familydoctor.wsdl.requesthead.zcjgmc:zcjgmc}")
        private String zcjgmc;//注册厂商名称

        @Value("${familydoctor.wsdl.requesthead.bdjgdm:510000002122}")
        private String bdjgdm;//绑定机构代码

        @Value("${familydoctor.wsdl.requesthead.bdjgmc:bdjgmc}")
        private String bdjgmc;//绑定机构名称

        @Value("${familydoctor.wsdl.requesthead.bdyydm:bdyydm}")
        private String bdyydm;//绑定应用代码

        @Value("${familydoctor.wsdl.requesthead.bdyymc:bdyymc}")
        private String bdyymc;//绑定应用名称

        @Value("${familydoctor.wsdl.requesthead.jkdm:jkdm}")
        private String jkdm;//接口代码

        @Value("${familydoctor.wsdl.requesthead.jkmc:jkmc}")
        private String jkmc;//接口名称

        @Value("${familydoctor.wsdl.requesthead.username:admin}")
        private String username;//

        @Value("${familydoctor.wsdl.requesthead.password:111111}")
        private String password;//

        @Value("${familydoctor.wsdl.requesthead.bdczxtdm:bdczxtdm}")
        private String bdczxtdm;//绑定操作系统代码

        @Value("${familydoctor.wsdl.requesthead.bdczxtmc:bdczxtmc}")
        private String bdczxtmc;//绑定操作系统名称

    }

}
