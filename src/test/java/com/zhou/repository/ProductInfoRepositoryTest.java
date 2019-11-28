package com.zhou.repository;

import com.zhou.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest() {

        ProductInfo productInfo=new ProductInfo("10001","皮蛋粥",new BigDecimal(9.8),10,"皮蛋加米，熬制","http://img1.mukewang.com/5dc3891e0001049611251125.jpg",0,3);
        ProductInfo res=repository.save(productInfo);
        System.out.println(res);
        Assert.assertNotNull(res);
    }

    @Test
    public void findByProductStatusTest() {

        repository.findByProductStatus(0);
    }

    @Test
    public void findOneTest() {

        ProductInfo temp = new ProductInfo();
        temp.setProductId("10001");

        Example<ProductInfo> example = Example.of(temp);
        Optional<ProductInfo> category1 = repository.findOne(example);

        if (category1.isPresent()) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }

}