package com.zhou.controller;

import com.zhou.converter.OrderForm2OrderDTOConverter;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.form.OrderForm;
import com.zhou.service.BuyerService;
import com.zhou.service.OrderService;
import com.zhou.util.ResultUtil;
import com.zhou.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/19 12:52
 */
@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {


    @Autowired
    OrderService orderService;

    @Autowired
    BuyerService buyerService;

    /**javabean form-data  x-www-form-urlencoded
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        /**
         * BindingResult 会自己验证表单数据正确性
         * 与@Valid、@NotEmpty(message = "姓名必填") 配合使用
         */
        //查询表单错误
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }
       OrderDTO createResult = orderService.create(orderDTO);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultUtil.success(map);

    }

    /**
     * @RequestParam Params
     */
    @PostMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openId") String openId,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){


        if (StringUtils.isEmpty(openId)){
            log.error("【订单列表】openid为空");
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }

        Page<OrderDTO> orderDTOPage=orderService.findList(openId, PageRequest.of(page,size));

        return  ResultUtil.success(orderDTOPage.getContent());
    }



    @GetMapping("/detail")
    public ResultVO<List<OrderDTO>> detail(@RequestParam("openId") String openId,
                                         @RequestParam("orderId") String orderId){

         OrderDTO orderDTO= buyerService.findOrderOne(openId,orderId);

         return ResultUtil.success().setData(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO<List<OrderDTO>> cancel(@RequestParam("openId") String openId,
                                           @RequestParam("orderId") String orderId){

        OrderDTO orderDTO= buyerService.cancelOrder(openId,orderId);
        orderService.cancel(orderDTO);

        return ResultUtil.success();
    }

}

