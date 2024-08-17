package com.victor.springbootmall.service.impl;

import com.victor.springbootmall.dao.OrderDao;
import com.victor.springbootmall.dao.ProductDao;
import com.victor.springbootmall.dto.BuyItem;
import com.victor.springbootmall.dto.CreateOrderRequest;
import com.victor.springbootmall.model.Order;
import com.victor.springbootmall.model.OrderItem;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            Integer productAmount = buyItem.getQuantity() * product.getPrice();
            totalAmount += productAmount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(productAmount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItem(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemsList(orderItems);

        return order;
    }
}
