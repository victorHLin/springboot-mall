package com.victor.springbootmall.service;

import com.victor.springbootmall.dto.CreateOrderRequest;
import com.victor.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
