package com.wondersgroup.healthcloud.api.http.controllers;

import com.wondersgroup.healthcloud.entity.request.orderws.OrderRequest;
import com.wondersgroup.healthcloud.entity.response.orderws.Response;
import com.wondersgroup.healthcloud.services.orderws.OrderWSService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangzhixiu on 07/11/2016.
 */
@RestController
@RequestMapping(path = "/")
public class TestController {


    @Autowired
    private OrderWSService orderWSService;

    /**
     * 测试接口跳转原生app
     *
     * @param jumpUrl
     * @param resp
     */
    @RequestMapping(value = "/api/test/testJump", method = RequestMethod.GET)
    public void testJump(@RequestParam(required = false, defaultValue = "") String jumpUrl, HttpServletResponse resp) {
        jumpUrl = StringUtils.isEmpty(jumpUrl) ? "com.wondersgroup.healthcloud.sichuan://user/appointment?loginOrVerify=1" : jumpUrl;
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Cache-Control", "no-cache");
            PrintWriter out = resp.getWriter();
            out.println("<html><script language=\"javascript\">");
            out.println("location.href=\"" + jumpUrl + "\";");
            out.println("</script></html>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/orderService/setPromiseNoticeService", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public Response postUser(@RequestBody OrderRequest orderRequest) {
        return orderWSService.reportOrderStatus(orderRequest);
    }
}
