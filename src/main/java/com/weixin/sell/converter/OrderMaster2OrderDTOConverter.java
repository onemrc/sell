package com.weixin.sell.converter;

import com.weixin.sell.domain.OrderMaster;
import com.weixin.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器类
 * OrderMaster ->OrderDTO
 */
public class OrderMaster2OrderDTOConverter {

    private static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);
        return  orderDTO;
    }

    public static List<OrderDTO> conver(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
