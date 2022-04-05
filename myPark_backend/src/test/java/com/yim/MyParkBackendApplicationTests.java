package com.yim;

import com.yim.pojo.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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



}
