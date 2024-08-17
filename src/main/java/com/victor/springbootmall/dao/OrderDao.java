package com.victor.springbootmall.dao;

import com.victor.springbootmall.model.Order;
import com.victor.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
