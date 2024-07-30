package com.victor.springbootmall.dao;

import com.victor.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
