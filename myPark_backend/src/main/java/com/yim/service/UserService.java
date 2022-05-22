package com.yim.service;


import com.yim.pojo.*;

import java.util.Date;
import java.util.List;


public interface UserService {

    List<Notice> selectNoticeList();

    int createAdvice(Advice advice);

    List<Advice> selectMyAdvice(Integer id);

    List<ParkingLot> selectParkingLot();

    List<ParkingSpace> selectParkingSpace(Integer lotId);

    int updateSpaceStatus(ParkingSpace space);

    int createOrder(Order order);

    List<Order> selectMyorderList(Integer userId);

    int changeSta(ParkingSpace space, Order order);

    int changeMoney(int fee, Integer userId);

    ParkingSpace selectOneSpace(Integer parkingSpaceId);

    User selectUser(Integer userId);

    int updateUser(User user);

    int createUser(User user);

    User forgetPassWord(Integer id);

    ParkingSpace selectOrderSpace(Integer spaceId);

    List<Order> selectMyorderListByDate(Integer userId, Date date1, Date date2);

    ParkingLot selectOneParkingLot(Integer parkingLotId);

//    List<Map> selectParkingStatus(Integer userId);
}
