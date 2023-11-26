package com.moensun.pay.wxpay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "triones.wxpay")
@Data
public class WxPayProperties {
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
}
