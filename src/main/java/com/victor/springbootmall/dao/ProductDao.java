package com.victor.springbootmall.dao;

import com.victor.springbootmall.dto.ProductQueryParams;
import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    int countProduct(ProductQueryParams productQueryParams);
}
