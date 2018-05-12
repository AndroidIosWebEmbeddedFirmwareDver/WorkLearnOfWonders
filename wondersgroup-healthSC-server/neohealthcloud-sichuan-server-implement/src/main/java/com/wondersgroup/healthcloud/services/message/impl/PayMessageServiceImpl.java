package com.wondersgroup.healthcloud.services.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.message.MessageUser;
import com.wondersgroup.healthcloud.jpa.entity.message.PayMessage;
import com.wondersgroup.healthcloud.jpa.repository.message.MessageUserRepository;
import com.wondersgroup.healthcloud.jpa.repository.message.PayMessageRepository;
import com.wondersgroup.healthcloud.services.message.MessageUserService;
import com.wondersgroup.healthcloud.services.message.PayMessageService;

@Service
public class PayMessageServiceImpl implements PayMessageService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PayMessageServiceImpl.class);

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
    private PayMessageRepository payMessageRepository;

    @Autowired
    private MessageUserRepository messageUserRepository;

    @Override
    public PayMessage save(PayMessage payMessage) {
        return payMessageRepository.save(payMessage);
    }

    @Override
    public List<Map<String, Object>> queryMessageInfo(Integer pageNum, Integer pageSize, String messageId, PayMessage payMessage) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.type,m.pay_type,m.order_id,m.hospital_name,m.patient_name,m.department,m.doctor_name,m.price,m.order_time,m.create_date,m.pay_status,m.clinic_type,m" +
                ".prescription_code,m.prescription_time,"
                + "mu.register_id register_id,mu.message_id message_id,mu.state state FROM tb_pay_message m left join tb_message_user mu on m.id = mu.message_id where 1=1");
        if (!StringUtils.isEmpty(messageId)) {
            sql.append(" and mu.message_id = '").append(messageId).append("'");
        }
        if (!StringUtils.isEmpty(payMessage.getPayType())) {
            sql.append(" and m.pay_type = ").append(payMessage.getPayType());
        }
        if (!StringUtils.isEmpty(payMessage.getRegisterId())) {
            sql.append(" and m.register_id = '").append(payMessage.getRegisterId()).append("'");
        }
        sql.append(" ORDER BY m.create_date DESC")
                .append(" LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
        List<Map<String, Object>> result = getJt().queryForList(sql.toString());
        return result;
    }

    @Override
    public int queryCount(PayMessage message) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) FROM tb_pay_message WHERE   1 = 1 ");
        if (message != null) {
            if (message.getRegisterId() != null) {
                sql.append(" and register_id = '").append(message.getRegisterId()).append("'");
            }
        }
        return getJt().queryForObject(sql.toString(), Integer.class);
    }

    @Override
    public List<String> updateNotReadMessages(String registerId) {
        List<String> list = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id FROM tb_pay_message m left join tb_message_user mu on m.id = mu.message_id where 1=1 ")
                .append(" and mu.state is null and m.register_id = '").append(registerId).append("'")
                .append(" ORDER BY m.create_date DESC");
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
            messageUser.setType(2);
            messageUser.setCreateDate(new Date());
            messageUserRepository.saveAndFlush(messageUser);
        }
        return list;
    }

    @Override
    public List<PayMessage> queryList(Integer pageNum, Integer pageSize, String registerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM tb_pay_message WHERE   1 = 1 ");
        if (registerId != null) {
            sql.append(" and register_id = '").append(registerId).append("'");
        }
        sql.append(" ORDER BY create_date DESC")
                .append(" LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
        return getJt().query(sql.toString(), new Object[]{}, new BeanPropertyRowMapper<PayMessage>(PayMessage.class));
    }


}
