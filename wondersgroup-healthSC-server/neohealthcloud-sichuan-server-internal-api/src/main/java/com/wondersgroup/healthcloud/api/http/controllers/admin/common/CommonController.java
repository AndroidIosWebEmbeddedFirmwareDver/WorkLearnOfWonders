package com.wondersgroup.healthcloud.api.http.controllers.admin.common;

import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.utils.UploaderUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by longshasha on 16/8/12.
 */

@RestController
@RequestMapping("/admin/common")
@Admin
public class CommonController {
    private static final Logger log = Logger.getLogger(CommonController.class);


    @RequestMapping(value = "/getQiniuToken", method = RequestMethod.GET)
    public JsonResponseEntity<Map<String, Object>> qiniuConfig(@RequestParam(required = false) String key) {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<Map<String, Object>>();
        Map<String, Object> map = Maps.newHashMap();
        if (key == null) {
            map.put("token", UploaderUtil.getUpToken());
        } else {
            map.put("token", UploaderUtil.getUpTokenUEditor(key));
        }
        map.put("expires", UploaderUtil.expires);
        map.put("domain", UploaderUtil.domain);
        response.setData(map);
        return response;
    }
}
