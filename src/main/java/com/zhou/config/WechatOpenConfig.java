package com.zhou.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 13:31
 */
public class WechatOpenConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxMpService =new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return  wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){

        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage =new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        return  wxMpInMemoryConfigStorage;
    }
}
