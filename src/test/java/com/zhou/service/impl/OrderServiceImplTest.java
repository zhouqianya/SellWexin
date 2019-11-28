package com.zhou.service.impl;

import com.zhou.dto.OrderDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Test()
    void create() {
        orderService.create(new OrderDTO().setBuyerName("周天").setBuyerOpenid("1827")
                .setBuyerPhone("18271696864").setBuyerAddress("上海").setOrderAmount(new BigDecimal(100.9)));
        while (true){
//            create();
        }
    }

    @Test
    void findOne() {
        orderService.findOne("1574837928738");
    }

    @Test
    void findList() {
        Page<OrderDTO> page = orderService.findList("1827", PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());

    }

    @Test
    void cancel() {

        orderService.cancel(orderService.findOne("1"));
    }

    @Test
    void finish() {
        orderService.finish(orderService.findOne("1"));

    }

    @Test
    void paid() {
        orderService.paid(orderService.findOne("1"));

    }

    @Test
    void list() {
        Page<OrderDTO> page = orderService.findList(PageRequest.of(0, 1));

        Assert.assertTrue("测试查找全部订单",page.getTotalElements()>0);
    }
}