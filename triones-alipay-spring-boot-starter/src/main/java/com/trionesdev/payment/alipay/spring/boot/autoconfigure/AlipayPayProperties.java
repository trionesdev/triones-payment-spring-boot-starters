package com.trionesdev.payment.alipay.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "triones.payment.alipay")
@Data
public class AlipayPayProperties {
    private Boolean enabled;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String appCertPath;
    private String alipayPublicCertPath;
    private String rootCertPath;
    private String appCertContent;
    private String alipayPublicCertContent;
    private String rootCertContent;

    private String transactionNotifyUrl;
    private String refundNotifyUrl;
    private String transferNotifyUrl;
    private Map<String, String> transactionNotifyUrls;
    private Map<String, String> refundNotifyUrls;
    private Map<String, String> transferNotifyUrls;
}
