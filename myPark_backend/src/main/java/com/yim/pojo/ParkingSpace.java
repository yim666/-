package com.yim.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("parkingspace")
public class ParkingSpace {
    @TableId(value = "parking_space_id",type = IdType.AUTO)
    private Integer parkingSpaceId;
    private Integer parkingLotId;
    private Integer status;
}
