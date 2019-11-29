package com.zhou.handle;

import com.zhou.exception.SellException;
import com.zhou.exception.SellerAuthrizeException;
import com.zhou.util.ResultUtil;
import com.zhou.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
     * ResponseStatus 前端收到响应码不是200  不会重定向
     */
    @ExceptionHandler(value = SellerAuthrizeException.class)
//    @ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "认证失败") //返回状态码
    public ModelAndView handleAuthrizeException() {
        //跳转二维码登录
//  return new ModelAndView("redirect:http://localhost:8080/sell/wechar/qrAuthorize?returnUrl=http://localhost:8080/sell/login?openid=aaa");
        return new ModelAndView("redirect:http://localhost:8080/sell/seller/login?openid=aaa");

    }


    @ExceptionHandler(value = SellException.class)
    public ResultVO handleSellException(SellException e){
        return ResultUtil.error(e.getCode(),e.getMessage());
    }


}
