package com.weixin.sell.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderDetail {

//  id
    @Id
    private String detailId;

//    订单id
    private String orderId;

//    商品id
    private String productId;

//    商品名称
    private String productName;

//    订单总价格
    private BigDecimal orderAmount;

//    商品数量
    private Integer productQuantity;

//    商品小图
    private String productIcon;

//    创建时间
    private Date createTime;

//    更新时间
    private Date updateTime;
}