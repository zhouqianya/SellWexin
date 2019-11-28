package com.zhou.service;

import com.zhou.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openId,String orderId);

    OrderDTO cancelOrder(String openId,String orderId);

}
