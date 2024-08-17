package com.victor.springbootmall.service;

import com.victor.springbootmall.dto.CreateOrderRequest;
import com.victor.springbootmall.dto.OrderQueryPrams;
import com.victor.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryPrams orderQueryPrams);

    Integer countOrder(OrderQueryPrams orderQueryPrams);
}
