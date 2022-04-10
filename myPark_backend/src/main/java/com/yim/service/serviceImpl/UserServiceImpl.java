package com.yim.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yim.mapper.*;
import com.yim.pojo.*;
import com.yim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private ParkingLotMappr parkingLotMappr;
    @Autowired
    private ParkingSpaceMapper parkingSpaceMappr;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Notice> selectNoticeList() {
        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("time");
        List<Notice> list = noticeMapper.selectList(qw);
        return list;
    }

    @Override
    public int createAdvice(Advice advice) {
        int insert = adviceMapper.insert(advice);
        return insert;
    }

    @Override
    public List<Advice> selectMyAdvice(Integer id) {
        QueryWrapper<Advice> qw = new QueryWrapper();
        qw.eq("user_id", id).orderByDesc("time");
        List<Advice> advice = adviceMapper.selectList(qw);
        return advice;
    }

    @Override
    public List<ParkingLot> selectParkingLot() {
        return parkingLotMappr.selectList(null);
    }

    @Override
    public List<ParkingSpace> selectParkingSpace(Integer lotId) {
        QueryWrapper<ParkingSpace> qw = new QueryWrapper();
        qw.eq("parking_lot_id", lotId).eq("status", 0);
        return parkingSpaceMappr.selectList(qw);
    }

    @Override
    public int updateSpaceStatus(ParkingSpace space) {
        return parkingSpaceMappr.updateStatus(space);
    }

    @Override
    public int createOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public List<Order> selectMyorderList(Integer userId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id", userId);
        return orderMapper.selectList(qw);
    }
}
