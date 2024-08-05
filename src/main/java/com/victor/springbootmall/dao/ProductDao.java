package com.victor.springbootmall.dao;

import com.victor.springbootmall.constant.ProductCategory;
import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
