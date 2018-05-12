package com.wondersgroup.healthcloud.services.message;

import java.util.List;

import com.wondersgroup.healthcloud.jpa.entity.message.Message;

public interface MessageService {
    List<String> updateNotReadMessages(String registerId);

    Message save(Message message);

    Message update(Message message);

    Message findByid(String id);

    List<Message> queryList(Integer pageNum, Integer pageSize, Message message);

    int queryCount(Message message);
}
