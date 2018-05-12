package com.wondersgroup.healthcloud.services.beinhospital;

import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.pay.PayService;

import java.util.List;

import static com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder.Status.SUCCESS;

/**
 * Created by nick on 2016/11/14.
 */
public class PaySuccessOrderService extends AbstractPayOrderService {

    private PayService payService;

    public PaySuccessOrderService(int start, int end, PayOrder.Status status, PayService payService) {
        super(start, end, status);
        this.payService = payService;
    }

    @Override
    public MyOrder getMyOrders(String uid) {
        MyOrder myOrder = new MyOrder();
        boolean hasMore = true;
        List<PayOrder> payOrders = null;
        int total = payService.countByUidAndStatus(uid, SUCCESS.toString());
        if (total == 0) {
            hasMore = false;
        } else {
            if (start + pageSize >= total) {
                hasMore = false;
            }
            payOrders = payService.findByUidAndStatus(uid, SUCCESS.toString(), start, pageSize);
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
        List<PayOrder> payOrders = null;
        int total = payService.countByUidAndStatus(uid, SUCCESS.toString(), cityCode);
        if (total == 0) {
            hasMore = false;
        } else {
            if (start + pageSize >= total) {
                hasMore = false;
            }
            payOrders = payService.findByUidAndStatus(uid, SUCCESS.toString(), start, pageSize, cityCode);
        }
        myOrder.setHasMore(hasMore);
        myOrder.setNextPage(String.valueOf(start + pageSize));
        myOrder.setOrders(generateOrders(payOrders, payService.hospitalService));
        return myOrder;
    }
}
