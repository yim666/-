package com.yim.controller;

import com.yim.pojo.Advice;
import com.yim.pojo.Notice;
import com.yim.pojo.ParkingLot;
import com.yim.pojo.ParkingSpace;
import com.yim.service.UserService;
import com.yim.util.ApiResHandler;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/selectNoticeList")
    public ApiRes selectNoticeList(){
        List<Notice> notices = userService.selectNoticeList();
        return ApiResHandler.succss(notices);
    }

    @GetMapping("/selectMyAdvice")
    public ApiRes selectMyAdvice( Integer id){
//        Integer i = Integer.valueOf(id);
        List<Advice> advice = userService.selectMyAdvice(id);
        return ApiResHandler.succss(advice);
    }

    @PostMapping("/createAdvice")
    public ApiRes createAdvice(@RequestBody Advice advice){
        advice.setTime(null);
        int i = userService.createAdvice(advice);
        return ApiResHandler.succss(i);
    }

    @GetMapping("/selectParkingLot")
    public ApiRes selectParkingLot(){
        List<ParkingLot> lots = userService.selectParkingLot();
        return ApiResHandler.succss(lots);
    }

    @GetMapping("/selectParkingSpace")
    public ApiRes selectParkingSpace(Integer lotId){
        List<ParkingSpace> spaces = userService.selectParkingSpace(lotId);
        return ApiResHandler.succss(spaces);
    }
}
