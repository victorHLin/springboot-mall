package com.victor.springbootmall.service;

import com.victor.springbootmall.constant.ProductCategory;
import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(int id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
