package com.weixin.sell.dao;

import com.weixin.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDAOTest {

    @Autowired
    private ProductCategoryDAO categoryDAO;

    @Test
    public void findByIdTest(){
        Optional<ProductCategory> category=categoryDAO.findById(1);
        System.out.println(category.toString());
    }

    @Test
    @Transactional//测试完后回滚，数据库保持干净
    public void saveTest(){
//        Optional<ProductCategory> category=categoryDAO.findById(2);

        ProductCategory category=new ProductCategory("男生最爱",3);

        categoryDAO.save(category);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(1,8,9);

        List<ProductCategory> result=categoryDAO.findByCategoryTypeIn(list);

        System.out.println("reslistSize="+result.size());
    }

}