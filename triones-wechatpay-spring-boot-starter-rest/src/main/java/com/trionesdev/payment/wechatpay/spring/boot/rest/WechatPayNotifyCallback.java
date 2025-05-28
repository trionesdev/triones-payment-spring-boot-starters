package com.trionesdev.payment.wechatpay.spring.boot.rest;

import com.trionesdev.payment.wechatpay.v3.model.notify.WechatPayRefoundNotifyParseResponse;
import com.trionesdev.payment.wechatpay.v3.model.notify.WechatPayTransactionNotifyParseResponse;
import com.trionesdev.payment.wechatpay.v3.operation.model.WechatPayTransferNotifyParseResponse;

public interface WechatPayNotifyCallback {
    /**
     * 付款回调
     * @param response
     */
    void transactionNotify(WechatPayTransactionNotifyParseResponse response);

    /**
     * 退款回调
     * @param response
     */
    void refundNotify(WechatPayRefoundNotifyParseResponse response);

    /**
     * 转账回调
     * @param response
     */
    void transferNotify(WechatPayTransferNotifyParseResponse response);
}
