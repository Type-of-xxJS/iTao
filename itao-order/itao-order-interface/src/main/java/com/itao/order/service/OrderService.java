package com.itao.order.service;

import com.itao.common.utils.E3Result;
import com.itao.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
