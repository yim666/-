package com.yim.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yim.mapper.AdviceMapper;
import com.yim.mapper.NoticeMapper;
import com.yim.mapper.ParkingLotMappr;
import com.yim.mapper.ParkingSpaceMapper;
import com.yim.pojo.Advice;
import com.yim.pojo.Notice;
import com.yim.pojo.ParkingLot;
import com.yim.pojo.ParkingSpace;
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
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parking_lot_id", lotId);
        return parkingSpaceMappr.selectList(qw);
    }
}
