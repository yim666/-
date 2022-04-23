package com.yim.controller;

import com.yim.pojo.Admin;
import com.yim.pojo.Login;
import com.yim.pojo.User;
import com.yim.service.LoginService;
import com.yim.util.ApiResHandler;
import com.yim.util.MailClient;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MailClient mailClient;

//    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    @PostMapping("/login")
    public ApiRes login(@RequestBody Login login){
        String userName = login.getUserName();
        String password = login.getPassword();
        int role = userName.charAt(0)-'0';
//        权限为管理
        if (role==1){
            Admin admin = loginService.adminLogin(userName, password);
            return ApiResHandler.success(admin);
        }
        //权限为用户
        if(role==2){
            User user = loginService.userLogin(userName, password);
            return ApiResHandler.success(user);
        }
        return ApiResHandler.fail();
    }

    @RequestMapping("/sendEmail")
    public ApiRes testTextMail(String email) {
        mailClient.sendMail(email, "注册", "恭喜您注册成功！");
        return ApiResHandler.success();
    }



}


