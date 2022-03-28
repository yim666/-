package com.yim.service;


import com.yim.pojo.Admin;
import com.yim.pojo.User;

public interface LoginService {

    User userLogin(String userName, String password);

    Admin adminLogin(String userName, String password);
}
