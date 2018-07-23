package com.weixin.sell.service;

import com.weixin.sell.domain.ProductCategory;


import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<ProductCategory> findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
