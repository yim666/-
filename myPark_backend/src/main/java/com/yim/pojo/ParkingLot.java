package com.yim.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("parkinglot")
public class ParkingLot {
    private Integer parkingLotId;
    private String parkingLotName;
}
