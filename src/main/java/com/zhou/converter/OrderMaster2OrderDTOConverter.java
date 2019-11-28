package com.zhou.converter;

import com.zhou.dataobject.OrderMaster;
import com.zhou.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 15:05
 */
public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public  static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
       List<OrderDTO> orderDTOList= orderMasterList.stream().map(e ->
                converter(e)
                ).collect(Collectors.toList());
       return  orderDTOList;
    }
}
