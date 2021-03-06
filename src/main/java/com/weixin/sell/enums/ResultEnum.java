package com.weixin.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_PAY_STATUS_ERROR(16, "订单支付状态不正确"),

    CART_EMPTY(17, "购物车不能为空"),

    ORDER_OWNER_ERROR(18, "该订单不属于当前用户"),
    ORDER_CANCEL_SUCCESS(19, "订单取消成功"),
    PRODUCT_STATUS_ERROR(20, "商品状态不正确"),
    CATEGORY_EXIST_ERROR(21, "商品类别已存在");


    private Integer code;//状态码

    private String message;//提示信息

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
