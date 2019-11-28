package com.zhou.service.impl;

import com.zhou.dto.OrderDTO;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/20 14:50
 */
@Service
@Slf4j
public class BuyerServiceImpl implements com.zhou.service.BuyerService {

    @Autowired
    OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {

        return checkOrderOwner(openId,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO =checkOrderOwner(openId,orderId);

        if (null == orderDTO){
            throw  new SellException(ResultEnum.ORDERNOTEXIST);
        }
        orderService.cancel(orderDTO);
        return null;
    }

    private  OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO orderDTO= orderService.findOne(orderId);

        if (null == orderDTO){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("【查询订单】订单openid不一致");
            throw  new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
