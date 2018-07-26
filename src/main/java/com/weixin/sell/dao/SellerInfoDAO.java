package com.weixin.sell.dao;

import com.weixin.sell.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDAO extends JpaRepository<SellerInfo,String>{

    SellerInfo findByOpenid(String openId);
}
