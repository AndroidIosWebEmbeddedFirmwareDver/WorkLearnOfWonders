package com.wondersgroup.healthcloud.jpa.repository.order;

import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zhaozhenxing on 2016/11/07.
 */

public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {
    OrderInfo findByScOrderId(String scOrderId);

    OrderInfo findByScOrderIdAndHosCode(String scOrderId, String hosCode);

    /**
     * 判断用户是否在医院预约过订单信息
     */
    @Query(nativeQuery = true, value = "select id from tb_order_info where uid=?1 and hos_code=?2 limit 1")
    String hasOrderInfoForUserInHospital(String uid, String hosCode);
}