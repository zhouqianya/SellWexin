package com.zhou.controller;

import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/22 13:20
 */
@Controller
@Slf4j
@RequestMapping("/wechar")
public class  WechatController {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxMpService wxOpenService;
    /**
     *   【 前端在微信打开连接时候，直接请求/authorize 接口 获取到openid 才可以打开页面,微信会自动讲openid保存在缓存】
     */

    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {


        /**
         * url 重定向url 获取openid的方法
         * returnUrl 最后实际跳转的url
         */
        String url = "http://henhaoji.nat300.top/sell/wechar/userInfo";
        /**
         * 1.result 得到的就是拼接好的地址，获取code
         * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx43ba128d1342e4ba&redirect_uri=http://sell.com/sell/weixin/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
         */
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,result={}", redirectUrl);

        return "redirect:" + redirectUrl;
    }


    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String redirectUrl) {

        /**
         * 2.执行下面的请求，得到AccessToken、openid等信息
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx43ba128d1342e4ba&secret=9d89fa1185536a6e239ed13ece6d7b3b&code=071NsOS41t36eT1fF2R41nCSS41NsOSP&grant_type=authorization_code
         */
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.info("【微信网页授权】{}", e.getMessage());
            throw new SellException(ResultEnum.WECHAR_MP__ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        log.info("【微信网页授权】openid={}",openId);
        return "redirect:" + redirectUrl + "?openid=" + openId;
    }

    /**
     *  微信二维码登录  请求 /qrAuthorize   returnUrl= ... seller/login   自动跳到登录接口
     * @param returnUrl
     * @return
     */

    @RequestMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {


        /**
         * url 重定向url 获取openid的方法
         * returnUrl 最后实际跳转的url
         */
//        String url = "http://henhaoji.nat300.top/sell/wechar/qrUserInfo";
        String url =  "http://sell.springboot.cn/sell/wechat/qrUserInfo";

        /**
         * 1.result 得到的就是拼接好的地址，获取code
         * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx43ba128d1342e4ba&redirect_uri=http://sell.com/sell/weixin/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
         */
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,result={}", redirectUrl);

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                           @RequestParam("state") String redirectUrl) {

        /**
         * 2.执行下面的请求，得到AccessToken、openid等信息
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx43ba128d1342e4ba&secret=9d89fa1185536a6e239ed13ece6d7b3b&code=071NsOS41t36eT1fF2R41nCSS41NsOSP&grant_type=authorization_code
         */
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.info("【微信网页授权】{}", e.getMessage());
            throw new SellException(ResultEnum.WECHAR_MP__ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        log.info("【微信网页授权】openid={}",openId);
        return "redirect:" + redirectUrl + "?openid=" + openId;
    }
}
