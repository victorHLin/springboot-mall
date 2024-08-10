package com.victor.springbootmall.service;

import com.victor.springbootmall.dto.UserRegisterRequest;
import com.victor.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
