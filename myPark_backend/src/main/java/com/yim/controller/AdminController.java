package com.yim.controller;

import com.yim.pojo.Advice;
import com.yim.pojo.Notice;
import com.yim.pojo.User;
import com.yim.service.AdminService;
import com.yim.util.ApiResHandler;
import com.yim.util.PageRes;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/selectUserList")
    public ApiRes selectUserList(){
        List<User> users = adminService.selectUserList();
        return ApiResHandler.succss(users);
    }

    @PutMapping("/updateUserList")
    public ApiRes updateUserList(@RequestBody User user){
        int i = adminService.updateUserList(user);
        return ApiResHandler.succss(i);
    }

    @GetMapping("/selectNoticeList")
    public ApiRes selectNotice(PageRes pageRes){
        PageRes p = adminService.selectNotice(pageRes);
        return ApiResHandler.succss(p);
    }

    @PostMapping("/createNotice")
    public ApiRes createNotice(@RequestBody Notice notice){
        notice.setTime(null);
//        notice.setId(10);
        notice.setStatus(null);
        int i = adminService.createNotice(notice);
        return ApiResHandler.succss(i);
    }

    @GetMapping("/selectAdviceList")
    public ApiRes selectAdviceList(){
        List<Advice> advice = adminService.selectAdviceList();
        return ApiResHandler.succss(advice);
    }

}
