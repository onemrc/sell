package com.weixin.sell.dao;

import com.weixin.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDAOTest {
    private final String OPENID = "100100";
    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("陈一");
        orderMaster.setBuyerPhone("10000");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(20));


        Assert.assertNotNull(orderMasterDAO.save(orderMaster));
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        //从第0页开始 ， size=3
        PageRequest pageRequest = new PageRequest(0, 3);

        Page<OrderMaster> res = orderMasterDAO.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, res.getTotalElements());
        System.out.println(res.getTotalElements());//内容总条数
    }


}