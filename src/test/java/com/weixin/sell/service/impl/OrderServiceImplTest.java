package com.weixin.sell.service.impl;

import com.weixin.sell.domain.OrderDetail;
import com.weixin.sell.dto.OrderDTO;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="1101110";

    private final String ORDER_ID ="1531981774928519973";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("陈一");
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerPhone("12345");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();

//        OrderDetail o1=new OrderDetail();
//        o1.setProductId("123456");
//        o1.setProductQuantity(1);
//        orderDetailList.add(o1);

        OrderDetail o2=new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);



        Assert.assertNotNull(orderService.create(orderDTO));
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest=new PageRequest(0,3);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,pageRequest);

        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

}