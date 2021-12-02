package com.example.aws.service;

import com.example.aws.entity.User;
import com.example.aws.model.request.LoginRequest;

public interface UserService extends GeneralService<User> {
    User login(LoginRequest loginRequest) throws Exception;
}
