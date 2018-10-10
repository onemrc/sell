package com.weixin.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weixin.sell.domain.OrderDetail;
import com.weixin.sell.dto.OrderDTO;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误,String={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
