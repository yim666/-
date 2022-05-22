package com.yim.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("parkinglot")
public class ParkingLot {
    @TableId(value = "parking_lot_id",type = IdType.AUTO)
    private Integer parkingLotId;
    private String parkingLotName;
    private Integer status;
}
