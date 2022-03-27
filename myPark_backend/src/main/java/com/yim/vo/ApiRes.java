package com.yim.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRes<T> {
    /**
     * 错误码，表示一种错误类型
     * 请求成功，状态码为200
     */
    private int code;

    /**
     * 对错误代码的详细解释
     */
    private String message;

    /**
     * 返回的结果包装在value中，value可以是单个对象 也可以是Map ArrayList啥的
     */
    private T data;


}
