package com.victor.springbootmall.dao.impl;

import com.victor.springbootmall.dao.OrderDao;
import com.victor.springbootmall.dto.OrderQueryPrams;
import com.victor.springbootmall.model.Order;
import com.victor.springbootmall.model.OrderItem;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.rowMapper.OrderItemRowMapper;
import com.victor.springbootmall.rowMapper.OrderRowMapper;
import com.victor.springbootmall.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id,total_amount, created_date, last_modified_date)" +
                " VALUES(:userId,:totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {

        //insert one item at a time
        /*
        for(OrderItem orderItem : orderItemList) {
            String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount)" +
                    " VALUES(:orderId,:productId,:quantity, :amount)";

            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("productId", orderItem.getProductId());
            map.put("quantity", orderItem.getQuantity());
            map.put("amount", orderItem.getAmount());

            namedParameterJdbcTemplate.update(sql, map);
        }
        */

        //batchUpdate
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount)" +
                " VALUES(:orderId,:productId,:quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id,total_amount, created_date, last_modified_date" +
                " FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList.isEmpty() ? null : orderList.get(0);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                " FROM order_item as oi "
                + " LEFT JOIN product as p ON oi.product_id = p.product_id"
                + " WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderQueryPrams orderQueryPrams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` " +
                "WHERE 1=1 ";
        Map<String, Object> map = new HashMap<String, Object>();
        sql = addFilteringSQL(sql, map, orderQueryPrams);

        sql += " ORDER BY created_date DESC";

        sql += " LIMIT :limit OFFSET  :offset";
        map.put("limit", orderQueryPrams.getLimit());
        map.put("offset", orderQueryPrams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryPrams orderQueryPrams) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<String, Object>();
        sql = addFilteringSQL(sql, map, orderQueryPrams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    private String addFilteringSQL(String sql, Map<String, Object> map, OrderQueryPrams orderQueryPrams) {
        if(orderQueryPrams.getUserId() != null){
            map.put("userId", orderQueryPrams.getUserId());
            sql += " AND user_id = :userId";
        }
        return sql;
    }
}
