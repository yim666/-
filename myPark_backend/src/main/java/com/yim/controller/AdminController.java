package com.yim.controller;

import com.yim.pojo.*;
import com.yim.service.AdminService;
import com.yim.util.ApiResHandler;
import com.yim.util.PageRes;
import com.yim.vo.ApiRes;
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

    @GetMapping("/selectOrderList")
    public ApiRes selectOrderList(){
        List<Order> Order = adminService.selectOrderList();
        return ApiResHandler.succss(Order);
    }

    @GetMapping("/selectParkingSpace")
    public ApiRes selectParkingSpace(Integer lotId){
        List<ParkingSpace> spaces = adminService.selectParkingSpace(lotId);
        return ApiResHandler.succss(spaces);
    }

    @PostMapping("/addSpace")
    public ApiRes addSpace(@RequestBody Map map){
        Integer id = (Integer) map.get("lotId");
        ParkingSpace space = new ParkingSpace();
        space.setParkingLotId(id);
        space.setStatus(0);
        int i = adminService.addSpace(space);
        return ApiResHandler.succss(i);
    }

    @PutMapping("/changeSta")
    public ApiRes changeSta(@RequestBody Map m){
        Integer spaceId = (Integer) m.get("spaceId");
        Integer spaceStatus = Integer.parseInt((String) m.get("spaceStatus")) ;
        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceId(spaceId);
        space.setStatus(spaceStatus);
        int i = adminService.changeSta(space);
        return ApiResHandler.succss(i);
    }

}
