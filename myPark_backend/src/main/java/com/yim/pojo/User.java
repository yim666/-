package com.yim.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String password;

    private String email;
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer role;

    private String phone;

    private String carId;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer money;

}
