package com.yim.controller;

import com.yim.pojo.*;
import com.yim.service.UserService;
import com.yim.util.ApiResHandler;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    //查询可使用的停车位
    @GetMapping("/selectParkingSpace")
    public ApiRes selectParkingSpace(Integer lotId){
        List<ParkingSpace> spaces = userService.selectParkingSpace(lotId);
        return ApiResHandler.succss(spaces);
    }

    @PutMapping("/updateSpaceStatus")
    public ApiRes updateSpaceStatus(@RequestBody ParkingSpace space){
        int i = userService.updateSpaceStatus(space);
        return ApiResHandler.succss(i);
    }

    @PostMapping("/createOrder")
    public ApiRes createOrder(@RequestBody Map<String,Map> map){
        Map user = map.get("user");
        Map space = map.get("parkingSpace");
        Order order = new Order();
        order.setUserId((Integer) user.get("userId"));
        order.setParkingSpaceId((Integer) space.get("parkingSpaceId"));
        order.setCarId((String) user.get("carId"));
        order.setCreateTime(null);
        order.setFee(null);
        order.setStatus(0);
        int i = userService.createOrder(order);
        return ApiResHandler.succss(i);
    }


    @GetMapping("/selectMyorderList")
    public ApiRes selectMyorderList(Integer userId){
        List<Order> Myorder = userService.selectMyorderList(userId);
        return ApiResHandler.succss(Myorder);
    }
}
