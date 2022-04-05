package com.yim.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageRes implements Serializable {
    //查询条件
    private String info;
//    当前页数
    private Integer current;
//    当前页条数
    private Integer pageSize;
//    总条数
    private Long total;
//    当前页具体数据
    private Object data;
}
