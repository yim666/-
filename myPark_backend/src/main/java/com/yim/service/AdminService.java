package com.yim.service;

import com.yim.pojo.*;
import com.yim.util.PageRes;

import java.util.List;

public interface AdminService {
    List<User> selectUserList();

    int updateUserList(User user);

    PageRes selectNotice(PageRes pageRes);

    int createNotice(Notice notice);

    List<Advice> selectAdviceList();

    List<Order> selectOrderList();

    List<ParkingSpace> selectParkingSpace(Integer lotId);

    int addSpace(ParkingSpace space);

    int changeSta(ParkingSpace space, Order order);

    int deleteSpace(Integer id);

    List<Order> selectOrderListByCarId(String carId);

    int addLot(ParkingLot lot);

    int deleteLot(Integer lotId);
}
