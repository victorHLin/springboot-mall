package com.victor.springbootmall.controller;

import com.victor.springbootmall.dto.CreateOrderRequest;
import com.victor.springbootmall.dto.OrderQueryPrams;
import com.victor.springbootmall.model.Order;
import com.victor.springbootmall.service.OrderService;
import com.victor.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import  java.util.List;

@Validated
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId, @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrder(@PathVariable Integer userId,
                                                @RequestParam(defaultValue="10") @Max(1000) @Min(1) Integer limit,
                                                @RequestParam(defaultValue="0") @Min(0) Integer offset ){
        OrderQueryPrams orderQueryPrams = new OrderQueryPrams();
        orderQueryPrams.setUserId(userId);
        orderQueryPrams.setLimit(limit);
        orderQueryPrams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderQueryPrams);

        Integer count = orderService.countOrder(orderQueryPrams);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResult(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
