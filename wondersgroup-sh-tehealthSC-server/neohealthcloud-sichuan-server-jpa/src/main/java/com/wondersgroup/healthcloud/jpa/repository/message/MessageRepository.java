package com.wondersgroup.healthcloud.jpa.repository.message;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wondersgroup.healthcloud.jpa.entity.message.Message;


public interface MessageRepository extends JpaRepository<Message, String> {

}
