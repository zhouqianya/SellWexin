package com.zhou.controller;

import com.zhou.dataobject.ProductCategory;
import com.zhou.dataobject.ProductInfo;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.form.ProductForm;
import com.zhou.service.CategoryService;
import com.zhou.service.ProductService;
import com.zhou.util.JsonUtil;
import com.zhou.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/27 18:27
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView detail(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               Map<String, Object> map) {
        Page<ProductInfo> productInfo = productService.findAll(PageRequest.of(page - 1, size));
        map.put("productInfo", productInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list");
    }

    @GetMapping("/onsale")
    public ModelAndView oonSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("/offsale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有类目
        List<ProductCategory> CategoryList = categoryService.findAll();
        map.put("categoryList", CategoryList);

        return new ModelAndView("product/index");
    }

    /**
     *  与  @Cacheable(cacheNames = "product",key = "123") 对应
     *  修改的成功时候，会把保存的对象 存到cacheNames = "product",key = "123" 里面去，但是 返回对象需要和缓存里对象一直
     *
     *  修改的成功时候，会清除缓存 @CacheEvict(cacheNames = "product",key = "123")
     *
     */
    @PostMapping("save")
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult, Map<String, Object> map) {

        log.info("productForm ={}", JsonUtil.toJson(productForm));
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo=new ProductInfo();
        try {

            if (!StringUtils.isEmpty(productForm.getProductId())){
                //如果不为空，查询数据库信息，复覆盖修改值
                productInfo = productService.findOne(productForm.getProductId());
            }else {
                //如果为空，就设置id
                productForm.setProductId(KeyUtil.genUniqueKey());
            }

            BeanUtils.copyProperties(productForm, productInfo);

            productService.save(productInfo);

        } catch (SellException e) {
            map.put("msg", e);
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }
}
