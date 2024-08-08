package com.victor.springbootmall.service.impl;

import com.victor.springbootmall.constant.ProductCategory;
import com.victor.springbootmall.dao.ProductDao;
import com.victor.springbootmall.dto.ProductQueryParams;
import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public int countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }
}
