package com.victor.springbootmall.dao;

import com.victor.springbootmall.dto.UserLoginRequest;
import com.victor.springbootmall.dto.UserRegisterRequest;
import com.victor.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

}
