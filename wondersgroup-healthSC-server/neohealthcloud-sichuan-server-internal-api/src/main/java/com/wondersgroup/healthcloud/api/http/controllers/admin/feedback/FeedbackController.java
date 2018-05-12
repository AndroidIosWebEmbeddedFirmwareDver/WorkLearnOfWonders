package com.wondersgroup.healthcloud.api.http.controllers.admin.feedback;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.feedback.Feedback;
import com.wondersgroup.healthcloud.services.feedback.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@Admin
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponseEntity<String> save(@RequestBody Feedback feedback) {
        JsonResponseEntity<String> responseEntity = new JsonResponseEntity<>();

        feedbackService.save(feedback);
        responseEntity.setMsg("我们已收到您的反馈，非常感谢！");
        return responseEntity;
    }

}
