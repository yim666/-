package com.yim.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.ParkingSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParkingSpaceMapper extends BaseMapper<ParkingSpace> {
    @Update("UPDATE parkingspace  SET  status=#{status}   WHERE parking_space_id=#{parkingSpaceId}")
    int updateStatus(ParkingSpace space);

    @Select("select p.*,p2.status as status3 from parkorder p left join parkingspace p2 on p.parking_space_id =p2.parking_space_id  where p.user_id =#{userId} and p2.status =1")
    List<Map> selectMyOrder(Integer userId);
}
