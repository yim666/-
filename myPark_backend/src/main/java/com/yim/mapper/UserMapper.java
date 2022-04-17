package com.yim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yim.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE user SET  money=money-#{fee}   WHERE user_id =#{userId}")
    int changeMoney(int fee, Integer userId);
}
