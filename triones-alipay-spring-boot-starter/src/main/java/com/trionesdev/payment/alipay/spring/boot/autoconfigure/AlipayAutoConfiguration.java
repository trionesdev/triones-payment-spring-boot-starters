package com.trionesdev.payment.alipay.spring.boot.autoconfigure;

import com.trionesdev.payment.alipay.v2.Alipay;
import com.trionesdev.payment.alipay.v2.AlipayIntegrationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "triones.payment.alipay", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AlipayPayProperties.class})
public class AlipayAutoConfiguration {
    private final AlipayPayProperties alipayPayProperties;

    @Bean
    public Alipay alipay(){
        return new Alipay(alipayIntegrationConfig());
    }

    private AlipayIntegrationConfig alipayIntegrationConfig(){
        AlipayIntegrationConfig config = new AlipayIntegrationConfig();
        config.setAppId(alipayPayProperties.getAppId());
        config.setPrivateKey(alipayPayProperties.getPrivateKey());
        config.setAlipayPublicKey(alipayPayProperties.getAlipayPublicKey());

        return config;
    }
}
