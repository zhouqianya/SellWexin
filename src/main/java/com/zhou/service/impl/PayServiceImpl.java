package com.zhou.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.model.wxpay.WxPayApi;
import com.lly835.bestpay.model.wxpay.request.WxPayUnifiedorderRequest;
import com.lly835.bestpay.model.wxpay.response.WxPaySyncResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.XmlUtil;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.service.OrderService;
import com.zhou.service.PayService;
import com.zhou.util.HttpsClientRequestFactory;
import com.zhou.util.JsonUtil;
import com.zhou.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;
import sun.reflect.generics.tree.BaseType;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/23 08:54
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    BestPayServiceImpl bestPayService;

    @Autowired
    OrderService orderService;

    /**
     * 统一下单
     * response：
     * {nonce_str=PbXzm4xMBWrUapcN,
     * appid=wxd898fcb01713c658,
     * sign=441BA6A60769D13DAA718DCFEF585779,
     * trade_type=JSAPI, return_msg=OK,
     * result_code=SUCCESS, mch_id=1483469312,
     * return_code=SUCCESS,
     * prepay_id=wx251746084115726829ba982b1005245700}
     * <p>
     * 获取prepay_id 预付订单
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid("oTgZpwcTd3dzLaO4BZLZpxRs-DvY");
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】payRequest={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);

        log.info("【微信支付】payResponse={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1验证微信签名

        //2支付状态

        //3支付金额

        //4支付人 下单人和支付人
        //异步通知
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信异步通知】payResponese={}", JsonUtil.toJson(payResponse));

        //修改订单支付状态
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (null == orderDTO) {
            log.info("【微信支付异步通知】订单不存在,orderid={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDERNOTEXIST);
        }

        /**
         *不可以用 equals  compareTo
         * 0.1  0.10
         *
         */

        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信异步通知】数据库金额和支付金额不一致");
            throw new SellException(ResultEnum.WECHAR_NOTIFY_ERROR);
        }

        orderService.paid(orderDTO);


        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】refundRequest={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】refundResponse={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }


    public void testSDK() {

        WXPay wxpay = new WXPay(new WXPayConfig() {
            @Override
            public String getAppID() {
                return "wxd898fcb01713c658";
            }

            @Override
            public String getMchID() {
                return "1483469312";
            }

            @Override
            public String getKey() {
                return "098F6BCD4621D373CADE4E832627B4F6";
            }

            @Override
            public InputStream getCertStream() {
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(new File("/Users/xmly/code/src/main/resources/h5.p12"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return fileInputStream;
            }

            @Override
            public int getHttpConnectTimeoutMs() {
                return 1000;
            }

            @Override
            public int getHttpReadTimeoutMs() {
                return 1000;
            }
        });

        HashMap<String, String> postData = new HashMap<>();
        postData.put("appid", "wxd898fcb01713c658");
        postData.put("mch_id", "1483469312");
        postData.put("nonce_str", "b8TClwDZjviWcxL9");

        postData.put("sign", "F321F09D990DE557028D45CD31A687E8");

        postData.put("body", "微信点餐订单");

        postData.put("notify_url", "http://sell.com/sell/pay/notify");

        postData.put("openid", "oTgZpwcTd3dzLaO4BZLZpxRs-DvY");
        postData.put("out_trade_no", "1574222812238");
        postData.put("spbill_create_ip", "8.8.8.8");
        postData.put("total_fee", "3902");
        postData.put("trade_type", "JSAPI");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(postData);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("0.10".equals("0.1")); //fasle
        System.out.println("0.10".compareTo("0.1")); //1  第一个数比第二个大就返回1
        System.out.println(0.1000 - 0.1000000);

//        compareTo   0等于
    }

}
