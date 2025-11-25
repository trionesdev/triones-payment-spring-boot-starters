package com.trionesdev.payment.wechatpay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "triones.payment.wechatpay")
@Data
public class WechatPayProperties {
    private Boolean enabled;
    private String appId;
    private String mchId;
    private String apiV3Key;
    private String privateKey;
    private String privateCert;
    private String privateKeyBase64;
    private String privateCertBase64;
    private String privateKeyPath;
    private String privateCertPath;
    private String transactionNotifyUrl;
    private String refundNotifyUrl;
    private String transferNotifyUrl;
    private Map<String, String> transactionNotifyUrls;
    private Map<String, String> refundNotifyUrls;
    private Map<String, String> transferNotifyUrls;
}
