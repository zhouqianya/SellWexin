package com.zhou.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/23 09:11
 */
@Component
public class WechatPayConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {


        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayConfig());

        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayConfig() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppsecret());
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
