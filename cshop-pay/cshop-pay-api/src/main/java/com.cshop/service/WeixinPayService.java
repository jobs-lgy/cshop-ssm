package com.cshop.service;

import java.util.Map;

/**
 * 微信支付接口
 */
public interface WeixinPayService {

    /**
     * 生成微信支付二维码
     *
     * @param orderId   订单号
     * @param money     金额(分)
     * @param notifyUrl 回调地址
     * @return
     */
    public Map createNative(Long orderId, Long money, String notifyUrl);


    /**
     * 查询支付状态
     *
     * @param orderId 订单号
     */
    public Map queryPayStatus(Long orderId);


    /***
     * 关闭订单方法
     * @param out_trade_no : 商户订单号
     */
    Map closePay(String out_trade_no);

}
