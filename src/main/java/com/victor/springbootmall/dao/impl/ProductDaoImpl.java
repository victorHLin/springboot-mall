package com.victor.springbootmall.dao.impl;

import com.victor.springbootmall.dao.ProductDao;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT * FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);

        List<Product> query = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return !query.isEmpty() ? query.get(0) : null;
    }
}
