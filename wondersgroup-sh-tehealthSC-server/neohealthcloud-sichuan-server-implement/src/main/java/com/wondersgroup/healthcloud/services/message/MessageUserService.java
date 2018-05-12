package com.wondersgroup.healthcloud.services.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.wondersgroup.healthcloud.jpa.entity.message.MessageUser;
import com.wondersgroup.healthcloud.jpa.repository.message.MessageUserRepository;

@Component
public class MessageUserService {

    @Autowired
    private MessageUserRepository messageUserRepository;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jt;

    public List<MessageUser> queryList(List<String> list, String registerId) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<MessageUser>();
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM tb_message_user where register_id = ").append("'").append(registerId).append("'");
        sql.append(" and message_id in(");
        for (String id : list) {
            sql.append("'").append(id).append("',");
        }
        return getJt()
                .query(sql.substring(0, sql.length() - 1) + ")", new Object[]{}, new BeanPropertyRowMapper<MessageUser>(MessageUser.class));
    }

    public int queryInfo(String messageId, String registerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) FROM tb_message_user where 1=1 ")
                .append(" and ").append(" message_id = ").append("'").append(messageId).append("'")
                .append(" and ").append(" register_id = ").append("'").append(registerId).append("'");
        return getJt().queryForObject(sql.toString(), Integer.class);
    }

    public Map<String, MessageUser> queryMessageUserMap(List<String> list, String registerId) {
        Map<String, MessageUser> map = new HashMap<String, MessageUser>();
        List<MessageUser> result = queryList(list, registerId);
        for (MessageUser mu : result) {
            map.put(mu.getMessageId(), mu);
        }
        return map;
    }

    /**
     * 获取系统阅读消息总条数
     *
     * @param type       1 系统消息, 2 支付消息, default 0
     * @param registerId 用户id
     * @return int
     */
    public int findReadSysCountByUser(int type, String registerId) {
        StringBuffer sql = new StringBuffer("select count(*) from tb_message_user mu join tb_system_message sm on mu.message_id = sm.id where mu.register_id = '" + registerId
                + "' and sm.is_show =1 and mu.state = 1 and mu.type = " + type);
        int count = getJt().queryForObject(sql.toString(), Integer.class);
        return count;

    }

    public int findReadPayCountByUser(int type, String registerId) {
        StringBuffer sql = new StringBuffer("select count(*) from tb_message_user mu join tb_pay_message sm on mu.message_id = sm.id where mu.register_id = '" + registerId
                + "' and mu.state = 1 and mu.type = " + type);
        int count = getJt().queryForObject(sql.toString(), Integer.class);
        return count;

    }

    private JdbcTemplate getJt() {
        if (jt == null) {
            jt = new JdbcTemplate(dataSource);
        }
        return jt;
    }
}