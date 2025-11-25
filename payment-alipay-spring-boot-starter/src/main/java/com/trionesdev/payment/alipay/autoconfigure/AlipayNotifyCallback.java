package com.trionesdev.payment.alipay.autoconfigure;

import com.trionesdev.payment.alipay.v3.modal.AlipayNotifyModel;

public interface AlipayNotifyCallback {
    void transactionNotify(AlipayNotifyModel model);
}
