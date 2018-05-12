package com.wondersgroup.healthcloud.services.beinhospital;

import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.pay.PayService;

/**
 * Created by nick on 2016/11/14.
 *
 * @author nick
 */
public class PayOrderServiceManager extends AbstractPayOrderService {

    private PayOrderService payOrderService;

    public PayOrderServiceManager(int start, int pageSize, PayOrder.Status status, PayService payService) {
        super(start, pageSize, status);
        if (status != null) {
            switch (status) {
                case NOTPAY:
                    payOrderService = new NotPayOrderService(start, pageSize, PayOrder.Status.NOTPAY, payService);
                    break;
                case EXPIRED:
                    payOrderService = new PayExpiredOrderService(start, pageSize, PayOrder.Status.EXPIRED, payService);
                    break;
                case SUCCESS:
                    payOrderService = new PaySuccessOrderService(start, pageSize, PayOrder.Status.SUCCESS, payService);
                    break;
                case REFUNDSUCCESS:
                    payOrderService = new RefundSuccessOrderService(start, pageSize, PayOrder.Status.REFUNDSUCCESS, payService);
                    break;
            }
        } else {
            payOrderService = new AllPayStatusOrderService(start, pageSize, payService);
        }
    }

    public PayOrderServiceManager(int start, int pageSize, String type, PayService payService) {
        super(start, pageSize, null);
        this.payOrderService = new AllPayStatusOrderService(start, pageSize, payService, type);
    }

    @Override
    public MyOrder getMyOrders(String uid) {
        return payOrderService.getMyOrders(uid);
    }

    @Override
    public MyOrder getMyOrders(String uid, String cityCode) {
        return payOrderService.getMyOrders(uid, cityCode);
    }


}
