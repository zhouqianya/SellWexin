package com.zhou.service.impl;

import com.zhou.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    void findOne() {
        productService.findOne("10001");
    }

    @Test
    void findUpAll() {
        productService.findUpAll();
    }

    @Test
    void findAll() {
        Pageable request=PageRequest.of(0,2);
        Page<ProductInfo> productInfos=productService.findAll(request);

    }

    @Test
    void save() {
        ProductInfo productInfo=new ProductInfo("10002","皮蛋粥",new BigDecimal(9.8),10,"皮蛋加米，熬制","http://img1.mukewang.com/5dc3891e0001049611251125.jpg",0,3);
        ProductInfo res=productService.save(productInfo);
        Assert.assertNotNull(res);
    }
}