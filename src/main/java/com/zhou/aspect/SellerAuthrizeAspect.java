package com.zhou.aspect;

import com.zhou.constant.CookieConstant;
import com.zhou.constant.RedisConstant;
import com.zhou.exception.SellerAuthrizeException;
import com.zhou.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 17:27
 */
@Aspect
@Slf4j
@Component
public class SellerAuthrizeAspect {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * execution 包含
     * !execution  排除
     * Seller开头的类 里面的方法
     * com.zhou.controller.Seller*.*(..))
     */
    @Pointcut("execution(public  * com.zhou.controller.Seller*.*(..))" + "&& !execution(public  * com.zhou.controller.SellerUserController.*(..))")
    public void verift() {
    }

    @Before("verift()")
    public void doVerify() {
        //获取请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if (cookie ==null){
            log.warn("【登录校验】cookie没有查到token");
            throw  new SellerAuthrizeException();
        }

        //查redis token是否有效
        String tokenValue=stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】redis没有查到token");
            throw  new SellerAuthrizeException();
        }
    }

}
