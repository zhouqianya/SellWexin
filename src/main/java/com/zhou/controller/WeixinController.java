package com.zhou.controller;

import com.zhou.util.HttpsClientRequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/22 11:52
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public String user(@RequestParam String code,
                       @RequestParam String state) {

        log.info(code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx43ba128d1342e4ba&secret=9d89fa1185536a6e239ed13ece6d7b3b&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        String res = restTemplate.getForObject(url, String.class);

        log.info(res);
        return "ok";
    }
}
