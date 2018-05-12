package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.common.utils.IdGen;
import com.wondersgroup.healthSC.services.interfaces.SMS;
import com.wondersgroup.healthSC.services.jpa.entity.User;
import com.wondersgroup.healthSC.services.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthSC.services.jpa.entity.pay.PayOrderHistory;
import com.wondersgroup.healthSC.services.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthSC.services.jpa.repository.UserRepository;
import com.wondersgroup.healthSC.services.jpa.repository.pay.PayOrderHistoryRepository;
import com.wondersgroup.healthSC.services.jpa.repository.pay.PayOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 04/11/2016.
 */
@Component
public class PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private PayOrderHistoryRepository historyRepository;

    /**
     * 保存订单, 并且在日志表中记录
     *
     * @param payOrder
     * @return
     */
    private PayOrder save(PayOrder payOrder) {
        payOrder = payOrderRepository.save(payOrder);
        orderLog(payOrder);
        return payOrder;
    }

    public void handleNotPayClinicOrder(){
        List<PayOrder> payOrders = payOrderRepository.findBySubjectTypeAndStatus(SubjectType.CLINIC.toString(), PayOrder.Status.NOTPAY.toString());
        if(!CollectionUtils.isEmpty(payOrders)){
            for(PayOrder order: payOrders){
                Date updateTime = order.getUpdateTime();
                Date now = new Date();
                if(updateTime.before(now)){
                    order.setStatus(PayOrder.Status.EXPIRED);
                    save(order);
                }
            }
        }
    }

    /**
     * 记录订单状态变更日志
     *
     * @param payOrder
     */
    private void orderLog(PayOrder payOrder) {
        PayOrderHistory payOrderHistory = new PayOrderHistory();
        payOrderHistory.setId(IdGen.uuid());
        payOrderHistory.setPayOrderId(payOrder.getId());
        payOrderHistory.setStatus(payOrder.getStatus());
        payOrderHistory.setTime(payOrder.getUpdateTime());

        historyRepository.save(payOrderHistory);
    }

}
