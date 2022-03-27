package com.yim.service;


import com.yim.pojo.User;

public interface LoginService {

    User userLogin(String userName, String password);
}
