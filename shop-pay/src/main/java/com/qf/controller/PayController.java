package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qf.entity.Order;
import com.qf.feign.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-21
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    String appid = "2021000116682998";
    String prikey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCtPp6YcsFM2XzYGLEQ58rFe4fBPgUY/ahie0Frw6lK8YVHalDT8vwZ3AVgZ+6cvLVS6s7sidWh4wrlZ0XGWM7ZwPnCKPZT2Ct17PrFG1yx2MbIZndJTaqLdqSTD+JhRwNY/WkBWSyeq/aVk7FrQ3ib2Nrf39KWYrZ6IE4BRcj3MLfDsj8+omdZTvIQ1zWBdc3wE0xexudMYOhlKKe9NiTDYqVktxFpItNZo82OdlUOyEXEiLLdWO2FeSeIsNb/d38CJZkRzKL6m38WYf8LNmbCtsg1ZH3i+Nl+h/KzrUaktdYJ0J7cCpsjvtnniRa0VYK0gvqv7nluE3rj5bOAytLAgMBAAECggEAV5DrpgXSP7+5c52FTbuH7ROaHgn0MaTu3DZNJtuq0hlLcWYXudLB7GNAAgh/fCf6a89v/mRSWnQhAioPviPyCR/wy11MeA/n00+JZ9qdoKHX0ZtnO3Ls9oIVxqFyh603p/jp3mMGnVP4cCwWRfS0MpkxYBlgq+zlSyurNl6irBEH63xJVjtZ7OuMBTiMMicEv2BKyN6xmg0ju/LMWgjNjsHtU5jVoMBvciaafuEjNE8RmArdNPk/eQb21OuNLY4crZXbQGI46RINzi8DOpFk5VtUpZREJ6DTp6LJQNuSOvFvK1ECl2vtC+PbzqzhxlRRApGIgW2iQuzbYCIs7grXEQKBgQDW5Vgwf7PY57hdbFgMagHczrllYxqFeDQyFJJ7NYb5WpIFXT2ytFPqDjy4qTxhxAO3hK4TVDSlEUbsObFwQuVTgLdkClqEyJeW/lOOp2DlfRG74L5y1YGp7sg+9laBWP6uv2bBYngNcxRdCDVHVYqa6zOMAEwIPtck5V9JyMojNwKBgQCbtTa2m3+GwzGaeWs941mXCs840mpGNrjVSCs2KJL4BtfsgbpRGg4CAjKvayO6KgPzpYM/PjcHO87L0tlyk7fkE7oYveBbXlfNar+RDADdKc7ZlKEWdSTl4vRBP1ocF6oLz/GZSst4mSbMlU3UdESN3GZ+9zVjulvntB6F2y9qjQKBgDX2Z3veRYI9Re2nzEBpykfxXVrZVQqZEbpVsibgXSF2nd5c+KZMZBoNMzBHxxLhzvEhjzjpV0LwILgnEKiZE88xLlwfwXVAjdhryv2yiEF4c6CTIh/h/2p1vLEa2MQoXePxylLbcDwoXr+x1Pcxwtl+IHaSiIU1in8IXjJACopnAoGANJvqq5IoaWEEHnxNxrBNMeJeF4XIkIamtrFWoErfNVwikm9Wg1Z5SyZw96IMhTby9NyEVtbvgssrWOSZwcO1SyN7KiblahmbMvWRWjGjDRa2I/+62bqkx+OBwZXlHT7Fno4YxEFoNAwS9uavUEwkaYYBP37JFDQAH25VLcbb43UCgYEA0CAAb1twUPsdJQt8IL/5VbnH/3TV5JG5mHkmdbCnrIx56s+9ktAAQR7awpS22SgOHS7T/KJK7Y6k6zMRCjpmJlwV2mgyNZE/TvZrDPq30czFf2mBHrShfM+y/vL4HQTUFnXEsQYi6T98dbXft0jPrcLRpu20EVU8muYvF981V+8=";
    String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAke28z5EPzpBzHQ6k+M++zV7PUqYbTfX9P8cotlGtUJtnjRXywYAwUEpQJl0ndwQ6kkrEu+E6G9DUhwp+RThdLb71PX4isfRPsk5YKcwGvZ7nGrTgJKAf2pNafbzf47P4dWL3QAPFImWDxidCXDa3/X2CktchmVCXqYqEOK3EcMYHGtTwt3CDsUPRowFVBLrMdlDYmvt6tAT9Yr4IUts8xIBcr2xWnSLtHJJWTuXX0dRjmmNUGJKk4mqN8VEtH5L2Rd+0PpCTe4tQVSyUgICr+TsnJVVdeOIExg6Jb4UYXrJGfEaU7Qqxkc+IylUGiCagsYsQqY+jQvIl8TpKAqWMgQIDAQAB";
    String format = "json";
    String charset = "UTF-8";
    String sig = "RSA2";

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/alipay")
    public void alipay(HttpServletResponse response, String orderId, Integer userId) throws Exception {

        System.out.println("orderId:"+orderId);
        System.out.println("userId:"+userId);


        // 1.根据orderId查询order对象
        Order order = orderService.getOrderById(userId, orderId);

        // 1.创建支付的客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                // 支付宝网关，需要设置沙箱
                serverUrl,
                // 商家创建应用后生成的appid
                appid,
                // 商家的私钥
                prikey,
                // 参数返回格式
                format,
                // 编码
                charset,
                // 支付宝的公钥，从沙箱中可以看到
                pubKey,
                // 设置签名的算法
                sig
        );  //获得初始化的AlipayClient

        // 2.创建一个请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request

        // 设置同步跳转地址
        alipayRequest.setReturnUrl("http://localhost/shop-home");
        // 设置异步通知的地址 （支付通知商家扣款是否成功)
        alipayRequest.setNotifyUrl("http://hpq7kp.natappfree.cc/pay/updateOrderStatus"); //在公共参数中设置回跳和通知地址

        // 3.设置要传递给支付的参数
        // long orderId = System.currentTimeMillis();

        System.out.println("orderId:" + orderId);
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":" + orderId + "," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getTotalPrice()+ "," +
                "    \"subject\":\"2002-shop\"," +
                "    \"body\":\"2002-shop\"" +
                "  }");

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
//            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf8");
        response.getWriter().write(form); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
//    public void updateOrderStatus(String out_trade_no, String trade_no, String trade_status, String total_amount) throws Exception {
    public void updateOrderStatus(HttpServletRequest request) throws Exception {

//        System.out.println("订单号：" + out_trade_no);
//        System.out.println("支付宝交易号：" + trade_no);
//        System.out.println("支付状态：" + trade_status);
//        System.out.println("订单金额：" + total_amount);

        // 1.定义一个map用来接收支付宝传递过来的参数
        Map<String, String> params = new HashMap<String, String>();

        // 2.循环遍历添加到params
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        System.out.println("paras:"+params);
        // 验证
        boolean signVerified = AlipaySignature.rsaCheckV1(params, pubKey, charset, sig); //调用SDK验证签名
        if (signVerified) {
            System.out.println("当前请求是支付宝调用。。。。。");
            // 处理业务逻辑
            String orderId = params.get("out_trade_no");
            String trade_status = params.get("trade_status");

            if("TRADE_SUCCESS".equals(trade_status)){
                // 交易成功。。。
                // 时间+后四位+流水号
//                orderService.updateOrderStatus(orderId,"2"); // 1: 2: 3: 4:
                System.out.println("修改订单的状态的为已支付");
                orderService.updateOrderStatus(orderId,"2");
            }
//            test(out_trade_no);
        }else{
            System.out.println("接口是一个非正常的请求");
        }


    }

    // 调用支付宝的查询接口，判断订单支付状态
    @RequestMapping("/test")
    public void test(String oid) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl,
                appid,
                prikey,
                format,
                charset,
                pubKey,
                sig
        );

        // 创建一个查询的请求
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        request.setBizContent("{" + "\"out_trade_no\":" + oid + "}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
