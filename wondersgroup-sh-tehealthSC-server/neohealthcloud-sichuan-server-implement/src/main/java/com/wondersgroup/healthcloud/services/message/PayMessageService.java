package com.wondersgroup.healthcloud.services.message;

import java.util.List;
import java.util.Map;

import com.wondersgroup.healthcloud.jpa.entity.message.PayMessage;

public interface PayMessageService {
    List<String> updateNotReadMessages(String registerId);

    PayMessage save(PayMessage payMessage);

    int queryCount(PayMessage payMessage);

    List<PayMessage> queryList(Integer pageNum, Integer pageSize, String registerId);

    List<Map<String, Object>> queryMessageInfo(Integer pageNum, Integer pageSize, String messageId, PayMessage payMessage);
}
