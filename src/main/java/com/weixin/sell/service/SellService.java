package com.weixin.sell.service;

import com.weixin.sell.domain.SellerInfo;

public interface SellService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
