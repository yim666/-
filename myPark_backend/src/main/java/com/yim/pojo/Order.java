package com.yim.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order")
public class Order {
    private Integer parkingSpaceId;
    private Date createTime;
    private Date endTime;
    private Integer fee;
    private Integer status;
}
