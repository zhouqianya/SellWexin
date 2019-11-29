package com.zhou.service;

import com.zhou.dto.OrderDTO;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 19:05
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
