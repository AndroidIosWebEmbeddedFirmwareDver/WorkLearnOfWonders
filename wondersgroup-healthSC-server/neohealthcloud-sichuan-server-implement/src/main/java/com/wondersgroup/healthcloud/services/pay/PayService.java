package com.wondersgroup.healthcloud.services.pay;

import java.util.Date;
import java.util.List;

import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.common.utils.MD5Utils;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrderHistory;
import com.wondersgroup.healthcloud.jpa.entity.pay.RefundOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthcloud.jpa.repository.pay.PayOrderHistoryRepository;
import com.wondersgroup.healthcloud.jpa.repository.pay.PayOrderRepository;
import com.wondersgroup.healthcloud.jpa.repository.pay.RefundOrderRepository;

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
@Transactional(readOnly = true)
@Component
public class PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private PayOrderHistoryRepository historyRepository;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    /*@Autowired
    private LianPayUtilSelector selector;*/

    @Autowired
    private NewLianPayUtilSelector newSelector;

    @Autowired
    private BeanFactory factory;

    @Autowired
    public HospitalService hospitalService;

    /**
     * 生成一笔支付订单,
     * 由于链支付的傻逼设计, 需要通过手机客户端sdk生成订单,
     * 故在业务需要支付时, 调用本方法在数据库中生成一笔订单数据, 并递给客户端去调用链支付
     *
     * @param amount    数额(单位:分)
     * @param type      业务订单类型(第一版中包含挂号支付和诊间支付)
     * @param subjectId 业务订单id
     * @param subject   订单名称
     * @param body      订单详情
     * @param desc      订单描述
     * @param uid       用户id
     * @return
     */
    @Transactional
    public PayOrder generatePayOrder(Long amount, SubjectType type, String subjectId, String subject, String body, String desc, String uid, String businessData, String... cityCode) {
        String appName = null;
        if (type.equals(SubjectType.APPOINTMENT_SL)) {
            type = SubjectType.APPOINTMENT;
            appName = "sl";
        }
        if (type.equals(SubjectType.CLINIC_SL)) {
            type = SubjectType.CLINIC;
            appName = "sl";
        }
        PayOrder po = findOneBySubjectIdAndType(subjectId, type);
        if (po != null) {
            throw new CommonException(1000, "订单已经存在, 无法再次生成");
        }
        PayOrder payOrder = fullPublicField(amount, type, subjectId, subject, body, desc, uid, businessData);
        if (cityCode != null && cityCode.length > 0) {
            payOrder.setCityCode(cityCode[0]);
        }
        payOrder.setAppName(appName);
        return save(payOrder);
    }

    public List<PayOrder> getPayOrdersByPrescriptionNum(String prescriptionNum) {
        List<PayOrder> payOrders = payOrderRepository.findPayOrdersByPrescriptionNum(prescriptionNum);
        return payOrders;
    }

    public List<PayOrder> getPayOrdersByPrescriptionNum(String prescriptionNum, String cityCode) {
        List<PayOrder> payOrders = payOrderRepository.findPayOrdersByPrescriptionNum(prescriptionNum, cityCode);
        return payOrders;
    }

    /**
     * 生成一笔支付订单,
     * 由于链支付的傻逼设计, 需要通过手机客户端sdk生成订单,
     * 故在业务需要支付时, 调用本方法在数据库中生成一笔订单数据, 并递给客户端去调用链支付
     *
     * @param amount       数额(单位:分)
     * @param type         业务订单类型(第一版中包含挂号支付和诊间支付)
     * @param subjectId    业务订单id
     * @param subject      订单名称
     * @param body         订单详情
     * @param desc         订单描述
     * @param uid          用户id
     * @param businessData 用户id
     * @param showOrderId  用户id
     * @return
     */
    @Transactional
    public PayOrder generatePayOrder(Long amount, SubjectType type, String subjectId, String subject, String body, String desc, String uid, String businessData, String showOrderId) {
        PayOrder po = findOneBySubjectIdAndType(subjectId, type);
        if (po != null) {
            throw new CommonException(1000, "订单已经存在, 无法再次生成");
        }
        PayOrder payOrder = fullPublicField(amount, type, subjectId, subject, body, desc, uid, businessData);
        payOrder.setShowOrderId(showOrderId);
        return save(payOrder);
    }

    /**
     * 双流-生成一笔支付订单,
     * 由于链支付的傻逼设计, 需要通过手机客户端sdk生成订单,
     * 故在业务需要支付时, 调用本方法在数据库中生成一笔订单数据, 并递给客户端去调用链支付
     *
     * @param amount       数额(单位:分)
     * @param type         业务订单类型(第一版中包含挂号支付和诊间支付)
     * @param subjectId    业务订单id
     * @param subject      订单名称
     * @param body         订单详情
     * @param desc         订单描述
     * @param uid          用户id
     * @param businessData 用户id
     * @param showOrderId  用户id
     * @return
     */
    @Transactional
    public PayOrder generatePayOrderSL(Long amount, SubjectType type, String subjectId, String subject, String body, String desc, String uid, String businessData, String showOrderId, String
            cityCode) {
        if (type.equals(SubjectType.APPOINTMENT_SL)) {
            type = SubjectType.APPOINTMENT;
        }
        if (type.equals(SubjectType.CLINIC_SL)) {
            type = SubjectType.CLINIC;
        }
        PayOrder po = findOneBySubjectIdAndType(subjectId, type);
        if (po != null) {
            throw new CommonException(1000, "订单已经存在, 无法再次生成");
        }
        PayOrder payOrder = fullPublicField(amount, type, subjectId, subject, body, desc, uid, businessData);
        payOrder.setShowOrderId(showOrderId);
        payOrder.setAppName("sl");
        payOrder.setCityCode(cityCode);
        return save(payOrder);
    }

    /**
     * 由于傻逼链支付查询订单状态必须要支付渠道, 而创建订单是由客户端发起的, 所以服务端不知道用户是使用什么渠道支付的,
     * 所以需要客户端再次手动的将订单的支付渠道告诉服务端, 本方法用于记录支付渠道
     * 只有在未支付状态可以修改支付渠道, 且已最新为准
     *
     * @param orderId
     * @param channel
     * @return
     */
    @Transactional
    public PayOrder savePayChannel(String orderId, String channel, String appName) {
        PayOrder payOrder = findOne(orderId);

        if (PayOrder.Status.EXPIRED.equals(payOrder.getStatus())) {
            throw new CommonException(9000, "支付订单已过期, 请重新下单");
        }
        if (!PayOrder.Status.NOTPAY.equals(payOrder.getStatus())) {
            throw new CommonException(9001, "订单已支付, 无法再次支付");
        }
        if (payOrder.getDueTime() != null && System.currentTimeMillis() > payOrder.getDueTime().getTime()) {//这里判断为防止定时任务执行失败
            throw new CommonException(9000, "支付订单已过期, 请重新下单");
        }

        payOrder.setChannel(channel);
        payOrder.setAppName(appName);

        return payOrderRepository.save(payOrder);
    }

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

    /**
     * 删除订单
     *
     * @param orderId
     */
    public void deleteOrder(String orderId) {
        payOrderRepository.delete(orderId);
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

    /**
     * //TODO;2018年02月01日15:07:24 新增修改订单状态必须 匹配 channel 渠道，以金融事业部为准,总会有他们手贱的要去支付多次。
     * 支付成功链支付调用的回调接口
     * 本接口会再调用各个业务模块设置的回调接口
     *
     * @param orderId
     * @param amount
     * @return
     */
    @Transactional
    public Boolean paySuccessCallback(String orderId, String channel, Long amount) {
        PayOrder order = findOne(orderId);
        if (order.getAmount().equals(amount)) {
            if (order.getStatus().equals(PayOrder.Status.NOTPAY)) {
                order.setStatus(PayOrder.Status.SUCCESS);
                //匹配 channel 渠道，以金融事业部为准
                if (order.getChannel() != null && !order.getChannel().contains(channel)) {
                    order.setChannel(channel);
                }
                order.setUpdateTime(new Date());
                save(order);

                String beanName = getCallbackBeanByOrder(order);
                PayBusinessCallback pbc = (PayBusinessCallback) factory.getBean(beanName);
                if (pbc != null) {
                    pbc.onPaySuccess(order.getSubjectId());
                    return true;
                } else {
                    throw new RuntimeException();
                }
            } else {
                logger.info("订单状态非未支付状态,订单号:" + orderId);
                throw new RuntimeException();
            }
        } else {
            logger.info("订单金额不匹配,订单号:" + orderId + ",订单金额:" + order.getAmount() + ",确认金额:" + amount);
            throw new RuntimeException();
        }
    }


    /**
     * 本接口会再调用各个业务模块设置的回调接口
     *
     * @param orderId
     * @param amount
     * @return
     */
    @Transactional
    public Boolean paySuccessCallback(String orderId, Long amount) {
        PayOrder order = findOne(orderId);
        if (order.getAmount().equals(amount)) {
            if (order.getStatus().equals(PayOrder.Status.NOTPAY)) {
                order.setStatus(PayOrder.Status.SUCCESS);
                order.setUpdateTime(new Date());
                save(order);

                String beanName = getCallbackBeanByOrder(order);
                PayBusinessCallback pbc = (PayBusinessCallback) factory.getBean(beanName);
                if (pbc != null) {
                    pbc.onPaySuccess(order.getSubjectId());
                    return true;
                } else {
                    throw new RuntimeException();
                }
            } else {
                logger.info("订单状态非未支付状态,订单号:" + orderId);
                throw new RuntimeException();
            }
        } else {
            logger.info("订单金额不匹配,订单号:" + orderId + ",订单金额:" + order.getAmount() + ",确认金额:" + amount);
            throw new RuntimeException();
        }
    }

    @Transactional
    public PayOrder changeOrderStatus(String orderId, PayOrder.Status status) {
        PayOrder payOrder = findOne(orderId);
        if (payOrder.getStatus().equals(PayOrder.Status.SUCCESS)) {
            payOrder.setStatus(status);
            payOrder.setUpdateTime(new Date());
            payOrder = save(payOrder);
        }
        return payOrder;
    }

    /**
     * 主动发起退款
     *
     * @param amount
     * @param orderId
     * @return
     */
    @Transactional
    public PayOrder refundOrder(String orderId, Long amount) {
        PayOrder payOrder = findOne(orderId);
        if (payOrder.getStatus().equals(PayOrder.Status.SUCCESS)) {
            payOrder.setStatus(PayOrder.Status.REFUND);
            payOrder.setUpdateTime(new Date());
            payOrder = save(payOrder);


            RefundOrder refundOrder = new RefundOrder();

            String refundId = forgeRefundId(IdGen.uuid());//由于支付宝退款规则退款订单号需要前几位是退款当天的日期，
            refundOrder.setId(refundId);

            refundOrder.setOrderId(payOrder.getId());
            refundOrder.setAmount(amount);
            refundOrder.setCreateTime(new Date());

            refundOrder = refundOrderRepository.save(refundOrder);

            Hospital hospital = getHospital(payOrder);


            newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).refund(payOrder.getChannel(), payOrder.getId(), refundOrder.getId(), amount);

            // selector.getByName(payOrder.getAppName()).refund(payOrder.getChannel(), payOrder.getId(), refundOrder.getId(), amount);
            return payOrder;
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public PayOrder refundOrderOnFailure(String orderId) {
        PayOrder payOrder = findOne(orderId);
        if (payOrder.getStatus().equals(PayOrder.Status.NOTPAY)) {
            payOrder.setStatus(PayOrder.Status.FAILURE);
            payOrder.setUpdateTime(new Date());
            payOrder = save(payOrder);

            RefundOrder refundOrder = new RefundOrder();

            String refundId = forgeRefundId(IdGen.uuid());//由于支付宝退款规则退款订单号需要前几位是退款当天的日期，
            refundOrder.setId(refundId);

            refundOrder.setOrderId(payOrder.getId());
            refundOrder.setAmount(payOrder.getAmount());
            refundOrder.setCreateTime(new Date());

            refundOrder = refundOrderRepository.save(refundOrder);

            //建行支付不走退款
            if (!"ccbpay".equals(payOrder.getChannel())) {

                Hospital hospital = getHospital(payOrder);
                newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).refund(payOrder.getChannel(), payOrder.getId(), refundOrder.getId(), payOrder.getAmount());
                //selector.getByName(payOrder.getAppName()).refund(payOrder.getChannel(), payOrder.getId(), refundOrder.getId(), payOrder.getAmount());
            }
            return payOrder;
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public Boolean refundSuccessCallback(String refundOrderId, Long amount) {
        RefundOrder refundOrder = refundOrderRepository.findOne(refundOrderId);
        if (refundOrder != null) {
            PayOrder order = findOne(refundOrder.getOrderId());
            if (order.getStatus().equals(PayOrder.Status.REFUND) || order.getStatus().equals(PayOrder.Status.FAILURE)) {
                PayOrder.Status previousStatus = order.getStatus();
                order.setStatus(PayOrder.Status.REFUNDSUCCESS);
                order.setUpdateTime(new Date());
                save(order);

                refundOrder.setSuccessTime(order.getUpdateTime());
                refundOrderRepository.save(refundOrder);

                if (previousStatus.equals(PayOrder.Status.REFUND)) {//正常退款的流程需要调用回调接口
                    String beanName = getCallbackBeanByOrder(order);
                    PayBusinessCallback pbc = (PayBusinessCallback) factory.getBean(beanName);
                    if (pbc != null) {
                        pbc.onRefundSuccess(order.getSubjectId(), amount);
                        return true;
                    } else {
                        throw new RuntimeException();
                    }
                } else {
                    return true;
                }
            } else {
                throw new RuntimeException();//todo
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public Boolean expireCallback(String orderId) {
        PayOrder order = findOne(orderId);
        if (order.getStatus().equals(PayOrder.Status.NOTPAY)) {
            order.setStatus(PayOrder.Status.EXPIRED);
            order.setUpdateTime(new Date());
            save(order);

            String beanName = getCallbackBeanByOrder(order);
            PayBusinessCallback pbc = (PayBusinessCallback) factory.getBean(beanName);
            if (pbc != null) {
                pbc.onExpire(order.getSubjectId());
                return true;
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();//todo
        }
    }

    /**
     * 通过id获取一个订单信息, 若不存在, 则抛出runtime exception
     *
     * @param orderId 订单id
     * @return
     */
    public PayOrder findOne(String orderId) {
        PayOrder payOrder = payOrderRepository.findOne(orderId);
        if (payOrder != null) {
            return payOrder;
        } else {
            throw new RuntimeException();
        }
    }

    public List<PayOrder> findByUid(String uid) {
        return payOrderRepository.getByUid(uid);
    }

    public PayOrder findOneBySubjectId(String subjectId) {
        return payOrderRepository.getBySubjectId(subjectId);
    }

    public PayOrder findOneBySubjectIdAndType(String subjectId, SubjectType type) {
        List<PayOrder> result = payOrderRepository.getBySubjectIdAndType(subjectId, type);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public PayOrder update(PayOrder payOrder) {
        return payOrderRepository.save(payOrder);
    }

    public List<PayOrder> findByUidAndStatus(String uid, String status, int start, int end) {
        return payOrderRepository.findByUidAndStatus(uid, status, start, end);
    }

    public List<PayOrder> findByUidAndStatus(String uid, String status, int start, int end, String cityCode) {
        return payOrderRepository.findByUidAndStatus(uid, status, start, end, cityCode);
    }

    public List<PayOrder> findByUidAndSubjectType(String uid, String type, int start, int end) {
        return payOrderRepository.findByUidAndSubjectType(uid, type, start, end);
    }

    public List<PayOrder> findByUidAndSubjectType(String uid, String type, int start, int end, String cityCode) {
        return payOrderRepository.findByUidAndSubjectType(uid, type, start, end, cityCode);
    }

    public List<PayOrder> findByUid(String uid, int start, int end) {
        return payOrderRepository.findByUid(uid, start, end);
    }

    public List<PayOrder> findByUid(String uid, int start, int end, String cityCode) {
        return payOrderRepository.findByUid(uid, start, end, cityCode);
    }

    public Integer countByUidAndStatus(String uid, String status) {
        return payOrderRepository.countByUidAndStatus(uid, status);
    }

    public Integer countByUidAndStatus(String uid, String status, String cityCode) {
        return payOrderRepository.countByUidAndStatus(uid, status, cityCode);
    }

    public Integer countByUid(String uid) {
        return payOrderRepository.countByUid(uid);
    }

    public Integer countByUid(String uid, String cityCode) {
        return payOrderRepository.countByUid(uid, cityCode);
    }

    public Integer countByUidAndSubjectType(String uid, String type) {
        return payOrderRepository.countByUidAndSubjectType(uid, type);
    }

    public Integer countByUidAndSubjectType(String uid, String type, String cityCode) {
        return payOrderRepository.countByUidAndSubjectType(uid, type, cityCode);
    }

    /**
     * 填充公共属性
     *
     * @param amount
     * @param type
     * @param subjectId
     * @param subject
     * @param body
     * @param desc
     * @param uid
     * @return
     */
    public PayOrder fullPublicField(Long amount, SubjectType type, String subjectId, String subject, String body, String desc, String uid, String businessData) {
        PayOrder payOrder = new PayOrder();
        payOrder.setId(IdGen.uuid());
        payOrder.setUid(uid);
        payOrder.setAmount(amount);
        payOrder.setStatus(PayOrder.Status.NOTPAY);
        payOrder.setSubjectId(subjectId);
        payOrder.setSubject(subject);
        payOrder.setSubjectType(type);
        payOrder.setBody(body);
        payOrder.setDescription(desc);
        payOrder.setBusiness(businessData);
        payOrder.setCreateTime(new Date());
        payOrder.setUpdateTime(payOrder.getCreateTime());
        return payOrder;
    }

    private String getCallbackBeanByOrder(PayOrder payOrder) {

        String subjectType = payOrder.getSubjectType().toString();
        String appName = payOrder.getAppName();


        if ("sl".equals(appName) && SubjectType.APPOINTMENT.toString().equals(subjectType)) {//双流预约挂号
            return subjectType + "_" + appName.toUpperCase();
        }

        return subjectType;
    }

    private Hospital getHospital(PayOrder payOrder) {
        PayOrderDTO payOrderDTO = new PayOrderDTO(payOrder);

        return hospitalService.findByHospitalCode(payOrderDTO.getHospitalCode());
    }

    public String forgeRefundId(String refundId) {
        return DateUtils.getDate() + MD5Utils.encrypt16(refundId);
    }
}
