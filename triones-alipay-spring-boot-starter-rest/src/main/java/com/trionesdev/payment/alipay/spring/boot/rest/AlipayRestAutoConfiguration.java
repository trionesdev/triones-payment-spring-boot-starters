package com.trionesdev.payment.alipay.spring.boot.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {AlipayResource.class})
public class AlipayRestAutoConfiguration {
}
