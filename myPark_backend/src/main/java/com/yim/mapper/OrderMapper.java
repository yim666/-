package com.yim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Insert("INSERT INTO parkorder  ( user_id, parking_space_id, car_id,status )  VALUES  ( #{userId},#{parkingSpaceId},#{carId},#{status} )")
    int insertOrder(Order order);

    @Update("UPDATE parkorder SET  status=#{status} , end_time=#{endTime},fee=#{fee}   WHERE parking_space_id=#{parkingSpaceId} AND id=#{id}")
    void updateStatus( Order order);




}
