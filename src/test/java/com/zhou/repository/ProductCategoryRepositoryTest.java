package com.zhou.repository;

import com.zhou.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void find() {
        List<ProductCategory> productCategory = repository.findAll();
        System.out.println(productCategory);
    }

    @Test
    public void saveTest() {

        ProductCategory productCategory=new ProductCategory("男生最爱",7);
        ProductCategory res=repository.save(productCategory);
        Assert.assertNotNull(res);
    }

    @Test
    public void findByCategoryTest() {

        List<Integer> list= Arrays.asList(3,4);
        List<ProductCategory> res=repository.findByCategoryTypeIn(list);
        System.out.println(res);
        Assert.assertNotNull(res);
    }
}