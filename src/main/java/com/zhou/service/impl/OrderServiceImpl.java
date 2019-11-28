package com.zhou.service.impl;

import com.zhou.converter.OrderMaster2OrderDTOConverter;
import com.zhou.dataobject.OrderDetail;
import com.zhou.dataobject.OrderMaster;
import com.zhou.dataobject.ProductInfo;
import com.zhou.dto.CartDTO;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.OrderStatusEnum;
import com.zhou.enums.PayStatusEnum;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.repository.OrderDetailRepository;
import com.zhou.repository.OrderMasterRepository;
import com.zhou.service.OrderService;
import com.zhou.service.PayService;
import com.zhou.service.ProductService;
import com.zhou.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 12:26
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderMasterRepository orderMasterRepository;


    @Autowired
    PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCTNOTEXIST);
            }
            orderAmount = productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity())
                            .add(orderAmount));

            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        log.info("【创建订单】OrderId={}",orderMaster.getOrderId());
        orderMasterRepository.save(orderMaster);

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        /**
         * jpa findOne方法 springboot需要这样使用
         * 在查找取消订单的时候报错了 改用findById
         */


//        OrderMaster temp = new OrderMaster();
//        temp.setOrderId(orderId);
//
//        Example<OrderMaster> example = Example.of(temp);
//        Optional<OrderMaster> category1 = orderMasterRepository.findOne(example);
//
//
//        if (!category1.isPresent()) {
//            throw new SellException(ResultEnum.ORDERNOTEXIST);
//        }

        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);

        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDERNOTEXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERNOTEXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String orderId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(orderId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }


    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】订单状态不正确");
            throw new SellException(ResultEnum.ORDERSTATUSERROE);
        }
        //修订订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.info("【取消订单】更新失败");
            throw new SellException(ResultEnum.ORDERUPDATEFAIL);
        }

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SellException(ResultEnum.ORDERDETAILEMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不对，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDERSTATUSERROE);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.info("【完结订单】更新失败,ordermaster={}",updateResult);
            throw new SellException(ResultEnum.ORDERUPDATEFAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不对，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDERSTATUSERROE);
        }


        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】不是带支付状态，orderId={},PayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDERD_PAY_STATUS_ERROR);
        }

        orderDTO.setPayStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.info("【支付订单】更新失败,ordermaster={}",updateResult);
            throw new SellException(ResultEnum.ORDERUPDATEFAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
       Page<OrderMaster> orderMasterPage= orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
