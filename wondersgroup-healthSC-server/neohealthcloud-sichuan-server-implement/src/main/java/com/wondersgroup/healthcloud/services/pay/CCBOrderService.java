package com.wondersgroup.healthcloud.services.pay;

import com.wondersgroup.healthcloud.services.pay.dto.CCBOrder;

/**
 * Created by limenghua on 2017/1/12.
 */
public interface CCBOrderService {
    /**
     * 获取下单url
     *
     * @param ccbOrder
     * @return
     */
    String getCCBUrl(CCBOrder ccbOrder);

    /**
     * 单位为分
     *
     * @param payment
     * @return
     */
    String getCCBUrl(int payment);
}
