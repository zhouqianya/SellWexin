package com.zhou.service.impl;

import com.zhou.dataobject.SellerInfo;
import com.zhou.repository.SellerInfoRepository;
import com.zhou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 13:19
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
