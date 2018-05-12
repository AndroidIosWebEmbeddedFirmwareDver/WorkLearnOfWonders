package com.wondersgroup.healthcloud.services.beinhospital;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.pay.PayService;

import java.util.List;

/**
 * Created by nick on 2016/11/14.
 *
 * @author nick
 */
public class AllPayStatusOrderService extends AbstractPayOrderService {

    private PayService payService;
    private SubjectType type;

    public AllPayStatusOrderService(int start, int end, PayService payService) {
        super(start, end, null);
        this.payService = payService;
    }

    public AllPayStatusOrderService(int start, int end, PayService payService, String type) {
        super(start, end, null);
        this.payService = payService;
        this.type = Enum.valueOf(SubjectType.class, type);
    }

    @Override
    public MyOrder getMyOrders(String uid) {
        MyOrder myOrder = new MyOrder();
        boolean hasMore = true;
        List<PayOrder> payOrders = Lists.newArrayList();
        int total;
        if (type != null) {
            total = payService.countByUidAndSubjectType(uid, type.toString());
            if (total == 0) {
                hasMore = false;
            } else {
                if (start + pageSize >= total) {
                    hasMore = false;
                }
                payOrders = payService.findByUidAndSubjectType(uid, type.toString(), start, pageSize);
            }
        } else {
            total = payService.countByUid(uid);
            if (total == 0) {
                hasMore = false;
            } else {
                if (start + pageSize >= total) {
                    hasMore = false;
                }
                payOrders = payService.findByUid(uid, start, pageSize);
            }
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
        int total;
        if (type != null) {
            total = payService.countByUidAndSubjectType(uid, type.toString(), cityCode);
            if (total == 0) {
                hasMore = false;
            } else {
                if (start + pageSize >= total) {
                    hasMore = false;
                }
                payOrders = payService.findByUidAndSubjectType(uid, type.toString(), start, pageSize, cityCode);
            }
        } else {
            total = payService.countByUid(uid, cityCode);
            if (total == 0) {
                hasMore = false;
            } else {
                if (start + pageSize >= total) {
                    hasMore = false;
                }
                payOrders = payService.findByUid(uid, start, pageSize, cityCode);
            }
        }
        myOrder.setHasMore(hasMore);
        myOrder.setNextPage(String.valueOf(start + pageSize));
        myOrder.setOrders(generateOrders(payOrders, payService.hospitalService));
        return myOrder;
    }
}
