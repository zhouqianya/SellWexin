package com.zhou.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/22 13:29
 * @ConfigurationProperties获取配置中心值
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private String mpAppId;
    private String mpAppsecret;
    /**
     * 以下参数是微信支付需要使用的
     * 商户号
     */
    private String mchId;
    /**
     * 商户秘钥
     */
    private String mchKey;
    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 商户支付异步通知
     */
    private String notifyUrl;

    private String openAppId;

    private String openAppSecret;
}
