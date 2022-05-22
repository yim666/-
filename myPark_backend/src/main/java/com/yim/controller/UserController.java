package com.yim.controller;

import com.yim.pojo.*;
import com.yim.service.UserService;
import com.yim.util.ApiResHandler;
import com.yim.util.DateTimeUtils;
import com.yim.util.MailClient;
import com.yim.vo.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    //     项目路径
    @Value("${community.path.domain}")
    private String domain;
    //    模板引擎，用来开启ThymeLeaf
    @Autowired
    private TemplateEngine templateEngine;
//    //项目名
//    @Value("${server.servlet.context-path}")
//    private String contextPath;

    @Autowired
    private UserService userService;
    @Autowired
    private MailClient mailClient;

    @PostMapping("/CreateUser")
    public ApiRes createUser(@RequestBody User user){
        String password = user.getPassword();
        //spring提供的盐值加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(password);
        user.setPassword(pass);
        int userId = userService.createUser(user);
        String email = user.getEmail();
        //创建完成发送用户ID到用户邮箱
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
        // 发送激活邮件
//        Context是thymeleaf的类，用来给模板引擎传值
        User user= userService.selectUser(id);
        String password = user.getPassword();
        String code = password.substring(2, 8);
        Context context = new Context();
        context.setVariable("userName", user.getUserName());
        // http://localhost:8000/user/2001/code
        String url = domain  + "/user/" + user.getUserId()+"/"+ code ;
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "找回账号", content);
        return ApiResHandler.success(user.getEmail());
    }

    @GetMapping("/{userId}/{code}")
    public String resetPassword( @PathVariable("userId") Integer id,@PathVariable("code") String code){
        User user= userService.selectUser(id);
        String password1 = user.getPassword();
        String code2 = password1.substring(2, 8);
        if(!code2.equals(code)){
            return "无效访问";
        }
        String password = "12345";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(password);
        user.setPassword(pass);
        int i = userService.updateUser(user);
        String a="密码重置为12345，请及时登录更改密码";
        return a;
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
    @GetMapping("/selectOrderSpace")
    public ApiRes selectOrderSpace(Integer spaceId){
        ParkingSpace space = userService.selectOrderSpace(spaceId);
        if(space.getStatus()==1){
            ParkingSpace space1 = new ParkingSpace();
            space1.setParkingSpaceId(spaceId);
            space1.setStatus(0);
            int i = userService.updateSpaceStatus(space1);
            Integer a= 1;
            return ApiResHandler.success(a);
        }else{
            Integer b= 2;
            return ApiResHandler.success(b);

        }

    }
    /**
     * params: spaceId status
     * return:
     * */
    @PutMapping("/updateSpaceStatus")
    public ApiRes updateSpaceStatus(@RequestBody ParkingSpace space){
        int i = userService.updateSpaceStatus(space);
        return ApiResHandler.success(i);
    }

    @PutMapping("/updateUser")
    public ApiRes updateUser(@RequestBody User user){
        String password = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(password);
        user.setPassword(pass);
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
            ParkingLot lot=userService.selectOneParkingLot(space1.getParkingLotId());
            Order order = new Order();
            order.setUserId((Integer) user.get("userId"));
            order.setParkingSpaceId(parkingSpaceId);
            order.setParkingLotName(lot.getParkingLotName());
            order.setCarId((String) user.get("carId"));
            order.setCreateTime(null);
            order.setFee(null);
            order.setStatus(1);
            int id = userService.createOrder(order);
            return ApiResHandler.success(id);
        }else {
            return ApiResHandler.fail();
        }
    }


    @GetMapping("/selectMyorderList")
    public ApiRes selectMyorderList(Integer userId){
        List<Order> Myorder = userService.selectMyorderList(userId);
        return ApiResHandler.success(Myorder);
    }
    @PostMapping("/selectMyorderListByDate")
    public ApiRes selectMyorderListByDate(@RequestBody Map SelectOrder) throws ParseException {
        String userIdd = (String) SelectOrder.get("userId");
        Integer userId= Integer.valueOf(userIdd);
        ArrayList dateRange = (ArrayList) SelectOrder.get("dateRange");
        String from =  dateRange.get(0)  + " 00:00:00";
        String to =   dateRange.get(1) + " 23:59:59";
        Date date1 = DateTimeUtils.StringToDate(from);
        Date date2 = DateTimeUtils.StringToDate(to);

        List<Order> Myorder = userService.selectMyorderListByDate(userId,date1,date2);
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
            System.out.println(spaceId+"订单已完成，待支付");
        }
        int i = userService.changeSta(space,order);
        if (i==2){
            System.out.println("状态变更为 停车中。。。");
        }
        return ApiResHandler.success(i);
    }
}
