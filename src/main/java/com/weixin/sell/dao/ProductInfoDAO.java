package com.weixin.sell.dao;

import com.weixin.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDAO extends JpaRepository<ProductInfo, String> {

    //    查询上架的商品
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
