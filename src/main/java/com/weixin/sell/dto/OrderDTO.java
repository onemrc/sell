package com.weixin.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weixin.sell.domain.OrderDetail;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import com.weixin.sell.utils.EnumUtil;
import com.weixin.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专门用于在各个层之间 传输数据
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//if是null 就不返回
public class OrderDTO {

    List<OrderDetail> orderDetailList;
    private String orderId;
    //买家名字
    private String buyerName;
    //    买家电话
    private String buyerPhone;
    //    买家地址
    private String buyerAddress;
    //    买家微信openid
    private String buyerOpenid;
    //    金额
    private BigDecimal orderAmount;
    //    订单状态，默认0新下单
    private Integer orderStatus;
    //    支付状态，默认 未支付
    private Integer payStatus;
    //    创建时间
    @JsonSerialize(using = Date2LongSerializer.class)//Date格式转换
    private Date createTime;
    //    更新时间
    @JsonSerialize(using = Date2LongSerializer.class)//Date格式转换
    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
