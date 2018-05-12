package com.wondersgroup.healthcloud.jpa.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wondersgroup.healthcloud.jpa.entity.feedback.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {

}
