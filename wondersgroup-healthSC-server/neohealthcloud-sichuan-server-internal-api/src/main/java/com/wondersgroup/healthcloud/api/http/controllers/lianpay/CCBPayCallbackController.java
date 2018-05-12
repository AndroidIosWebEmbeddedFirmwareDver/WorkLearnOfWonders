package com.wondersgroup.healthcloud.api.http.controllers.lianpay;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wondersgroup.healthcloud.services.pay.CCBPayService;
import com.wondersgroup.healthcloud.services.pay.LianPayFallback;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.services.pay.dto.CCBCallbackDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/pay/ccb")
public class CCBPayCallbackController {

    @Autowired
    private CCBPayService ccbPayService;

    @Autowired
    private PayService payService;

    @Autowired
    private LianPayFallback lianPayFallback;

    @RequestMapping(value = "/callback", method = {RequestMethod.POST, RequestMethod.GET})
    public String asyncCallback(CCBCallbackDto dto) {
        return callBack(dto);
    }

    @RequestMapping(value = "/notify", method = {RequestMethod.POST, RequestMethod.GET})
    public void syncCallback(CCBCallbackDto dto, HttpServletResponse resp) {
        String jumpUrl = "com.wondersgroup.healthcloud.sichuan://user/orders?loginOrVerify=1";
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

    private String callBack(CCBCallbackDto dto) {
        if (!ccbPayService.verifySigature(dto)) {
            return "failure";
        }

        String orderId = dto.getREMARK2();
        try {
            payService.paySuccessCallback(orderId, dto.getAmount());
            return "success";
        } catch (Exception e) {
            Boolean needToRefund = lianPayFallback.markOnFailure(orderId);
            if (needToRefund) {
                payService.refundOrderOnFailure(orderId);
            }
            throw new RuntimeException(e);
        }
    }

}
