package com.yim.pojo;

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
    private Integer parkingSpaceId;
    private Integer parkingLotId;
    private Integer status;
}
