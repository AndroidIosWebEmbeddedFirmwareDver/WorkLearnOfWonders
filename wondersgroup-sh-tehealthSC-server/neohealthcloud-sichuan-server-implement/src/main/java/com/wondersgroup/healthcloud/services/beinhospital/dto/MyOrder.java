package com.wondersgroup.healthcloud.services.beinhospital.dto;

import com.wondersgroup.healthcloud.entity.po.Order;
import lombok.Data;

import java.util.List;

/**
 * Created by nick on 2016/11/14.
 *
 * @author nick
 */
@Data
public class MyOrder {

    private boolean hasMore;
    private List<Order> orders;
    private String nextPage;


}
