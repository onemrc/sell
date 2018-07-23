package com.weixin.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 */

@Data
public class ProductVO {

    @JsonProperty("name")       //序列化返回给前端的JSon 名字就是 name
    private String categoryName;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
