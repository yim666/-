package com.yim;

import com.yim.pojo.Notice;
import com.yim.util.DateTimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class MyParkBackendApplicationTests {

    @Test
    void contextLoads() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        try {
            Date parse = formatter.parse(dateString);
            System.out.println(parse);
            Notice notice = new Notice();
            notice.setTime(parse);
            System.out.println(notice.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
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
