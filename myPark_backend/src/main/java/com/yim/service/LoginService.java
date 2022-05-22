package com.yim.service;


import com.yim.pojo.Admin;
import com.yim.pojo.User;

public interface LoginService {

    User userLogin(Integer userName, String password);

    Admin adminLogin(Integer userName, String password);
}
