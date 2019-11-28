package com.zhou.service.impl;

import com.zhou.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PayServiceImplTest {

    @Autowired
    PayServiceImpl payService;

    @Autowired
    OrderServiceImpl orderService;
    @Test
    void create() {
        payService.create(orderService.findOne("1574224050753"));
    }
}