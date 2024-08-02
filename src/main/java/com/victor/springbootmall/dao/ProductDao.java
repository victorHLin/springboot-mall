package com.victor.springbootmall.dao;

import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
