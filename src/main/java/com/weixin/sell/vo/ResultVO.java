package com.weixin.sell.vo;

import lombok.Data;

/**
 * Http请求最外层
 */

@Data
public class ResultVO<T> {

//    错误码
    private Integer code;

//    提示信息
    private String msg;

//    具体内容
    private T data;
}
