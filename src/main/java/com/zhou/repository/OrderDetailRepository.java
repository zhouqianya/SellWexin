package com.zhou.repository;

import com.zhou.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 20:49
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
