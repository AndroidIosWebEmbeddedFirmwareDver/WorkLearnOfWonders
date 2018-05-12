package com.wondersgroup.healthcloud.services.order.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.repository.order.OrderInfoRepository;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.order.OrderInfoService;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 预约订单服务
 * 用于调用处理本地表业务逻辑
 * Created by zhaozhenxing on 2016/11/07.
 */

@Service(value = "orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    private static Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public List<Order> list(OrderInfo orderInfo, int pageNo, int pageSize) {
        String sql = generateSQL(orderInfo, pageNo, pageSize);
        if (sql == null) {
            return null;
        }
        try {
            List<PayOrder> rtnList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(PayOrder.class));
            if (rtnList != null && rtnList.size() > 0) {
                return generateOrders(rtnList);
            }
        } catch (Exception ex) {
            logger.error("OrderInfoServiceImpl.list --> " + ex.getMessage());
        }
        return null;
    }

    @Override
    public OrderInfo detail(String id) {
        OrderInfo rtnEntity = null;
        try {
            rtnEntity = orderInfoRepository.findOne(id);
        } catch (Exception ex) {
            logger.error("OrderInfoServiceImpl.detail --> " + ex.getMessage());
        }
        return rtnEntity;
    }

    @Override
    @Transactional
    public OrderInfo saveAndUpdate(OrderInfo orderInfo) {
        try {
            if (orderInfo.getId() == null) { // insert
                orderInfo.setCreateTime(new Date());
            }
            orderInfo.setUpdateTime(new Date());
            orderInfo = orderInfoRepository.save(orderInfo);
            if (orderInfo != null) {
                return orderInfo;
            }
        } catch (Exception ex) {
            logger.error("OrderInfoServiceImpl.saveAndUpdate --> " + ex.getMessage());
        }
        return null;
    }

    @Override
    public String validation(OrderInfo orderInfo) {
        if (orderInfo == null || StringUtils.isEmpty(orderInfo.getUid())) {
            return "提交预约单信息有误！";
        }

        //if (StringUtils.isEmpty(orderInfo.getMedi_card_type_code())) {
        //    return "请上传就诊卡类型！";
        //}

        try {
            OrderInfo paramInfo = new OrderInfo();
            paramInfo.setUid(orderInfo.getUid());
            paramInfo.setState("1");
            List<OrderInfo> rtnList = orderInfoRepository.findAll(Example.of(paramInfo));
            if (rtnList != null && rtnList.size() > 0) {// 一个用户只能存在一条未支付订单
                return "当前用户存在未支付订单！";
            }
        } catch (Exception ex) {
            logger.error("OrderInfoServiceImpl.list --> " + ex.getMessage());
            return "预约挂号验证失败!";
        }
        return null;
    }

    @Override
    public String cancelValidation(OrderInfo orderInfo) {
        /*2016-12-01 确定当天不可取消，非当天能取消
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition parsePosition = new ParsePosition(0);
        Date orderDate = sdf.parse(orderInfo.getDate(), parsePosition);
        if ((new Date()).after(orderDate)) {
            return "该预约已过取消有效时限！";
        }*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (orderInfo.getDate() != null && orderInfo.getDate().startsWith(sdf.format(new Date()))) {
            return "就诊当日无法取消预约！";
        }
        return null;
    }

    public String generateSQL(OrderInfo orderInfo, int pageNo, int pageSize) {
        if (orderInfo == null) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append("   b.*,");
        sql.append("   a.is_evaluated,");
        sql.append("   a.show_order_id");
        sql.append(" FROM");
        sql.append("   tb_order_info a");
        sql.append("   LEFT JOIN tb_pay_order b");
        sql.append("     ON a.id = b.subject_id");
        sql.append(" WHERE b.id IS NOT NULL");
        sql.append("   AND a.del_flag = '0'");
        sql.append("   AND a.state <> '1'");// 不展示已预约未支付的订单
        if (StringUtils.isNotEmpty(orderInfo.getUid())) {
            sql.append(" AND a.uid = '" + orderInfo.getUid() + "'");
        }
        if (StringUtils.isNotEmpty(orderInfo.getState())) {
            sql.append(" AND a.state IN (" + orderInfo.getState() + ")");
        }
        if (StringUtils.isNotEmpty(orderInfo.getCityCode())) {
            sql.append(" AND a.city_code = '" + orderInfo.getCityCode() + "'");
        }

        sql.append(" ORDER BY a.create_time DESC");
        sql.append(" LIMIT " + ((pageNo - 1) * 10) + " , " + pageSize);
        return sql.toString();
    }

    List<Order> generateOrders(List<PayOrder> payOrders) {
        List<Order> orders = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!CollectionUtils.isEmpty(payOrders)) {
                orders = Lists.newArrayList();
                for (PayOrder payOrder : payOrders) {
                    Order order = new Order();
                    order.setOrderId(payOrder.getSubjectId());
                    order.setOrderType(payOrder.getSubjectType().toString());
                    order.setPayStatus(payOrder.getStatus().toString());
                    try {
                        BigDecimal price = new BigDecimal(payOrder.getAmount()).divide(new BigDecimal(100));
                        order.setPrice(price.setScale(2).toString());
                    } catch (Exception ex) {
                        logger.error(Exceptions.getStackTraceAsString(ex));
                        order.setPrice("" + Double.valueOf(payOrder.getAmount()) / 100);
                    }
                    //TODO；大坑，存的是歷史數據，不會事實更新
                    order.setBusiness(mapper.readTree(payOrder.getBusiness()));
                    //TODO;设置businessOrderState
                    order.setBusinessOrderState(orderInfoRepository.findOne(payOrder.getSubjectId()).getState());

                    PayOrderDTO payOrderDTO = hospitalService.getLianPayParam(payOrder);
                    order.setPay_order(payOrderDTO);
                    order.setOrderTime(payOrder.getUpdateTime());
                    order.setIsEvaluated(payOrder.getIsEvaluated());
                    order.setShowOrderId(payOrder.getShowOrderId());
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            logger.error(Exceptions.getStackTraceAsString(e));
        }
        return orders;
    }
}