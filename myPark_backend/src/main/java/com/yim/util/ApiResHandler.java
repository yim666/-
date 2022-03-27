package com.yim.util;

import com.yim.vo.ApiRes;

public class ApiResHandler {

    public static ApiRes succss(Object o){
        ApiRes apiRes = new ApiRes();
        apiRes.setCode(200);
        apiRes.setMessage("请求成功！！");
        apiRes.setData(o);
        return apiRes;
    }

    public static ApiRes success(){
        return succss(null);
    }

    public static ApiRes fail(){
        ApiRes apiRes = new ApiRes();
        apiRes.setCode(400);
        apiRes.setMessage("请求失败！！");
        return apiRes;
    }

    //自定义请求返回类型
    public  static <T> ApiRes defineApiRes(Integer code,String message,T data){
        ApiRes apiRes = new ApiRes();
        apiRes.setData(data);
        apiRes.setCode(code);
        apiRes.setMessage(message);
        return apiRes;
    }
}
