package com.wondersgroup.healthcloud.webservice.http.controllers;

import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.entity.request.orderws.OrderRequest;
import com.wondersgroup.healthcloud.entity.response.orderws.Response;
import com.wondersgroup.healthcloud.services.orderws.OrderWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
@RestController
@RequestMapping(path = "/orderService")
public class OrderController {
    @Autowired
    private OrderWSService orderWSService;

    @RequestMapping(value = "/setPromiseNoticeService", method = RequestMethod.POST)
    public Response postUser(@RequestBody OrderRequest orderRequest) {
        return orderWSService.reportOrderStatus(orderRequest);
    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public JsonResponseEntity monitor() {
        return new JsonResponseEntity(0, "Hi Panda!");
    }
}
