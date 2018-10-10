package com.weixin.sell.dao;

import com.weixin.sell.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, String> {

    //    按照买家orderId查订单详情 ，带分页
    List<OrderDetail> findByOrderId(String orderId);
}
