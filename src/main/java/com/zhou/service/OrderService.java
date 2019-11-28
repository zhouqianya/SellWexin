package com.zhou.service;

import com.zhou.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
    OrderDTO findOne(String orderId);
    Page<OrderDTO> findList(String orderId, Pageable pageable);
    OrderDTO cancel(OrderDTO orderDTO);
    OrderDTO finish(OrderDTO orderDTO);
    OrderDTO paid(OrderDTO orderDTO);
    Page<OrderDTO> findList(Pageable pageable);

}
