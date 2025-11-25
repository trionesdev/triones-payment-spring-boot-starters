package com.trionesdev.payment.wechatpay.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotifyVO {
    private String code;
    private String message;
}
