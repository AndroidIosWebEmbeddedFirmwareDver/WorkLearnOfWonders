package com.wondersgroup.healthcloud.services.feedback;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.feedback.Feedback;
import com.wondersgroup.healthcloud.jpa.repository.feedback.FeedbackRepository;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    public void save(Feedback feedback) {
        Date date = new Date();
        feedback.setId(IdGen.uuid());
        feedback.setStatus(0);
        feedback.setPlatform(1);
        feedback.setCreateTime(date);
        feedback.setUpdateTime(date);
        feedbackRepository.save(feedback);
    }

}
