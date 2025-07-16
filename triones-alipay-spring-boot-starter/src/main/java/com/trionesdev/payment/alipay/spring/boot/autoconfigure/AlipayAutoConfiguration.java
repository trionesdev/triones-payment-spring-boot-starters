package com.trionesdev.payment.alipay.spring.boot.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "triones.payment.alipay", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AlipayPayProperties.class})
public class AlipayAutoConfiguration {
    private final AlipayPayProperties alipayPayProperties;
}
