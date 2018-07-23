package com.weixin.sell.dao;

import com.weixin.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDAO extends JpaRepository<OrderMaster,String> {

//    按照买家openid查订单 ，带分页
    Page<OrderMaster> findByBuyerOpenid(String buyerOpid, Pageable pageable);


}
