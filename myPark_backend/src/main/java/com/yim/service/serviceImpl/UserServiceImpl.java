package com.yim.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yim.mapper.*;
import com.yim.pojo.*;
import com.yim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private ParkingLotMapper parkingLotMapper;
    @Autowired
    private ParkingSpaceMapper parkingSpaceMapper;

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
        return parkingLotMapper.selectList(null);
    }

    @Override
    public List<ParkingSpace> selectParkingSpace(Integer lotId) {
        QueryWrapper<ParkingSpace> qw = new QueryWrapper();
        qw.eq("parking_lot_id", lotId).eq("status", 0);
        return parkingSpaceMapper.selectList(qw);
    }

    @Override
    public int updateSpaceStatus(ParkingSpace space) {
        return parkingSpaceMapper.updateStatus(space);
    }

    @Override
    public int createOrder(Order order) {
        int i = orderMapper.insert(order);
        return order.getId();
    }

    @Override
    public List<Order> selectMyorderList(Integer userId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id", userId);
        qw.orderByDesc("create_time");
        return orderMapper.selectList(qw);
    }

    @Override
    public int changeSta(ParkingSpace space, Order order) {
        parkingSpaceMapper.updateStatus(space);
        orderMapper.updateStatus(order);
        return order.getStatus();
    }

    @Override
    public int changeMoney(int fee, Integer userId) {
        return userMapper.changeMoney(fee,userId);
    }

    @Override
    public ParkingSpace selectOneSpace(Integer parkingSpaceId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parking_space_id", parkingSpaceId);
        return parkingSpaceMapper.selectOne(qw);
    }

    @Override
    public User selectUser(Integer userId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id", userId);
        return userMapper.selectOne(qw);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int createUser(User user) {
        int i = userMapper.insert(user);
        return user.getUserId();
    }


//    用户找回密码，查询当前用户名信息
    @Override
    public User forgetPassWord(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public ParkingSpace selectOrderSpace(Integer spaceId) {
        ParkingSpace parkingSpace = parkingSpaceMapper.selectById(spaceId);
        return parkingSpace;
    }

    @Override
    public List<Order> selectMyorderListByDate(Integer userId, Date date1, Date date2) {
        QueryWrapper<Order> qw = new QueryWrapper<>();
        if(userId==0){
            qw.ge("create_time", date1).le("end_time", date2).orderByDesc("end_time");
        }else {
            qw.eq("user_id", userId).ge("create_time", date1).le("end_time", date2).orderByDesc("end_time");
        }
        return orderMapper.selectList(qw);
    }

    @Override
    public ParkingLot selectOneParkingLot(Integer parkingLotId) {
        return parkingLotMapper.selectById(parkingLotId);
    }

}
