package com.yim;

import com.yim.pojo.Admin;
import com.yim.pojo.Notice;
import com.yim.pojo.User;
import com.yim.service.LoginService;
import com.yim.util.ApiResHandler;
import com.yim.util.DateTimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class MyParkBackendApplicationTests {
    @Autowired
    private LoginService loginService;
        @Test
        void loginTest() {
            User user = new User();
            user.setUserId(2001);
            user.setPassword("123");
            //spring提供的盐值加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String pass = encoder.encode(user.getPassword());
            //权限为用户
                User user2 = loginService.userLogin(user.getUserId(), pass);
                boolean matches = encoder.matches(user.getPassword(), user2.getPassword());
                if((user2 !=null) && matches){
                    System.out.println(user2);
                }else {
                    System.out.println("登录失败，用户或密码错误！！！");
            }
        }

    @Test
    void testDate() throws ParseException {
        //设置需要相减的两个时间
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() - 21 * 60 * 1000);
        //调用时间相减的方法
        long stamp = date1.getTime()-date2.getTime();
        //使用获得的结果毫秒，拼接时间字符串
        long stampDay = stamp / (24 * 60 * 60 * 1000);
        long stampHour = stamp / (60 * 60 * 1000) % 24;
        long stampMin = stamp / (60 * 1000) % 60;
        long stampSec = stamp / 1000 % 60;
        String timeText = stampDay + "天" + stampHour + "小时"+stampMin + "分钟"+stampSec + "秒";
        System.out.println(timeText);
    }



}
