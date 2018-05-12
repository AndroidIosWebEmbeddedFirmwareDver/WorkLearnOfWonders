package com.wondersgroup.healthcloud.api.http.controllers.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.message.Message;
import com.wondersgroup.healthcloud.services.message.MessageService;
import com.wondersgroup.healthcloud.utils.Pager;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/add")
    @Admin
    public JsonResponseEntity<String> add(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        JsonResponseEntity<String> response = new JsonResponseEntity<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        Date date = new Date();
        Message message = new Message();
        message.setId(IdGen.uuid());
        message.setType(reader.readDefaultInteger("type", 1));
        message.setTitle(reader.readString("title", true));
        message.setContent(reader.readString("content", true));
        message.setImgUrl(reader.readString("imgUrl", true));
        message.setUrl(reader.readString("url", true));
        message.setIsShow(reader.readDefaultBoolean("isShow", true));
        String startTime = reader.readString("startTime", true);
        try {
            message.setStartTime(StringUtils.isBlank(startTime) ? null : format.parse(startTime));
            String endTime = reader.readString("endTime", true);
            message.setEndTime(StringUtils.isBlank(endTime) ? null : format.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        message.setCreateDate(date);
        message.setUpdateDate(date);
        messageService.save(message);
        response.setMsg("添加成功");
        return response;
    }

    @PostMapping("/queryList")
    @Admin
    public JsonResponseEntity<Pager> queryList(@RequestBody Pager pager) {
        JsonResponseEntity<Pager> response = new JsonResponseEntity<Pager>();
        int pageNum = 1;
        if (pager.getNumber() != 0) {
            pageNum = pager.getNumber();
        }
        List<Message> messages = messageService.queryList(pageNum, pager.getSize(), null);
        if (messages != null && messages.size() > 0) {
            int totalSize = messageService.queryCount(null);
            pager.setTotalElements(totalSize);
            pager.setData(messages);
        }
        response.setData(pager);
        response.setMsg("查询成功");
        return response;
    }

    @GetMapping("/detail")
    @Admin
    public JsonResponseEntity<Message> detail(@RequestParam String id) {
        JsonResponseEntity<Message> response = new JsonResponseEntity<Message>();
        response.setData(messageService.findByid(id));
        response.setMsg("查询成功");
        return response;
    }

    @PostMapping("/update")
    @Admin
    public JsonResponseEntity<String> update(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        JsonResponseEntity<String> response = new JsonResponseEntity<String>();
        Date date = new Date();
        Message message = new Message();
        message.setId(reader.readString("id", false));
        Message mess = messageService.findByid(message.getId());
        if (reader.readString("title", true) != null) {
            mess.setTitle(reader.readString("title", true));
        }
        if (reader.readString("content", true) != null) {
            mess.setContent(reader.readString("content", true));
        }
        if (reader.readString("imgUrl", true) != null) {
            mess.setImgUrl(reader.readString("imgUrl", true));
        }
        if (reader.readInteger("type", true) != null) {
            mess.setType(reader.readInteger("type", true));
        }
        if (reader.readString("url", true) != null) {
            mess.setUrl(reader.readString("url", true));
        }
        if (reader.readString("isShow", true) != null) {
            mess.setIsShow(reader.readDefaultBoolean("isShow", true));
        }
        mess.setUpdateDate(date);
        messageService.update(mess);
        response.setMsg("修改成功");
        return response;
    }
}
