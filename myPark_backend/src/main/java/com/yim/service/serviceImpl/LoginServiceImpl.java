package com.yim.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yim.mapper.UserMapper;
import com.yim.pojo.User;
import com.yim.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(String userName, String password) {
        QueryWrapper<User> qw = new QueryWrapper();
        qw.eq("user_id", userName).eq("password", password);
        User user =  userMapper.selectOne(qw);
        return user;
    }
}
