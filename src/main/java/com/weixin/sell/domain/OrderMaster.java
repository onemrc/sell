package com.weixin.sell.domain;

import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate //自动更新 timestamp
public class OrderMaster {

    @Id //订单id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //    支付状态，默认 未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //    创建时间
    private Date createTime;

    //    更新时间
    private Date updateTime;

}
