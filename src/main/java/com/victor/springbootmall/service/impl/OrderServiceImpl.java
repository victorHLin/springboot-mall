package com.victor.springbootmall.service.impl;

import com.victor.springbootmall.dao.OrderDao;
import com.victor.springbootmall.dao.ProductDao;
import com.victor.springbootmall.dao.UserDao;
import com.victor.springbootmall.dto.BuyItem;
import com.victor.springbootmall.dto.CreateOrderRequest;
import com.victor.springbootmall.dto.OrderQueryPrams;
import com.victor.springbootmall.model.Order;
import com.victor.springbootmall.model.OrderItem;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        if(userDao.getUserById(userId) == null) {
            log.warn("userId {} not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            // check if items exist & if we have enough items in stock
            if(product == null) {
                log.warn("productId {} not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("productId {} not enough. only {} left but you want {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // update stock
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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

    @Override
    public List<Order> getOrders(OrderQueryPrams orderQueryPrams) {
        List<Order> orderList = orderDao.getOrders(orderQueryPrams);
        for(Order order : orderList) {
            order.setOrderItemsList(orderDao.getOrderItemsByOrderId(order.getOrderId()));
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryPrams orderQueryPrams) {
        return orderDao.countOrder(orderQueryPrams);
    }
}
