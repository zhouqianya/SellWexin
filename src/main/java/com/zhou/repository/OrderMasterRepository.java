package com.zhou.repository;

import com.zhou.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 20:49
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster>  findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
