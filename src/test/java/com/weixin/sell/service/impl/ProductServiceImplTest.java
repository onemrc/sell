package com.weixin.sell.service.impl;

import com.weixin.sell.domain.ProductInfo;
import com.weixin.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo=productService.findOne("123456");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList=productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest=PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage=productService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("汉堡");
        productInfo.setProductPrice(new BigDecimal(10.0));
        productInfo.setProductStock(20);
        productInfo.setProductDescription("肯德基");
        productInfo.setProductIcon("xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(1);

        ProductInfo result=productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSaleTest(){
        ProductInfo reslut=productService.onSale("123456");
        Assert.assertNotEquals(ProductStatusEnum.UP,reslut.getProductStatus());
    }

    @Test
    public void ofSaleTest(){
        ProductInfo reslut=productService.ofSale("123456");
        Assert.assertNotEquals(ProductStatusEnum.DOWN,reslut.getProductStatus());
    }

}