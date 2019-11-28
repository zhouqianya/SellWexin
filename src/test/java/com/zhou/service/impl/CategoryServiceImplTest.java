package com.zhou.service.impl;

import com.zhou.dataobject.ProductCategory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    void findOne() {

        categoryService.findOne(1);
    }

    @Test
    void findAll() {
        categoryService.findAll();
    }

    @Test
    void findByCategoryTypeIn() {
        categoryService.findByCategoryTypeIn(Arrays.asList(2, 3));
    }

    @Test
    void save() {
        categoryService.save(new ProductCategory("haha", 5));
    }
}