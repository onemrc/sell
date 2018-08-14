package com.weixin.sell.controller;

import com.weixin.sell.converter.OrderForm2OrderDTOConverter;
import com.weixin.sell.dto.OrderDTO;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.OrderForm;
import com.weixin.sell.service.BuyerService;
import com.weixin.sell.service.OrderService;
import com.weixin.sell.utils.ResultVOUtil;
import com.weixin.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {
//    不推荐这种注射方式
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private BuyerService buyerService;
    private final OrderService orderService;

    private final BuyerService buyerService;

    @Autowired
    public BuyerOrderController(OrderService orderService, BuyerService buyerService) {
        this.orderService = orderService;
        this.buyerService = buyerService;
    }


    //创建订单
    @PostMapping(value = "/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result=orderService.create(orderDTO);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping(value = "/list")
    public ResultVO list(@RequestParam("openid") String openid,
                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);

        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    //订单详情
    @GetMapping(value = "/detail")
    public ResultVO detail(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，需改进
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);

        return ResultVOUtil.success(orderDTO);

    }

    //取消订单
    @PostMapping(value = "/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){

        buyerService.cancelOrderOne(openid,orderId);
        return ResultVOUtil.success();
    }
}
