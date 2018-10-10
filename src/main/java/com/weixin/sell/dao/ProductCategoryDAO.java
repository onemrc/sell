package com.weixin.sell.dao;

import com.weixin.sell.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Integer> {

    //通过CategoryType一次性查多条记录
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
