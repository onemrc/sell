package com.weixin.sell.controller;

import com.weixin.sell.domain.ProductCategory;
import com.weixin.sell.domain.ProductInfo;
import com.weixin.sell.service.CategoryService;
import com.weixin.sell.service.ProductService;
import com.weixin.sell.utils.ResultVOUtil;
import com.weixin.sell.vo.ProductInfoVO;
import com.weixin.sell.vo.ProductVO;
import com.weixin.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {
    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public BuyerProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "list")
    public ResultVO list(){
//        1.查询所有的上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();

//        2.查询类目
        List<Integer> categoryTypeList=new ArrayList<>();
//          传统方法
        for (ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }

        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

//        3.数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setType(productCategory.getCategoryType());//商品种类
            productVO.setCategoryName(productCategory.getCategoryName());//种类名称

            //商品详情
            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);//spring提供的一个类,可以把 productInfo copy到 productInfoVO
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


//        ResultVO resultVO=new ResultVO();
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        resultVO.setData(productVOList);

        return ResultVOUtil.success(productVOList);
    }
}
