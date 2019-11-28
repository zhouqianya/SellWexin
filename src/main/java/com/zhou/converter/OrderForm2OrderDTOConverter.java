package com.zhou.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhou.dataobject.OrderDetail;
import com.zhou.dataobject.OrderMaster;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/19 16:04
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            //gson string 转 list集合
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
