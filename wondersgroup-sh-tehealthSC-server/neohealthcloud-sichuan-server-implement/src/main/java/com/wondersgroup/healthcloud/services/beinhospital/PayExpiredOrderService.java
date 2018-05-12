package com.wondersgroup.healthcloud.services.beinhospital;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.pay.PayService;

import java.util.List;

import static com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder.Status.EXPIRED;

/**
 * Created by nick on 2016/11/14.
 *
 * @author nick
 */
public class PayExpiredOrderService extends AbstractPayOrderService {

    private PayService payService;

    public PayExpiredOrderService(int start, int end, PayOrder.Status status, PayService payService) {
        super(start, end, status);
        this.payService = payService;
    }

    @Override
    public MyOrder getMyOrders(String uid) {
        MyOrder myOrder = new MyOrder();
        boolean hasMore = true;
        List<PayOrder> payOrders = Lists.newArrayList();
        int total = payService.countByUidAndStatus(uid, EXPIRED.toString());
        if (total == 0) {
            hasMore = false;
        } else {
            if (start + pageSize >= total) {
                hasMore = false;
            }
            payOrders = payService.findByUidAndStatus(uid, EXPIRED.toString(), start, pageSize);
        }
        myOrder.setHasMore(hasMore);
        myOrder.setNextPage(String.valueOf(start + pageSize));
        myOrder.setOrders(generateOrders(payOrders, payService.hospitalService));
        return myOrder;
    }

    @Override
    public MyOrder getMyOrders(String uid, String cityCode) {
        MyOrder myOrder = new MyOrder();
        boolean hasMore = true;
        List<PayOrder> payOrders = Lists.newArrayList();
        int total = payService.countByUidAndStatus(uid, EXPIRED.toString(), cityCode);
        if (total == 0) {
            hasMore = false;
        } else {
            if (start + pageSize >= total) {
                hasMore = false;
            }
            payOrders = payService.findByUidAndStatus(uid, EXPIRED.toString(), start, pageSize, cityCode);
        }
        myOrder.setHasMore(hasMore);
        myOrder.setNextPage(String.valueOf(start + pageSize));
        myOrder.setOrders(generateOrders(payOrders, payService.hospitalService));
        return myOrder;
    }
}
