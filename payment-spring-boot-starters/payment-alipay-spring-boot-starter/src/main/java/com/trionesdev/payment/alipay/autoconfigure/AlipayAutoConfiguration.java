package com.trionesdev.payment.alipay.autoconfigure;

import com.trionesdev.payment.alipay.v3.Alipay;
import com.trionesdev.payment.alipay.v3.AlipayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "triones.payment.alipay", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AlipayPayProperties.class})
@Import(value = {AlipayResource.class})
public class AlipayAutoConfiguration {
    private final AlipayPayProperties alipayPayProperties;

    @Bean
    public Alipay alipay() {
        return new Alipay(alipayIntegrationConfig());
    }

    private AlipayConfig alipayIntegrationConfig() {
        AlipayConfig config = new AlipayConfig();
        config.setAppId(alipayPayProperties.getAppId());
        config.setPrivateKey(alipayPayProperties.getPrivateKey());
        config.setAlipayPublicKey(alipayPayProperties.getAlipayPublicKey());
        config.setNotifyUrl(alipayPayProperties.getNotifyUrl());
        return config;
    }
}
