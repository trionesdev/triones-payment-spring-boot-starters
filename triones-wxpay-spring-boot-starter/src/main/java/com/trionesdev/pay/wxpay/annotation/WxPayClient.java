package com.trionesdev.pay.wxpay.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WxPayClient {
    String appId() default "";

    String mchId() default "";
    String apiV3Key() default "";
    String privateKey() default "";
    String privateCert() default "";
    String privateKeyBase64() default "";
    String privateCertBase64() default "";
    String privateKeyPath() default "";
    String privateCertPath() default "";
    String transactionNotifyUrl() default "";
    String refundNotifyUrl() default "";
}
