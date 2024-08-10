package com.victor.springbootmall.service.impl;

import com.victor.springbootmall.dao.UserDao;
import com.victor.springbootmall.dto.UserRegisterRequest;
import com.victor.springbootmall.model.User;
import com.victor.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
