package com.yim.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yim.mapper.*;
import com.yim.pojo.*;
import com.yim.service.AdminService;
import com.yim.util.PageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ParkingSpaceMapper parkingSpaceMapper;
    @Override
    public List<User> selectUserList() {

        return userMapper.selectList(null);
    }

    @Override
    public int updateUserList(User user) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id", user.getUserId());
        int update = userMapper.update(user, qw);
        return update;
    }

    @Override
    public PageRes selectNotice(PageRes pageRes) {
        IPage<Notice> page = new Page<>(pageRes.getCurrent(), pageRes.getPageSize());

        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("time");
        IPage ipage = noticeMapper.selectPage(page, qw);
        pageRes.setTotal(ipage.getTotal()).setData(ipage.getRecords());
        return pageRes;
    }

    @Override
    public int createNotice(Notice notice) {
        int insert = noticeMapper.insert(notice);
        return insert;
    }

    @Override
    public List<Advice> selectAdviceList() {
        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("time");
        List<Advice> list = adviceMapper.selectList(qw);
        return list;
    }

    @Override
    public List<Order> selectOrderList() {
        return orderMapper.selectList(null);
    }

    @Override
    public List<ParkingSpace> selectParkingSpace(Integer lotId) {
        QueryWrapper<ParkingSpace> qw = new QueryWrapper();
        qw.eq("parking_lot_id", lotId);
        return parkingSpaceMapper.selectList(qw);
    }

    @Override
    public int addSpace(ParkingSpace space) {
        return parkingSpaceMapper.insert(space);
    }

    @Override
    public int changeSta(ParkingSpace space) {
        return parkingSpaceMapper.updateStatus(space);
    }
}
