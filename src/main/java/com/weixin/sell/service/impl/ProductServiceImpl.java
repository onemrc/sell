package com.weixin.sell.service.impl;

import com.weixin.sell.dao.ProductInfoDAO;
import com.weixin.sell.domain.ProductInfo;
import com.weixin.sell.dto.CartDTO;
import com.weixin.sell.enums.ProductStatusEnum;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDAO.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            Optional<ProductInfo> temp=productInfoDAO.findById(cartDTO.getProductId());
            ProductInfo productInfo;
            if (temp.isPresent()){
                productInfo=productInfoDAO.findById(cartDTO.getProductId()).get();
            }else {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (result<0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            productInfoDAO.save(productInfo);

        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> temp=productInfoDAO.findById(productId);
        ProductInfo productInfo;
        if (temp.isPresent()){
            productInfo=productInfoDAO.findById(productId).get();
        }else {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        return productInfoDAO.save(productInfo);
    }

    @Override
    public ProductInfo ofSale(String productId) {
        Optional<ProductInfo> temp=productInfoDAO.findById(productId);
        ProductInfo productInfo;
        if (temp.isPresent()){
            productInfo=productInfoDAO.findById(productId).get();
        }else {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        return productInfoDAO.save(productInfo);
    }
}
