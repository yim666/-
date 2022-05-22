package com.yim.controller;

import com.yim.pojo.*;
import com.yim.service.AdminService;
import com.yim.util.ApiResHandler;
import com.yim.util.PageRes;
import com.yim.vo.ApiRes;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/selectUserList")
    public ApiRes selectUserList(){
        List<User> users = adminService.selectUserList();
        return ApiResHandler.success(users);
    }

    @PutMapping("/updateUserList")
    public ApiRes updateUserList(@RequestBody User user){
        int i = adminService.updateUserList(user);
        return ApiResHandler.success(i);
    }

    @GetMapping("/selectNoticeList")
    public ApiRes selectNotice(PageRes pageRes){
        PageRes p = adminService.selectNotice(pageRes);
        return ApiResHandler.success(p);
    }

    @PostMapping("/createNotice")
    public ApiRes createNotice(@RequestBody Notice notice){
        notice.setTime(null);
//        notice.setId(10);
        notice.setStatus(null);
        int i = adminService.createNotice(notice);
        return ApiResHandler.success(i);
    }

    @GetMapping("/selectAdviceList")
    public ApiRes selectAdviceList(){
        List<Advice> advice = adminService.selectAdviceList();
        return ApiResHandler.success(advice);
    }

    @GetMapping("/selectOrderList")
    public ApiRes selectOrderList(){
        List<Order> Order = adminService.selectOrderList();
        return ApiResHandler.success(Order);
    }

    @GetMapping("/selectOrderListByCarId")
    public ApiRes selectOrderListByCarId(String carId){
        List<Order> Order = adminService.selectOrderListByCarId(carId);
        return ApiResHandler.success(Order);
    }

    @GetMapping("/selectParkingSpace")
    public ApiRes selectParkingSpace(Integer lotId){
        List<ParkingSpace> spaces = adminService.selectParkingSpace(lotId);
        return ApiResHandler.success(spaces);
    }

    @PostMapping("/addSpace")
    public ApiRes addSpace(@RequestBody Map map){
        Integer id = (Integer) map.get("lotId");
        ParkingSpace space = new ParkingSpace();
        space.setParkingLotId(id);
        space.setStatus(0);
        int i = adminService.addSpace(space);
        return ApiResHandler.success(i);
    }

    @PostMapping("/addLot")
    public ApiRes addLot(@RequestBody Map map){
        String name = (String) map.get("lotName");
        ParkingSpace space = new ParkingSpace();
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotName(name);
        lot.setStatus(0);
        int i = adminService.addLot(lot);
        return ApiResHandler.success(i);
    }
    //管理不得随意变更车位状态，会使进行中的订单产生混乱
    @PutMapping("/changeSta")
    public ApiRes changeSta(@RequestBody Map m){
        Integer spaceId = (Integer) m.get("spaceId");
        Integer spaceStatus = Integer.parseInt((String) m.get("spaceStatus")) ;
        ParkingSpace space = new ParkingSpace();
        Order order = new Order();
        space.setParkingSpaceId(spaceId);
        order.setParkingSpaceId(spaceId);
        space.setStatus(spaceStatus);
        order.setStatus(spaceStatus);
        int i = adminService.changeSta(space,order);
        if (i==2){
            System.out.println("状态变更为 停车中。。。");
        }
        if(i==0){
            System.out.println("订单已完成，待支付");
        }
        return ApiResHandler.success(i);
    }
    @DeleteMapping("/deleteSpace")
    public ApiRes deleteSpace( Integer spaceId){
        int i=adminService.deleteSpace(spaceId);
        return ApiResHandler.success(i);
    }
    @DeleteMapping("/deleteLot")
    public ApiRes deleteLot( Integer lotId){
        int i=adminService.deleteLot(lotId);
        return ApiResHandler.success(i);
    }

}
