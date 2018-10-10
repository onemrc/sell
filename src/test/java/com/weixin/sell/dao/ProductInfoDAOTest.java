package com.weixin.sell.dao;

import com.weixin.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDAOTest {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("炸鸡");
        productInfo.setProductPrice(new BigDecimal(17.0));
        productInfo.setProductStock(20);
        productInfo.setProductDescription("肯德基");
        productInfo.setProductIcon("xxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);

        Assert.assertNotNull(productInfoDAO.save(productInfo));
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productInfoList = productInfoDAO.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }

}