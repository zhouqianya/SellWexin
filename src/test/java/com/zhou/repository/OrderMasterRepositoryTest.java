package com.zhou.repository;

import com.zhou.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    @Test
    public void save() {

        repository.save(new OrderMaster().setBuyerName("周天").setBuyerOpenid("1827")
                .setBuyerPhone("18271696864").setBuyerAddress("上海").setOrderAmount(new BigDecimal(100.9)));
    }


    @Test
    void findByBuyerOpenidTest() {

        repository.findByBuyerOpenid("1", PageRequest.of(0, 5));
    }


    @Test
    void findOneTest() {


        List<OrderMaster> orderMasterList = repository.findAll();
        orderMasterList.forEach(e -> System.out.println("---》" + e.getOrderId()));


        OrderMaster temp = new OrderMaster();
        temp.setOrderId("1");
        Example<OrderMaster> example = Example.of(temp);
        Optional<OrderMaster> category1 = repository.findOne(example);

        log.info(category1.get().getOrderId());


    }

}