package com.wondersgroup.healthcloud.services.beinhospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by nick on 2016/11/14.
 *
 * @author nick
 */
public abstract class AbstractPayOrderService implements PayOrderService {

    protected int start;
    protected int pageSize;
    protected PayOrder.Status status;

    protected Logger logger = LoggerFactory.getLogger("EX");

    AbstractPayOrderService(int start, int pageSize, PayOrder.Status status) {
        this.start = start;
        this.pageSize = pageSize;
        this.status = status;
    }

    protected List<Order> generateOrders(List<PayOrder> payOrders, HospitalService hospitalService) {
        List<Order> orders = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!CollectionUtils.isEmpty(payOrders)) {
                orders = Lists.newArrayList();
                for (PayOrder payOrder : payOrders) {
                    Order order = new Order();
                    if (SubjectType.APPOINTMENT.equals(payOrder.getSubjectType()) || SubjectType.APPOINTMENT_SL.equals(payOrder.getSubjectType())) {
                        order.setOrderId(payOrder.getShowOrderId());
                    } else {
                        order.setOrderId(payOrder.getSubjectId());
                    }
                    order.setOrderType(payOrder.getSubjectType().toString());
                    order.setPayStatus(payOrder.getStatus().toString());
                    try {
                        BigDecimal price = new BigDecimal(payOrder.getAmount()).divide(new BigDecimal(100));
                        order.setPrice(price.setScale(2).toString());
                    } catch (Exception ex) {
                        logger.error(Exceptions.getStackTraceAsString(ex));
                        order.setPrice("" + Double.valueOf(payOrder.getAmount()) / 100);
                    }
                    order.setBusiness(mapper.readTree(payOrder.getBusiness()));
                    PayOrderDTO payOrderDTO = hospitalService.getLianPayParam(payOrder);
                    order.setPay_order(payOrderDTO);
                    order.setOrderTime(payOrder.getUpdateTime());
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            logger.error(Exceptions.getStackTraceAsString(e));
        }
        return orders;
    }

}
