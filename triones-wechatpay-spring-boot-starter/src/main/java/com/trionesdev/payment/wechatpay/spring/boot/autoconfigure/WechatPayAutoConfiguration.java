package com.trionesdev.payment.wechatpay.spring.boot.autoconfigure;

import com.trionesdev.payment.wechatpay.v3.WechatPay;
import com.trionesdev.payment.wechatpay.v3.WechatPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "triones.payment.wechatpay", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {WechatPayProperties.class})
public class WechatPayAutoConfiguration {

    private final WechatPayProperties wxPayProperties;

    @Bean
    public WechatPay wxPay() {
        return new WechatPay(wxPayConfig());
    }

    private WechatPayConfig wxPayConfig() {
        String privateKey = wxPayProperties.getPrivateKey();
        String privateCert = wxPayProperties.getPrivateCert();
        if (StringUtils.isBlank(privateKey) && StringUtils.isNotBlank(wxPayProperties.getPrivateKeyBase64())) {
            privateKey = new String(Base64.getDecoder().decode(wxPayProperties.getPrivateKeyBase64()));
        }
        if (StringUtils.isBlank(privateCert) && StringUtils.isNotBlank(wxPayProperties.getPrivateCertBase64())) {
            privateCert = new String(Base64.getDecoder().decode(wxPayProperties.getPrivateCertBase64()));
        }
        return WechatPayConfig.builder()
                .appId(wxPayProperties.getAppId())
                .mchId(wxPayProperties.getMchId())
                .privateKey(privateKey)
                .privateCert(privateCert)
                .privateKeyPath(wxPayProperties.getPrivateKeyPath())
                .privateCertPath(wxPayProperties.getPrivateCertPath())
                .apiV3Key(wxPayProperties.getApiV3Key())
                .transactionNotifyUrl(wxPayProperties.getTransactionNotifyUrl())
                .refundNotifyUrl(wxPayProperties.getRefundNotifyUrl())
                .transactionNotifyUrls(wxPayProperties.getTransactionNotifyUrls())
                .refundNotifyUrls(wxPayProperties.getRefundNotifyUrls())
                .build();
    }

}
