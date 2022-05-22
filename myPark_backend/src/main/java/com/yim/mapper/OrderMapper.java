package com.yim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Insert("INSERT INTO parkorder  ( user_id, parking_space_id, car_id,status )  VALUES  ( #{userId},#{parkingSpaceId},#{carId},#{status} )")
    int insertOrder(Order order);

    @Select("select o.id,o.user_id ,o.parking_space_id ,\n" +
            "(select parking_lot_name from parkinglot where parking_lot_id=p.parking_lot_id) as parkingLotName, \n" +
            "o.car_id ,o.create_time ,o.end_time ,o.fee ,o.status \n" +
            "from parkorder o\n" +
            "left join parkingspace  p on o.parking_space_id =p.parking_space_id \n" +
            "order by create_time desc ")
    List<Order> selectOrderLists();

    @Update("UPDATE parkorder SET  status=#{status} , end_time=#{endTime},fee=#{fee}   WHERE parking_space_id=#{parkingSpaceId} AND id=#{id}")
    void updateStatus( Order order);




}
