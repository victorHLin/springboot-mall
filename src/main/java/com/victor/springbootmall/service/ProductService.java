package com.victor.springbootmall.service;

import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(int id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
