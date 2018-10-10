package com.weixin.sell.dao;

import com.weixin.sell.domain.OrderDetail;
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
public class OrderDetailDAOTest {
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("12345");
        orderDetail.setOrderId("11");
        orderDetail.setProductId("123");
        orderDetail.setProductIcon("xx.jpg");
        orderDetail.setOrderAmount(new BigDecimal(3.0));
        orderDetail.setProductName("肠粉");
        orderDetail.setProductQuantity(6);


        Assert.assertNotNull(orderDetailDAO.save(orderDetail));
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId("11");
        Assert.assertNotEquals(0, orderDetailList.size());

    }

}