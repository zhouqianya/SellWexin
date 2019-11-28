package com.zhou.repository;

import com.zhou.dataobject.OrderDetail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    void save() {
        orderDetailRepository.save(new OrderDetail().setDetailId("2019")
                .setOrderId("1").setProductId("1001").setProductIcon("http://")
                .setProductName("pipi").setProductPrice(new BigDecimal(6.6))
                .setProductQuantity(11));
    }

    @Test
    void findByBuyerOpenidTest() {

        orderDetailRepository.findByOrderId("1");
    }
}