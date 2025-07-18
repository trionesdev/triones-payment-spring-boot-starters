package com.trionesdev.payment.alipay.spring.boot.rest;

import com.trionesdev.payment.alipay.v3.modal.AlipayNotifyModel;

public interface AlipayNotifyCallback {
    void transactionNotify(AlipayNotifyModel model);
}
