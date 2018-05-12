package com.wondersgroup.healthcloud.api.http.controllers.h5.feedback;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.feedback.Feedback;
import com.wondersgroup.healthcloud.services.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("H5FeedbackController")
@RequestMapping("/h5/api/feedback")
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
