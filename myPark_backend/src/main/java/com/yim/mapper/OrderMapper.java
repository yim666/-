package com.yim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Insert("INSERT INTO parkorder  ( user_id, parking_space_id, car_id,status )  VALUES  ( #{userId},#{parkingSpaceId},#{carId},#{status} )")
    int insertOrder(Order order);
}
