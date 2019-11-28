package com.zhou.controller;

import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.zhou.config.WechatPayConfig;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.service.OrderService;
import com.zhou.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/23 08:48
 */
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @Autowired
    OrderService orderService;

    @Autowired
    PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam(required = false, value = "orderid") String orderId,
                               @RequestParam(required = false, value = "returnUrl") String returnUrl){
        Map<String, Object> map = new HashMap<>();
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (null == orderDTO) {
            throw new SellException(ResultEnum.ORDERNOTEXIST);
        }

        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public   ModelAndView notifyd(@RequestBody String notifyData){

        payService.notify(notifyData);

        return new ModelAndView("/pay/success") ;
    }


}
