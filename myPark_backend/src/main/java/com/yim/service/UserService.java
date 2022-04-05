package com.yim.service;


import com.yim.pojo.Advice;
import com.yim.pojo.Notice;
import com.yim.pojo.ParkingLot;
import com.yim.pojo.ParkingSpace;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    List<Notice> selectNoticeList();

    int createAdvice(Advice advice);

    List<Advice> selectMyAdvice(Integer id);

    List<ParkingLot> selectParkingLot();

    List<ParkingSpace> selectParkingSpace(Integer lotId);
}
