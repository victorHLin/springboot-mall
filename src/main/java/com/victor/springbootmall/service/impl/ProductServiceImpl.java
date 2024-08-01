package com.victor.springbootmall.service.impl;

import com.victor.springbootmall.dao.ProductDao;
import com.victor.springbootmall.dto.ProductRequest;
import com.victor.springbootmall.model.Product;
import com.victor.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
