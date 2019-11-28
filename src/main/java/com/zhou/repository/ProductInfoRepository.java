package com.zhou.repository;

import com.zhou.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program sell
 * @description: 商品
 * @author: 周茜
 * @create: 2019/11/14 19:03
 */

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**
     *命名必须规范
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
