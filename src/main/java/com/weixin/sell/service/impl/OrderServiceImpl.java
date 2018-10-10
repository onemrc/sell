package com.weixin.sell.service.impl;

import com.weixin.sell.converter.OrderMaster2OrderDTOConverter;
import com.weixin.sell.dao.OrderDetailDAO;
import com.weixin.sell.dao.OrderMasterDAO;
import com.weixin.sell.domain.OrderDetail;
import com.weixin.sell.domain.OrderMaster;
import com.weixin.sell.domain.ProductInfo;
import com.weixin.sell.dto.CartDTO;
import com.weixin.sell.dto.OrderDTO;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.service.OrderService;
import com.weixin.sell.service.ProductService;
import com.weixin.sell.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final OrderDetailDAO orderDetailDAO;
    private final OrderMasterDAO orderMasterDAO;
    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(ProductService productService, OrderDetailDAO orderDetailDAO, OrderMasterDAO orderMasterDAO) {
        this.productService = productService;
        this.orderDetailDAO = orderDetailDAO;
        this.orderMasterDAO = orderMasterDAO;
    }

    @Override
    @Transactional //事务回滚
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量、价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {//商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库  orderDetail
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderAmount(orderAmount);
            orderDetailDAO.save(orderDetail);
        }


        //3.写入订单数据库(orderMaster、orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDAO.save(orderMaster);

        //4.扣库存
        //这个表达式的意思是，将orderDTO.getOrderDetailList 的属性值取出 再赋给 CartDTO ，再存入List 直接赋给 cartDTOList
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> temp = orderMasterDAO.findById(orderId);
        OrderMaster orderMaster;
        if (temp.isPresent()) {
            orderMaster = orderMasterDAO.findById(orderId).get();
        } else {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }


        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {//if 空
            throw new SellException(ResultEnum.ORDER_DETAIL_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDAO.findByBuyerOpenid(buyerOpenId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.conver(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【取消订单】订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();

        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDAO.save(orderMaster);
        if (updateResult == null) {
            logger.error("【取消订单】 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //放回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            logger.error("【取消订单】订单中无商品详情,orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);


        //如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【完结订单】订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster updateResult = orderMasterDAO.save(orderMaster);
        if (updateResult == null) {
            logger.error("【完结订单】 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【订单支付】订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            logger.error("【订单支付】订单支付状态不正确,OrderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }


        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster updateResult = orderMasterDAO.save(orderMaster);
        if (updateResult == null) {
            logger.error("【订单支付】 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }


        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDAO.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.conver(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
