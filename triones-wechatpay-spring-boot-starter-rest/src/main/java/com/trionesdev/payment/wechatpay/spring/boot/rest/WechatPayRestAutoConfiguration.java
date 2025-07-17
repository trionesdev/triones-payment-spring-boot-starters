package com.trionesdev.payment.wechatpay.spring.boot.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WechatPayResource.class)
public class WechatPayRestAutoConfiguration {
}
