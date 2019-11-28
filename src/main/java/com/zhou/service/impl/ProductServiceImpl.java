package com.zhou.service.impl;

import com.zhou.dataobject.ProductInfo;
import com.zhou.dto.CartDTO;
import com.zhou.enums.ProductStatusEnum;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.repository.ProductInfoRepository;
import com.zhou.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 12:40
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository repository;


    @Override
    public ProductInfo findOne(String productId) {

        /**
         * jpa findOne方法 springboot需要这样使用
         */
//
//        ProductInfo temp = new ProductInfo();
//        temp.setProductId(productId);
//
//        Example<ProductInfo> example = Example.of(temp);
//        Optional<ProductInfo> category1 = repository.findOne(example);
//
//        if (category1.isPresent()) {
//            return category1.get();
//        } else {
//            return null;
//        }

        Optional<ProductInfo> category1= repository.findById(productId);
        return category1.orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {

        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if (null == productInfo) {
                throw new SellException(ResultEnum.PRODUCTNOTEXIST);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if (null == productInfo) {
                throw new SellException(ResultEnum.PRODUCTNOTEXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCTSTOCKERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (null == productInfo) {
            throw new SellException(ResultEnum.PRODUCTNOTEXIST);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
            throw new SellException(ResultEnum.ORDERUPDATEFAIL);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);

    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (null == productInfo) {
            throw new SellException(ResultEnum.PRODUCTNOTEXIST);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
            throw new SellException(ResultEnum.ORDERUPDATEFAIL);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
