package com.cshop.service.impl;


import com.cshop.util.HttpClient;
import com.cshop.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${appid}")
    private String appid;

    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;


    public Map createNative(Long orderId, Long money, String notifyUrl) {
        //1.创建参数
        Map<String, String> param = new HashMap();//创建参数
        param.put("appid", appid);//公众号
        param.put("mch_id", partner);//商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", "青橙");//商品描述
        param.put("out_trade_no", String.valueOf(orderId));//商户订单号
        param.put("total_fee", money + "");//总金额（分）
        param.put("spbill_create_ip", "127.0.0.1");//IP
        param.put("notify_url", notifyUrl);//暂时随便写一个
        param.put("trade_type", "NATIVE");//交易类型
        try {
            //2.发送请求
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println(xmlParam);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setHttps(true);
            client.setXmlParam(xmlParam);
            client.post();
            //3.获得结果
            String result = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            Map<String, String> map = new HashMap();
            map.put("code_url", resultMap.get("code_url"));//支付地址
            map.put("total_fee", money + "");//总金额
            map.put("out_trade_no", String.valueOf(orderId));//订单号
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }

    }

    @Override
    public Map queryPayStatus(Long orderId) {
        Map param = new HashMap();
        param.put("appid", appid);//公众账号ID
        param.put("mch_id", partner);//商户号
        param.put("out_trade_no", String.valueOf(orderId));//订单号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            HttpClient client = new HttpClient(url);
            client.setHttps(true);
            client.setXmlParam(xmlParam);
            client.post();
            String result = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            System.out.println(map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /****
     * 关闭订单实现
     * @param out_trade_no : 商户订单号
     * @return
     */
    @Override
    public Map closePay(String out_trade_no) {
        try {
            //1.创建参数
            Map<String, String> param = new HashMap();//创建参数
            param.put("appid", appid);//公众号
            param.put("mch_id", partner);//商户号
            param.put("out_trade_no", out_trade_no);//商户订单号
            param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串

            //2.发送请求
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println(xmlParam);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            client.setHttps(true);
            client.setXmlParam(xmlParam);
            client.post();
            //3.获得结果
            String result = client.getContent();
            System.out.println(result);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
