package com.weixin.sell.controller;

import com.weixin.sell.dto.OrderDTO;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller //用于前端展示
@RequestMapping(value = "/seller/order")
@Slf4j
public class SellerOrderController {
    private final OrderService orderService;

    @Autowired
    public SellerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 订单列表
     *
     * @param page 第几页，从 1 开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "6") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家取消订单】发生异常{}", e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/order/list");
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        return new ModelAndView("common/success");
    }

    @GetMapping(value = "/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);

        } catch (SellException e) {
            log.error("【卖家取消订单】发生异常{}", e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }
        map.put("orderDTO", orderDTO);

        return new ModelAndView("/order/detail", map);
    }

    @GetMapping(value = "/finish")
    public ModelAndView finished(@RequestParam(value = "orderId") String orderId,
                                 Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家完结订单】发生异常{}", e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/order/list");
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        return new ModelAndView("/common/success", map);
    }

}
