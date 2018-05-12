package com.wondersgroup.healthcloud.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhixiu on 15/2/11.
 */
public class PageFactory {

    public static Pageable create(Integer page, Integer pageSize, String... sortFieldName) {
        if (page == null || pageSize == null) {
            return null;
        }
        if (page < 0 || pageSize < 0) {
            return null;
        }
        page = page == 0 ? 1 : page;
        if (sortFieldName != null && sortFieldName.length != 0) {
            return new PageRequest(page != null ? (page - 1) : 1, pageSize != null ? pageSize : 20, new Sort(createOrder(sortFieldName)));
        } else {
            return new PageRequest(page != null ? (page - 1) : 1, pageSize != null ? pageSize : 20);
        }
    }

    private static List<Order> createOrder(String... sortFieldName) {
        List<Order> orderList = new ArrayList<Order>();
        for (String sort : sortFieldName) {
            if (!StringUtils.isEmpty(sort)) {
                if (sort.contains(":")) {
                    String[] s = sort.split(":");
                    if ("DESC".equalsIgnoreCase(s[1])) {
                        Order o = new Order(Direction.DESC, s[0]);
                        orderList.add(o);
                    } else {
                        Order o = new Order(Direction.ASC, s[0]);
                        orderList.add(o);
                    }
                } else {
                    Order o = new Order(Direction.ASC, sort);
                    orderList.add(o);
                }
                continue;
            }
        }
        return orderList;
    }

}
