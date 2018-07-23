package com.weixin.sell.service.impl;

import com.weixin.sell.dao.ProductCategoryDAO;
import com.weixin.sell.domain.ProductCategory;
import com.weixin.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryDAO categoryDAO;

    @Override
    public Optional<ProductCategory> findOne(Integer categoryId) {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryDAO.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDAO.save(productCategory);
    }
}
