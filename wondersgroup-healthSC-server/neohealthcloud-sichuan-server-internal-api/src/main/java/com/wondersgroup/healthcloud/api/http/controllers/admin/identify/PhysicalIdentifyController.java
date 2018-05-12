package com.wondersgroup.healthcloud.api.http.controllers.admin.identify;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.api.http.dto.identify.HealthQuestionAPIEnity;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.identify.PhysicalIdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by zhuchunliu on 2016/8/16.
 */
@RestController
@RequestMapping(value = "/api/physicalIdentify")
@Admin
public class PhysicalIdentifyController {
    @Autowired
    private PhysicalIdentifyService physicalIdentifyService;


    @PostMapping
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<List<HealthQuestionAPIEnity>> doPhysiqueIdentify(
            @RequestBody String request) {

        JsonResponseEntity<List<HealthQuestionAPIEnity>> response = new JsonResponseEntity<List<HealthQuestionAPIEnity>>();

        JsonKeyReader reader = new JsonKeyReader(request);
        String registerid = reader.readString("registerid", true);
        String content = reader.readString("content", false);

        List<HealthQuestionAPIEnity> list = Lists.newArrayList();
        String info = physicalIdentifyService.physiqueIdentify(registerid, content);
        if (null != info && !"".equals(info)) {
            String[] arr = info.split(",");
            for (String physique : arr) {
                list.add(new HealthQuestionAPIEnity(physique));
            }
        }
        response.setData(list);
        return response;
    }
}
