package com.wondersgroup.healthcloud.api.http.controllers.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.api.http.dto.message.MessageDTO;
import com.wondersgroup.healthcloud.api.http.dto.message.MessageInfoDTO;
import com.wondersgroup.healthcloud.api.http.dto.message.PayMessageDTO;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.message.Message;
import com.wondersgroup.healthcloud.jpa.entity.message.MessageUser;
import com.wondersgroup.healthcloud.jpa.entity.message.PayMessage;
import com.wondersgroup.healthcloud.jpa.repository.message.MessageUserRepository;
import com.wondersgroup.healthcloud.services.message.MessageService;
import com.wondersgroup.healthcloud.services.message.MessageUserService;
import com.wondersgroup.healthcloud.services.message.PayMessageService;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private PayMessageService payMessageService;
    @Autowired
    private MessageUserService messageUserService;
    @Autowired
    private MessageUserRepository messageUserRepository;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");

    /**
     * 新的未读消息数量和最新消息内容
     *
     * @param uid
     * @param type
     * @return JsonListResponseEntity<MessageInfoDTO>
     */
    @GetMapping("/newMessage")
    @VersionRange
    public JsonListResponseEntity<MessageInfoDTO> newMessage(
            @RequestParam String uid,
            @RequestParam(required = false) Integer type
    ) {
        List<MessageInfoDTO> list = new ArrayList<MessageInfoDTO>();
        Message me = new Message(true);
        JsonListResponseEntity<MessageInfoDTO> response = new JsonListResponseEntity<MessageInfoDTO>();
        List<Message> messages = messageService.queryList(1, 1, me);

        List<PayMessage> payMessages = payMessageService.queryList(1, 1, uid);

        int sysMessCount = messageService.queryCount(me);
        int payMessCount = payMessageService.queryCount(new PayMessage(uid));

        int sysCount = sysMessCount - messageUserService.findReadSysCountByUser(1, uid);
        int payCount = payMessCount - messageUserService.findReadPayCountByUser(2, uid);

        Message message = (messages == null || messages.isEmpty()) ? new Message() : messages.get(0);
        PayMessage payMessage = (payMessages == null || payMessages.isEmpty()) ? new PayMessage() : payMessages.get(0);
        String sysTitle = message.getTitle() == null ? "暂无消息" : message.getTitle();
        String payContent = payMessage.getId() == null ? "暂无通知" : "支付订单消息";

        list.add(new MessageInfoDTO(1, "系统消息", sysCount < 0 ? 0 : sysCount, sysTitle, message.getCreateDate() == null ? "" : format.format(message.getCreateDate())));
        list.add(new MessageInfoDTO(2, "支付通知", payCount < 0 ? 0 : payCount, payContent, payMessage.getCreateDate() == null ? "" : format.format(payMessage.getCreateDate())));

        response.setContent(list);
        response.setMsg("查询成功");
        return response;
    }

    /**
     * 系统消息列表，进入列表自动更新所有未读消息
     *
     * @param uid
     * @param messageId
     * @param flag
     * @param pageSize
     * @return JsonListResponseEntity<MessageDTO>
     */
    @GetMapping("/queryList")
    @VersionRange
    public JsonListResponseEntity<MessageDTO> queryList(
            @RequestParam String uid,
            @RequestParam(required = false) String messageId,
            @RequestParam(defaultValue = "1") int flag,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        JsonListResponseEntity<MessageDTO> response = new JsonListResponseEntity<MessageDTO>();
        Message message = new Message(true);

        List<Message> lists = messageService.queryList(flag, pageSize, message);
        List<MessageDTO> messages = getMessageDTO(lists, uid);
        boolean more = false;
        if (messages != null && messages.size() > 0) {
            List<Message> mes = messageService.queryList(flag + 1, pageSize, message);
            if (mes != null && mes.size() > 0) {
                more = true;
            }
        }
        updateNotReadMessagesBySys(uid);
        response.setContent(messages, more, null, String.valueOf(flag + 1));
        response.setMsg("查询成功");
        return response;
    }

    /**
     * 支付消息列表，进入列表自动更新所有未读消息
     *
     * @param uid
     * @param messageId
     * @param payType
     * @param flag
     * @param pageSize
     * @return JsonListResponseEntity<PayMessageDTO>
     */
    @GetMapping("/pay/queryList")
    @VersionRange
    public JsonListResponseEntity<PayMessageDTO> queryList(
            @RequestParam String uid,
            @RequestParam(required = false) String messageId,
            @RequestParam(required = false) Integer payType,
            @RequestParam(defaultValue = "1") int flag,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        JsonListResponseEntity<PayMessageDTO> response = new JsonListResponseEntity<PayMessageDTO>();
        List<Map<String, Object>> result = payMessageService.queryMessageInfo(flag, pageSize, messageId, new PayMessage(payType, uid));
        List<PayMessageDTO> messages = PayMessageDTO.listToPayMessageDTO(result, uid, messageId);
        boolean more = false;
        if (messages != null && messages.size() > 0) {
            List<Map<String, Object>> mes = payMessageService.queryMessageInfo(flag + 1, pageSize, messageId, new PayMessage(payType, uid));
            if (mes != null && mes.size() > 0) {
                more = true;
            }
        }
        updateNotReadMessagesByPay(uid);
        response.setContent(messages, more, null, String.valueOf(flag + 1));
        response.setMsg("查询成功");
        return response;
    }

    /**
     * @param uid
     * @param type
     * @return JsonResponseEntity<String>
     */
    @GetMapping("/update")
    @VersionRange
    public JsonResponseEntity<String> queryList(
            @RequestParam String uid,
            @RequestParam(defaultValue = "1") int type
    ) {
        JsonResponseEntity<String> response = new JsonResponseEntity<String>();
        if (type == 2) {
            payMessageService.updateNotReadMessages(uid);
        } else if (type == 1) {
            messageService.updateNotReadMessages(uid);
        }
        response.setMsg("更新成功");
        return response;
    }

    /**
     * 有未读的系统消息修改为已读
     *
     * @param uid void
     */
    public void updateNotReadMessagesBySys(String uid) {
        Message message = new Message(true);
        int sysCount = messageService.queryCount(message) - messageUserService.findReadSysCountByUser(1, uid);
        if (sysCount > 0) {
            messageService.updateNotReadMessages(uid);
        }
    }

    /**
     * 有未读的支付消息修改为已读
     *
     * @param uid void
     */
    public void updateNotReadMessagesByPay(String uid) {
        int sysCount = payMessageService.queryCount(new PayMessage(uid)) - messageUserService.findReadPayCountByUser(2, uid);
        if (sysCount > 0) {
            payMessageService.updateNotReadMessages(uid);
        }
    }


    public List<MessageDTO> getMessageDTO(List<Message> lists, String registerId) {
        if (lists == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (Message message : lists) {
            list.add(message.getId());
        }
        List<MessageDTO> infos = new ArrayList<MessageDTO>();
        Map<String, MessageUser> map = messageUserService.queryMessageUserMap(list, registerId);
        for (Message message : lists) {
            MessageDTO m = new MessageDTO(message);
            if (map.containsKey(message.getId())) {
                MessageUser mu = map.get(message.getId());
                m.setState(mu.getState());
                m.setRegisterId(mu.getRegisterId());
                m.setMessageId(mu.getRegisterId());
            }
            infos.add(m);
        }
        return infos;
    }
}
