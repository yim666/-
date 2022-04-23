package com.yim.controller;

import com.yim.pojo.*;
import com.yim.service.UserService;
import com.yim.util.ApiResHandler;
import com.yim.util.DateTimeUtils;
import com.yim.util.MailClient;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailClient mailClient;

    @PostMapping("/CreateUser")
    public ApiRes createUser(@RequestBody User user){
        int userId = userService.createUser(user);
        String email = user.getEmail();
        mailClient.sendMail(email, "注册", "恭喜您注册成功！您的账号为："+ userId);
        return ApiResHandler.success(userId);
    }

    @GetMapping("/selectNoticeList")
    public ApiRes selectNoticeList(){
        List<Notice> notices = userService.selectNoticeList();
        return ApiResHandler.success(notices);
    }

    @GetMapping("/selectUser")
    public ApiRes selectUser(Integer id){
        User u= userService.selectUser(id);
        return ApiResHandler.success(u);
    }

    @GetMapping("/forgetPassWord")
    public ApiRes forgetPassWord(Integer id){
        User u= userService.forgetPassWord(id);
        String password = u.getPassword();
        String email = u.getEmail();
        mailClient.sendMail(email, "密码找回", "您当前账号的密码为："+ password);
        return ApiResHandler.success(email);
    }

    @GetMapping("/selectMyAdvice")
    public ApiRes selectMyAdvice( Integer id){
//        Integer i = Integer.valueOf(id);
        List<Advice> advice = userService.selectMyAdvice(id);
        return ApiResHandler.success(advice);
    }

    @PostMapping("/createAdvice")
    public ApiRes createAdvice(@RequestBody Advice advice){
        advice.setTime(null);
        int i = userService.createAdvice(advice);
        return ApiResHandler.success(i);
    }


    @GetMapping("/selectParkingLot")
    public ApiRes selectParkingLot(){
        List<ParkingLot> lots = userService.selectParkingLot();
        return ApiResHandler.success(lots);
    }

    //查询可使用的停车位
    @GetMapping("/selectParkingSpace")
    public ApiRes selectParkingSpace(Integer lotId){
        List<ParkingSpace> spaces = userService.selectParkingSpace(lotId);
        return ApiResHandler.success(spaces);
    }
    //查询当前用户进行中的订单
//    @GetMapping("/selectParkingStatus")
//    public ApiRes selectParkingStatus(Integer userId){
//        List<Map> orders = userService.selectParkingStatus(userId);
//        return ApiResHandler.success(orders);
//    }

    @PutMapping("/updateSpaceStatus")
    public ApiRes updateSpaceStatus(@RequestBody ParkingSpace space){
        int i = userService.updateSpaceStatus(space);
        return ApiResHandler.success(i);
    }

    @PutMapping("/updateUser")
    public ApiRes updateUser(@RequestBody User user){
        int i = userService.updateUser(user);
        return ApiResHandler.success(i);
    }

    @PostMapping("/createOrder")
    public ApiRes createOrder(@RequestBody Map<String,Map> map){
        Map user = map.get("user");
        Map space = map.get("parkingSpace");
        Integer parkingSpaceId = (Integer) space.get("parkingSpaceId");
//        确定车位状态是否可以预定
        ParkingSpace space1 = userService.selectOneSpace(parkingSpaceId);
        if (space1.getStatus()==0) {
            Order order = new Order();
            order.setUserId((Integer) user.get("userId"));
            order.setParkingSpaceId(parkingSpaceId);
            order.setCarId((String) user.get("carId"));
            order.setCreateTime(null);
            order.setFee(null);
            order.setStatus(1);
            int i = userService.createOrder(order);
            return ApiResHandler.success(i);
        }else {
            return ApiResHandler.fail();
        }
    }


    @GetMapping("/selectMyorderList")
    public ApiRes selectMyorderList(Integer userId){
        List<Order> Myorder = userService.selectMyorderList(userId);
        return ApiResHandler.success(Myorder);
    }

    @PutMapping("/changeSta")
    public ApiRes changeSta(@RequestBody Map m) throws ParseException {
        Integer spaceId = (Integer) m.get("spaceId");
        Integer spaceStatus = Integer.parseInt((String) m.get("spaceStatus")) ;
        Integer orderId =  (Integer) m.get("id");
        Integer userId =  Integer.parseInt((String) m.get("userId"));
        String createTime =  (String) m.get("createTime");
        ParkingSpace space = new ParkingSpace();
        Order order = new Order();
        space.setParkingSpaceId(spaceId);
        order.setParkingSpaceId(spaceId);
        space.setStatus(spaceStatus);
        order.setStatus(spaceStatus);
        order.setId(orderId);
        if(spaceStatus == 0){
            LocalDateTime now = LocalDateTime.now();
            Date date = DateTimeUtils.dateToDate(now);
            order.setEndTime(date);
            Date date1 = DateTimeUtils.StringToDate(createTime);
            long l = date.getTime() - date1.getTime();
//            相差的小时数
            long stampHour = l / (60 * 60 * 1000) ;
            int fee =(int) (stampHour + 1) * 10;
            order.setFee(fee);
            int j=userService.changeMoney(fee,userId);
            System.out.println(date);
            System.out.println("订单已完成，待支付");
        }
        int i = userService.changeSta(space,order);
        if (i==2){
            System.out.println("状态变更为 停车中。。。");
        }
        return ApiResHandler.success(i);
    }
}
