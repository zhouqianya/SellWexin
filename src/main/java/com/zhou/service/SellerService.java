package com.zhou.service;

import com.zhou.dataobject.SellerInfo;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 13:19
 */
public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);

}
