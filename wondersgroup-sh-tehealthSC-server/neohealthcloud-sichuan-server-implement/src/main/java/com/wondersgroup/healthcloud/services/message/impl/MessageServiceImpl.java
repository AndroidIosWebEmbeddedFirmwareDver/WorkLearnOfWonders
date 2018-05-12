package com.wondersgroup.healthcloud.services.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.message.Message;
import com.wondersgroup.healthcloud.jpa.entity.message.MessageUser;
import com.wondersgroup.healthcloud.jpa.repository.message.MessageRepository;
import com.wondersgroup.healthcloud.jpa.repository.message.MessageUserRepository;
import com.wondersgroup.healthcloud.services.message.MessageService;
import com.wondersgroup.healthcloud.services.message.MessageUserService;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageUserRepository messageUserRepository;
    @Autowired
    private MessageUserService messageUserService;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jt;

    private JdbcTemplate getJt() {
        if (jt == null) {
            jt = new JdbcTemplate(dataSource);
        }
        return jt;
    }

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> queryList(Integer pageNum, Integer pageSize, Message message) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM tb_system_message WHERE 1=1 ");
        if (message != null) {
            if (message.getIsShow() != null) {
                sql.append(" and is_show = ").append(message.getIsShow());
            }
        }
        sql.append(" ORDER BY create_date DESC")
                .append(" LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
        return getJt().query(sql.toString(), new Object[]{}, new BeanPropertyRowMapper<Message>(Message.class));
    }

    @Override
    public int queryCount(Message message) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) FROM tb_system_message WHERE 1=1 ");
        if (message != null) {
            if (message.getIsShow() != null) {
                sql.append(" and is_show = ").append(message.getIsShow());
            }
        }
        return getJt().queryForObject(sql.toString(), Integer.class);
    }

    @Override
    public Message update(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @Override
    public Message findByid(String id) {
        return messageRepository.findOne(id);
    }

    @Override
    public List<String> updateNotReadMessages(String registerId) {
        List<String> list = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT id FROM  tb_system_message sm where not EXISTS ( SELECT * FROM tb_message_user mu WHERE sm.id = mu.message_id ")
                .append(" and mu.register_id = '").append(registerId).append("')");
        List<Map<String, Object>> result = getJt().queryForList(sql.toString());
        if (result != null && !result.isEmpty()) {
            for (Map<String, Object> map : result) {
                if (map.containsKey("id") && map.get("id") != null) {
                    list.add(map.get("id").toString());
                }
            }
        }

        for (String id : list) {
            int count = messageUserService.queryInfo(id, registerId);
            if (count > 0) {
                continue;
            }
            MessageUser messageUser = new MessageUser();
            messageUser.setId(IdGen.uuid());
            messageUser.setMessageId(id);
            messageUser.setRegisterId(registerId);
            messageUser.setState(1);
            messageUser.setType(1);
            messageUser.setCreateDate(new Date());
            messageUserRepository.saveAndFlush(messageUser);
        }
        return list;
    }

}
