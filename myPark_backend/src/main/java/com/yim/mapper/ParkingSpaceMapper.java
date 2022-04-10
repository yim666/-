package com.yim.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.ParkingSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ParkingSpaceMapper extends BaseMapper<ParkingSpace> {
    @Update("UPDATE parkingspace  SET  status=#{status}   WHERE parking_space_id=#{parkingSpaceId}")
    int updateStatus(ParkingSpace space);
}
