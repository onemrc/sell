package com.weixin.sell.service.impl;

import com.weixin.sell.dao.SellerInfoDAO;
import com.weixin.sell.domain.SellerInfo;
import com.weixin.sell.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellService {

    @Autowired
    private SellerInfoDAO sellerInfoDAO;


    @Override
    public SellerInfo findSellerInfoByOpenid(String openId) {
        return sellerInfoDAO.findByOpenid(openId);
    }
}
