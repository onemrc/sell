package com.weixin.sell.dao;

import com.weixin.sell.domain.SellerInfo;
import com.weixin.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDAOTest {

    private final String OPENID = "abc";
    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        SellerInfo result = sellerInfoDAO.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerInfoDAO.findByOpenid(OPENID);
        Assert.assertEquals(sellerInfo.getOpenid(), OPENID);
    }

}