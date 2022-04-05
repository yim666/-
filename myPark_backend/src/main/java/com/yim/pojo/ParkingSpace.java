package com.yim.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("parkingspace")
public class ParkingSpace {
    private Integer parkingSpaceId;
    private Integer parkingLotId;
    private Integer status;
}
