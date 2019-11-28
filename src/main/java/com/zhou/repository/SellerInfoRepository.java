package com.zhou.repository;

import com.zhou.dataobject.ProductInfo;
import com.zhou.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 13:17
 */

public interface SellerInfoRepository extends JpaRepository<SellerInfo,Integer> {
    SellerInfo findByOpenid(String openid);
}
