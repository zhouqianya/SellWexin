package com.zhou.controller;

import com.zhou.dataobject.ProductCategory;
import com.zhou.dataobject.ProductInfo;
import com.zhou.service.CategoryService;
import com.zhou.service.ProductService;
import com.zhou.util.ResultUtil;
import com.zhou.vo.ProductInfoVO;
import com.zhou.vo.ProductVO;
import com.zhou.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 13:20
 */
@RestController
@Slf4j
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {

        //当前上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //当前上架商品的类型
        List<Integer> catagoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        //类型对应的名称等 类型表
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(catagoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        /**
         * 遍历类型种类
         */
        for (ProductCategory productCategory : productCategoryList) {

            //当前类型，所包含的商品
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType()).setCategoryName(productCategory.getCategoryName());


            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            /**
             * 遍历商品，主要是取出商品类型和当前循环一致的类型
             */
            for (ProductInfo productInfo : productInfoList) {
                log.info("price={}",productInfo.getProductPrice());
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    log.info("2price={}",productInfoVO.getProductPrice());
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVO(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultUtil.success(productVOList);
    }
}
