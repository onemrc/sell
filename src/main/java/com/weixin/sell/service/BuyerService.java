package com.weixin.sell.service;

import com.weixin.sell.dto.OrderDTO;

public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消一个订单
    OrderDTO cancelOrderOne(String openid,String orderId);
}
