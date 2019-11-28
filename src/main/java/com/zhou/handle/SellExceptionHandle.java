package com.zhou.handle;

import com.zhou.exception.SellerAuthrizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 17:42
 */
@ControllerAdvice
public class SellExceptionHandle {

    /**
     * 捕获 SellerAuthrizeException 异常
     */
    @ExceptionHandler(value = SellerAuthrizeException.class)
    public ModelAndView handleAuthrizeException() {
        //跳转二维码登录
//  return new ModelAndView("redirect:http://localhost:8080/sell/wechar/qrAuthorize?returnUrl=http://localhost:8080/sell/login?openid=aaa");
        return new ModelAndView("redirect:http://localhost:8080/sell/seller/login");

    }
}
