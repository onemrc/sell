package com.weixin.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "在架"),
    DOWN(1, "下架");

    private Integer code;

    private String massage;

    ProductStatusEnum(Integer code, String massage) {
        this.code = code;
        this.massage = massage;
    }
}
